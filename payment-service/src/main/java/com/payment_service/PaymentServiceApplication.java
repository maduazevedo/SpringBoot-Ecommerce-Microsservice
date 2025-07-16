package com.payment_service;
import com.shared_app.PaymentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

@SpringBootApplication
public class PaymentServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);

		try {
			LocateRegistry.createRegistry(1100);
			PaymentService service = new PaymentServiceImpl();
			Naming.rebind("rmi://localhost:1100/PaymentService", service);
			System.out.println("PaymentService registrado no RMI.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
