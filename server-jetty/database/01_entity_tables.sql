DROP TABLE    IF EXISTS relative_position, entity;
DROP SEQUENCE IF EXISTS entity_seq;
DROP SEQUENCE IF EXISTS relative_position_seq;

CREATE SEQUENCE entity_seq;

CREATE TABLE entity (
  id           BIGINT         PRIMARY KEY,
  name         VARCHAR(100)   NOT NULL,
  description  VARCHAR(2047)
);

CREATE SEQUENCE relative_position_seq;

CREATE TABLE relative_position (
  id               BIGINT    PRIMARY KEY,
  center_entity_id BIGINT    NOT NULL,
  aux_entity_id    BIGINT    NOT NULL,
  dx               INT       NOT NULL,
  dy               INT       NOT NULL
);

ALTER TABLE relative_position
  ADD CONSTRAINT rel_pos_center__entities  FOREIGN KEY (center_entity_id) REFERENCES bubbles_entities (id) ON DELETE CASCADE,
  ADD CONSTRAINT rel_pos_aux__entities     FOREIGN KEY (aux_entity_id)    REFERENCES bubbles_entities (id) ON DELETE CASCADE,
  ADD CONSTRAINT rel_pos_center_aux_unique UNIQUE (center_entity_id, aux_entity_id);

