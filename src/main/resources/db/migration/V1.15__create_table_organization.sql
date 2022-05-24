drop table if exists organization;

create table organization
(
    organization_id  bigint         not null auto_increment,
    name             varchar(255)   not null,
    image            varchar(255)   not null,
    address          varchar(255)   null,
    phone            integer        null,
    email            varchar(255)   not null,
    welcome_text     varchar(255)   not null,
    about_us_text    varchar(255)   null,
    slides_id         bigint         not null,
    created_at       datetime(6)    not null,
    is_active        bit            not null,
    updated_at       datetime(6)    null,
    primary key (organization_id)
    foreign key (slides_id) references slides (slides_id)
) engine = InnoDB;
