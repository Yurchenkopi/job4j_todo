CREATE TABLE IF NOT EXISTS todo_users(
    id SERIAL PRIMARY KEY             ,
    name VARCHAR             NOT NULL ,
    login VARCHAR UNIQUE     NOT NULL ,
    password VARCHAR         NOT NULL
)