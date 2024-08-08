CREATE DATABASE `tabulator`;
CREATE TABLE `user` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `user`(id, name, age) VALUES('id100', 'Bob Oil', 32);
INSERT INTO `user`(id, name, age) VALUES('id101', 'May Mary', 11);
INSERT INTO `user`(id, name, age) VALUES('id102', 'Philps B', 43);