INSERT INTO SECURITY_AUTHORITY (ID, NAME) VALUES (-1, 'ROLE_ANONYMOUS');
INSERT INTO SECURITY_AUTHORITY (ID, NAME) VALUES (-2, 'ROLE_USER');
INSERT INTO SECURITY_AUTHORITY (ID, NAME) VALUES (-4, 'ROLE_ADMIN');
INSERT INTO SECURITY_GROUP (ID, LABEL, GROUP_TYPE) VALUES (-1, 'ANONYMOUS', 1);
INSERT INTO SECURITY_GROUP (ID, LABEL, GROUP_TYPE) VALUES (-2, 'USER', 1);
INSERT INTO SECURITY_GROUP (ID, LABEL, GROUP_TYPE) VALUES (-4, 'ADMINISTRATOR', 1);

INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (0, null);
INSERT INTO SECURITY_PERMISSION (ID, MASK, ACL_OBJECT_IDENTITY_FK, RECIPIENT_FK) VALUES (1, 32767, 0, -4);

INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (1, 'repository.path', 'java.io.temp');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (2, 'mail.from.address', 'plexus@openuss-plexus.org');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (3, 'mail.from.name', 'OpenUSS');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (4, 'mail.host.name', 'localhost');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (5, 'mail.host.user', 'plexus@openuss-plexus.org');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (6, 'mail.host.password', 'plexus');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (7, 'openuss.server.url', 'http://localhost:8080/plexus-web');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (8, 'openuss.copyright', '(c) Copyright OpenUSS 2000 - 2007');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (9, 'documentation.url', 'http://localhost:8080/plexus-web/views/secured/course/main.faces?&course=163355');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (10, 'support.url', 'http://localhost:8080/plexus-web/views/secured/course/main.faces?&course=163355');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (11, 'bugtracking.url', 'http://teamopenuss.uni-muenster.de/jira');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (12, 'impressum.text', '<a href="http://www.wi.uni-muenster.de/aw">OpenUSS wird Lehrstuhl f�r WI und Controlling entwickelt</a>');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (13, 'provider.url', 'http://openuss.sourceforge.net');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (14, 'getting.started', '');
INSERT INTO SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) VALUES (15, 'mail.smtp.auth', 'true');


INSERT INTO SECURITY_AUTHORITY (ID, NAME) VALUES (-10, 'admin');
INSERT INTO SECURITY_USER (ID, PWD_HASH, EMAIL, ENABLED, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CREDENTIALS_EXPIRED, LAST_LOGIN, LOCALE, THEME, TIMEZONE, AGE_GROUP, MATRICULATION, STUDIES, IMAGE_ID, EMAIL_PUBLIC, ADDRESS_PUBLIC, TELEPHONE_PUBLIC, PORTRAIT_PUBLIC, IMAGE_PUBLIC, PROFILE_PUBLIC, PORTRAIT, FIRST_NAME, LAST_NAME, TITLE, PROFESSION, ADDRESS, CITY, COUNTRY, TELEPHONE, POSTCODE, SMS_EMAIL , SUBSCRIBE_NEWSLETTER, SUBSCRIBE_DISCUSSION) VALUES (-10, 'LjMlrJI4ae9Jvdz2mKs0DA==', 'unknown@openuss-plexus.com', 1, 0, 0, 0, '2007-07-12 13:27:26', 'de', 'plexus', 'Europe/Berlin', NULL, NULL, NULL, NULL, 0, 0, 0, 0, 0, 0,'', 'Admin', 'Administrator', '', '', 'Leonardo-Campus 3', 'M�nster', NULL, '', '48149', '', 0 ,0);

INSERT INTO SECURITY_GROUP2AUTHORITY (MEMBERS_FK, GROUPS_FK) VALUES (-10, -4);
INSERT INTO SECURITY_GROUP2AUTHORITY (MEMBERS_FK, GROUPS_FK) VALUES (-10, -2);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (-10, 0);

INSERT INTO SECURITY_AUTHORITY (ID, NAME) VALUES (-11, 'UNKNOWN');
INSERT INTO SECURITY_USER (ID, PWD_HASH, EMAIL, ENABLED, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CREDENTIALS_EXPIRED, LAST_LOGIN, LOCALE, THEME, TIMEZONE, AGE_GROUP, MATRICULATION, STUDIES, IMAGE_ID, EMAIL_PUBLIC, ADDRESS_PUBLIC, TELEPHONE_PUBLIC, PORTRAIT_PUBLIC, IMAGE_PUBLIC, PROFILE_PUBLIC, PORTRAIT, FIRST_NAME, LAST_NAME, TITLE, PROFESSION, ADDRESS, CITY, COUNTRY, TELEPHONE, POSTCODE, SMS_EMAIL, SUBSCRIBE_NEWSLETTER, SUBSCRIBE_DISCUSSION) VALUES (-11, 'JVTGVQDCb//31Je2jKDaqg==', 'plexus@openuss-plexus.com', 0, 0, 1, 0, '2007-07-12 13:27:26', 'de', 'plexus', 'Europe/Berlin', NULL, NULL, NULL, NULL, 0, 0, 0, 0, 0, 0, '', 'UNKNOWN', 'UNKNOWN OR DELETED', '', '', 'N/A', 'N/A', NULL, '', 'N/A', '', 0, 0);

INSERT INTO SECURITY_GROUP2AUTHORITY (MEMBERS_FK, GROUPS_FK) VALUES (-11, -2);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (-11, 0);

INSERT INTO SECURITY_MEMBERSHIP (ID) VALUES (1);
INSERT INTO SECURITY_MEMBERSHIP (ID) VALUES (2);
INSERT INTO SECURITY_MEMBERSHIP (ID) VALUES (3);
INSERT INTO SECURITY_MEMBERSHIP (ID) VALUES (4);
INSERT INTO SECURITY_MEMBERSHIP (ID) VALUES (5);

INSERT INTO SECURITY_MEMBER_MEMBERSHIP (MEMBERSHIP_MEMBERS_FK, MEMBERS_FK) VALUES (1, -10);
INSERT INTO SECURITY_MEMBER_MEMBERSHIP (MEMBERSHIP_MEMBERS_FK, MEMBERS_FK) VALUES (2, -10);
INSERT INTO SECURITY_MEMBER_MEMBERSHIP (MEMBERSHIP_MEMBERS_FK, MEMBERS_FK) VALUES (3, -10);
INSERT INTO SECURITY_MEMBER_MEMBERSHIP (MEMBERSHIP_MEMBERS_FK, MEMBERS_FK) VALUES (4, -10);
INSERT INTO SECURITY_MEMBER_MEMBERSHIP (MEMBERSHIP_MEMBERS_FK, MEMBERS_FK) VALUES (5, -10);

INSERT INTO LECTURE_ORGANISATION (ID, NAME, SHORT_NAME, SHORTCUT, DESCRIPTION, OWNER_NAME, ADDRESS, POSTCODE, CITY, COUNTRY, TELEPHONE, TELEFAX, WEBSITE, EMAIL, LOCALE, THEME, IMAGE_ID, ENABLED, MEMBERSHIP_FK) VALUES (11, 'OpenUSS University', 'OpenUSS University', 'University', '', 'OpenUSS', 'Leonardo-Campus 3', '48149', 'M�nster', '48143 M�nster', '', '', 'www.openuss.de', 'openuss@e-learning.uni-muenster.de', 'de', 'plexus', NULL, 1, 1);
INSERT INTO LECTURE_ORGANISATION (ID, NAME, SHORT_NAME, SHORTCUT, DESCRIPTION, OWNER_NAME, ADDRESS, POSTCODE, CITY, COUNTRY, TELEPHONE, TELEFAX, WEBSITE, EMAIL, LOCALE, THEME, IMAGE_ID, ENABLED, MEMBERSHIP_FK) VALUES (12, 'E-Learning', 'E-Learning', 'E-Learning', '', 'OpenUSS', 'Leonardo-Campus 3', '48149', 'M�nster', '48143 M�nster', '', '', 'www.openuss.de', 'openuss@e-learning.uni-muenster.de', 'de', 'plexus', NULL, 1, 2);
INSERT INTO LECTURE_ORGANISATION (ID, NAME, SHORT_NAME, SHORTCUT, DESCRIPTION, OWNER_NAME, ADDRESS, POSTCODE, CITY, COUNTRY, TELEPHONE, TELEFAX, WEBSITE, EMAIL, LOCALE, THEME, IMAGE_ID, ENABLED, MEMBERSHIP_FK) VALUES (13, 'Sandbox', 'Sandbox', 'Sandbox', '<b>Welcome to OpenUSS Sandbox</b>', 'OpenUSS', '', '', '', '', '', '', '', '', 'de', 'none', NULL, 1, 3);
INSERT INTO LECTURE_ORGANISATION (ID, NAME, SHORT_NAME, SHORTCUT, DESCRIPTION, OWNER_NAME, ADDRESS, POSTCODE, CITY, COUNTRY, TELEPHONE, TELEFAX, WEBSITE, EMAIL, LOCALE, THEME, IMAGE_ID, ENABLED, MEMBERSHIP_FK) VALUES (14, 'Community', 'Community', 'Community', '', 'OpenUSS', '', '', '', '', '', '', '', '', 'de', 'plexus', NULL, 1, 4);
INSERT INTO LECTURE_ORGANISATION (ID, NAME, SHORT_NAME, SHORTCUT, DESCRIPTION, OWNER_NAME, ADDRESS, POSTCODE, CITY, COUNTRY, TELEPHONE, TELEFAX, WEBSITE, EMAIL, LOCALE, THEME, IMAGE_ID, ENABLED, MEMBERSHIP_FK) VALUES (15, 'Support', 'Support', 'Support', '', 'OpenUSS Team', 'Leonardo-Campus 3', '48149', 'M�nster', 'Deutschland', '', '', '', 'openuss@e-learning.uni-muenster.de', 'de', 'plexus', NULL, 1, 5);

INSERT INTO LECTURE_UNIVERSITY (ID, UNIVERSITY_TYPE) VALUES (11, 0);

INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (21, 'Permanentes Angebot', '', '1970-01-01 00:00:00', '2050-12-31 00:00:00', 1, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (22, 'SS 00', '', '2000-04-01 00:00:00', '2000-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (23, 'WS 00/01', '', '2000-10-01 00:00:00', '2001-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (24, 'SS 01', '', '2001-04-01 00:00:00', '2001-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (25, 'WS 01/02', '', '2001-10-01 00:00:00', '2002-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (26, 'SS 02', '', '2002-04-01 00:00:00', '2002-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (27, 'WS 02/03', '', '2002-10-01 00:00:00', '2003-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (28, 'SS 03', '', '2003-04-01 00:00:00', '2003-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (29, 'WS 03/04', '', '2003-10-01 00:00:00', '2004-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (30, 'SS 04', '', '2004-04-01 00:00:00', '2004-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (31, 'WS 04/05', '', '2004-10-01 00:00:00', '2005-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (32, 'SS 05', '', '2005-04-01 00:00:00', '2005-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (33, 'WS 05/06', '', '2005-10-01 00:00:00', '2006-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (34, 'SS 06', '', '2006-04-01 00:00:00', '2006-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (35, 'WS 06/07', '', '2006-10-01 00:00:00', '2007-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (36, 'SS 07', '', '2007-04-01 00:00:00', '2007-09-30 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (37, 'WS 07/08', '', '2007-10-01 00:00:00', '2008-03-31 00:00:00', 0, 11);
INSERT INTO LECTURE_PERIOD (ID, NAME, DESCRIPTION, STARTDATE, ENDDATE, DEFAULT_PERIOD, UNIVERSITY_FK) VALUES (38, 'SS 08', '', '2008-04-01 00:00:00', '2008-09-30 00:00:00', 0, 11);

INSERT INTO LECTURE_DEPARTMENT (ID, DEPARTMENT_TYPE, DEFAULT_DEPARTMENT, UNIVERSITY_FK) VALUES (12, 1, 1, 11);
INSERT INTO LECTURE_DEPARTMENT (ID, DEPARTMENT_TYPE, DEFAULT_DEPARTMENT, UNIVERSITY_FK) VALUES (13, 1, 0, 11);
INSERT INTO LECTURE_DEPARTMENT (ID, DEPARTMENT_TYPE, DEFAULT_DEPARTMENT, UNIVERSITY_FK) VALUES (14, 0, 0, 11);

INSERT INTO LECTURE_INSTITUTE (ID, DEPARTMENT_FK) VALUES (15, 14);

INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (11, 0);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (12, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (13, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (14, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (15, 14);

INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (21, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (22, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (23, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (24, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (25, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (26, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (27, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (28, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (29, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (30, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (31, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (32, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (33, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (34, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (35, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (36, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (37, 11);
INSERT INTO SECURITY_OBJECT_IDENTITY (ID, PARENT_FK) VALUES (38, 11);

SET GENERATOR GLOBAL_SEQUENCE TO 1000;
