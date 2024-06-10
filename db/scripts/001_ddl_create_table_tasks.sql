CREATE TABLE IF NOT EXISTS tasks (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR,
                       description TEXT,
                       created TIMESTAMP,
                       done BOOLEAN
);