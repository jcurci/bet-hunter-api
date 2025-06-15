CREATE TABLE users(
	id varchar(36) PRIMARY KEY,
    email varchar(100) UNIQUE NOT NULL,
    password varchar(100) NOT NULL,
    name varchar(60) NOT NULL,
    cellphone varchar(11) UNIQUE NOT NULL,
    money DECIMAL(18,2),
    points INT,
    placement INT NOT NULL
);

CREATE TABLE article(
	id varchar(36) PRIMARY KEY,
    title varchar(100) NOT NULL
);