import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransacaoService {
    private Usuario usuario;
    private List<Transacao> transacoes;
    CategoriaService categoriaService;


    public TransacaoService(Usuario usuario) {
        this.usuario = usuario;
        transacoes = new ArrayList<Transacao>();
        categoriaService = new CategoriaService(usuario);
    }

    public void listarTransacoesMesAtual() {
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        List<Transacao> transacoesMesAtual = usuario.getTransacoes().stream()
                .filter(transacao -> transacao.getData().getMonthValue() == mesAtual && transacao.getData().getYear() == anoAtual)
                .toList();

        if (transacoesMesAtual.isEmpty()) {
            System.out.println("Nenhuma transação encontrada para o mês atual.");
        } else {
            System.out.println("Transações do mês atual:");
            transacoesMesAtual.forEach(transacao -> System.out.println(
                    transacao.getDescricao() + " - " + transacao.getValor() + " - " + transacao.getData()
            ));
        }
    }

    public void listarTransacoesFuturas() {
        LocalDate hoje = LocalDate.now();

        List<Transacao> transacoesFuturas = usuario.getTransacoes().stream()
                .filter(transacao -> transacao.getData().isAfter(hoje))
                .toList();

        if (transacoesFuturas.isEmpty()) {
            System.out.println("Nenhuma transação futura encontrada.");
        } else {
            System.out.println("Transações futuras:");
            transacoesFuturas.forEach(transacao -> System.out.println(
                    transacao.getDescricao() + " - " + transacao.getValor() + " - " + transacao.getData()
            ));
        }
    }

    public void gerarTransacoesRecorrentes() {
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        List<Transacao> copiaTransacoes = new ArrayList<>(usuario.getTransacoes());

        for (Transacao transacao : copiaTransacoes) {
            if (transacao instanceof TransacaoRecorrente) {
                TransacaoRecorrente transacaoRecorrente = (TransacaoRecorrente) transacao;
                LocalDate proximaData = transacaoRecorrente.getProximaData();

                // Verifica se a transação recorrente deve ser gerada este mês
                if (proximaData.getMonthValue() == mesAtual && proximaData.getYear() == anoAtual) {
                    // Cria uma nova transação para o mês atual
                    Transacao novaTransacao = new Transacao(
                            transacaoRecorrente.getTipo(),
                            transacaoRecorrente.getDescricao(),
                            transacaoRecorrente.getValor(),
                            proximaData,
                            transacaoRecorrente.getCategoria()
                    );

                    // Adiciona a nova transação ao usuário
                    this.usuario.adicionarTransacao(novaTransacao);
                    System.out.println("NOVA TRANSACAO " + novaTransacao.getDescricao());

                    // Atualiza a próxima data da transação recorrente
                    transacaoRecorrente.atualizarProximaData();

                    // Salva a nova transação no banco de dados
                    // ConexaoSQLite.adicionarTransacaoNoBanco(novaTransacao, usuario.getNome());

                    System.out.println("Transação recorrente gerada: " + novaTransacao.getDescricao());
                }
            }
        }
    }

    public void realizarTransacao() {
        Scanner scanner = new Scanner(System.in);

        // Validador de categorias
        Categoria categoriaCompra = validarCategoriaTransacao();

        System.out.println("Qual o tipo da transacao? (Receita/Despesa)");
        System.out.println("[1] Receita\n[2] Despesa");
        int tipoTransacao = scanner.nextInt(); // Alterado para int
        scanner.nextLine(); // Consumir a nova linha

        System.out.println("Qual a descricao que gostaria de colocar para a transacao? ");
        String descricaoTransacao = scanner.nextLine();

        System.out.println("Preco da conta: ");
        double valorTransacao = scanner.nextDouble();

        System.out.println("Qual foi a data da transacao? ");
        System.out.println("Dia: ");
        int dia = scanner.nextInt();
        System.out.println("Mês:");
        int mes = scanner.nextInt();
        System.out.println("Ano: ");
        int ano = scanner.nextInt();
        LocalDate dataTransacao = LocalDate.of(ano, mes, dia);

        System.out.println("É uma transacao recorrente? ");
        System.out.println("[1] Sim\n[2] Nao");
        int opcaoTransacaoRecorrente = scanner.nextInt();

        // Atualizar saldo do usuário
        double saldoAtual = usuario.getSaldo();
        if (tipoTransacao == 1) { // Receita
            saldoAtual += valorTransacao;
        } else if (tipoTransacao == 2) { // Despesa
            saldoAtual -= valorTransacao;
        }
        this.usuario.setSaldo(saldoAtual);

        // Atualizar saldo no banco de dados
        ConexaoSQLite.atualizarSaldoNoBanco(this.usuario.getNome(), saldoAtual);

        // Criar transação
        if (opcaoTransacaoRecorrente == 1) {
            // Transação recorrente
            TransacaoRecorrente transacao = new TransacaoRecorrente(
                    (tipoTransacao == 1) ? "receita" : "despesa",
                    descricaoTransacao,
                    valorTransacao,
                    dataTransacao,
                    categoriaCompra
            );
            usuario.adicionarTransacao(transacao);
            System.out.println("✅ Transação recorrente adicionada com sucesso!");
        } else {
            // Transação única
            TransacaoVariavel transacaoVariavel = new TransacaoVariavel(
                    (tipoTransacao == 1) ? "receita" : "despesa",
                    descricaoTransacao,
                    valorTransacao,
                    dataTransacao,
                    categoriaCompra,
                    1 // Parcelas (1 para transação única)
            );
            usuario.adicionarTransacao(transacaoVariavel);
            System.out.println("✅ Transação única adicionada com sucesso!");
        }

        System.out.println("Saldo atualizado: " + usuario.getSaldo());
    }

    private Categoria validarCategoriaTransacao() {
        Scanner scanner = new Scanner(System.in);
        List<Categoria> categorias = this.categoriaService.getCategorias();

        while(true) {
            System.out.println("Seleciona a categoria da Transacao: ");
            System.out.println("[1] Adicionar Categoria");

            if(!categorias.isEmpty()) {
                for(int i = 0; i < categorias.size(); i++) {
                    System.out.println("[" + (i+2) + "] " + categorias.get(i).getNome());
                }
            }

            int opcaoCategoria = scanner.nextInt();

            if(opcaoCategoria == 1) {
                categoriaService.criarCategoria();
            }
            else if(opcaoCategoria > (categorias.size() + 2)) {
                System.out.println("Opcao Invalida");
            } else {
                // Refatorar depois (Esta um pouco verboso, mas e a fim de teste apenas)
                int indexCategoria = opcaoCategoria - 2;
                System.out.println("Categoria Selecionada: " + categorias.get(indexCategoria).getNome());
                return categorias.get(indexCategoria);
            }
        }
    }

    public void removerTransacao() {
        Scanner scanner = new Scanner(System.in);

        List<Transacao> transacoesUsuario = usuario.getTransacoes();

        if(transacoesUsuario.isEmpty()) {
            System.out.println("Usuario nao possui nenhuma transacao cadastrada!");
            return;
        }

        while(true) {
            System.out.println("Qual transcao deseja remover: ");
            for(int i = 0; i < transacoesUsuario.size(); i++) {
                System.out.println("[" + (i+1) + "]" + transacoesUsuario.get(i).getDescricao() +
                        ": " + transacoesUsuario.get(i).getData().toString());
            }
            System.out.println("[0] Voltar");
            int opcaoTransacao = scanner.nextInt();

            if(opcaoTransacao == 0) {
                return;
            }

            else if(opcaoTransacao > transacoesUsuario.size()) {
                System.out.println("Opcao Invalida");
            }
            else {
                transacoesUsuario.remove(opcaoTransacao - 1);
                System.out.println("Transacao removida com sucesso!");
                return;
            }
        }
    }

    public void verFaturaAtual() {
        if (usuario.getCartoes().isEmpty()) {
            System.out.println("Nenhum cartão cadastrado.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione o cartão para ver a fatura:");
        // listarCartao();
        int opcaoCartao = scanner.nextInt();

        if (opcaoCartao > 0 && opcaoCartao <= usuario.getCartoes().size()) {
            Cartao cartao = usuario.getCartoes().get(opcaoCartao - 1);
            if (cartao instanceof CartaoDeCredito) {
                CartaoDeCredito cartaoCredito = (CartaoDeCredito) cartao;
                double totalFatura = cartaoCredito.getLimite() - cartaoCredito.getSaldoDisponivel();
                System.out.println("Fatura atual do cartão " + cartaoCredito.getNome() + ": R$" + totalFatura);
            } else {
                System.out.println("Este cartão não é um cartão de crédito.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }
}
