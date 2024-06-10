INSERT INTO task_filters (name)
SELECT *
FROM (VALUES
          ('все'),
          ('новые'),
          ('выполненные')
     )
WHERE NOT EXISTS (
        SELECT 1
        FROM task_filters
        WHERE name IN ('все', 'новые', 'выполненные')
    );
