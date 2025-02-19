import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Classe que controla a interação entre usuário e sistema
public class ControleFinanceiro {
    protected Usuario usuario;
    protected ArrayList<Categoria> categorias;
    private List<TransacaoRecorrente> transacoesRecorrentes;
    private MenuPrincipal menuPrincipal;

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ControleFinanceiro() {
        categorias = new ArrayList<>();//Precisa deicidir quais categorias
        transacoesRecorrentes = new ArrayList<>();
    }

    public void executar() {
        // Implementação do fluxo principal do sistema
        Scanner scanner = new Scanner(System.in);

        usuario = ConexaoSQLite.carregarUsuario();
        categorias = (ArrayList<Categoria>) ConexaoSQLite.carregarCategorias();

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

        List<Transacao> transacoes = ConexaoSQLite.carregarTransacoes();
        if(transacoes != null) {
            for(Transacao transacao : transacoes) {
                usuario.adicionarTransacao(transacao);
            }

            gerarTransacoesRecorrentes();
        }

        atualizarTransacoesRecorrentes();

        System.out.println("Usuario: " + usuario.getNome());
        menuPrincipal = new MenuPrincipal(usuario);
        menuPrincipal.exibir();
    }

    private void atualizarTransacoesRecorrentes() {

        LocalDate hoje = LocalDate.now();
        TransacaoRecorrente novaTransacaoRecorrente;

        List<TransacaoRecorrente> transacoesFiltradas = new ArrayList<>(transacoesRecorrentes.stream()
                .collect(Collectors.toMap(
                        TransacaoRecorrente::getDescricao,
                        t -> t,
                        (t1, t2) -> t1.getData().isAfter(t2.getData()) ? t1 : t2
                )).values());

        // Exibir resultado
        System.out.println("Transações filtradas:");
        transacoesFiltradas.forEach(System.out::println);



        for(TransacaoRecorrente transacao : transacoesFiltradas) {
            if(transacao.isAtiva()) {
                // Verificar se ja se passaram 30 dias desde a ultima transacao recorrente
                LocalDate dataRenovacao = transacao.getData().plusDays(30);

                while(dataRenovacao.isBefore(hoje) || dataRenovacao.isEqual(hoje)) {
                    // Adiciona a transacao recorrente
                    String tipo = transacao.getTipo();
                    String descricao = transacao.getDescricao();
                    double valor = transacao.getValor();
                    Categoria categoria = transacao.getCategoria();
                    String metodoPagamento = transacao.getMetodoPagamento();


                    novaTransacaoRecorrente = new TransacaoRecorrente(tipo, descricao, valor, dataRenovacao, categoria, metodoPagamento, true);

                    usuario.adicionarTransacao(novaTransacaoRecorrente);
                    ConexaoSQLite.adicionarTransacaoDB(novaTransacaoRecorrente);

                    dataRenovacao = dataRenovacao.plusDays(30);
                }
            }
        }
    }

    private void configInicialUsuario() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome do Usuario: ");
        String nomeUsuario = scanner.nextLine();

        usuario = new Usuario(nomeUsuario);

        ConexaoSQLite.adicionarUsuarioDB(usuario);
    }

    public void gerarTransacoesRecorrentes() {
        List<Transacao> todasTransacoes = this.usuario.getTransacoes();

        for(Transacao transacao : todasTransacoes) {
            if(transacao instanceof TransacaoRecorrente) {
                transacoesRecorrentes.add((TransacaoRecorrente) transacao);
            }
        }
    }
}
