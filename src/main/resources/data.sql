INSERT INTO qa.department (department_name) VALUES ('Wealth');
INSERT INTO qa.department (department_name) VALUES ('CoreEng');
INSERT INTO qa.department (department_name) VALUES ('Trade');


INSERT INTO qa.ticket (department_id,title,author,description,urgency_level,status, solution) VALUES (1,'Blue screen crash','Parvir Chomber','The system blue screens','medium','open',null);
INSERT INTO qa.ticket (department_id,title,author,description,urgency_level,status,solution) VALUES (1,'Software does not install','Joe Harris','Software in my application center does not install','high','open',null);
INSERT INTO qa.ticket (department_id,title,author,description,urgency_level,status,solution) VALUES (1,'Sound error','Tom Jeeve','There is no sound','low','open',null);
INSERT INTO qa.ticket (department_id,title,author,description,urgency_level,status,solution) VALUES (1,'Network error','Jimmy Butler','Cannot get online','high','in progress',null);

INSERT INTO qa.ticket (department_id,title,author,description,urgency_level,status,solution) VALUES (2,'Display error','Bruce Browne','The screen flickers when computer is running','high','open',null);
INSERT INTO qa.ticket (title,author,description,urgency_level,status,solution) VALUES ('Password reset','Jayson Tatum','I require a password reset','high','open',null);



INSERT INTO qa.ticket (department_id,title,author,description,urgency_level,status,solution) VALUES (2,'Laptop does not charge','Blake Girffin','Laptop does not charge','high','closed','The battery has been replaced and now should be fully functional');