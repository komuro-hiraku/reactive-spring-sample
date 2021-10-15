# reactive-spring-sample

## Env

- macOS 10.15.7
- Docker version 20.10.8, build 3967b7d
- IntelliJ IDEA 2021.2.2 (Community Edition)
- OpenJDK Runtime Environment Corretto-11.0.12.7.2 (build 11.0.12+7-LTS)

## preparation

### postgreql

Start docker container

```shell
$ docker run  --rm --name postgres-r2dbc -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres
```

Create database

```shell
$ docker exec -it postgres-r2dbc  psql -U postgres 
psql (12.2 (Debian 12.2-2.pgdg100+1))
Type "help" for help.

postgres=# CREATE DATABASE customers;
CREATE DATABASE
```

## Trouble-shooting

### Remove Table

```shell
$ docker exec -it postgres-r2dbc  psql -U postgres 
psql (12.2 (Debian 12.2-2.pgdg100+1))
Type "help" for help.

postgres-# \c customers
You are now connected to database "customers" as user "postgres".

customers-# \dt;
          List of relations
 Schema |   Name   | Type  |  Owner   
--------+----------+-------+----------
 public | customer | table | postgres
(1 row)

customers=# drop table customer;
DROP TABLE
customers=# \dt;
Did not find any relations.
```

