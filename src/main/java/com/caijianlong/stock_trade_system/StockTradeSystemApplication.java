package com.caijianlong.stock_trade_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
public class StockTradeSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockTradeSystemApplication.class, args);
	}

}
