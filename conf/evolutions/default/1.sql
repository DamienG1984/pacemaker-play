# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table task (
  id                            bigint not null,
  name                          varchar(255),
  done                          boolean,
  due_date                      timestamp,
  constraint pk_task primary key (id)
);
create sequence task_seq;

create table my_user (
  id                            bigint not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_my_user primary key (id)
);
create sequence my_user_seq;


# --- !Downs

drop table if exists task;
drop sequence if exists task_seq;

drop table if exists my_user;
drop sequence if exists my_user_seq;

