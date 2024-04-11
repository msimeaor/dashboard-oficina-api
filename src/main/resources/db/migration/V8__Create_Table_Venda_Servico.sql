CREATE TABLE `venda_servico` (
     `venda_id` bigint NOT NULL,
     `servico_id` bigint NOT NULL,
     PRIMARY KEY (`venda_id`,`servico_id`),
     KEY `FKdm5rqxxo0ndio1kjrcwsv3hyh` (`servico_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;