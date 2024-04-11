CREATE TABLE `anotacao` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `descricao` varchar(255) DEFAULT NULL,
    `titulo` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;