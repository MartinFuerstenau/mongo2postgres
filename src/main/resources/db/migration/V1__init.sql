CREATE TABLE HEROS(
	id BIGINT GENERATED BY DEFAULT AS IDENTITY,
	hero JSONB NOT NULL
);

create sequence heros_sequence start with 1 increment by 1;