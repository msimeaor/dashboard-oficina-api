CREATE TABLE `telefone` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `ddd` varchar(3) DEFAULT NULL,
    `numero` varchar(9) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_bpqxm7divmbgtv17ykdtte47o` (`numero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;