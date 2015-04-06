INSERT INTO entity (id, name, description) VALUES 
                   (1,  'A',  NULL),
                   (2,  'B',  ''),
                   (3,  'C',  'ci'),
                   (4,  'D',  'di'),
                   (5,  'X',  'iks'),
                   (6,  'Z',  'Neque porro quisquam est, qui dolorem ipsum, quia dolor sit, amet, consectetur, adipisci velit');
ALTER SEQUENCE entity_seq RESTART WITH 10;

INSERT INTO relative_position (id, center_entity_id, aux_entity_id,   dx,   dy) VALUES
                              ( 1, 1,                2,                0, -100),
                              ( 2, 1,                3,             -100, -100),
                              ( 3, 1,                4,              100,  100),
                              ( 4, 2,                3,             -100, -100),
                              ( 5, 2,                1,             -100,    0),
                              ( 6, 2,                5,             -100,  100),
                              ( 7, 2,                6,              100, -100),
                              ( 8, 3,                2,             -100, -100),
                              ( 9, 3,                1,              100, -100),
                              (10, 4,                1,                0, -100),
                              (11, 5,                2,              100,  100),
                              (12, 6,                2,              100, -100);
ALTER SEQUENCE relative_position_seq RESTART WITH 15;
