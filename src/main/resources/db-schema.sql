drop table if exists customer;

create table customer (
    id serial not null,
    user_name varchar(128),
    primary key (id)
);
