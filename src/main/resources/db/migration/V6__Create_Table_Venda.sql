CREATE TABLE `venda` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `data_criacao` date DEFAULT NULL,
     `forma_pagamento` enum('CHEQUE','CREDITO','DEBITO','DINHEIRO','PIX') DEFAULT NULL,
     `quantidade_parcelas` int DEFAULT NULL,
     `total` decimal(7,2) DEFAULT NULL,
     `anotacao_id` bigint DEFAULT NULL,
     `cliente_id` bigint DEFAULT NULL,
     PRIMARY KEY (`id`),
     UNIQUE KEY `UK_s51tywlxf3xs7x35yxsamgpct` (`anotacao_id`),
     KEY `FK50murhuotq9h2dnxej317jjiy` (`cliente_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;