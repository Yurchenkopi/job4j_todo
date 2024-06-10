ALTER TABLE tasks
ADD COLUMN IF NOT EXISTS user_id int NOT NULL references todo_users(id) default 1;
