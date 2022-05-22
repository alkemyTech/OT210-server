drop table if exists slides;

create table slides
(
    slides_id         bigint         not null auto_increment,
    image_url         varchar(255)   null,
    text              varchar(255)   null,
    orde              varchar(255)   null,
    organization_id   bigint         not null,
    created_at        datetime(6)    not null,
    is_active         bit            not null,
    updated_at        datetime(6)    null,
    primary key (slides_id),
    foreign key (organization_id) references organization (organization_id)
) engine = InnoDB;