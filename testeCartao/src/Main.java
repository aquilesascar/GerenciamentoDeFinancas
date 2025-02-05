import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                CartaoDeCredito cartao = new CartaoDeCredito("1234-5678-9876-5432", 5000, new Date());
                List<Categoria> categorias = new ArrayList<>();

                while (true) {
                    System.out.println("1. Fazer compra");
                    System.out.println("2. Gerar fatura");
                    System.out.println("3. Pagar fatura");
                    System.out.println("4. Verificar limite");
                    System.out.println("5. Adicionar categoria");
                    System.out.println("6. Adicionar gasto em categoria");
                    System.out.println("7. Exibir relatório financeiro");
                    System.out.println("8. Sair");
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    if (opcao == 1) {
                        System.out.println("Digite a descrição da compra:");
                        String descricao = scanner.nextLine();
                        System.out.println("Digite o valor da compra:");
                        double valor = scanner.nextDouble();
                        cartao.adicionarCompra(new Transacao(descricao, valor, new Date(), "À vista"));
                    } else if (opcao == 2) {
                        cartao.gerarFaturaDetalhada();
                    } else if (opcao == 3) {
                        cartao.pagarFatura();
                    } else if (opcao == 4) {
                        cartao.verificarLimite();
                    } else if (opcao == 5) {
                        System.out.println("Digite o nome da nova categoria:");
                        String nome = scanner.nextLine();
                        System.out.println("Digite o limite da categoria:");
                        double limite = scanner.nextDouble();
                        categorias.add(new Categoria(nome, limite));
                    } else if (opcao == 6) {
                        System.out.println("Escolha a categoria:");
                        for (int i = 0; i < categorias.size(); i++) {
                            System.out.println((i + 1) + ". " + categorias.get(i).nome);
                        }
                        int escolha = scanner.nextInt();
                        System.out.println("Digite o valor do gasto:");
                        double valor = scanner.nextDouble();
                        categorias.get(escolha - 1).adicionarGasto(valor);
                    } else if (opcao == 7) {
                        RelatorioFinanceiro.gerarRelatorio(categorias);
                    } else if (opcao == 8) {
                        break;
                    }
                }
                scanner.close();

            }
}
