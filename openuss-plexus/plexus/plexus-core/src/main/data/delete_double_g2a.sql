-- backup g2a table
create table security_g2aback(
    members_fk bigint not null,
    groups_fk bigint not null
);
insert into security_g2aback select * from security_group2authority;

-- create table with multiple entries
create table security_g2abackup(
    members_fk bigint not null,
    groups_fk bigint not null
);

insert into security_g2abackup 
select a1, b1
from (select distinct members_fk as a1, groups_fk as b1 from security_group2authority)
where ((select count(*)
        from security_group2authority
        where security_group2authority.groups_fk = b1
        and security_group2authority.members_fk = a1 )
       > 1)
       
-- remove multiple entries from original table
create procedure delete_double_g2a
as
declare variable userId bigint;
declare variable groupId bigint;
begin
    for
        SELECT members_fk as userId, groups_fk as groupId
        FROM security_g2abackup
    into
        :userId, :groupId do
    begin
        DELETE FROM security_group2authority WHERE members_fk = :userId and groups_fk = :groupId;

        INSERT INTO security_group2authority
            (members_fk, groups_fk)
            VALUES (:userId,:groupId);
     end
end;
execute procedure delete_double_g2a;
drop PROCEDURE delete_double_g2a;

drop table security_g2abackup;

drop table security_g2aback;
