CREATE DATABASE `tabulator`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `Price` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('홍길동', '2014-10-01', 50, 3630700);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('Steve', '2024-01-01', 20, 630800);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('Bill', '2023-05-07', 100, 630000);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('홍길동', '2014-11-01', 70, 150000);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('Steve', '2020-10-10', 55, 1000000);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('test', '2020-10-10', 155, 1000000);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('test1', '2020-10-10', 99, 1000000);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('test2', '2020-10-10', 26, 1000000);
INSERT INTO `user` (name, `Date`, Quantity, Price) VALUES ('test3', '2020-10-10', 63, 1000000);