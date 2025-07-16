package com.client_app;

import com.shared_app.shared_entity.Product;
import com.shared_app.PurchaseService;
import com.shared_app.PaymentService;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;


import java.rmi.Naming;

@SpringBootApplication
public class ClientAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClientAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			PurchaseService purchase = (PurchaseService) Naming.lookup("rmi://localhost:1099/PurchaseService");
			PaymentService payment = (PaymentService) Naming.lookup("rmi://localhost:1100/PaymentService");
			Scanner scanner = new Scanner(System.in);

			System.out.println("=== SISTEMA DE E-COMMERCE ===");
			System.out.println("Conectado aos microserviços!");

			while (true) {
				System.out.println("\n--- MENU PRINCIPAL ---");
				System.out.println("1. Listar produtos");
				System.out.println("2. Adicionar produto");
				System.out.println("3. Atualizar produto");
				System.out.println("4. Remover produto");
				System.out.println("5. Criar pedido");
				System.out.println("6. Processar pagamento");
				System.out.println("0. Sair");
				System.out.print("Digite sua opção: ");

				int opcao = scanner.nextInt();
				scanner.nextLine(); // Limpar buffer

				switch (opcao) {
					case 1: // Listar produtos
						System.out.println("\n=== PRODUTOS DISPONÍVEIS ===");
						List<Product> produtos = purchase.listAllProducts();
						if (produtos.isEmpty()) {
							System.out.println("Nenhum produto cadastrado.");
						} else {
							for (Product p : produtos) {
								System.out.println(p.getId() + " - " + p.getName() +
										" - R$" + p.getPrice() + " (estoque: " + p.getStock() + ")");
							}
						}
						break;

					case 2: // Adicionar produto
						System.out.println("\n=== ADICIONAR PRODUTO ===");
						System.out.print("Nome do produto: ");
						String nome = scanner.nextLine();

						System.out.print("Preço: R$");
						String precoInput = scanner.nextLine().replace(",", ".");

						double preco;
						try {
							preco = Double.parseDouble(precoInput);
						} catch (NumberFormatException e) {
							System.out.println("[ERRO] Valor inválido! Digite um número válido para preço.");
							break;
						}

						System.out.print("Estoque: ");
						String estoqueInput = scanner.nextLine();

						int estoque;
						try {
							estoque = Integer.parseInt(estoqueInput);
						} catch (NumberFormatException e) {
							System.out.println("[ERRO] Valor inválido! Digite um número inteiro para estoque.");
							break;
						}

						String novoId = purchase.addProduct(nome, preco, estoque);
						System.out.println("Produto adicionado com ID: " + novoId);
						break;


					case 3: // Atualizar produto
						System.out.println("\n=== ATUALIZAR PRODUTO ===");
						System.out.print("ID do produto: ");
						String idUpdate = scanner.nextLine();
						System.out.print("Novo nome: ");
						String novoNome = scanner.nextLine();
						System.out.print("Novo preço: R$");
						String priceInput = scanner.nextLine().replace(",", ".");
						double novoPreco;
						try {
							novoPreco = Double.parseDouble(priceInput);
						} catch (NumberFormatException e) {
							System.out.println("[ERRO] Valor inválido! Digite um número no campo de preço.");
							break;
						}

						System.out.print("Novo estoque: ");
						String estoqueeInput = scanner.nextLine();  // variável com um "e"

						int estoquee;
						try {
							estoque = Integer.parseInt(estoqueeInput);
						} catch (NumberFormatException e) {
							System.out.println("[ERRO] Valor inválido! Digite um número inteiro para estoque.");
							break;
						}
						String atualizado = purchase.updateProduct(idUpdate, novoNome, novoPreco, estoque);
						System.out.println(atualizado);
						break;

					case 4: // Remover produto
						System.out.println("\n=== REMOVER PRODUTO ===");
						System.out.print("ID do produto: ");
						String idDelete = scanner.nextLine();

						boolean removido = purchase.deleteProduct(idDelete);
						if (removido) {
							System.out.println("Produto removido com sucesso!");
						} else {
							System.out.println("Produto não encontrado!");
						}
						break;

					case 5: // Criar pedido
						System.out.println("\n=== CRIAR PEDIDO ===");
						System.out.print("Insira o id do produto: ");
						String produtId = scanner.nextLine();
						System.out.print("Quantidade: ");
						int quantidade;
						try {
							quantidade = scanner.nextInt();
						} catch (NumberFormatException e) {
							System.out.println("[ERRO] Valor inválido! Digite um número no campo de quantidade.");
							break;
						}
						scanner.nextLine();

						String orderResponse = purchase.processOrder(produtId, quantidade);
						System.out.println(orderResponse);
						break;

					case 6: // Processar pagamento
						System.out.println("\n=== PROCESSAR PAGAMENTO ===");
						System.out.print("ID do pedido: ");
						String orderIdPay = scanner.nextLine();
						System.out.print("Valor: R$");
						double valor = scanner.nextDouble();
						String resultado = payment.pay(orderIdPay, valor);
						System.out.println(resultado);
						break;

					case 0: // Sair
						System.out.println("Saindo do sistema...");
						return;

					default:
						System.out.println("Opção inválida!");
				}
			}

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			e.printStackTrace();
		}
	}
}