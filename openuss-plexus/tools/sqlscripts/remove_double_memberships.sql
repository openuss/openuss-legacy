CREATE TABLE TEMP_MEMBERS (
    MEMBERSHIPID  BIGINT,
    USERID        BIGINT
);
INSERT INTO TEMP_MEMBERS (MEMBERSHIPID, USERID) SELECT DISTINCT * FROM security_member_membership;
DELETE FROM security_member_membership;
INSERT INTO security_member_membership SELECT * FROM temp_members;
DROP TABLE TEMP_MEMBERS;
