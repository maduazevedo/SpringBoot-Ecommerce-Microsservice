package com.shared_app;

import com.shared_app.shared_entity.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PurchaseService extends Remote {

    String addProduct(String name, double price, int stock) throws RemoteException;
    List<Product> listAllProducts() throws RemoteException;
    String updateProduct(String productId, String name, double price, int stock) throws RemoteException;
    boolean deleteProduct(String productId) throws RemoteException;
    String processOrder(String product, int quantity) throws RemoteException;
    boolean validateOrder(String orderId, double amount) throws RemoteException;

}
