import java.time.LocalDate;
import java.util.Scanner;

// Classe com a lógica de negócios para transações
public class TransacaoService {
    public void adicionarTransacao(Usuario usuario, Transacao transacao) {
        // Implementação
    }

    public void removerTransacao(Usuario usuario, Transacao transacao) {
        // Implementação
    }

    public void criarTransacao(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Categoria da Transacao/Conta: ");
        String categoriaStr = scanner.nextLine();

        System.out.println("Qual o tipo da transacao? (Receita/Despesa)");
        System.out.println("[1] Receita\n[2] Despesa");
        String tipoStr = scanner.nextLine(); // mudar pra int (talvez)

        System.out.println("Qual a descricao que gostaria de colocar para a transacao? ");
        String descricaoTransacao = scanner.nextLine();

        // Categorias Dinamicas
        Categoria categoriaTransacao = new Categoria(categoriaStr);

        System.out.println("Preco da conta: ");
        double valorTransacao = scanner.nextDouble();

        System.out.println("Forma de Pagamnento: ");
        System.out.println("[1] A vista\n[2] Parcelado ");
        int opcaoPagamento = scanner.nextInt();

        switch (opcaoPagamento) {
            case 1:
                // a vista
                System.out.println("E uma transacao recorrente? ");
                System.out.println("[1] Sim\n[2] Nao");
                int opcaoTransacaoRecorrente = scanner.nextInt();
                break;
            case 2:
                System.out.println("Qual a quantidade de parcelas? ");
                int opcaoParcelas = scanner.nextInt();
                valorTransacao = calcularValorParcelas(valorTransacao, opcaoParcelas);
                break;
            default:
                break;
        }

        System.out.println("Preco final: " + valorTransacao);
        LocalDate dataAtual = LocalDate.now();

        Transacao novaTransacao = new Transacao(tipoStr, descricaoTransacao, valorTransacao, dataAtual, categoriaTransacao);
        System.out.println("Transacao realizada com sucesso!");

        usuario.adicionarTransacao(novaTransacao);
    }

    public double calcularValorParcelas(double precoProduto, int qtParcelas) {
        return precoProduto / qtParcelas;
    }

}
