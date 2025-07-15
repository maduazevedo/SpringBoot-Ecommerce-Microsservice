package com.payment_service;

import com.shared_app.PaymentService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PaymentServiceImpl extends UnicastRemoteObject implements PaymentService {
    public PaymentServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String pay(String orderId, double amount) {
        return "Pagamento de R$" + amount + " processado para " + orderId;
    }
}
