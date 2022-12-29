CREATE DATABASE cs202;

USE cs202;

/* ***************************************************************************************************** */

CREATE TABLE genre (
	genre_id INT NOT NULL AUTO_INCREMENT,
	genre VARCHAR(100) NOT NULL UNIQUE,
	PRIMARY KEY(genre_id, genre)
);

ALTER TABLE genre AUTO_INCREMENT = 101;

INSERT INTO genre (genre)
VALUES  ('Action'), ('History'), ('Children'), ('Classic'), ('Comic') , ('Crime'), ('Drama'), ('Fantasy'), 
	('Poetry'), ('Romance'), ('Short Story'), ('Western'), ('Art'), ('Biograpgy'), ('Bussiness'), 
    	('Dictionary'), ('Health'), ('Philosophy'), ('Mechanical Engineering'), ('Civil Engineering'),
   	('Computer Science'), ('Math'), ('Industrial Engineering'), ('Physics'), ('Journal'), 
	('Electrical Engineering'), ('Management'), ('Sports'), ('Travel');

/* ***************************************************************************************************** */

CREATE TABLE book (
	book_id INT NOT NULL UNIQUE,
	book_name VARCHAR(100) NOT NULL UNIQUE,
	book_amount INT,
	genre VARCHAR(100) ,
	PRIMARY KEY (book_id),
	CONSTRAINT FK_Genre FOREIGN KEY (genre) 
	REFERENCES genre(genre),
    CHECK (book_amount >= 0)
);


CREATE TABLE book_backup LIKE book;

/* ***************************************************************************************************** */

CREATE TABLE user (
	user_id INT NOT NULL UNIQUE,
	user_name VARCHAR(50) NOT NULL UNIQUE,
	user_email VARCHAR(100) DEFAULT NULL,
	user_address TEXT DEFAULT NULL,
	PRIMARY KEY(user_id)
);

/* ***************************************************************************************************** */


CREATE TABLE checkout (
	checkout_id INT NOT NULL,
	book_id INT NOT NULL,
	user_id INT NOT NULL,
	checkout_time DATETIME,
	return_date DATETIME DEFAULT NULL,
	status INT DEFAULT 0,
	PRIMARY KEY (checkout_id),
	CONSTRAINT FK_Book FOREIGN KEY (book_id) 
	REFERENCES book(book_id),
	CONSTRAINT FK_User FOREIGN KEY (user_id) 
	REFERENCES user(user_id)
);



