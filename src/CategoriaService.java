import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoriaService {
    private Usuario usuario;
    private List<Categoria> categorias;

    public CategoriaService(Usuario usuario) {
        this.usuario = usuario;
        categorias = ConexaoSQLite.carregarCategorias();
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void criarCategoria() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Nome do categoria: ");
            String nomeCategoria = scanner.nextLine();

            if(nomeCategoria.isEmpty()) {
                System.out.println("O campo de preenchimento nao pode estar vazio! Tente Novamente!");
            } else {
                Categoria novaCategoria = new Categoria(nomeCategoria);
                categorias.add(novaCategoria);
                ConexaoSQLite.adicionarCategoriaDB(novaCategoria);
                return;
            }
        }
    }

    public void listarCategoria() {
        if (this.categorias.isEmpty() || this.categorias == null) {
            System.out.println("Nao ha categorias criadas");
            return;
        }

        for(Categoria cat: categorias) {
            System.out.println(cat.getNome());
        }
    }

    public void removerCategoria() {
        Scanner scanner = new Scanner(System.in);
        if(this.categorias.isEmpty()) {
            System.out.println("Nao ha categorias para serem removidas!");
            return;
        }

        while(true) {
            System.out.println("Seleciona a categoria: ");
            for(int i = 0; i < categorias.size(); i++) {
                System.out.println("[" + (i+1) + "] " + categorias.get(i).getNome());
            }
            System.out.println("[0] Voltar");
            int opcaoCategoria = scanner.nextInt();

            if(opcaoCategoria == 0) {
                return;
            }

            if(opcaoCategoria > categorias.size()) {
                System.out.println("Opcao Invalida");
            }
            else {
                Categoria categoriaRemover = categorias.get(opcaoCategoria-1);
                ConexaoSQLite.removerCategoriaBD(categoriaRemover.getNome());

                categorias.remove(opcaoCategoria - 1);
                return;
            }
        }
    }
}
