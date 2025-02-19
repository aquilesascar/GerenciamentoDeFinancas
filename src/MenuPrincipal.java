import java.util.Scanner;

public class MenuPrincipal extends Menu{
    private MenuTransacao menuTransacao;
    private MenuCartao menuCartao;
    private MenuCategoria menuCategoria;
    private Usuario usuario;

    public MenuPrincipal(Usuario usuario){
        // Inicializa as classes dos menus
        super(usuario);
        menuCartao = new MenuCartao(usuario);
        menuCategoria = new MenuCategoria(usuario);
        menuTransacao = new MenuTransacao(usuario);
    }


    @Override
    public void exibir() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Digite a Opcao Desejada: ");
            System.out.println("[1] Transacoes");
            System.out.println("[2] Cartoes");
            System.out.println("[3] Relatorio");
            System.out.println("[4] Categoria");
            System.out.println("[0] Sair");

            int opcaoMenuPrincipal = scanner.nextInt();
            switch (opcaoMenuPrincipal) {
                case 1:
                    // menu relacionado as transacoes
                    menuTransacao.exibir();
                    break;
                case 2:
                    // menu relacionado aos cartoes
                    menuCartao.exibir();
                    break;
                case 3:
                    // Gera o relatorio para o usuario
                    // Crirar um menu so para os relatorios
                    // menuRelatorio()
                    RelatorioService.gerarRelatorioMesAno();
                    break;
                case 4:
                    menuCategoria.exibir();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opcao Invalida");
                    break;
            }
        }
    }
}
