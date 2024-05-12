CREATE TABLE task_filters
(
    id   serial primary key,
    name varchar not null unique
);