-- Sample data for initial books
INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Things Fall Apart', 'FICTION', '978-0-14-006734-1', 'Chinua Achebe', 1958, 12.50, 50);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('No Longer at Ease', 'FICTION', '978-0-385-47454-4', 'Chinua Achebe', 1960, 13.75, 40);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Half of a Yellow Sun', 'FICTION', '978-0-307-27340-3', 'Chimamanda Ngozi Adichie', 2006, 18.75, 25);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Small Country', 'FICTION', '978-978-54316-1-8', 'Ayọ̀bámi Adébáyọ̀', 2021, 18.50, 20);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Born on a Tuesday', 'FICTION', '978-0-14-312894-3', 'Elnathan John', 2016, 16.75, 30);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Becoming Nigerian: A Guide', 'SATIRE', '978-978-96791-0-2', 'Elnathan John', 2019, 14.99, 25);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('How To Sell To Nigerians', 'SATIRE', '978-978-96791-1-9', 'Akin Alabi', 2018, 20.00, 15);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Every Leaf a Hallelujah', 'FICTION', '978-0-593-49033-6', 'Ben Okri', 2021, 19.20, 28);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Under the Bridge', 'THRILLER', '978-978-52033-1-5', 'Helon Habila', 2023, 17.50, 22);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('The Death of Vivek Oji', 'FICTION', '978-0-593-31627-8', 'Akwaeke Emezi', 2020, 18.00, 27);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('The Great Adventure', 'FICTION', '978-1-234567-89-0', 'John Doe', 2020, 19.99, 50);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Mystery at Midnight', 'MYSTERY', '978-0-987654-32-1', 'Jane Smith', 2019, 15.99, 30);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Silent Scream', 'THRILLER', '978-5-678901-23-4', 'Robert Johnson', 2021, 21.99, 25);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Dark Shadows', 'HORROR', '978-9-876543-21-0', 'Sarah Williams', 2022, 18.99, 20);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Modern Life', 'SATIRE', '978-3-456789-01-2', 'Michael Brown', 2018, 14.99, 15);

INSERT INTO books (title, genre, isbn, author, publication_year, price, quantity)
VALUES ('Whispers of Nature', 'POETRY', '978-7-654321-09-8', 'Emily Parker', 2020, 12.99, 40);

-- Sample users
INSERT INTO users (username)
VALUES ('user1');

INSERT INTO users (username)
VALUES ('user2');

-- Sample carts
INSERT INTO cart (user_id) VALUES (1);
INSERT INTO cart (user_id) VALUES (2);