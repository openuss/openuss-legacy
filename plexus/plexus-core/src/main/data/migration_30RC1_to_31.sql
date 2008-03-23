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

-- Migration of collaboration extensions

create table PAPERSUBMISSION_EXAM (ID BIGINT not null, DOMAIN_ID BIGINT not null, NAME VARCHAR(100) not null, DEADLINE TIMESTAMP not null, DESCRIPTION VARCHAR(32000), primary key (ID));
create table PAPERSUBMISSION_PAPER (ID BIGINT not null, DELIVER_DATE TIMESTAMP not null, EXAM_FK BIGINT, SENDER_FK BIGINT, primary key (ID));
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

