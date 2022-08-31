INSERT INTO genre(id, name) VALUES(nextval('genre_id_seq'), 'Thriller');
INSERT INTO genre(id, name) VALUES(nextval('genre_id_seq'), 'Romance');
INSERT INTO genre(id, name) VALUES(nextval('genre_id_seq'), 'Fantasy');
INSERT INTO genre(id, name) VALUES(nextval('genre_id_seq'), 'Science Fiction');
INSERT INTO genre(id, name) VALUES(nextval('genre_id_seq'), 'Mystery');

INSERT INTO author(id, first_name, last_name, middle_name, full_name) VALUES(nextval('author_id_seq'), 'Jodi', 'Picoult', '', 'Jodi Picoult');
INSERT INTO author(id, first_name, last_name, middle_name, full_name) VALUES(nextval('author_id_seq'), 'Danielle', 'Steel', '', 'Danielle Steel');
INSERT INTO author(id, first_name, last_name, middle_name, full_name) VALUES(nextval('author_id_seq'), 'Joanne', 'Rowling', 'Kathleen', 'Joanne Kathleen Rowling');
INSERT INTO author(id, first_name, last_name, middle_name, full_name) VALUES(nextval('author_id_seq'), 'Orson ', 'Card', 'Scott', 'Orson Scott Card');
INSERT INTO author(id, first_name, last_name, middle_name, full_name) VALUES(nextval('author_id_seq'), 'Rita', 'Brown', 'Mae', 'Rita Mae Brown');

INSERT INTO book( id, title, author_id, genre_id) VALUES(nextval('book_id_seq'), 'Leaving Time', 1, 1);
INSERT INTO book( id, title, author_id, genre_id) VALUES(nextval('book_id_seq'), 'Until the End of Time', 2, 2);
INSERT INTO book( id, title, author_id, genre_id) VALUES(nextval('book_id_seq'), 'Harry Potter and the Sorcerer’s Stone', 3, 3);
INSERT INTO book( id, title, author_id, genre_id) VALUES(nextval('book_id_seq'), 'Ender’s Game', 4, 4);
INSERT INTO book( id, title, author_id, genre_id) VALUES(nextval('book_id_seq'), 'Nine Lives to Die', 5, 5);
