drop table if exists member;

create table member
(
    member_id bigint not null auto_increment,
    name varchar(255) not null,
    facebookUrl varchar(255) null,
    instagramUrl varchar(255) null,
    linkedinUrl varchar(255) null,
    image varchar(255) not null,
    description varchar(255) null,
    created_at datetime(6) not null,
    updated_at datetime(6) null,
    is_active bit not null,
    primary key (member_id)

) engine = InnoDB;