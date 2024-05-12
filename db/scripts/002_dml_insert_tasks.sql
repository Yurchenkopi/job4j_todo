INSERT INTO tasks(description, created, done)
VALUES
('Поесть', CURRENT_DATE + TIME '9:00', false),
('Сходить на работу', CURRENT_DATE + TIME '1:00', true),
('Поботать', CURRENT_DATE + TIME '11:00', false),
('Поспать', CURRENT_DATE + TIME '12:00', false);
