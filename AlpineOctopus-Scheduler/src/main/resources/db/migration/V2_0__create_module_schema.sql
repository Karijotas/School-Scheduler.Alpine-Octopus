CREATE TABLE MODULE (
   ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
   NAME CHARACTER VARYING(80),
   DESCRIPTION CHARACTER VARYING(255),

   CONSTRAINT MODULE_PK PRIMARY KEY (ID)
);