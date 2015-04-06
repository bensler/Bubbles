DROP TABLE bubbles_link_tag_xrefs, bubbles_tags, bubbles_links;

CREATE TABLE `bubbles_links` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `url` VARCHAR( 2048 ) NOT NULL,
  `description` VARCHAR( 2047 ),
  PRIMARY KEY ( `id` ) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

CREATE TABLE `bubbles_tags` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR( 127 ) NOT NULL,
  `description` VARCHAR( 2047 ),
  `parent_id` INT UNSIGNED NULL,
  PRIMARY KEY ( `id` ) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

ALTER TABLE `bubbles_tags`
  ADD CONSTRAINT `bubbles_fk_tag__parent_tag` FOREIGN KEY (`parent_id`) REFERENCES `bubbles_tags` (`id`);

INSERT INTO `bubbles_tags` (`id` ,`name` ,`description` ,`parent_id`) VALUES 
  (1 , 'new', 'kinda inbox', NULL), 
  (2 , 'media', '', NULL), 
  (3 , 'video', '', 2);

CREATE TABLE `bubbles_link_tag_xrefs` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `link_id` INT UNSIGNED NOT NULL,
  `tag_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY ( `id` ) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;

ALTER TABLE `bubbles_link_tag_xrefs`
  ADD CONSTRAINT `bubbles_fk_link_tag_xrefs__tags` FOREIGN KEY (`tag_id`) REFERENCES `bubbles_tags` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bubbles_fk_link_tag_xrefs__links` FOREIGN KEY (`link_id`) REFERENCES `bubbles_links` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bubbles_unique_link_tag` UNIQUE (`tag_id`, `link_id`);
  



