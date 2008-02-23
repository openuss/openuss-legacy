CREATE TABLE TEMP_MEMBERS (
    MEMBERSHIPID  BIGINT,
    USERID        BIGINT
);
INSERT INTO TEMP_MEMBERS (MEMBERSHIPID, USERID) SELECT DISTINCT * FROM security_member_membership;
DELETE FROM security_member_membership;
INSERT INTO security_member_membership SELECT * FROM temp_members;
DROP TABLE TEMP_MEMBERS;

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

UPDATE SECURITY_USER
SET
    LOCALE = (SELECT LOCALE FROM security_user_preferences WHERE security_user_preferences.id = SECURITY_USER.preferences_fk),
    THEME = (SELECT THEME FROM security_user_preferences WHERE security_user_preferences.id = SECURITY_USER.preferences_fk),
    TIMEZONE = (SELECT TIMEZONE FROM security_user_preferences WHERE security_user_preferences.id = SECURITY_USER.preferences_fk),
    FIRST_NAME = (SELECT FIRST_NAME FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    LAST_NAME = (SELECT LAST_NAME FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    TITLE = (SELECT TITLE FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    PROFESSION = (SELECT PROFESSION FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    ADDRESS = (SELECT ADDRESS FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    CITY = (SELECT CITY  FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    COUNTRY = ( SELECT COUNTRY  FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    TELEPHONE = (SELECT TELEPHONE FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    POSTCODE = (SELECT POSTCODE FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    SMS_EMAIL = (SELECT SMS_EMAIL FROM security_user_contact WHERE security_user_contact.id = SECURITY_USER.contact_fk),
    PORTRAIT = (SELECT PORTRAIT  FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    AGE_GROUP = (SELECT AGE_GROUP FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    MATRICULATION = (SELECT MATRICULATION FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    STUDIES = (SELECT STUDIES FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    IMAGE_ID = (SELECT IMAGE_FILE_ID FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    EMAIL_PUBLIC = (SELECT EMAIL_PUBLIC FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    ADDRESS_PUBLIC = (SELECT ADDRESS_PUBLIC FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    TELEPHONE_PUBLIC = (SELECT TELEPHONE_PUBLIC  FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    PORTRAIT_PUBLIC = (SELECT PORTRAIT_PUBLIC  FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    IMAGE_PUBLIC = (SELECT IMAGE_PUBLIC FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk),
    PROFILE_PUBLIC = (SELECT PROFILE_PUBLIC  FROM security_user_profile WHERE security_user_profile.id = SECURITY_USER.profile_fk);

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


CREATE TABLE COURSES2GROUPS (
    COURSES_FK BIGINT not null, 
    GROUPS_FK BIGINT not null, 
    primary key (COURSES_FK, GROUPS_FK)
);
ALTER TABLE COURSES2GROUPS ADD CONSTRAINT LECTURE_COURSE_GROUPS_FKC FOREIGN KEY (GROUPS_FK) REFERENCES SECURITY_GROUP;
ALTER TABLE COURSES2GROUPS ADD CONSTRAINT SECURITY_GROUP_COURSES_FKC FOREIGN KEY (COURSES_FK) REFERENCES LECTURE_COURSE;
