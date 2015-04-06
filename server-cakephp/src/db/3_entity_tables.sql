DROP TABLE bubbles_relative_positions,bubbles_entities;

CREATE TABLE `bubbles_entities` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR( 100 ) NOT NULL,
  `description` VARCHAR( 2047 ),
  PRIMARY KEY ( `id` ) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

CREATE TABLE `bubbles_relative_positions` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `center_entity_id` INT UNSIGNED NOT NULL,
  `aux_entity_id` INT UNSIGNED NOT NULL,
  `dx` INT NOT NULL,
  `dy` INT NOT NULL,
  PRIMARY KEY ( `id`) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

ALTER TABLE `bubbles_relative_positions`
  ADD CONSTRAINT `rel_pos_center__entities` FOREIGN KEY (`center_entity_id`) REFERENCES `bubbles_entities` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rel_pos_aux__entities` FOREIGN KEY (`aux_entity_id`) REFERENCES `bubbles_entities` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rel_pos_center_aux_unique` UNIQUE (`center_entity_id`, `aux_entity_id`);

