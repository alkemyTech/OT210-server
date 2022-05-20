drop table if exists news;

create table news
(
    news_id bigint not null auto_increment,
    name           varchar(255) not null,
    content        varchar(255) not null ,
    image          varchar(255) not null,
    category_id    bigint not null,
    date_of_creation datetime(6) not null,
    modification_date datetime(6) not null,
    created_at    datetime(6)  not null,
    is_active     bit          not null,
    updated_at    datetime(6)  null,
    primary key (news_id),
    foreign key (category_id) references categories (category_id)
) engine = InnoDB;
