create table tb_customer (
    created_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    id bigint not null,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    cpf varchar(255) not null unique,
    email varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);