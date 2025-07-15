package com.client_app;

import com.shared_app.PurchaseService;
import com.shared_app.PaymentService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.rmi.Naming;

@SpringBootApplication
public class ClientAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClientAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PurchaseService purchase = (PurchaseService) Naming.lookup("rmi://localhost:1099/PurchaseService");
		PaymentService payment = (PaymentService) Naming.lookup("rmi://localhost:1100/PaymentService");

		String orderId = purchase.processOrder("Notebook", 1);
		System.out.println("Pedido criado com ID: " + orderId);

		String result = payment.pay(orderId, 2500.0);
		System.out.println("Pagamento realizado: " + result);
	}
}
