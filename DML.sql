INSERT INTO book 
VALUES  (10001, 'Discrete Mathematics', 10, 'Math'),
	(10002, 'Linear Algebra', 5, 'Math'),
	(10003, 'Economy 101', 6, 'Bussiness'),
	(10004, 'Economy 102', 4, 'Bussiness'),
	(10005, 'Art 101', 2, 'Art'),
	(10006, 'Art 102', 3, 'Art'),
	(10007, 'Core Java', 4, 'Computer Science'),
	(10008, 'Statics', 8, 'Mechanical Engineering'),
	(10009, 'Dynamics', 7, 'Mechanical Engineering'),
	(10010, 'Intro to Python', 10, 'Computer Science'),
	(10011, 'Physics 101', 10, 'Physics'),
	(10012, 'Physics 102', 10, 'Physics'),
	(10013, 'Management 101', 7, 'Management'),
	(10014, 'Database Management Systems', 12, 'Computer Science'),
	(10015, 'Circuits 1', 3, 'Electrical Engineering');

INSERT INTO book_backup SELECT * FROM book;
/* ***************************************************************************************************** */

INSERT INTO user
VALUES  (1001, 'Emin', 'amin.alamdari@ozu.edu.tr', 'Dormitory 6'),
	(1002, 'Berk', 'berk.buzcu@ozu.edu.tr', 'Atasehir'),
	(1003, 'Harun', 'harun.yesevi@ozu.edu.tr', null),
	(1004, 'Onur', 'onur.keskin@ozu.edu.tr', 'Tasdelen'),
	(1005, 'Ayse', 'ayse.cifci@boun.edu.tr', 'Levent');