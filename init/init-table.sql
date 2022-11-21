create table member
(
    member_id   varchar(255) not null
        primary key,
    fcm_token   varchar(255) null,
    created_at  timestamp(6) null,
    modified_at timestamp(6) null
);

create table member_setting
(
    member_setting_id varchar(255) not null,
    curfew            tinyint(1)   not null,
    notice            tinyint(1)   not null,
    regular           tinyint(1)   not null
);

create table notice
(
    notice_id  bigint       not null
        primary key,
    title      varchar(255) null,
    writer     varchar(255) null,
    created_at varchar(255) null
);

create table party
(
    party_id     bigint auto_increment
        primary key,
    title        varchar(255) not null,
    content      varchar(255) not null,
    open_tok_url varchar(255) not null,
    created_at   timestamp(6) not null,
    modified_at  timestamp(6) null,
    member_id    varchar(255) null
);

create table penalty_item
(
    penalty_item_id   bigint auto_increment
        primary key,
    penalty           int default 0 null,
    content           varchar(255)  null,
    penalty_status_id bigint        null,
    created_date      date          null,
    member_id         varchar(255)  null
);

create table penalty_status
(
    penalty_status_id bigint auto_increment
        primary key,
    member_id         varchar(255)  null,
    total_penalty     int default 0 not null
);