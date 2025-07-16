package com.purchase_service;

import com.shared_app.shared_entity.Product;
import com.shared_app.PurchaseService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;
import java.util.*;


public class PurchaseServiceImpl extends UnicastRemoteObject implements PurchaseService {

    private Map<String, Product> products = new HashMap<>();
    public Map<String, Double> orders = new HashMap<>();

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

        if(name == null){
            return "[ERRO] Insira um nome no seu produto";
        }
        if(price < 1){
            return "[ERRO] Não é possível adicionar um preço nulo ou negativo";
        }
        if(stock < 1){
            return "[ERRO] Não é possível adicionar uma quantidade nula ou negativa no estoque";
        }

        Product newProduct = new Product(id, name, price, stock);
        products.put(id, newProduct);
        return id;
    }

    @Override
    public List<Product> listAllProducts() throws RemoteException {
        return new ArrayList<>(products.values());
    }

    @Override
    public String updateProduct(String productId, String name, double price, int stock) throws RemoteException {
            Product product = products.get(productId);

            if (product != null) {

                if (price < 1) {
                    return "[ERRO] Não é possível atualizar o valor do produto para nulo ou negativo";
                }

                if (stock < 1) {
                    return "[ERRO] Não é possível atualizar a quantidade do produto para nula ou negativa";
                }

                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                return "Produto '" + product.getName() + "' atualizado com sucesso!";
            }
            return "[ERRO] Id do produto incorreto ou inexistente";
    }

    @Override
    public boolean deleteProduct(String productId) throws RemoteException {
        return products.remove(productId) != null;
    }

    @Override
    public String processOrder(String productId, int quantity) {
        Product product = products.get(productId);

        if (product == null) {
            return "\n[ERRO] Produto não encontrado.";
        }

        if(quantity < 1){
            return "\n[ERRO] Não é possível comprar 0 produtos ou quantidade negativa.";
        }

        if (product.getStock() < quantity) {
            return "\n[ERRO] Estoque insuficiente para a quantidade solicitada.";
        }

        product.setStock(product.getStock() - quantity);
        double total = product.getPrice() * quantity;
        String orderId = "ORDER-" + UUID.randomUUID();
        orders.put(orderId, total);

        return "\n[PEDIDO CRIADO]\n" +
                "ID do Pedido: " + orderId + "\n" +
                "Produto: " + product.getName() + "\n" +
                "Quantidade: " + quantity + "\n" +
                "Total: R$ " + String.format("%.2f", total) + "\n" +
                "\nPor gentileza, realize o pagamento do seu pedido.";
    }

    @Override
    public boolean validateOrder(String orderId, double amount) throws RemoteException {
        return orders.containsKey(orderId) && Objects.equals(orders.get(orderId), amount);
    }
}
