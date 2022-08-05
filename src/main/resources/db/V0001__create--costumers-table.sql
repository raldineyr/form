create schema if not exists prv_cliente;

create table prv_cliente (

id bigserial primary key,
name varchar(80) not null,
bitrthDate date not null,

cpf varchar(14) not null,
rg varchar(14) not null,

phone varchar(14) not null,
email varchar(60) not null,

fatherName varchar(80) not null,
motherName varchar(80) not null,

creationDate timestamp not null,
);