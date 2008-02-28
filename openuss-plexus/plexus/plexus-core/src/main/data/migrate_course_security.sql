----------------------------------------------------------------------------------
--                              MAIN MIGRATION PART                             --
----------------------------------------------------------------------------------

----------------------------------------------------------------------------------
--                          adding of course-group table                        --
----------------------------------------------------------------------------------

CREATE TABLE COURSES2GROUPS (
    COURSES_FK BIGINT not null, 
    GROUPS_FK BIGINT not null, 
    primary key (COURSES_FK, GROUPS_FK)
);
ALTER TABLE COURSES2GROUPS ADD CONSTRAINT LECTURE_COURSE_GROUPS_FKC FOREIGN KEY (GROUPS_FK) REFERENCES SECURITY_GROUP;
ALTER TABLE COURSES2GROUPS ADD CONSTRAINT SECURITY_GROUP_COURSES_FKC FOREIGN KEY (COURSES_FK) REFERENCES LECTURE_COURSE;

-- adding groups for all courses --

alter table SECURITY_GROUP drop constraint SECURITY_GROUPIFKC;

create procedure create_groups
as
declare variable groupid bigint;
declare variable courseid bigint;
begin
    for select id from lecture_course into :courseid do
    begin
        execute statement 'SELECT NEXT VALUE FOR GLOBAL_SEQUENCE FROM RDB$DATABASE'
        into :groupid;

        INSERT INTO SECURITY_GROUP (ID, NAME, LABEL, GROUP_TYPE, PWD, MEMBERSHIP_FK)
        VALUES (:groupid, ('GROUP_COURSE_'|| :courseid || '_PARTICIPANTS'), 'autogroup_participant_label', 5, NULL, NULL);

        insert into courses2groups (courses_fk, groups_fk)
        values (:courseid, :groupid);
     end
end;
execute procedure create_groups;


INSERT INTO SECURITY_AUTHORITY (ID)
select id
FROM security_group
where security_group.group_type=5;

alter table SECURITY_GROUP add constraint SECURITY_GROUPIFKC foreign key (ID) references SECURITY_AUTHORITY;

----------------------------------------------------------------------------------
-- move all read permissions to course to read permission for new created group --
----------------------------------------------------------------------------------

-- add all members of course to new created group --

insert into security_group2authority (members_fk, groups_fk)
select cm.user_fk, c2g.groups_fk
from course_member cm, courses2groups c2g
where cm.member_type = 1 and cm.course_fk = c2g.courses_fk;

-- delete all read permissions to course --

delete from security_permission
where security_permission.mask = 1040 and security_permission.acl_object_identity_fk in (select id from lecture_course);

-- add read permission to course to group --

create procedure add_coursegroup_permissions
as
declare variable courseid bigint;
declare variable permissionid bigint;
begin
    for select id from lecture_course into :courseid do
    begin
        execute statement 'SELECT NEXT VALUE FOR GLOBAL_SEQUENCE FROM RDB$DATABASE'
        into :permissionid;

        insert into security_permission select :permissionid, 1040, :courseid, c2g.groups_fk
        from courses2groups c2g
        where c2g.courses_fk = :courseid;
     end
end;
execute procedure add_coursegroup_permissions;

drop procedure add_coursegroup_permissions;

drop procedure create_groups;


