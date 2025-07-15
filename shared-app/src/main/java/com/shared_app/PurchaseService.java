package com.shared_app;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PurchaseService extends Remote {
    String processOrder(String product, int quantity) throws RemoteException;
}
