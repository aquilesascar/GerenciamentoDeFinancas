import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe que controla a interação entre usuário e sistema
public class ControleFinanceiro {
    protected Usuario usuario;
    protected ArrayList<Categoria> categorias;
    private RelatorioService relatorioService;


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

        ConexaoSQLite.conectar();

        gerarDadosDeTeste();

        /*
        Tirar o comentario quando quiser usar o banco de dados
         */
        // Banco de dados tenta carregar o usuario
        // usuario = ConexaoSQLite.carregarUsuario();

        // Caso nao haja usuario existente e dado ao usuario a opcao de configurar um novo usuario
        if(usuario == null) {
            // Configuracao Inicial do Usuario (Caso nao Haja usuario Registrado)
            configInicialUsuario();
        }

        System.out.println("Usuario: " + usuario.getNome());
        menuPrincipal();
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

    //Talvez essa funçao fique eum sublcasse so para trasnferencias de transação
    private void realizarTransacao() {
            Scanner scanner = new Scanner(System.in);

            // Validador de categorias
            Categoria categoriaCompra = validarCategoria();

            System.out.println("Qual o tipo da transacao? (Receita/Despesa)");
            System.out.println("[1] Receita\n[2] Despesa");
            String tipoStr = scanner.nextLine(); // mudar pra int (talvez)

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

            System.out.println("E uma transacao recorrente? ");
            System.out.println("[1] Sim\n[2] Nao");
            int opcaoTransacaoRecorrente = scanner.nextInt();
            switch (opcaoTransacaoRecorrente) {
                case 1:
                    //String tipo, String descricao, double valor, LocalDate data, Categoria categoria
                    TransacaoRecorrente transacao = new TransacaoRecorrente(tipoStr, descricaoTransacao,
                            valorTransacao, dataTransacao, categoriaCompra);
                    usuario.adicionarTransacao(transacao);

                    break;
                case 2:
                    System.out.println("Forma de Pagamnento: ");
                    System.out.println("[1] A vista\n[2] Parcelado ");
                    int opcaoPagamento = scanner.nextInt();
                    TransacaoVariavel transacaoVariavel;
                    switch (opcaoPagamento) {
                        case 1:
                            // a vista
                            //String tipo, String descricao, double valor, LocalDate data, Categoria categoria, int quantParcelas
                            transacaoVariavel = new TransacaoVariavel(tipoStr,descricaoTransacao,valorTransacao,dataTransacao,
                                    categoriaCompra,1);
                            usuario.adicionarTransacao(transacaoVariavel);
                            System.out.println("Transação feita com sucesso!");
                            System.out.println(transacaoVariavel.getDescricao()+"   "+transacaoVariavel.getQuantParcelas()+
                                    "x de "+transacaoVariavel.getValorParcela());
                            break;

                        case 2:
                            System.out.println("Qual a quantidade de parcelas? ");
                            int opcaoParcelas = scanner.nextInt();
                            transacaoVariavel= new TransacaoVariavel(tipoStr,descricaoTransacao,valorTransacao,dataTransacao,
                                    categoriaCompra,opcaoParcelas);
                            usuario.adicionarTransacao(transacaoVariavel);
                            System.out.println("Transação adicionada com sucesso!");
                            System.out.println(transacaoVariavel.getDescricao()+"   "+transacaoVariavel.getQuantParcelas()+
                                    "x de "+transacaoVariavel.getValorParcela()+"   Total: "+transacaoVariavel.getValor());
                            break;
                        default:
                            System.out.println("Esta opção não existe.");
                            break;
                    }

                default:
                    System.out.println("Esta opção nao existe.");
            }
    }

    private void menuPrincipal() {
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
                    menuTransacao();
                    break;
                case 2:
                    // menu relacionado aos cartoes
                    menuCartao();
                    break;
                case 3:
                    // Gera o relatorio para o usuario
                    // gerarRelatorio()
                    break;
                case 4:
                    menuCategoria();
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

    private void menuCategoria() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Digite a opcao desejada: ");
            System.out.println("[1] Adicionar Categoria");
            System.out.println("[2] Remover Categoria");
            System.out.println("[3] Listar Categoria");
            System.out.println("[0] Voltar");

            int opcaoMenuCategoria = scanner.nextInt();

            switch (opcaoMenuCategoria) {
                case 1:
                    criarCategoria();
                    break;
                case 2:
                    removerCategoria();
                    break;
                case 3:
                    listarCategoria();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opcao Invalida!");
            }

        }
    }

    private void menuTransacao() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Digite a opcao Desejada: ");
            System.out.println("[1] Adicionar Transacao");
            System.out.println("[2] Remover Transacao");
            System.out.println("[3] Listar Transacoes do Mes Atual");
            System.out.println("[4] Listar Transacoes Futuras");
            System.out.println("[5] Visualizar Fatura do Cartao");
            System.out.println("[0] Sair");

            int opcaoTransacao = scanner.nextInt();
            switch (opcaoTransacao) {
                case 1:
                    // Adiciona uma nova Transacao
                    realizarTransacao();
                    break;
                case 2:
                    // Remove uma transacao
                    removerTransacao();
                    break;
                case 3:
                    // lista as transacoes do Mes Atual
                    listarTransacoesMesAtual();
                    break;
                case 4:
                    // Lista as transacoes futuras
                    listarTransacoesFuturas();
                    break;
                case 5:
                    // Ve a fatura do cartao do usuario (Talvez deixar essa opcao em cartoes)
                    verFaturaAtual();
                    break;
                case 0:
                    // volta para o menu anterior (menuPrincipal)
                    return;
                default:
                    System.out.println("Opcao Invalida");
                    break;
            }
        }
    }

    private void verFaturaAtual() {
        if (usuario.getCartoes().isEmpty()) {
            System.out.println("Nenhum cartão cadastrado.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecione o cartão para ver a fatura:");
        listarCartao();
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

    private void listarTransacoesMesAtual() {
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

    private void listarTransacoesFuturas() {
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

    private void menuCartao() {
        Scanner scanner = new Scanner(System.in);
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
                    adicionarCartao();
                    break;
                case 2:
                    // Remove um cartao
                    removerCartao();
                    break;
                case 3:
                    // Lista os cartoes
                    listarCartao();
                    break;
                case 0:
                    // volta pro menu principal
                    return;
            }
        }
    }

    private void adicionarCartao() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Qual o tipo de cartao que deseja adicionar?");
            System.out.println("[1] Credito");
            System.out.println("[2] Debito");
            System.out.println("[0] Voltar");
            int opcaoCartao = scanner.nextInt();

            switch (opcaoCartao) {
                case 1, 2:
                    criarCartao(opcaoCartao);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opcao Invalida");
                    break;
            }
        }
    }

    private void criarCartao(int opcaoCartao) {
        final int CREDITO = 1;
        final int DEBITO = 2;

        Scanner scanner = new Scanner(System.in);
        Cartao novoCartao = null;

        System.out.println("Digite o nome do cartao: ");
        // validar
        String nomeCartao = scanner.nextLine();

        //não precisa do número do cartão, melhor por segurança
        /*System.out.println("Digite o numero do cartao: ");
        // validar
        String numeroCartao = scanner.nextLine();
         */
        System.out.println("Digite a bandeira do cartao: ");
        // validar
        String bandeiraCartao = scanner.nextLine();

        if(opcaoCartao == CREDITO) {
            System.out.println("Limite do cartao: ");
            int limiteCartao = scanner.nextInt();

            System.out.println("Saldo disponivel: ");
            int saldoDisponivel = scanner.nextInt();

            System.out.println("Data de Fechamento da Fatura do Cartao");
            System.out.println("Dia: ");
            // Validar
            int diaFechamentoFatura = scanner.nextInt();

            //Validar
            System.out.println("Mes de Fechamento do cartao (1...12): ");
            int mesFechamentoFatura = scanner.nextInt();

            int anoAtual = LocalDate.now().getYear();
            LocalDate dataFechamento = LocalDate.of(anoAtual, mesFechamentoFatura, diaFechamentoFatura);

            novoCartao = new CartaoDeCredito(nomeCartao, bandeiraCartao, limiteCartao, saldoDisponivel, dataFechamento);
        }

        else if (opcaoCartao == DEBITO) {
            novoCartao = new CartaoDeDebito(nomeCartao, bandeiraCartao);
        }

        usuario.adicionarCartao(novoCartao);
    }

    private void removerCartao() {
        if(usuario.getCartoes().isEmpty()) {
            System.out.println("Usuario nao possui cartoes cadastrados!");
            return;
        }
        int cartoesLen = usuario.getCartoes().size();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Qual cartao deseja remover: ");

            for(int i = 0; i < cartoesLen; i++) {
                System.out.println("[" + (i+1) + "] " + usuario.getCartoes().get(i).getNome());
            }
            System.out.println("[0] Sair");

            int opcaoCartao = scanner.nextInt();

            if(opcaoCartao > 0 && opcaoCartao <= cartoesLen) {
                // Conferir
                usuario.getCartoes().remove(opcaoCartao - 1);
                break;
            }
            else if (opcaoCartao == 0) {
                break;
            } else {
                System.out.println("Opcao Invalida");
            }
        }
    }

    private void listarCategoria() {
        if (categorias.isEmpty()){
            System.out.println("Nao ha categorias criadas");
            return;
        }

        for(Categoria cat: categorias) {
            System.out.println(cat.getNome());
        }
    }

    private void listarCartao() {
        for(int i = 0; i < usuario.getCartoes().size(); i++) {
            System.out.println("[" + (i+1) + "] " + usuario.getCartoes().get(i).getNome());
        }
    }

    private Categoria validarCategoria() {
        Scanner scanner = new Scanner(System.in);

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
                criarCategoria();
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

    private void criarCategoria() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Nome do categoria: ");
            String nomeCategoria = scanner.nextLine();

            if(nomeCategoria.isEmpty()) {
                System.out.println("O campo de preenchimento nao pode estar vazio! Tente Novamente!");
            } else {
                Categoria novaCategoria = new Categoria(nomeCategoria);
                categorias.add(novaCategoria);
                return;
            }
        }
    }

    private void removerCategoria() {
        Scanner scanner = new Scanner(System.in);
        if(categorias.isEmpty()) {
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
                categorias.remove(opcaoCategoria - 1);
                return;
            }
        }
    }

    private void removerTransacao() {
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

        usuario.adicionarTransacao(new Transacao("despesa", "netflix", 45.0, dataAgr, entretenimento));
        usuario.adicionarTransacao(new Transacao("despesa", "churrasco", 280.55, dataChurrasco, alimentacao));
        usuario.adicionarTransacao(new Transacao("receita", "bolsa", 100, dataEducacaoBonus, educacao));
    }

}
