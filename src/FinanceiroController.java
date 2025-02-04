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

        // Configuracao Inicial do Usuario
        configInicialUsuario();

        // Realiza uma Transacao
        realizarTransacao();
    }

    private void configInicialUsuario() {
        /*
        Funcao responsavel para configuracao inicial do usuario:
        Nome, Saldo Inicial, Usuario recebe ou nao salario, salario, data de recebimento do salario.
         */

        // Adicionar validadores/verificadores posteriormente
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome do Usuario: ");
        String nomeUsuario = scanner.nextLine();

        System.out.print("Saldo Inicial: ");
        double saldoInicial = scanner.nextDouble();

        usuario = new Usuario(nomeUsuario, saldoInicial);
    }

    private void realizarTransacao() {
        transacaoService = new TransacaoService();
        transacaoService.criarTransacao(usuario);
    }
}
