create table `the_table` (
  `id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

revoke all privileges, grant option from 'test'@'%';

grant select on *.* to 'test'@'%';