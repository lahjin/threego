INSERT INTO ROLE VALUES (1, 'ROLE_USER');
INSERT INTO ROLE VALUES (2, 'ROLE_ADMIN');
INSERT INTO ROLE VALUES (3, 'ROLE_MANAGER');

INSERT INTO USER (id, username, password, nickname) VALUES (1, 'user', '$2a$10$AtCM6kSdJVcezytJVraoEujndLCABFP76YEpdAKVvnfW17EBdQoDm', 'CommonUser');
INSERT INTO USER (id, username, password, nickname) VALUES (2, 'admin', '$2a$10$cRAyFVrOBOX/.xoZ20Rob.9QzKjDM7OEMLtqyIWDo7qWz.y8W3cbO', 'SuperAdmin');
INSERT INTO User (id, username, password, nickname) VALUES (3, 'manager', '$2a$10$oA5l62g8OQFiDcM4bhDehepGfkSc1NKo4JFrFGZ8cpLwDlG3t.IqC', 'Manager');

INSERT INTO USER_ROLES VALUES (1, 1);
INSERT INTO USER_ROLES VALUES (2, 2);
INSERT INTO USER_ROLES VALUES (3, 3);