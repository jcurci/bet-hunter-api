CREATE TABLE lesson(
    id varchar(36) PRIMARY KEY,
    title varchar(35) NOT NULL
);

CREATE TABLE topic(
    id varchar(36) PRIMARY KEY,
    id_lesson varchar(36) NOT NULL,
    topic_number INT NOT NULL,
    FOREIGN KEY (id_lesson) references lesson(id)
);

CREATE TABLE question(
    id varchar(36) PRIMARY KEY,
    id_topic varchar(36) NOT NULL,
    question_number INT NOT NULL,
    statement varchar(100),
    FOREIGN KEY (id_topic) references topic(id)
);

CREATE TABLE alternatives(
    id varchar(36) PRIMARY KEY,
    id_question varchar(36) NOT NULL,
    text varchar(50) NOT NULL,
    correct BOOL DEFAULT FALSE,
    FOREIGN KEY (id_question) references question(id)
);

CREATE TABLE user_answers (
    id varchar(36) PRIMARY KEY,
    id_user varchar(36) NOT NULL,
    id_question varchar(36) NOT NULL,
    id_alternative varchar(36),
    correct BOOL DEFAULT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id),
    FOREIGN KEY (id_question) REFERENCES question(id),
    FOREIGN KEY (id_alternative) REFERENCES alternatives(id)
);


CREATE TABLE user_progress_lesson(
    id varchar(36) PRIMARY KEY,
    id_user varchar(36) NOT NULL,
    id_lesson varchar(36) NOT NULL,
    id_actual_topic varchar(36) NULL,
    FOREIGN KEY (id_user) references users(id),
    FOREIGN KEY (id_lesson) references lesson(id),
    FOREIGN KEY (id_actual_topic) references topic(id)
);

CREATE TABLE user_progress_topic(
    id varchar(36) PRIMARY KEY,
    id_user varchar(36) NOT NULL,
    id_topic varchar(36) NOT NULL,
    id_question varchar(36) NOT NULL,
    id_actual_question varchar(36) NULL,
    FOREIGN KEY (id_user) references users(id),
    FOREIGN KEY (id_topic) references topic(id),
    FOREIGN KEY (id_actual_question) references question(id)
);