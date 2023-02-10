CREATE TABLE SUBJECT (
   ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
   NAME CHARACTER VARYING(80),
   DESCRIPTION CHARACTER VARYING(255),

   CONSTRAINT SUBJECT_PK PRIMARY KEY (ID)
);

CREATE TABLE program
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255),
    description   VARCHAR(255),
    created_date  TIMESTAMP,
    modified_date TIMESTAMP,
    CONSTRAINT pk_program PRIMARY KEY (id)
);

CREATE TABLE programs_subjects
(
    program_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    CONSTRAINT pk_programs_subjects PRIMARY KEY (program_id, subject_id)
);

ALTER TABLE programs_subjects
    ADD CONSTRAINT fk_prosub_on_program FOREIGN KEY (program_id) REFERENCES program (id);

ALTER TABLE programs_subjects
    ADD CONSTRAINT fk_prosub_on_subject FOREIGN KEY (subject_id) REFERENCES subject (id);

