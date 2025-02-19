import java.util.Scanner;

public class MenuTransacao extends Menu {
    TransacaoService transacaoService;

    public MenuTransacao(Usuario usuario) {
        super(usuario);
        transacaoService = new TransacaoService(usuario);
    }

    @Override
    public void exibir() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Digite a opcao Desejada: ");
            System.out.println("[1] Adicionar Transacao");
            System.out.println("[2] Remover Transacao");
            System.out.println("[3] Listar Transacoes do Mes Atual");
            System.out.println("[4] Listar Transacoes Futuras");
            System.out.println("[5] Visualizar Fatura do Cartao");
            System.out.println("[6] Cancelar Transacao Recorrente");
            System.out.println("[0] Sair");

            int opcaoTransacao = scanner.nextInt();
            switch (opcaoTransacao) {
                case 1:
                    // Adiciona uma nova Transacao
                    transacaoService.realizarTransacao();
                    break;
                case 2:
                    // Remove uma transacao
                    transacaoService.removerTransacao();
                    break;
                case 3:
                    // lista as transacoes do Mes Atual
                    transacaoService.listarTransacoesMesAtual();
                    break;
                case 4:
                    // Lista as transacoes futuras
                    transacaoService.listarTransacoesFuturas();
                    break;
                case 5:
                    // Ve a fatura do cartao do usuario (Talvez deixar essa opcao em cartoes)
                    // transacaoService.verFaturaAtual();
                    break;
                case 6:
                    transacaoService.cancelarTransacaoRecorrente();
                case 0:
                    // volta para o menu anterior (menuPrincipal)
                    return;
                default:
                    System.out.println("Opcao Invalida");
                    break;
            }
        }
    }


}
