create TABLE users
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    login VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255)  NOT NULL,
    surname VARCHAR(255)  NOT NULL
);

create table user_details
(
    id BIGSERIAL not null primary key,
    user_id bigint not null,
    job varchar(255),
    birthday varchar(255),
    subject varchar(255),
    created_at date,
    modified_date date,
    foreign key (user_id) references users(id)
);

create table degree
(
    id BIGSERIAL not null primary key,
    title varchar(255)
);

insert into degree (title) values ('BACHELOR'), ('MASTER'), ('PHD');

create table user_education
(
    id BIGSERIAL not null primary key,
    university_name VARCHAR(255),
    major VARCHAR(255),
    user_id bigint not null,
    degree_id bigint not null,
    foreign key (user_id) references users(id),
    foreign key (degree_id) references degree(id)
);



create table roles
(
    id BIGSERIAL not null primary key,
    name VARCHAR(80) not null
);

create table users_roles
(
    user_id bigint not null,
    role_id integer not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)

);
-- change there post to posts
create table post
(
    id BIGSERIAL not null primary key,
    title varchar(255) not null,
    content text not null,
    creator_id bigint not null,
    created_at date not null,
    foreign key (creator_id) references users(id)
);

create table liked_post
(
    id BIGSERIAL not null primary key,
    user_id bigint not null,
    post_id bigint not null,
    liked integer not null,
    foreign key (user_id) references users(id),
    foreign key (post_id) references post(id)
);

create table post_comments
(
    id BIGSERIAL not null primary key,
    user_id bigint not null,
    post_id bigint not null,
    content varchar(255) not null,
    created_at date not null,
    foreign key (user_id) references users(id),
    foreign key (post_id) references post(id)
);

create table hashtags
(
    id BIGSERIAL not null primary key,
    title varchar(255)
);

create table post_hashtags
(
    post_id bigint not null,
    hashtag_id bigint not null,
    primary key (post_id, hashtag_id),
    foreign key (post_id) references users (id),
    foreign key (hashtag_id) references roles (id)
);








