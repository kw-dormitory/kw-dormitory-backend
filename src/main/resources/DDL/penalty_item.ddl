create table penalty_item
(
    penalty_item_id   bigint auto_increment
        primary key,
    penalty           int default 0 null,
    content           varchar(255) null
    penalty_status_id bigint null,
    created_at        datetime null,
    modified_at       datetime null,
);