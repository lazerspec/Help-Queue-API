INSERT INTO qa.DEPARTMENT (department_name) VALUES ('Wealth');
INSERT INTO qa.DEPARTMENT (department_name) VALUES ('CoreEng');
INSERT INTO qa.DEPARTMENT (department_name) VALUES ('Trade');


INSERT INTO qa.TICKET (department_id,title,author,description,urgency_level,status, solution) VALUES (1,'Ticket1','Parvir Chomber','A description of the ticket','high','open',null);
INSERT INTO qa.TICKET (department_id,title,author,description,urgency_level,status,solution) VALUES (1,'Ticket4','Samsung Galaxy','A fourth desc of the ticket','high','open',null);
INSERT INTO qa.TICKET (department_id,title,author,description,urgency_level,status,solution) VALUES (2,'Ticket2','Stan Marsh','A second description','high','open',null);
INSERT INTO qa.TICKET (title,author,description,urgency_level,status,solution) VALUES ('Ticket3','Randy Marsh','An orphaned ticket description','high','open',null);



INSERT INTO qa.TICKET (department_id,title,author,description,urgency_level,status,solution) VALUES (2,'Closed Ticket','Sharon Marsh','This ticket has been closed','high','closed','This is a solution');