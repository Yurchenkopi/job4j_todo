INSERT INTO categories_tasks(task_id)
SELECT t.id
FROM tasks t;

UPDATE categories_tasks
SET category_id = '4';
