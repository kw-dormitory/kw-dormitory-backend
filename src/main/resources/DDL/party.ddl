create table party
(
    party_id     bigint auto_increment
        primary key,
    created_at   datetime(6)  not null,
    modified_at  datetime(6)  null,
    content      varchar(255) not null,
    open_tok_url varchar(255) not null,
    title        varchar(255) not null,
    member_id    varchar(255)
);