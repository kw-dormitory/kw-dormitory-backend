create table penalty_status
(
    penalty_status_id bigint auto_increment
        primary key,
    member_id         varchar(255) null,
    total_penalty     int default 0 not null
);