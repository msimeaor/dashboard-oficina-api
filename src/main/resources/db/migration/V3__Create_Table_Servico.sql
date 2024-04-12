CREATE TABLE `servico` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `nome` varchar(100) DEFAULT NULL,
   `valor_unitario` decimal(7,2) DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UK_ktlf7q4ohqbhc2f4716md38yl` (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;