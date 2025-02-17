import java.util.Scanner;

public class MenuCartao extends Menu{
    CartaoService cartaoService;

    public MenuCartao(Usuario usuario) {
        super(usuario);
        cartaoService = new CartaoService(usuario);
    }

    @Override
    public void exibir() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("MENU CARTAO");
        System.out.println("-----------------------------------------------------");

        while(true) {
            System.out.println("Digite a opcao Desejada: ");
            System.out.println("[1] Adicionar Cartao");
            System.out.println("[2] Remover Cartao");
            System.out.println("[3] Listar Cartoes");
            System.out.println("[0] Sair");

            int opcaoCartao = scanner.nextInt();
            switch (opcaoCartao) {
                case 1:
                    // Adiciona um cartao
                    cartaoService.adicionarCartao();
                    break;
                case 2:
                    // Remove um cartao
                    cartaoService.removerCartao();
                    break;
                case 3:
                    // Lista os cartoes
                    cartaoService.listarCartao();
                    break;
                case 0:
                    // volta pro menu principal
                    return;
            }
        }
    }
}
