CREATE DATABASE `tabulator`;
CREATE TABLE `user` (
    `id` varchar(100) NOT NULL,
    `name` varchar(100) DEFAULT NULL,
    `Date` date DEFAULT NULL,
    `Quantity` int(11) DEFAULT NULL,
    `Price` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust1', '홍길동', '2014-10-01', 50, 3630700);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust2', 'Steve', '2024-01-01', 20, 630800);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust3', 'Bill', '2023-05-07', 100, 630000);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust4', '홍길동', '2014-11-01', 70, 150000);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust5', 'Steve', '2020-10-10', 55, 1000000);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust6', 'test', '2020-10-10', 155, 1000000);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust7', 'test1', '2020-10-10', 99, 1000000);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust8', 'test2', '2020-10-10', 99, 1000000);
INSERT INTO `user` (id, name, `Date`, Quantity, Price) VALUES ('Cust9', 'test3', '2020-10-10', 99, 1000000);