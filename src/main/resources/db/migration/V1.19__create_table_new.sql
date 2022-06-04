drop table if exists new;

create table new
(
    new_id bigint not null auto_increment,
    name           varchar(255) not null,
    content        varchar(255) not null ,
    category_id    bigint       not null,
    image          varchar(255) not null,
    created_at    datetime(6)  not null,
    is_active     bit          not null,
    updated_at    datetime(6)  null,
    primary key (new_id),
    foreign key (category_id) references category (category_id)
) engine = InnoDB;
