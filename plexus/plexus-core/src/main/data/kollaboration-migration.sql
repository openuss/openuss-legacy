create table PAPERSUBMISSION_EXAM (ID BIGINT not null, DOMAIN_ID BIGINT not null, NAME VARCHAR(100) not null, DEADLINE TIMESTAMP not null, DESCRIPTION VARCHAR(32000), primary key (ID));
create table PAPERSUBMISSION_PAPER (ID BIGINT not null, DELIVER_DATE TIMESTAMP not null, EXAM_FK BIGINT, SENDER_FK BIGINT, primary key (ID));
create table WIKI_WIKISITE (ID BIGINT not null, DOMAIN_ID BIGINT not null, NAME VARCHAR(100) not null, DELETED SMALLINT not null, READ_ONLY SMALLINT not null, primary key (ID));
create table WIKI_WIKISITEVERSION (ID BIGINT not null, TEXT BLOB not null, CREATION_DATE TIMESTAMP not null, NOTE VARCHAR(150), STABLE SMALLINT not null, WIKI_SITE_FK BIGINT, AUTHOR_FK BIGINT, primary key (ID));
create table WORKSPACE (ID BIGINT not null, DOMAIN_ID BIGINT not null, NAME VARCHAR(100) not null, primary key (ID));
create table USER2WORKSPACES (WORKSPACES_FK BIGINT not null, USER_FK BIGINT not null, primary key (WORKSPACES_FK, USER_FK));


alter table LECTURE_COURSE add COLLABORATION SMALLINT not null;
alter table LECTURE_COURSE add PAPERSUBMISSION SMALLINT not null;
alter table LECTURE_COURSE add WIKI SMALLINT not null;

alter table PAPERSUBMISSION_PAPER add constraint PAPERSUBMISSION_PAPER_SENDER_C foreign key (SENDER_FK) references SECURITY_USER;
alter table PAPERSUBMISSION_PAPER add constraint PAPERSUBMISSION_PAPER_EXAM_FKC foreign key (EXAM_FK) references PAPERSUBMISSION_EXAM;
alter table USER2WORKSPACES add constraint WORKSPACE_USER_FKC foreign key (USER_FK) references SECURITY_USER;
alter table USER2WORKSPACES add constraint SECURITY_USER_WORKSPACES_FKC foreign key (WORKSPACES_FK) references WORKSPACE;
alter table WIKI_WIKISITEVERSION add constraint WIKI_WIKISITEVERSION_AUTHOR_FC foreign key (AUTHOR_FK) references SECURITY_USER;
alter table WIKI_WIKISITEVERSION add constraint WIKI_WIKISITEVERSION_WIKI_SITC foreign key (WIKI_SITE_FK) references WIKI_WIKISITE;