drop table if exists activities;

create table activities
(
    activity_id bigint       not null auto_increment,
    created_at  datetime(6)  not null,
    is_active   bit          not null,
    updated_at  datetime(6)  null,
    name        varchar(255) not null,
    content     text         not null,
    image       varchar(255) not null,
    primary key (activity_id)
) engine = InnoDB;