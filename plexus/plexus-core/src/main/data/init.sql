insert into SECURITY_AUTHORITY (ID) values (-1);
insert into SECURITY_AUTHORITY (ID) values (-2);
insert into SECURITY_AUTHORITY (ID) values (-4);
insert into SECURITY_GROUP (ID, NAME, LABEL, GROUP_TYPE) values (-1, 'ROLE_ANONYMOUS', 'ANONYMOUS', 1);
insert into SECURITY_GROUP (ID, NAME, LABEL, GROUP_TYPE) values (-2, 'ROLE_USER', 'USER', 1);
insert into SECURITY_GROUP (ID, NAME, LABEL, GROUP_TYPE) values (-4, 'ROLE_ADMIN', 'ADMINISTRATOR', 1);

insert into SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (0, null);
insert into SECURITY_PERMISSION (ID, MASK, ACL_OBJECT_IDENTITY_FK, RECIPIENT_FK) values (1, 32767, 0, -4);

insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (0, 'openuss.url', 'localhost:8080');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (1, 'repository.path', 'java.io.temp');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (2, 'mail.from.address', 'plexus@openuss-plexus.org');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (3, 'mail.from.name', 'OpenUSS');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (4, 'mail.host.name', 'localhost');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (5, 'mail.host.user', 'plexus@openuss-plexus.org');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (6, 'mail.host.password', 'plexus');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (7, 'openuss.server.url', 'http://localhost:8080/');
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (8, 'openuss.copyright', '(c) Copyright OpenUSS 2000 - 2007');

INSERT INTO SECURITY_AUTHORITY (ID) VALUES (-10);
INSERT INTO SECURITY_USER_CONTACT (ID, FIRST_NAME, LAST_NAME, TITLE, PROFESSION, ADDRESS, CITY, COUNTRY, TELEPHONE, POSTCODE, SMS_EMAIL) VALUES (-10, 'Admin', 'Administrator', '', '', 'Leonardo-Campus 3', 'Münster', NULL, '', '48149', '');
INSERT INTO SECURITY_USER_PREFERENCES (ID, LOCALE, THEME, TIMEZONE) VALUES (-10, 'de', 'plexus', 'Europe/Berlin');
INSERT INTO SECURITY_USER_PROFILE (ID, AGE_GROUP, MATRICULATION, STUDIES, IMAGE_FILE_ID, EMAIL_PUBLIC, ADDRESS_PUBLIC, TELEPHONE_PUBLIC, PORTRAIT_PUBLIC, IMAGE_PUBLIC, PROFILE_PUBLIC) VALUES (-10, NULL, NULL, NULL, NULL, 0, 0, 0, 0, 0, 0);
INSERT INTO SECURITY_USER (ID, USERNAME, PWD_HASH, EMAIL, ENABLED, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CREDENTIALS_EXPIRED, LAST_LOGIN, PREFERENCES_FK, CONTACT_FK, PROFILE_FK) VALUES (-10, 'admin', 'masterkey', 'plexus@openuss-plexus.com', 1, 0, 0, 0, '2007-07-12 13:27:26', -10, -10, -10);

INSERT INTO SECURITY_GROUP2AUTHORITY (MEMBERS_FK, GROUPS_FK) VALUES (-10, -4);
INSERT INTO SECURITY_GROUP2AUTHORITY (MEMBERS_FK, GROUPS_FK) VALUES (-10, -2);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (-10, 0);
