package com.shared_app;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PurchaseService extends Remote {

    String addProduct(String name, double price, int stock) throws RemoteException;
    List<Product> listAllProducts() throws RemoteException;
    boolean updateProduct(String productId, String name, double price, int stock) throws RemoteException;
    boolean deleteProduct(String productId) throws RemoteException;
    String processOrder(String product, int quantity) throws RemoteException;
}
