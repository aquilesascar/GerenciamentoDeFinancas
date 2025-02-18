import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe que controla a interação entre usuário e sistema
public class ControleFinanceiro {
    protected Usuario usuario;
    // Retirar o array de categorias depois, pois ja se encontra em categoriaService
    protected ArrayList<Categoria> categorias;
    private RelatorioService relatorioService;
    private MenuPrincipal menuPrincipal;

    public ControleFinanceiro() {
        /*
        RELATORIO SERVICE FAZENDO INFINITAS CHAMADAS RECURSIVAS!
        ERRO DE STACK OVERFLOW!
         */
        // relatorioService = new RelatorioService();
        categorias = new ArrayList<>();//Precisa deicidir quais categorias
    }

    public void executar() {
        // Implementação do fluxo principal do sistema
        Scanner scanner = new Scanner(System.in);

        // gerarDadosDeTeste();

        /*
        Tirar o comentario quando quiser usar o banco de dados
         */
        // Banco de dados tenta carregar o usuario
        usuario = ConexaoSQLite.carregarUsuario();

        // Caso nao haja usuario existente e dado ao usuario a opcao de configurar um novo usuario
        if(usuario == null) {
            // Configuracao Inicial do Usuario
            // (Caso nao Haja usuario Registrado ou nao tenha possivel fazer uma requisicao do banco de dados)
            configInicialUsuario();
        }

        List<Cartao> cartoesUsuario = ConexaoSQLite.carregarCartao();
        if(cartoesUsuario != null) {
            for(Cartao cartao : cartoesUsuario) {
                usuario.adicionarCartao(cartao);
            }
        }

        System.out.println("Usuario: " + usuario.getNome());
        menuPrincipal = new MenuPrincipal(usuario);
        menuPrincipal.exibir();
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

        ConexaoSQLite.adicionarUsuarioDB(usuario);
    }

    public void gerarDadosDeTeste() {
        // Usuario de teste
        usuario = new Usuario("Seu Jose", 5000);

        //Categorias pre-definidas
        categorias.add(new Categoria("Entretenimento"));
        categorias.add(new Categoria("Alimentacao"));
        categorias.add(new Categoria("Despesas"));
        categorias.add(new Categoria("Educacao"));

        Categoria entretenimento = categorias.get(0);
        Categoria alimentacao = categorias.get(1);
        Categoria despesa = categorias.get(2);
        Categoria educacao = categorias.get(3);

        LocalDate dataAgr = LocalDate.now();
        LocalDate dataChurrasco = LocalDate.of(2025, 2, 12);
        LocalDate dataEducacaoBonus = LocalDate.of(2025, 1, 27);

    }

}
