import java.time.LocalDate;
import java.util.Scanner;

// Classe que controla a interação entre usuário e sistema
public class ControleFinanceiro {
    private Usuario usuario;
    private Categoria[] categorias;

    private RelatorioService relatorioService;
    public ControleFinanceiro() {
        usuario = new Usuario("Seu José",5000);
        relatorioService = new RelatorioService();
        categorias = new Categoria[10];//Precisa deicidir quais categorias
    }

    public void executar() {
        // Implementação do fluxo principal do sistema
        Scanner scanner = new Scanner(System.in);

        // Configuracao Inicial do Usuario
        configInicialUsuario();

        // Realiza uma Transacao
        realizarTransacao();
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
    }

    //Talvez essa funçao fique eum sublcasse so para trasnferencias de transação
    private void realizarTransacao() {


            Scanner scanner = new Scanner(System.in);

            System.out.println("Escolh uma categoria de Transação ");
            imprimirCategorias();

            Categoria categoriaStr = categorias[scanner.nextInt()];

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
        for (int i = 0; i < categorias.length; i++) {
            System.out.println("Digite ["+i+"] para "+ categorias[i].getNome());
        }
    }


}
