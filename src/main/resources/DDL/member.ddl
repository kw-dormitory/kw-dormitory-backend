create table member
(
    member_id   varchar(255) not null primary key,
    fcm_token   varchar(255) null,
    created_at  timestamp(6) null,
    modified_at timestamp(6) null
);