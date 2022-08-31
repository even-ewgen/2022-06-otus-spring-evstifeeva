-- public.author definition

DROP TABLE IF EXISTS author CASCADE;

CREATE TABLE author (
	id SERIAL PRIMARY KEY,
	first_name varchar NULL,
	last_name varchar NULL,
	middle_name varchar NULL,
	full_name varchar NOT NULL
);

-- public.genre definition

DROP TABLE IF EXISTS genre CASCADE;

CREATE TABLE genre (
	id SERIAL PRIMARY KEY,
	name varchar NULL
);

-- public.book definition

DROP TABLE IF EXISTS book CASCADE;

CREATE TABLE book (
	id SERIAL PRIMARY KEY,
	title varchar NULL,
	author_id int8 NULL,
	genre_id int8 NULL,
	CONSTRAINT book_fk_author FOREIGN KEY (author_id) REFERENCES author(id) ON UPDATE CASCADE ON DELETE SET NULL,
	CONSTRAINT book_fk_genre FOREIGN KEY (genre_id) REFERENCES genre(id) ON UPDATE CASCADE ON DELETE SET NULL
);
