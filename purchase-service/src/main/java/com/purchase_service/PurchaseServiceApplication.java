package com.purchase_service;
import com.shared_app.PurchaseService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

@SpringBootApplication
public class PurchaseServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PurchaseServiceApplication.class, args);

		try {
			LocateRegistry.createRegistry(1099);
			PurchaseService service = new PurchaseServiceImpl();
			Naming.rebind("rmi://localhost:1099/PurchaseService", service);
			System.out.println("PurchaseService registrado no RMI.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
