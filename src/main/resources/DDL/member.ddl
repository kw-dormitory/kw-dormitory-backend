create table member
(
    member_id   varchar(255) not null primary key,
    username    varchar(255) null,
    token       varchar(255) null,
    email       varchar(255) null,
    nickname    varchar(255) null,
    photo_url    varchar(255) null,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null
);