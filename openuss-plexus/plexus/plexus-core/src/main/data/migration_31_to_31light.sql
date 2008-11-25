-- 
-- DB Schema migration script from OpenUSS Plexus Version 3.1 to current 3.1-light Version
--
-- author Sebastian Roekens
--

-- add / alter workaround triggers for security_group2authority table
SET SQL DIALECT 3;
SET NAMES NONE;
SET TERM ^ ;
CREATE OR ALTER TRIGGER DELETE_DOUBLE_G2A_ENTRIES FOR SECURITY_GROUP2AUTHORITY
ACTIVE BEFORE INSERT POSITION 0
AS
begin
  if (exists (select * from security_group2authority where members_fk = new.members_fk and groups_fk = new.groups_fk)) then
  begin
    delete from security_group2authority where members_fk = new.members_fk and groups_fk = new.groups_fk;
  end
end
^
SET TERM ; ^
SET SQL DIALECT 3;
SET NAMES NONE;
SET TERM ^ ;

CREATE OR ALTER TRIGGER WORKAROUND_AUTHORITY FOR SECURITY_GROUP2AUTHORITY
ACTIVE AFTER DELETE POSITION 0
AS
BEGIN
  if (NOT EXISTS (SELECT * FROM security_group2authority WHERE members_fk = old.members_fk and groups_fk = -2)) then
  begin
    insert into security_group2authority (members_fk, groups_fk) values (old.members_fk, -2);
  end
END
^
SET TERM ; ^

-- alter table discussion_forum
alter table discussion_forum add name varchar (100);
update discussion_forum set name = (select shortcut from lecture_course where lecture_course.id = discussion_forum.domain_identifier);

-- remove multiple entries from group2authority table;
    alter trigger workaround_authority inactive;
    alter trigger delete_double_g2a_entries inactive;
    -- create table with single entries
    create table security_g2abackup(
        members_fk bigint not null,
        groups_fk bigint not null
    );
    insert into security_g2abackup select distinct members_fk, groups_fk from security_group2authority;
    delete from security_group2authority;
    insert into security_group2authority select * from security_g2abackup;
    alter trigger workaround_authority active;
    alter trigger delete_double_g2a_entries active;