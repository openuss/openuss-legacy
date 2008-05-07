-- 
-- DB Schema migration script from OpenUSS Plexus Version 3.0RC1 to current 3.1 Version
--
-- author Ingo Düppe
-- author Sebastian Roekens
--
-- Be aware that ibexpert logging on security_user table is switched of before. 
-- Otherwise the script cannot drop the foreign key reference columns in the user table 
--

-- Migration script for security refactoring. 
-- Consolidation of user preferences, contact, and profile to one user object

ALTER TABLE SECURITY_USER
    ADD LAST_NAME        VARCHAR(100) NOT NULL,
    ADD FIRST_NAME           VARCHAR(100) NOT NULL,
    ADD TITLE                VARCHAR(100),
    ADD IMAGE_ID             BIGINT,
    ADD LOCALE               VARCHAR(10) NOT NULL,
    ADD THEME                VARCHAR(64) NOT NULL,
    ADD TIMEZONE             VARCHAR(20) NOT NULL,
    ADD ADDRESS              VARCHAR(100),
    ADD AGE_GROUP            VARCHAR(50),
    ADD POSTCODE             VARCHAR(50),
    ADD TELEPHONE            VARCHAR(100),
    ADD COUNTRY              VARCHAR(100),
    ADD CITY                 VARCHAR(100),
    ADD PROFESSION           VARCHAR(100),
    ADD SMS_EMAIL            VARCHAR(100),
    ADD STUDIES              VARCHAR(100),
    ADD MATRICULATION        VARCHAR(100),
    ADD PORTRAIT             BLOB SUB_TYPE 0 SEGMENT SIZE 80,
    ADD PROFILE_PUBLIC       SMALLINT NOT NULL,
    ADD IMAGE_PUBLIC         SMALLINT NOT NULL,
    ADD PORTRAIT_PUBLIC      SMALLINT NOT NULL,
    ADD TELEPHONE_PUBLIC     SMALLINT NOT NULL,
    ADD ADDRESS_PUBLIC       SMALLINT NOT NULL,
    ADD EMAIL_PUBLIC         SMALLINT NOT NULL;

UPDATE SECURITY_USER u
SET
    LOCALE = (SELECT LOCALE FROM security_user_preferences preferences WHERE preferences.id = u.preferences_fk),
    THEME = (SELECT THEME FROM security_user_preferences preferences WHERE preferences.id = u.preferences_fk),
    TIMEZONE = (SELECT TIMEZONE FROM security_user_preferences preferences WHERE preferences.id = u.preferences_fk),
    FIRST_NAME = (SELECT FIRST_NAME FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    LAST_NAME = (SELECT LAST_NAME FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    TITLE = (SELECT TITLE FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    PROFESSION = (SELECT PROFESSION FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    ADDRESS = (SELECT ADDRESS FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    CITY = (SELECT CITY  FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    COUNTRY = ( SELECT COUNTRY  FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    TELEPHONE = (SELECT TELEPHONE FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    POSTCODE = (SELECT POSTCODE FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    SMS_EMAIL = (SELECT SMS_EMAIL FROM security_user_contact contact WHERE contact.id = u.contact_fk),
    PORTRAIT = (SELECT PORTRAIT  FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    AGE_GROUP = (SELECT AGE_GROUP FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    MATRICULATION = (SELECT MATRICULATION FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    STUDIES = (SELECT STUDIES FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    IMAGE_ID = (SELECT IMAGE_FILE_ID FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    EMAIL_PUBLIC = (SELECT EMAIL_PUBLIC FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    ADDRESS_PUBLIC = (SELECT ADDRESS_PUBLIC FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    TELEPHONE_PUBLIC = (SELECT TELEPHONE_PUBLIC  FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    PORTRAIT_PUBLIC = (SELECT PORTRAIT_PUBLIC  FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    IMAGE_PUBLIC = (SELECT IMAGE_PUBLIC FROM security_user_profile profile WHERE profile.id = u.profile_fk),
    PROFILE_PUBLIC = (SELECT PROFILE_PUBLIC  FROM security_user_profile profile WHERE profile.id = u.profile_fk);


ALTER TABLE SECURITY_USER DROP CONSTRAINT SECURITY_USER_CONTACT_FKC;
ALTER TABLE SECURITY_USER DROP CONSTRAINT SECURITY_USER_PREFERENCES_FKC;
ALTER TABLE SECURITY_USER DROP CONSTRAINT SECURITY_USER_PROFILE_FKC;

ALTER TABLE SECURITY_USER DROP CONSTRAINT INTEG_242;
ALTER TABLE SECURITY_USER DROP CONSTRAINT INTEG_243;
ALTER TABLE SECURITY_USER DROP CONSTRAINT INTEG_244;

DROP TABLE SECURITY_USER_PROFILE;
DROP TABLE SECURITY_USER_CONTACT;
DROP TABLE SECURITY_USER_PREFERENCES;

ALTER TABLE SECURITY_USER DROP CONTACT_FK;
ALTER TABLE SECURITY_USER DROP PROFILE_FK;
ALTER TABLE SECURITY_USER DROP PREFERENCES_FK;

-- Refactoring of user.username and group.name to authority.name --

ALTER TABLE SECURITY_AUTHORITY ADD NAME VARCHAR(64);

UPDATE SECURITY_AUTHORITY a SET a.name = (SELECT username FROM security_user u WHERE a.id = u.id);
UPDATE SECURITY_AUTHORITY a SET a.name = (SELECT name FROM security_group g WHERE a.id = g.id) WHERE a.name is null;

DROP INDEX GROUPNAME;
ALTER TABLE SECURITY_GROUP DROP CONSTRAINT INTEG_212;
ALTER TABLE SECURITY_GROUP DROP NAME;
ALTER TABLE SECURITY_AUTHORITY ADD UNIQUE (NAME);

DROP INDEX USERNAME;
ALTER TABLE SECURITY_USER DROP CONSTRAINT INTEG_235;
ALTER TABLE SECURITY_USER DROP USERNAME;

-- ALTER TABLE SECURITY_USER ADD UNIQUE (EMAIL); // NEED A DATABASE CLEAN SCRIPT FIRST

-- Add LDAPRoles

INSERT INTO SECURITY_AUTHORITY (ID, NAME) VALUES (-6, 'ROLE_LDAPUSER');
INSERT INTO SECURITY_GROUP (ID, LABEL, GROUP_TYPE) VALUES (-6, 'LDAPUSER', 1);

-- 
-- MAIN MIGRATION PART                             
-- Adding of course-group table
--                        

CREATE TABLE COURSES2GROUPS (
    COURSES_FK BIGINT not null, 
    GROUPS_FK BIGINT not null, 
    primary key (COURSES_FK, GROUPS_FK)
);
ALTER TABLE COURSES2GROUPS ADD CONSTRAINT LECTURE_COURSE_GROUPS_FKC FOREIGN KEY (GROUPS_FK) REFERENCES SECURITY_GROUP;
ALTER TABLE COURSES2GROUPS ADD CONSTRAINT SECURITY_GROUP_COURSES_FKC FOREIGN KEY (COURSES_FK) REFERENCES LECTURE_COURSE;

-- adding groups for all courses --

create procedure create_groups
as
declare variable groupid bigint;
declare variable courseid bigint;
begin
    for select id from lecture_course into :courseid do
    begin
        execute statement 'SELECT NEXT VALUE FOR GLOBAL_SEQUENCE FROM RDB$DATABASE'
        into :groupid;

        INSERT INTO SECURITY_AUTHORITY (ID, NAME)
        VALUES (:groupId, ('GROUP_COURSE_'|| :courseid || '_PARTICIPANTS'));

        INSERT INTO SECURITY_GROUP (ID, LABEL, GROUP_TYPE, PWD, MEMBERSHIP_FK)
        VALUES (:groupid, 'autogroup_participant_label', 5, NULL, NULL);

        insert into courses2groups (courses_fk, groups_fk)
        values (:courseid, :groupid);
     end
end;
execute procedure create_groups;

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
        insert into security_permission select gen_id(GLOBAL_SEQUENCE, 1), 1040, :courseid, c2g.groups_fk
        from courses2groups c2g
        where c2g.courses_fk = :courseid;
     end
end;
execute procedure add_coursegroup_permissions;

drop procedure add_coursegroup_permissions;

drop procedure create_groups;

-- Migration of course_member to lecture_course_member

/* Create Table... */
CREATE TABLE LECTURE_COURSE_MEMBER(
    COURSE_ID BIGINT NOT NULL,
    USER_ID BIGINT NOT NULL,
    MEMBER_TYPE INTEGER NOT NULL);

ALTER TABLE LECTURE_COURSE_MEMBER ADD PRIMARY KEY (COURSE_ID, USER_ID);
ALTER TABLE LECTURE_COURSE_MEMBER ADD CONSTRAINT FK_COURSE_MEMBER_COURSE FOREIGN KEY (COURSE_ID) REFERENCES LECTURE_COURSE (ID);
ALTER TABLE LECTURE_COURSE_MEMBER ADD CONSTRAINT FK_COURSE_MEMBER_USER FOREIGN KEY (USER_ID) REFERENCES SECURITY_USER (ID);

-- INSERT old COURSE_MEMBERS into new table
-- if double entries take the highest membership of assist, participant, or aspirant.

INSERT INTO LECTURE_COURSE_MEMBER (COURSE_ID, USER_ID, MEMBER_TYPE)
SELECT  DISTINCT
  COURSE_FK course_id,
  USER_FK user_id,
  (SELECT min(MEMBER_TYPE)
   FROM course_member c
   WHERE c.course_fk = m.course_fk and c.user_fk = m.user_fk) member_type
FROM
  COURSE_MEMBER m;

ALTER TABLE COURSE_MEMBER DROP CONSTRAINT COURSE_MEMBER_USER_FKC;

ALTER TABLE COURSE_MEMBER DROP CONSTRAINT COURSE_MEMBER_COURSE_FKC;


DROP TABLE COURSE_MEMBER;

-- Migration of collaboration extensions

create table PAPERSUBMISSION_EXAM (ID BIGINT not null, DOMAIN_ID BIGINT not null, NAME VARCHAR(100) not null, DEADLINE TIMESTAMP not null, DESCRIPTION VARCHAR(32000), primary key (ID));
create table PAPERSUBMISSION_PAPER (ID BIGINT NOT NULL, DELIVER_DATE  TIMESTAMP NOT NULL, COMMENT BLOB SUB_TYPE 0 SEGMENT SIZE 80, EXAM_FK BIGINT, SENDER_FK BIGINT);
create table WIKI_WIKISITE (ID BIGINT not null, DOMAIN_ID BIGINT not null, NAME VARCHAR(100) not null, DELETED SMALLINT not null, READ_ONLY SMALLINT not null, primary key (ID));
create table WIKI_WIKISITEVERSION (ID BIGINT not null, TEXT BLOB not null, CREATION_DATE TIMESTAMP not null, NOTE VARCHAR(150), STABLE SMALLINT not null, WIKI_SITE_FK BIGINT, AUTHOR_FK BIGINT, primary key (ID));
create table WORKSPACE (ID BIGINT not null, DOMAIN_ID BIGINT not null, NAME VARCHAR(100) not null, primary key (ID));
create table USER2WORKSPACES (WORKSPACES_FK BIGINT not null, USER_FK BIGINT not null, primary key (WORKSPACES_FK, USER_FK));


alter table LECTURE_COURSE add COLLABORATION SMALLINT not null;
alter table LECTURE_COURSE add PAPERSUBMISSION SMALLINT not null;

alter table PAPERSUBMISSION_PAPER add constraint PAPERSUBMISSION_PAPER_SENDER_C foreign key (SENDER_FK) references SECURITY_USER;
alter table PAPERSUBMISSION_PAPER add constraint PAPERSUBMISSION_PAPER_EXAM_FKC foreign key (EXAM_FK) references PAPERSUBMISSION_EXAM;
alter table USER2WORKSPACES add constraint WORKSPACE_USER_FKC foreign key (USER_FK) references SECURITY_USER;
alter table USER2WORKSPACES add constraint SECURITY_USER_WORKSPACES_FKC foreign key (WORKSPACES_FK) references WORKSPACE;
alter table WIKI_WIKISITEVERSION add constraint WIKI_WIKISITEVERSION_AUTHOR_FC foreign key (AUTHOR_FK) references SECURITY_USER;
alter table WIKI_WIKISITEVERSION add constraint WIKI_WIKISITEVERSION_WIKI_SITC foreign key (WIKI_SITE_FK) references WIKI_WIKISITE;

-- Different changes for bugfixing

ALTER TABLE NEWSLETTER_NEWSLETTER ALTER COLUMN NAME TYPE VARCHAR(250);
ALTER TABLE MESSAGE_TEMPLATEPARAMETER ALTER COLUMN PARAM_VALUE TYPE VARCHAR(1024);

ALTER TABLE LECTURE_COURSE DROP WIKI_PAGE_FK;
ALTER TABLE WIKI_PAGE DROP CONSTRAINT INTEG_289;
DROP TABLE WIKI_PAGE;

-- ----------------------------------------------------
-- Autosubscription of newsletter and discussions
-- ----------------------------------------------------

ALTER TABLE SECURITY_USER ADD SUBSCRIBE_NEWSLETTER SMALLINT NOT NULL;
ALTER TABLE SECURITY_USER ADD SUBSCRIBE_DISCUSSION SMALLINT NOT NULL;
UPDATE SECURITY_USER SET SUBSCRIBE_NEWSLETTER = 0, SUBSCRIBE_DISCUSSION = 0;

-- ----------------------------------------------------
-- Migration for use of composite IDs
-- ----------------------------------------------------

-- MIGRATION FOR SUBSCRIBER TABLE FOR USE OF COMPOSITE IDS

CREATE TABLE SUBSCRIBER (
    NEWSLETTER_ID  BIGINT NOT NULL,
    USER_ID        BIGINT NOT NULL,
    BLOCKED        SMALLINT NOT NULL
);

INSERT INTO SUBSCRIBER (NEWSLETTER_ID, USER_ID, BLOCKED)
SELECT  DISTINCT
    NEWSLETTER_FK,
    USER_FK,
    0
FROM
  NEWSLETTER_SUBSCRIBER;

DROP TABLE NEWSLETTER_SUBSCRIBER;

CREATE TABLE NEWSLETTER_SUBSCRIBER (
    NEWSLETTER_ID  BIGINT NOT NULL,
    USER_ID        BIGINT NOT NULL,
    BLOCKED        SMALLINT NOT NULL
);

ALTER TABLE NEWSLETTER_SUBSCRIBER ADD PRIMARY KEY (NEWSLETTER_ID, USER_ID);

ALTER TABLE NEWSLETTER_SUBSCRIBER ADD CONSTRAINT FK123C9FAEC6817AFB FOREIGN KEY (USER_ID) REFERENCES SECURITY_USER (ID);
ALTER TABLE NEWSLETTER_SUBSCRIBER ADD CONSTRAINT NEWSLETTER_SUBSCRIBER_NEWSLETC FOREIGN KEY (NEWSLETTER_ID) REFERENCES NEWSLETTER_NEWSLETTER (ID);

INSERT INTO newsletter_subscriber
SELECT *
FROM SUBSCRIBER;

DROP TABLE SUBSCRIBER;


-- MIGRATION FOR BRAINCONTEST_ANSWER TABLE FOR USE OF COMPOSITE IDS

CREATE TABLE ANSWER (
    SOLVER_ID    BIGINT NOT NULL,
    CONTEST_ID   BIGINT NOT NULL,
    ANSWERED_AT  TIMESTAMP NOT NULL
);

-- keep earliest answer time
INSERT INTO ANSWER
select distinct
    solver_fk solverId,
    contest_fk contestId,
    (SELECT min(answered_at)
       FROM BRAINCONTEST_ANSWER ba
       WHERE a.solver_fk = ba.solver_fk and a.contest_fk = ba.contest_fk) answered_at
FROM
  BRAINCONTEST_ANSWER a;


DROP TABLE BRAINCONTEST_ANSWER;

CREATE TABLE BRAINCONTEST_ANSWER (
    SOLVER_ID    BIGINT NOT NULL,
    CONTEST_ID   BIGINT NOT NULL,
    ANSWERED_AT  TIMESTAMP NOT NULL
);

ALTER TABLE BRAINCONTEST_ANSWER ADD PRIMARY KEY (SOLVER_ID, CONTEST_ID);

ALTER TABLE BRAINCONTEST_ANSWER ADD CONSTRAINT BRAINCONTEST_ANSWER_CONTEST_IC FOREIGN KEY (CONTEST_ID) REFERENCES BRAINCONTEST (ID);
ALTER TABLE BRAINCONTEST_ANSWER ADD CONSTRAINT FK2A8CD9BF94E841B3 FOREIGN KEY (SOLVER_ID) REFERENCES SECURITY_USER (ID);

INSERT INTO braincontest_answer
select *
FROM ANSWER;

DROP TABLE ANSWER;




-- MIGRATION FOR DISCUSSION_FORUMWATCH FOR USE OF COMPOSITE IDS

-- remove double entries
create procedure delete_double_forumwatch
as
declare variable userId bigint;
declare variable forumId bigint;
begin
    for
        SELECT userId, forumId
        FROM (SELECT user_fk as userId, forum_fk as forumId, count(id) as total
            FROM DISCUSSION_FORUMWATCH
            GROUP BY user_fk, forum_fk) WHERE total > 1
    into
        :userId, :forumId do
    begin
        DELETE FROM DISCUSSION_FORUMWATCH WHERE user_fk = :userId and forum_fk = :forumId;

        INSERT INTO DISCUSSION_FORUMWATCH
            (ID, USER_FK, FORUM_FK)
            VALUES (gen_id(GLOBAL_SEQUENCE,1),:userId,:forumId);
     end
end;
execute procedure delete_double_forumwatch;
drop PROCEDURE delete_double_forumwatch;

-- copy values into new table structure

CREATE TABLE FORUMWATCH (
    USER_ID   BIGINT NOT NULL,
    FORUM_ID  BIGINT NOT NULL
);


INSERT INTO FORUMWATCH
select
    user_fk,
    forum_fk
FROM
  DISCUSSION_FORUMWATCH;

DROP TABLE DISCUSSION_FORUMWATCH;

CREATE TABLE DISCUSSION_FORUMWATCH (
    USER_ID   BIGINT NOT NULL,
    FORUM_ID  BIGINT NOT NULL
);

ALTER TABLE DISCUSSION_FORUMWATCH ADD PRIMARY KEY (USER_ID, FORUM_ID);

ALTER TABLE DISCUSSION_FORUMWATCH ADD CONSTRAINT FK1AC7B8C59F5AC341 FOREIGN KEY (FORUM_ID) REFERENCES DISCUSSION_FORUM (ID);
ALTER TABLE DISCUSSION_FORUMWATCH ADD CONSTRAINT FK1AC7B8C5C6817AFB FOREIGN KEY (USER_ID) REFERENCES SECURITY_USER (ID);

INSERT INTO DISCUSSION_FORUMWATCH
SELECT * FROM FORUMWATCH;

DROP TABLE FORUMWATCH;


-- MIGRATION FOR DISCUSSION_TOPICWATCH FOR USE OF COMPOSITE IDS

-- remove double entries

create procedure delete_double_topicwatch
as
declare variable userId bigint;
declare variable topicId bigint;
begin
    for
        SELECT userId, topicId
        FROM (SELECT user_fk as userId, topic_fk as topicId, count(id) as total
            FROM DISCUSSION_topicWATCH
            GROUP BY user_fk, topic_fk) WHERE total > 1
    into
        :userId, :topicId do
    begin
        DELETE FROM DISCUSSION_topicWATCH WHERE user_fk = :userId and topic_fk = :topicId;

        INSERT INTO DISCUSSION_topicWATCH
            (ID, USER_FK, topic_FK)
            VALUES (gen_id(GLOBAL_SEQUENCE,1),:userId,:topicId);
     end
end;
execute procedure delete_double_topicwatch;
drop PROCEDURE delete_double_topicwatch;

-- copy values into new table structure

CREATE TABLE TOPICWATCH (
    USER_ID   BIGINT NOT NULL,
    TOPIC_ID  BIGINT NOT NULL
);

INSERT INTO TOPICWATCH
select
    user_fk,
    topic_fk
FROM
  DISCUSSION_TOPICWATCH;

DROP TABLE DISCUSSION_TOPICWATCH;


CREATE TABLE DISCUSSION_TOPICWATCH (
    USER_ID   BIGINT NOT NULL,
    TOPIC_ID  BIGINT NOT NULL
);

ALTER TABLE DISCUSSION_TOPICWATCH ADD PRIMARY KEY (USER_ID, TOPIC_ID);

ALTER TABLE DISCUSSION_TOPICWATCH ADD CONSTRAINT FK1B2E5177C6817AFB FOREIGN KEY (USER_ID) REFERENCES SECURITY_USER (ID);
ALTER TABLE DISCUSSION_TOPICWATCH ADD CONSTRAINT FK1B2E5177E67E6981 FOREIGN KEY (TOPIC_ID) REFERENCES DISCUSSION_TOPIC (ID);

INSERT INTO DISCUSSION_TOPICWATCH
SELECT * FROM TOPICWATCH;

DROP TABLE TOPICWATCH;

-- MIGRATION FOR SECURITY_PERMISSION FOR USE OF COMPOSITE IDS


CREATE TABLE PERMISSION (
    ACLOBJECTIDENTITY_ID  BIGINT NOT NULL,
    RECIPIENT_ID          BIGINT NOT NULL,
    MASK                  INTEGER NOT NULL
);


INSERT INTO PERMISSION (ACLOBJECTIDENTITY_ID, RECIPIENT_ID, MASK)
SELECT  DISTINCT
  ACL_OBJECT_IDENTITY_FK objectId,
  RECIPIENT_FK recipientId,
  (SELECT max(MASK)
   FROM security_permission p1
   WHERE p1.ACL_OBJECT_IDENTITY_FK = p2.ACL_OBJECT_IDENTITY_FK and p1.RECIPIENT_FK = p2.RECIPIENT_FK) MASK
FROM
  security_permission p2;

DROP TABLE SECURITY_PERMISSION;

CREATE TABLE SECURITY_PERMISSION (
    ACLOBJECTIDENTITY_ID  BIGINT NOT NULL,
    RECIPIENT_ID          BIGINT NOT NULL,
    MASK                  INTEGER NOT NULL
);

ALTER TABLE SECURITY_PERMISSION ADD PRIMARY KEY (ACLOBJECTIDENTITY_ID, RECIPIENT_ID);

ALTER TABLE SECURITY_PERMISSION ADD CONSTRAINT FKAA8982ED86B0D43 FOREIGN KEY (RECIPIENT_ID) REFERENCES SECURITY_AUTHORITY (ID);
ALTER TABLE SECURITY_PERMISSION ADD CONSTRAINT SECURITY_PERMISSION_ACLOBJECTC FOREIGN KEY (ACLOBJECTIDENTITY_ID) REFERENCES SECURITY_OBJECT_IDENTITY (ID);

INSERT INTO SECURITY_PERMISSION
SELECT * FROM PERMISSION;

DROP TABLE PERMISSION;


-- migrate course membership handling

create procedure refactor_course_security
as
declare variable courseid bigint;
declare variable userid bigint;
declare variable desktopid bigint;
declare variable groupid bigint;
declare variable courseuserid bigint;
begin
    -- traverse all courses which are open
    for
        select course.id
        from lecture_course course
        where course.access_type=1
        into :courseid do
    begin
        -- everybody that has a link to current course is added as a member
        -- if he is not an assistant
        for
            select distinct(d.user_fk)
            from desktop_desktop d, desktop_desktop_course dc
            where d.id=dc.desktops_fk and dc.courses_fk=:courseid
            into :userid do
        begin
            delete from lecture_course_member lcm
                   where lcm.course_id = :courseid and lcm.user_id = :userid and lcm.member_type!=0;

            if (not exists(select lcm2.user_id
                     from lecture_course_member lcm2
                     where lcm2.course_id = :courseid and lcm2.user_id=:userid and lcm2.member_type=0))
            then
                    insert into lecture_course_member (course_id, user_id, member_type)
                    values (:courseid, :userid, 1);

        end

        -- every course member gets a link to that course
        for
            select distinct(lcm3.user_id)
            from lecture_course_member lcm3
            where lcm3.course_id=:courseid
            into :userid do
        begin
            select desk.id
            from desktop_desktop desk
            where desk.user_fk = :userid
            into :desktopid;
            delete from desktop_desktop_course ddc
                   where (ddc.desktops_fk=:desktopid and ddc.courses_fk=:courseid);
            insert into desktop_desktop_course (desktops_fk, courses_fk)
                   values (:desktopid, :courseid);
        end
        -- remove permission of Role.USER to course
        delete from security_permission perm
               where perm.aclobjectidentity_id = :courseid and perm.recipient_id = -2;
        -- get participants group
        select c2g.groups_fk
        from courses2groups c2g
        where c2g.courses_fk=:courseid
        into :groupid;
        -- add all course members as members of participant group
        for
            select lcm4.user_id
            from lecture_course_member lcm4
            where (lcm4.course_id = :courseid and lcm4.member_type=1)
            into :userid do
        begin
            insert into security_group2authority (members_fk, groups_fk)
                   values (:userid, :groupid);
        end
    end
end;

execute procedure refactor_course_security;

drop procedure refactor_course_security;


