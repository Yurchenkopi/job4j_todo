CREATE TABLE IF NOT EXISTS task_filters
(
    id   serial primary key,
    name varchar not null unique
);