import java.util.Scanner;

public class MenuCategoria extends Menu{
    CategoriaService categoriaService;

    public MenuCategoria(Usuario usuario) {
        super(usuario);
        categoriaService = new CategoriaService(usuario);
    }

    @Override
    public void exibir() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------------------------------------");
        System.out.println("MENU CATEGORIAS");
        System.out.println("-----------------------------------------------------");

        while(true) {
            System.out.println("Digite a opcao desejada: ");
            System.out.println("[1] Adicionar Categoria");
            System.out.println("[2] Remover Categoria");
            System.out.println("[3] Listar Categoria");
            System.out.println("[0] Voltar");

            int opcaoMenuCategoria = scanner.nextInt();

            switch (opcaoMenuCategoria) {
                case 1:
                    categoriaService.criarCategoria();
                    break;
                case 2:
                    categoriaService.removerCategoria();
                    break;
                case 3:
                    categoriaService.listarCategoria();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opcao Invalida!");
            }
        }
    }
}
