CREATE TABLE "AUTHOR"(

    "ID" BIGINT AUTO_INCREMENT NOT NULL,

    "NAME" CHARACTER VARYING(255)

);

ALTER TABLE "AUTHOR" ADD CONSTRAINT "CONSTRAINT_7" PRIMARY KEY("ID");

CREATE TABLE "BOOK"(

    "ID" BIGINT AUTO_INCREMENT NOT NULL,

    "ACTIVE" BOOLEAN NOT NULL,

    "AVAILABLE_QUANTITY" INTEGER NOT NULL,

    "CREATED_AT" DATE NOT NULL,

    "DESCRIPTION" CHARACTER VARYING(1000),

    "DEPTH" DOUBLE PRECISION,

    "HEIGHT" DOUBLE PRECISION,

    "WIDTH" DOUBLE PRECISION,

    "FORMAT" CHARACTER VARYING(255) NOT NULL,

    "ISBN" CHARACTER VARYING(255),

    "LANGUAGE" CHARACTER VARYING(255) NOT NULL,

    "PAGES" INTEGER NOT NULL,

    "CURRENCY" CHARACTER VARYING(255) NOT NULL,

    "PRICE" NUMERIC(19, 2) NOT NULL,

    "PUBLISHING_DATE" DATE NOT NULL,

    "SKU" CHARACTER VARYING(10),

    "TITLE" CHARACTER VARYING(255),

    "PUBLISHER_ID" BIGINT NOT NULL

);

ALTER TABLE "BOOK" ADD CONSTRAINT "CONSTRAINT_1F32" PRIMARY KEY("ID");

CREATE TABLE "BOOK_AUTHOR"(

    "BOOK_ID" BIGINT NOT NULL,

    "AUTHOR_ID" BIGINT NOT NULL

);
ALTER TABLE "BOOK_AUTHOR" ADD CONSTRAINT "CONSTRAINT_A" PRIMARY KEY("BOOK_ID", "AUTHOR_ID");

CREATE TABLE "BOOK_CATEGORY"(

    "BOOK_ID" BIGINT NOT NULL,

    "CATEGORY_ID" BIGINT NOT NULL

);
ALTER TABLE "BOOK_CATEGORY" ADD CONSTRAINT "CONSTRAINT_9" PRIMARY KEY("BOOK_ID", "CATEGORY_ID");

CREATE TABLE "BOOK_IMAGES_URL"(

    "BOOK_ID" BIGINT NOT NULL,

    "IMAGES_URL" CHARACTER VARYING(255)

);

CREATE TABLE "CATEGORY"(

    "ID" BIGINT AUTO_INCREMENT NOT NULL,

    "ACTIVE" BOOLEAN NOT NULL,

    "DESCRIPTION" CHARACTER VARYING(255),

    "NAME" CHARACTER VARYING(100)

);
ALTER TABLE "CATEGORY" ADD CONSTRAINT "CONSTRAINT_3" PRIMARY KEY("ID");

CREATE TABLE "PUBLISHER"(

    "ID" BIGINT AUTO_INCREMENT NOT NULL,

    "ACTIVE" BOOLEAN NOT NULL,

    "NAME" CHARACTER VARYING(255),

    "SITE" CHARACTER VARYING(255)

);
ALTER TABLE "PUBLISHER" ADD CONSTRAINT "CONSTRAINT_F" PRIMARY KEY("ID");

ALTER TABLE "BOOK" ADD CONSTRAINT "CONSTRAINT_1F3" CHECK("PRICE" >= CAST(1 AS NUMERIC(1))) NOCHECK;
ALTER TABLE "BOOK" ADD CONSTRAINT "CONSTRAINT_1" CHECK("AVAILABLE_QUANTITY" >= 1) NOCHECK;
ALTER TABLE "BOOK" ADD CONSTRAINT "CONSTRAINT_1F" CHECK("PAGES" >= 1) NOCHECK;
ALTER TABLE "BOOK_IMAGES_URL" ADD CONSTRAINT "FKAEEUM6B1IPRHMPQNUN5S4N3RC" FOREIGN KEY("BOOK_ID") REFERENCES "BOOK"("ID") NOCHECK;
ALTER TABLE "BOOK_CATEGORY" ADD CONSTRAINT "FKAM8LLDERP40MVBBWCEQPU6L2S" FOREIGN KEY("CATEGORY_ID") REFERENCES "CATEGORY"("ID") NOCHECK;
ALTER TABLE "BOOK_AUTHOR" ADD CONSTRAINT "FKBJQHP85WJV8VPR0BEYGH6JSGO" FOREIGN KEY("AUTHOR_ID") REFERENCES "AUTHOR"("ID") NOCHECK;
ALTER TABLE "BOOK" ADD CONSTRAINT "FKGTVT7P649S4X80Y6F4842PNFQ" FOREIGN KEY("PUBLISHER_ID") REFERENCES "PUBLISHER"("ID") NOCHECK;
ALTER TABLE "BOOK_AUTHOR" ADD CONSTRAINT "FKHWGU59N9O80XV75PLF9GGJ7XN" FOREIGN KEY("BOOK_ID") REFERENCES "BOOK"("ID") NOCHECK;
ALTER TABLE "BOOK_CATEGORY" ADD CONSTRAINT "FKNYEGCBPVCE2MNMG26H0I856FD" FOREIGN KEY("BOOK_ID") REFERENCES "BOOK"("ID") NOCHECK;