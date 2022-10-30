DELIMITER $$
DROP PROCEDURE IF EXISTS insertLoop$$

CREATE PROCEDURE insertLoop()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 50000
        DO
            INSERT INTO party (party_id, title, content, open_tok_url, created_at, modified_at, member_id)
            VALUES (i, concat('title', i), concat('content', i), concat('open_tok_url', i), now(), now(), 'helloworld');
            SET i = i + 1;
END WHILE;
END$$
DELIMITER $$

CALL insertLoop;
$$