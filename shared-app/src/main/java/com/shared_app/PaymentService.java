package com.shared_app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PaymentService extends Remote {
    String pay(String orderId, double amount) throws RemoteException;
}
