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

    public void realizarTransacao() {
        Scanner scanner = new Scanner(System.in);

        // Validador de categorias
        Categoria categoriaCompra = validarCategoriaTransacao();

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
                this.usuario.adicionarTransacao(transacao);

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
                        this.usuario.adicionarTransacao(transacaoVariavel);
                        System.out.println("Transação feita com sucesso!");
                        System.out.println(transacaoVariavel.getDescricao()+"   "+transacaoVariavel.getQuantParcelas()+
                                "x de "+transacaoVariavel.getValorParcela());
                        break;

                    case 2:
                        System.out.println("Qual a quantidade de parcelas? ");
                        int opcaoParcelas = scanner.nextInt();
                        transacaoVariavel= new TransacaoVariavel(tipoStr,descricaoTransacao,valorTransacao,dataTransacao,
                                categoriaCompra,opcaoParcelas);
                        this.usuario.adicionarTransacao(transacaoVariavel);
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
