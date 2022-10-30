create table party
(
    party_id     bigint auto_increment primary key,
    title        varchar(255) not null,
    content      varchar(255) not null,
    open_tok_url varchar(255) not null,
    created_at   datetime(6) not null,
    modified_at  datetime(6) null,
    member_id    varchar(255)
);