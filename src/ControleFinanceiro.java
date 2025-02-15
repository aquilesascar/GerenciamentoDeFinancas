import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

// Classe que controla a interação entre usuário e sistema
public class ControleFinanceiro {
    protected Usuario usuario;
    protected ArrayList<Categoria> categorias;
    private RelatorioService relatorioService;


    public ControleFinanceiro() {
        // Usuario de Teste
         usuario = new Usuario("Seu José",5000);


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

        // Banco de dados tenta carregar o usuario
        usuario = ConexaoSQLite.carregarUsuario();

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

            System.out.println("Escolha uma categoria de Transação ");
            imprimirCategorias();

            //Categoria categoriaStr = categorias[scanner.nextInt()];

            // Categoria de Teste
            Categoria categoriaStr = new Categoria(scanner.nextLine());

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
                            valorTransacao, dataTransacao, categoriaStr);
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
                                    categoriaStr,1);
                            usuario.adicionarTransacao(transacaoVariavel);
                            System.out.println("Transação feita com sucesso!");
                            System.out.println(transacaoVariavel.getDescricao()+"   "+transacaoVariavel.getQuantParcelas()+
                                    "x de "+transacaoVariavel.getValorParcela());
                            break;

                        case 2:
                            System.out.println("Qual a quantidade de parcelas? ");
                            int opcaoParcelas = scanner.nextInt();
                            transacaoVariavel= new TransacaoVariavel(tipoStr,descricaoTransacao,valorTransacao,dataTransacao,
                                    categoriaStr,opcaoParcelas);
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

    public void imprimirCategorias() {
        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("Digite ["+i+"] para "+ categorias.get(i).getNome());
        }
    }

    private void menuPrincipal() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Digite a Opcao Desejada: ");
            System.out.println("[1] Transacoes");
            System.out.println("[2] Cartoes");
            System.out.println("[3] Relatorio");
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
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opcao Invalida");
                    break;
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
                    break;
                case 3:
                    // lista as transacoes do Mes Atual
                    break;
                case 4:
                    // Lista as transacoes futuras
                    break;
                case 5:
                    // Ve a fatura do cartao do usuario (Talvez deixar essa opcao em cartoes)
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
                    break;
                case 3:
                    // Lista os cartoes
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

}
