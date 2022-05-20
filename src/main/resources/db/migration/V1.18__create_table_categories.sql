drop table if exists categories;

create table categories
(
    category_id  bigint not null auto_increment,
    name       varchar(255) not null,
    description varchar(255) null,
    image varchar(255) null,
    date_of_creation datetime(6) not null,
    modification_date datetime(6) not null,
    created_at datetime(6)  not null,
    is_active  bit          not null,
    updated_at datetime(6)  null,
    primary key (category_id)
) engine = InnoDB;
