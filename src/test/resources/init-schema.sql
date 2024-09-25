create table `the_table` (
  `id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE USER 'readonly'@'%' IDENTIFIED BY 'testPassword';
GRANT SELECT ON the_database.* TO 'readonly'@'%';
FLUSH PRIVILEGES;
