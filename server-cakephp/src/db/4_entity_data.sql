INSERT INTO `bubbles_entities` (`id`,`name`,`description`) VALUES (1, 'A', NULL);
INSERT INTO `bubbles_entities` (`id`,`name`,`description`) VALUES (2, 'B', '');
INSERT INTO `bubbles_entities` (`id`,`name`,`description`) VALUES (3, 'C', 'ci');
INSERT INTO `bubbles_entities` (`id`,`name`,`description`) VALUES (4, 'D', 'di');
INSERT INTO `bubbles_entities` (`id`,`name`,`description`) VALUES (5, 'X', 'iks');
INSERT INTO `bubbles_entities` (`id`,`name`,`description`) VALUES (6, 'Z', 'Neque porro quisquam est, qui dolorem ipsum, quia dolor sit, amet, consectetur, adipisci velit');

INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (1,2, 0, -100);
INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (1,3, -100, -100);
INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (1,4, 100, 100);

INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (2,3, -100, -100);
INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (2,1, -100, 0);
INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (2,5, -100, 100);
INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (2,6, 100, -100);

INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (3,2, -100, -100);
INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (3,1, 100, -100);

INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (4,1, 0, -100);
      
INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (5,2, 100, 100);

INSERT INTO `bubbles_relative_positions` (`center_entity_id`,`aux_entity_id`,`dx`,`dy`) 
     VALUES (6,2, 100, -100);
