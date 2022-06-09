drop table if exists role;

create table role
(
    role_id bigint not null auto_increment,
    name varchar(25) not null,
    description varchar(150) null,
    is_active bit not null,
    created_at datetime(6) not null,
    updated_at datetime(6) null,
    primary key (role_id)
) engine = InnoDB;
