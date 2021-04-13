DROP SCHEMA IF EXISTS qa CASCADE;

CREATE SCHEMA qa;


CREATE TABLE qa.DEPARTMENT (
    id INTEGER NOT NULL AUTO_INCREMENT,
    department_name VARCHAR(200),
    primary key(id)
);

CREATE TABLE qa.TICKET (
    id INTEGER NOT NULL AUTO_INCREMENT,
    department_id INTEGER,
    title VARCHAR(250),
    author VARCHAR(300),
    description VARCHAR(MAX),
    time_created DATETIME DEFAULT CURRENT_TIMESTAMP,
    urgency_level VARCHAR(100),
    status VARCHAR(100),
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    solution VARCHAR(MAX) DEFAULT NULL,
    primary key(id)
);

alter table qa.TICKET add constraint fk_department_id foreign key (department_id) references qa.DEPARTMENT(id);