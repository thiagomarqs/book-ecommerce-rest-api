INSERT INTO AUTHOR (NAME) VALUES
('Mark Twain'),
('J.K Rowling'),
('Fernando Pessoa'),
('Machado de Assis'),
('Philip K. Dick');

INSERT INTO CATEGORY (NAME, ACTIVE, DESCRIPTION) VALUES
('Fantasy', TRUE, 'Fantasy books.'),
('Sci-Fi', TRUE, 'Sci-fi books.'),
('Drama', FALSE, 'Drama books.'),
('Romance', TRUE, 'Romance books.'),
('Self help', TRUE, 'Self help books.');

INSERT INTO PUBLISHER (NAME, ACTIVE, SITE) VALUES
('Pub Books', TRUE, 'https://www.pubbooks.com'),
('Publi & Sher Books', TRUE, 'https://www.publisherbooks.com'),
('Books Pub', TRUE, 'https://www.bookspub.com'),
('Inactive Publisher', FALSE, 'https://www.inactivepublisher.com');

INSERT INTO BOOK
(ACTIVE, AVAILABLE_QUANTITY, CREATED_AT, DESCRIPTION, DEPTH, HEIGHT, WIDTH, FORMAT, ISBN, LANGUAGE, PAGES, CURRENCY, PRICE, PUBLISHING_DATE, SKU, TITLE, PUBLISHER_ID)
VALUES
(TRUE
, 1234
, '2022-12-03'
, 'An incredible adventure is about to begin! Having become classics of our time, the Harry Potter stories never fail to bring comfort and escapism. With their message of hope, belonging and the enduring power of truth and love, the story of the Boy Who Lived continues to delight generations of new listeners.'
, 3.0
, 12.0
, 6.0
,'PAPERBACK'
, '978-0439708180'
, 'ENGLISH'
, 309
, 'BRL'
, 39.99
, '1998-9-1'
, '1234567890'
, 'Harry Potter and the Sorcerer`s Stone'
, 1);

INSERT INTO BOOK
(ACTIVE, AVAILABLE_QUANTITY, CREATED_AT, DESCRIPTION, DEPTH, HEIGHT, WIDTH, FORMAT, ISBN, LANGUAGE, PAGES, CURRENCY, PRICE, PUBLISHING_DATE, SKU, TITLE, PUBLISHER_ID)
VALUES
(TRUE
, 392
, '2022-12-03'
, 'It’s America in 1962. Slavery is legal once again. The few Jews who still survive hide under assumed names. In this world, we meet characters like Frank Frink, a dealer of counterfeit Americana who is himself hiding his Jewish ancestry; Nobusuke Tagomi, the Japanese trade minister in San Francisco, unsure of his standing within the bureaucracy and Japan`s with Germany; and Juliana Frink, Frank`s ex-wife, who may be more important than she realizes. These seemingly disparate characters gradually realize their connections to each other just as they realize that something is not quite right about their world. And it seems as though the answers might lie with Hawthorne Abendsen, a mysterious and reclusive author, whose best-selling novel describes a world in which the US won the War... The Man in the High Castle is Dick at his best, giving readers a harrowing vision of the world that almost was.'
, 3.0
, 12.0
, 6.0
,'PAPERBACK'
, '978-0547572482'
, 'ENGLISH'
, 288
, 'BRL'
, 35.99
, '2012-1-24'
, '0987654321'
, 'The Man In The High Castle'
, 2);

INSERT INTO BOOK_AUTHOR VALUES
(1, 2),
(1, 5);

INSERT INTO BOOK_CATEGORY VALUES
(1, 1),
(2, 2);

INSERT INTO BOOK_IMAGES_URL VALUES
(1, 'http://laborum.com'),
(1, 'http://amet.com'),
(2, 'http://askldjhaskld.com'),
(2, 'http://oiujwqeorw.com'),
(2, 'http://hdjfnsdijkfns.com');