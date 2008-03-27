drop PROCEDURE delete_double_forumwatch;
create procedure delete_double_forumwatch
as
declare variable userId bigint;
declare variable forumId bigint;
begin
    for
        SELECT userId, forumId
        FROM (SELECT user_fk as userId, forum_fk as forumId, count(id) as total
            FROM DISCUSSION_FORUMWATCH
            GROUP BY user_fk, forum_fk) WHERE total > 1
    into
        :userId, :forumId do
    begin
        DELETE FROM DISCUSSION_FORUMWATCH WHERE user_fk = :userId and forum_fk = :forumId;

        INSERT INTO DISCUSSION_FORUMWATCH
            (ID, USER_FK, FORUM_FK)
            VALUES (gen_id(GLOBAL_SEQUENCE,1),:userId,:forumId);
     end
end;
execute procedure delete_double_forumwatch;

drop PROCEDURE delete_double_subscriber;
create procedure delete_double_subscriber
as
declare variable userId bigint;
declare variable newsletterId bigint;
begin
    for
        SELECT userId, newsletterId
        FROM (SELECT user_fk as userId, newsletter_fk as newsletterId, count(id) as total
            FROM NEWSLETTER_SUBSCRIBER
            GROUP BY user_fk, newsletter_fk) WHERE total > 1
    into
        :userId, :newsletterId do
    begin
        DELETE FROM NEWSLETTER_SUBSCRIBER WHERE user_fk = :userId and newsletter_fk = :newsletterId;

        INSERT INTO NEWSLETTER_SUBSCRIBER
            (ID, USER_FK, NEWSLETTER_FK, BLOCKED)
            VALUES (gen_id(GLOBAL_SEQUENCE,1),:userId,:newsletterId, 0);
     end
end;
execute procedure delete_double_subscriber;