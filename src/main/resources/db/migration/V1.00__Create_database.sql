create table player
(
  id serial not null
    constraint player_pkey
    primary key,
  nick varchar(255),
  score integer
)
;

create table country
(
  id serial not null
    constraint country_pkey
    primary key,
  name varchar(255),
  capital varchar(255)
)
;