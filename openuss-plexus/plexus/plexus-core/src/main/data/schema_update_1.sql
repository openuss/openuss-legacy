/* 
 * Migration script for security refactoring. 
 * Consolidation of user preferences, contact, and profile to one user object
 * 
 * Be aware that ibexpert logging on security_user table is switched of before. 
 * Otherwise the script cannot drop the foreign key reference columns in the user table 
 * 
 */

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
ALTER TABLE SECURITY_USER ADD UNIQUE (EMAIL);