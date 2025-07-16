package com.payment_service;

import com.shared_app.PaymentService;
import com.shared_app.PurchaseService;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PaymentServiceImpl extends UnicastRemoteObject implements PaymentService {
    public PaymentServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String pay(String orderId, double amount) {
        try{

            PurchaseService purchaseService = (PurchaseService) Naming.lookup("rmi://localhost:1099/PurchaseService");
            boolean isValid = purchaseService.validateOrder(orderId, amount);

            if(!isValid){
                return "[ERRO] Pedido inválido ou valor incorreto.";
            }
            return  "[PAGAMENTO EFETUADO] ID do Pedido: " + orderId + ", Valor: R$" + String.format("%.2f", amount);

        } catch (Exception e) {
            return "[ERRO] Falha ao validar pedido: " + e.getMessage();
        }
    }
}
