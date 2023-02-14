CREATE TABLE ROOM (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255),
   building VARCHAR(255),
   description VARCHAR(255),
   created_date TIMESTAMP,
   modified_date TIMESTAMP,
   CONSTRAINT pk_room PRIMARY KEY (id)
);