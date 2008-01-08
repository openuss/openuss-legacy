-- update database from below 2717 to 2718
alter table newsletter_newsletter alter column name type varchar(100);
alter table message_message alter column sender_name type varchar(250);
alter table message_message alter column subject type varchar(250);
alter table message_templateparameter alter column name type varchar(100);
alter table message_templateparameter alter column name type varchar(1024);
alter table message_recipient alter column email type varchar(250);
alter table message_recipient alter column sms type varchar(250);
