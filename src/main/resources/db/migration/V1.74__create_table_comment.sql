drop table if exists comment;

create table comment
(
    comment_id  bigint       not null auto_increment,
    body        text         not null,
    created_at  datetime(6)  not null,
    is_active   bit          not null,
    updated_at  datetime(6)  null,
    user_id     bigint       not null,
    new_id      bigint       not null,
    primary key (comment_id),
    foreign key (user_id) references user(user_id),
    foreign key (new_id) references new(new_id)
) engine = InnoDB;
