CREATE TABLE `cliente` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `cpf` varchar(11) DEFAULT NULL,
   `data_criacao` date DEFAULT NULL,
   `data_nascimento` date DEFAULT NULL,
   `email` varchar(100) DEFAULT NULL,
   `genero` enum('FEMININO','MASCULINO') DEFAULT NULL,
   `primeiro_nome` varchar(50) DEFAULT NULL,
   `sobrenome` varchar(100) DEFAULT NULL,
   `telefone_id` bigint DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UK_r1u8010d60num5vc8fp0q1j2a` (`cpf`),
   UNIQUE KEY `UK_cmxo70m08n43599l3h0h07cc6` (`email`),
   UNIQUE KEY `UK_hf506sr0u8ffclfd6uusjr4w` (`telefone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;