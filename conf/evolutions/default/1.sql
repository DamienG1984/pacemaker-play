# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table my_activity (
  id                            bigserial not null,
  user_id                       bigint not null,
  atype                         varchar(255),
  location                      varchar(255),
  distance                      float,
  constraint pk_my_activity primary key (id)
);

create table my_friends (
  id                            bigserial not null,
  user_id                       bigint not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  constraint pk_my_friends primary key (id)
);

create table my_user (
  id                            bigserial not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  constraint pk_my_user primary key (id)
);

alter table my_activity add constraint fk_my_activity_user_id foreign key (user_id) references my_user (id) on delete restrict on update restrict;
create index ix_my_activity_user_id on my_activity (user_id);

alter table my_friends add constraint fk_my_friends_user_id foreign key (user_id) references my_user (id) on delete restrict on update restrict;
create index ix_my_friends_user_id on my_friends (user_id);


# --- !Downs

alter table if exists my_activity drop constraint if exists fk_my_activity_user_id;
drop index if exists ix_my_activity_user_id;

alter table if exists my_friends drop constraint if exists fk_my_friends_user_id;
drop index if exists ix_my_friends_user_id;

drop table if exists my_activity cascade;

drop table if exists my_friends cascade;

drop table if exists my_user cascade;

