CREATE TABLE `veiculo` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `ano_fabricacao` varchar(4) DEFAULT NULL,
   `fabricante` enum('AUDI','BMW','CHERY','CHEVROLET','CITROEN','FIAT','FORD','HONDA','HYUNDAI','IVECO','JAC','JAGUAR','JEEP','KIA','LANDROVER','LEXUS','LIFAN','MERCEDES','MINI','MITSUBISHI','NISSAN','PEUGEOT','PORSCHE','RAM','RENAULT','SUBARU','SUZUKI','TOYOTA','VOLKSWAGEM','VOLVO') DEFAULT NULL,
   `litragem_motor` varchar(3) DEFAULT NULL,
   `nome` varchar(100) DEFAULT NULL,
   `placa` varchar(7) DEFAULT NULL,
   `quilometragem` varchar(6) DEFAULT NULL,
   `anotacao_id` bigint DEFAULT NULL,
   PRIMARY KEY (`id`),
   UNIQUE KEY `UK_luoyk9d8idgi0wif7bxtefsr5` (`placa`),
   UNIQUE KEY `UK_3te2sudr4uqa69y8tas18fyil` (`anotacao_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;