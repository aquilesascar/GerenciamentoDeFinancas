import java.time.LocalDate;
import java.util.Scanner;

// Classe que controla a interação entre usuário e sistema
public class FinanceiroController {
    private Usuario usuario;
    private TransacaoService transacaoService;
    private RelatorioService relatorioService;

    public void executar() {
        // Implementação do fluxo principal do sistema
        Scanner scanner = new Scanner(System.in);
        usuario = new Usuario("Juca");

        // Criar um Menu
        // Perguntar o saldo do usuario
        // Perguntar se usuario recebe salario
        // Possibilidade de adicionar saldo


        System.out.println("Categoria da Transacao/Conta: ");
        String categoriaStr = scanner.nextLine();

        System.out.println("Qual o tipo da transacao? (Receita/Despesa)");
        System.out.println("[1] Receita\n [2] Despesa");
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


    private double calcularValorParcelas(double precoProduto, int qtParcelas) {
        return precoProduto / qtParcelas;
    }

}
