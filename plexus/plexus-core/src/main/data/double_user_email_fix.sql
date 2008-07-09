-- fix for double email problem (several users with non unique email adresses)

-----------------------------------------------------
-- delete non enabled users with non unique emails --
-----------------------------------------------------

-- remove newsletter email dependencies

delete from newsletter_subscriber where newsletter_subscriber.user_id in (
select 
    security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

-- remove discussion email dependencies

delete from discussion_forumwatch where discussion_forumwatch.user_id in (
select 
    security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

delete from discussion_topicwatch where discussion_topicwatch.user_id in (
select 
    security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);


-- remove personal data

update security_user
set security_user.deleted = 1
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.subscribe_discussion = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.subscribe_newsletter = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.email_public = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.address_public = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.telephone_public = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.portrait_public = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.image_public = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.profile_public = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.portrait =
(select security_user.portrait from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.matriculation =
(select security_user.matriculation from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.studies =
(select security_user.studies from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.account_expired = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.account_locked = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.credentials_expired = 0
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.last_login =
(select security_user.last_login from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.sms_email =
(select security_user.sms_email from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.profession =
(select security_user.profession from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.city =
(select security_user.city from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.country =
(select security_user.country from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.telephone =
(select security_user.telephone from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.postcode =
(select security_user.postcode from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.age_group =
(select security_user.age_group from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.address =
(select security_user.address from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.timezone =
(select security_user.timezone from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.theme =
(select security_user.theme from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.locale =
(select security_user.locale from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.image_id =
(select security_user.image_id from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.title =
(select security_user.title from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.first_name =
(select security_user.first_name from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.last_name =
(select security_user.last_name from security_user where security_user.id = -11)
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_user
set security_user.pwd_hash = 1
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

update security_authority
set security_authority.name = ((select sa2.name from security_authority sa2 where sa2.id = -11) || security_authority.id)
where security_authority.id in
(select sa1.id
 from security_authority sa1 inner join security_user on (security_user.id = sa1.id)
 where security_user.email in
 (SELECT userEmail
  FROM (SELECT email as userEmail, count(email) as total
        FROM security_user GROUP BY userEmail)
  WHERE total > 1)
 and security_user.enabled = 0);

update security_user
set security_user.email = (security_user.id ||'@openuss.de')
where security_user.id in (select security_authority.id
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in (SELECT userEmail
        FROM (SELECT  email as userEmail, count(email) as total
            FROM security_user
            GROUP BY userEmail) WHERE total > 1) and security_user.enabled = 0);

--------------------------------------------------------------------------
-- change email addresses of enabled users (manual notification needed) --
--------------------------------------------------------------------------

CREATE TABLE CHANGED_USERS(
    ID BIGINT NOT NULL,
    USERNAME VARCHAR(250) NOT NULL,
    EMAIL VARCHAR(250) NOT NULL,
    NEWEMAIL VARCHAR(250));

insert into changed_users
select 
    security_authority.id,
    security_authority.name,
    security_user.email,
    ''
from security_user
   inner join security_authority on (security_user.id = security_authority.id)
where security_user.email in
    (SELECT userEmail
     FROM (SELECT  email as userEmail, count(email) as total
           FROM security_user
           GROUP BY userEmail)
     WHERE total > 1);

update security_user
set security_user.email = ('123' || security_user.email)
where security_user.email in
(select security_user.email
 from security_user inner join security_authority on (security_user.id = security_authority.id)
 where security_user.email in
    (SELECT userEmail
     FROM (SELECT  email as userEmail, count(email) as total
           FROM security_user
           GROUP BY userEmail)
     WHERE total > 1));

ALTER TABLE SECURITY_USER ADD UNIQUE (EMAIL);

update changed_users
set changed_users.newemail =
(select security_user.email
 from security_user
 where security_user.id = changed_users.id);
