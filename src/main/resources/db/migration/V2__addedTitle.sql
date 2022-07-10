ALTER TABLE articles
    ADD COLUMN title varchar(255);

create table courses
(
    id BIGSERIAL not null primary key,
    user_id bigint not null,
    title varchar(255),
    description text,
    created_date date,
    foreign key (user_id) references users(id)
);

create table lessons
(
    id BIGSERIAL not null primary key,
    course_id bigint not null,
    title varchar(255) not null,
    content text not null,
    video_link text not null,
    lesson_number varchar(255),
    created_date date,
    foreign key (course_id) references courses(id)
);

create table comment_lessons
(
    id BIGSERIAL not null primary key,
    lesson_id bigint not null,
    user_id bigint not null,
    content varchar(255) not null,
    created_at date not null,
    foreign key (lesson_id) references lessons(id),
    foreign key (user_id) references users(id)
);

create table post_images
(
    id BIGSERIAL not null primary key,
    post_id bigint not null,
    data bytea,
    file_name varchar(255),
    file_type varchar(255),
    size bigint,
    uuid varchar(255),
    created_date date,
    foreign key (post_id) references posts(id)
);



