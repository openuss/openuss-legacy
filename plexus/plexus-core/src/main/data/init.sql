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
insert into SYSTEM_PROPERTY (ID, PROP_NAME, PROP_VALUE) values (7, 'openuss.server.url', 'http://localhost:8080/openuss-plexus/');