CREATE TABLE `cliente_veiculo` (
   `cliente_id` bigint NOT NULL,
   `veiculo_id` bigint NOT NULL,
   PRIMARY KEY (`cliente_id`,`veiculo_id`),
   KEY `FK6cwm4kpaw9lgk4i73kqpr20f0` (`veiculo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;