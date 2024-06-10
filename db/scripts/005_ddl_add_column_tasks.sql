ALTER TABLE tasks
ADD COLUMN IF NOT EXISTS user_id int references todo_users(id);
