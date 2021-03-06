drop table if exists user;

create table user
(
    user_id bigint not null auto_increment,
    first_name varchar(25) not null,
    last_name varchar(25) not null,
    email varchar(25) not null unique,
    password varchar(25) not null,
    photo varchar(255) null,
    role_id bigint not null,
    is_active bit not null,
    created_at datetime(6) not null,
    updated_at datetime(6) null,
    primary key (user_id),
    foreign key (role_id) references role(role_id)
) engine = InnoDB;
