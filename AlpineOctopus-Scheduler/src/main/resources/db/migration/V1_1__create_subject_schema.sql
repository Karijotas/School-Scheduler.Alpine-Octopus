CREATE TABLE subject
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_subject PRIMARY KEY (id)
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


CREATE TABLE program_subject_hours
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    program_id    BIGINT,
    subject_id    BIGINT,
    subject_hours INT                   NOT NULL,
    CONSTRAINT pk_programsubjecthours PRIMARY KEY (id)
);

ALTER TABLE program_subject_hours
    ADD CONSTRAINT FK_PROGRAMSUBJECTHOURS_ON_PROGRAM FOREIGN KEY (program_id) REFERENCES program (id);

ALTER TABLE program_subject_hours
    ADD CONSTRAINT FK_PROGRAMSUBJECTHOURS_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subject (id);

