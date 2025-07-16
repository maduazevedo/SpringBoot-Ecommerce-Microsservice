package com.purchase_service;

import com.shared_app.PurchaseService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import java.util.*;
import com.shared_app.Product;


public class PurchaseServiceImpl extends UnicastRemoteObject implements PurchaseService {
    private Map<String, Product> products = new HashMap<>();
    public PurchaseServiceImpl() throws RemoteException {
        super();
        products.put("PROD-001", new Product("PROD-001", "Notebook", 2500.0, 10));
        products.put("PROD-002", new Product("PROD-002", "Mouse", 50.0, 25));
        products.put("PROD-003", new Product("PROD-003", "Teclado", 150.0, 15));
        products.put("PROD-004", new Product("PROD-004", "Monitor", 800.0, 8));
        products.put("PROD-005", new Product("PROD-005", "Headset", 120.0, 20));
        products.put("PROD-006", new Product("PROD-006", "Smartphone", 1200.0, 12));
        products.put("PROD-007", new Product("PROD-007", "Tablet", 600.0, 18));
        products.put("PROD-008", new Product("PROD-008", "Impressora", 350.0, 6));
        products.put("PROD-009", new Product("PROD-009", "Webcam", 180.0, 30));

    }
    @Override
    public String addProduct(String name, double price, int stock) throws RemoteException {
        String id = "PROD-" + UUID.randomUUID().toString().substring(0, 8);
        Product newProduct = new Product(id, name, price, stock);
        products.put(id, newProduct);
        return id;
    }

    @Override
    public List<Product> listAllProducts() throws RemoteException {
        return new ArrayList<>(products.values());
    }

    @Override
    public boolean updateProduct(String productId, String name, double price, int stock) throws RemoteException {
        Product product = products.get(productId);
        if (product != null) {
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String productId) throws RemoteException {
        return products.remove(productId) != null;
    }
    @Override
    public String processOrder(String product, int quantity) {
        return "ORDER-" + UUID.randomUUID();
    }
}
