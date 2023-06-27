create table customers
(
    customer_id bigint not null
        primary key,
    password    varchar(1000),
    username    varchar(255)
);

alter table customers
    owner to postgres;

create table article
(
    article_id             bigserial
        primary key,
    article_date           timestamp,
    article_description    varchar(2048),
    article_description_en varchar(2048),
    article_link           varchar(255),
    article_source         varchar(255),
    article_title          varchar(255),
    article_title_en       varchar(255),
    customer_customer_id   bigint
        constraint fk8qj8c8w32ufmvytrlmlkglep5
            references customers
);

alter table article
    owner to postgres;

create table article_category
(
    article_id bigint not null
        constraint fkrw5912jiy0vyqoyqlo5r65igk
            references article,
    categories varchar(255)
);

alter table article_category
    owner to postgres;

create table customer_role
(
    customer_id bigint not null
        constraint fkrk3268jfmu796ejtnxt5pa4kt
            references customers,
    roles       varchar(255)
);

alter table customer_role
    owner to postgres;

-- TODO add users and roles