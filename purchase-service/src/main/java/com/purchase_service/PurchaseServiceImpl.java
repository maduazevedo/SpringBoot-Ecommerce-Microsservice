package com.purchase_service;

import com.shared_app.PurchaseService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class PurchaseServiceImpl extends UnicastRemoteObject implements PurchaseService {
    public PurchaseServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String processOrder(String product, int quantity) {
        return "ORDER-" + UUID.randomUUID();
    }
}
