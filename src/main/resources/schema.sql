
USE qa;

DROP TABLE IF EXISTS qa.ticket;
DROP TABLE IF EXISTS qa.department;

CREATE TABLE qa.department (
    id INTEGER NOT NULL AUTO_INCREMENT,
    department_name VARCHAR(200),
    primary key(id)
);

CREATE TABLE qa.ticket (
    id INTEGER NOT NULL AUTO_INCREMENT,
    department_id INTEGER,
    title VARCHAR(250),
    author VARCHAR(300),
    description VARCHAR(700),
    time_created DATETIME DEFAULT CURRENT_TIMESTAMP,
    urgency_level VARCHAR(100),
    status VARCHAR(100),
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    solution VARCHAR(700) DEFAULT NULL,
    primary key(id)
);

alter table qa.ticket add constraint fk_department_id foreign key (department_id) references qa.department(id);
