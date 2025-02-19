import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TransacaoService {
    private final int RECEITA = 1;
    private final int DESPESA = 2;
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

    /*
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
                    Transacao novaTransacao = new TransacaoRecorrente(
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

     */

    public void realizarTransacao() {
        Scanner scanner = new Scanner(System.in);

        // Validador de categorias
        Categoria categoriaCompra = validarCategoriaTransacao();

        System.out.println("Qual o tipo da transacao? (Receita/Despesa)");
        System.out.println("[1] Receita\n[2] Despesa");
        int opcaoTransacao = scanner.nextInt(); // Alterado para int
        scanner.nextLine();

        // Verfica se a opcao e valida
        if(opcaoTransacao < RECEITA || opcaoTransacao > DESPESA) {
            System.out.println("Opcao invalida.");
            return;
        }

        System.out.println("Qual a descricao que gostaria de colocar para a transacao? ");
        String descricaoTransacao = scanner.nextLine();

        System.out.println("Valor da Transacao: ");
        double valorTransacao = scanner.nextDouble();

        System.out.println("Data da transacao");
        System.out.println("Dia: ");
        int dia = scanner.nextInt();
        System.out.println("Mês:");
        int mes = scanner.nextInt();
        System.out.println("Ano: ");
        int ano = scanner.nextInt();
        LocalDate dataTransacao = LocalDate.of(ano, mes, dia);


        if(opcaoTransacao == DESPESA) {
            realizarTransacaoDespesa(valorTransacao, descricaoTransacao,categoriaCompra, dataTransacao);
        }
        else {
            realizarTransacaoReceita(valorTransacao, descricaoTransacao, categoriaCompra, dataTransacao);
        }

    }

    public void realizarTransacaoReceita(double valorTransacao, String descricaoTransacao,Categoria categoriaCompra, LocalDate dataTransacao) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Essa transacao e recorrente? ");
        System.out.println("[1] SIM");
        System.out.println("[2] NAO");

        Transacao novaTransacao;

        int opcaoReceita = scanner.nextInt();
        switch (opcaoReceita) {
            case 1:
                novaTransacao = new TransacaoRecorrente("RECEITA", descricaoTransacao, valorTransacao, dataTransacao, categoriaCompra, "", true);
                break;
            case 2:
                novaTransacao = new Transacao("RECEITA", descricaoTransacao, valorTransacao, dataTransacao, categoriaCompra, "");
                break;
            default:
                System.out.println("Opcao invalida.");
                return;
        }

        ConexaoSQLite.adicionarTransacaoDB(novaTransacao);
        transacoes.add(novaTransacao);
    }

    public void realizarTransacaoDespesa(double valorTransacao, String descricaoTransacao,Categoria categoriaCompra, LocalDate dataTransacao) {
        Scanner scanner = new Scanner(System.in);
        String metodoPagamento;

        System.out.println("Metodo de Pagamento: ");
        System.out.println("[1] PIX");
        System.out.println("[2] BOLETO");
        System.out.println("[3] Cartao de Credito");
        System.out.println("[4] Cartao de Debito");
        System.out.println("[5] Dinheiro");
        int opcaoPagamento = scanner.nextInt();

        switch (opcaoPagamento) {
            case 1:
                metodoPagamento = "PIX";
                break;
            case 2:
                metodoPagamento = "BOLETO";
                break;
            case 3:
                metodoPagamento = "CREDITO";
                // Usuario escolhe o cartao de credito
                break;
            case 4:
                metodoPagamento = "DEBITO";
                // Usuario escolhe o cartao de debito
                break;
            case 5:
                metodoPagamento = "DINHEIRO";
                break;
            default:
                System.out.println("Opcao invalida.");
                return;
        }

        if(metodoPagamento.equals("CREDITO")) {
            realizarTransacaoCredito(valorTransacao, descricaoTransacao, categoriaCompra, dataTransacao);
        }
        else {
            realizarTransacaoInstantanea(metodoPagamento, valorTransacao, descricaoTransacao, categoriaCompra, dataTransacao);
        }

    }

    public void realizarTransacaoInstantanea(String metodopagamento, double valorTransacao, String descricaoTransacao ,Categoria categoriaCompra, LocalDate dataTransacao) {
        Scanner scanner = new Scanner(System.in);
        Transacao novaTransacao;
        System.out.println("Essa transacao e recorrente? ");
        System.out.println("[1] SIM");
        System.out.println("[2] NAO");
        int opcaoRecorrente = scanner.nextInt();
        switch (opcaoRecorrente) {
            case 1:
                novaTransacao = new TransacaoRecorrente("DESPESA", descricaoTransacao, valorTransacao, dataTransacao, categoriaCompra, metodopagamento, true);
                break;
            case 2:
                novaTransacao = new Transacao("DESPESA", descricaoTransacao, valorTransacao, dataTransacao, categoriaCompra, metodopagamento);
                break;
            default:
                System.out.println("Opcao invalida.");
                return;
        }
        // Adicionar ao banco
        ConexaoSQLite.adicionarTransacaoDB(novaTransacao);

    }

    public void realizarTransacaoCredito(double valorTransacao, String descricaoTransacao ,Categoria categoriaCompra, LocalDate dataTransacao) {
        Scanner scanner = new Scanner(System.in);
        List<Cartao> cartoesUsuario = this.usuario.getCartoes();
        List<CartaoDeCredito> cartoesCreditoUsuario = new ArrayList<>();

        for(Cartao cartao: cartoesUsuario) {
            if(cartao instanceof CartaoDeCredito) {
                cartoesCreditoUsuario.add((CartaoDeCredito) cartao);
            }
        }

        if(cartoesCreditoUsuario.isEmpty()) {
            System.out.println("Nao foi possivel realizar a compra! Usuario nao possui nenhum cartao de credito!");
            return;
        }

        System.out.println("Qual cartao deseja utilizar: ");
        for(int i = 0; i < cartoesCreditoUsuario.size(); i++) {
            System.out.println("[" + (i+1) + "] " + cartoesCreditoUsuario.get(i).getNome());
        }

        int opcaoCartao = scanner.nextInt();

        if(opcaoCartao < 1 || opcaoCartao > cartoesCreditoUsuario.size()) {
            System.out.println("Opcao invalida.");
            return;
        }

        CartaoDeCredito cartaoUsuario = (CartaoDeCredito) cartoesCreditoUsuario.get(opcaoCartao - 1);

        System.out.println("Forma de Pagamento: ");
        System.out.println("[1] A vista");
        System.out.println("[2] Parcelado");
        int opcaoCompraCredito = scanner.nextInt();

        final int A_VISTA = 1;
        final int PARCELADO = 2;

        Transacao novaTransacao = null;

        if(opcaoCompraCredito < 1 || opcaoCompraCredito > 2) {
            System.out.println("Opcao invalida.");
            return;
        }

        if(opcaoCompraCredito == PARCELADO) {
            System.out.println("Digite o numero de parcelas: ");
            int parcelas = scanner.nextInt();

            if(parcelas < 1) {
                System.out.println("Quantiade de Parcelas invalida.");
                return;
            }

            double valorParcelas = valorTransacao / parcelas;

            novaTransacao = new Transacao("DESPESA", descricaoTransacao, valorParcelas,dataTransacao, categoriaCompra, "CREDITO");

            ConexaoSQLite.adicionarTransacaoDB(novaTransacao);
            transacoes.add(novaTransacao);

            System.out.println("Compra adicionada com sucesso!");
            System.out.println("1 parcela: " + novaTransacao.getValor() + " - "+ novaTransacao.getData());

            for(int i = 1; i < parcelas; i++) {
                LocalDate dataProximaParcela = novaTransacao.getData().plusMonths(1);
                novaTransacao = new Transacao("DESPESA", descricaoTransacao, valorParcelas, dataProximaParcela, categoriaCompra, "CREDITO");

                // Adicionar ao banco
                ConexaoSQLite.adicionarTransacaoDB(novaTransacao);
                transacoes.add(novaTransacao);


                System.out.println("Compra adicionada com sucesso!");
                System.out.println((i+1) + " parcela: " + novaTransacao.getData());
            }
            return;
        }

        else {
            System.out.println("E uma transacao recorrente? ");
            System.out.println("[1] SIM");
            System.out.println("[2] NAO");
            int opcaoRecorrente = scanner.nextInt();
            switch (opcaoRecorrente) {
                case 1:
                    novaTransacao = new TransacaoRecorrente("DESPESA", descricaoTransacao, valorTransacao, dataTransacao, categoriaCompra, "CREDITO", true);
                    break;
                case 2:
                    novaTransacao = new Transacao("DESPESA", descricaoTransacao, valorTransacao, dataTransacao, categoriaCompra, "CREDITO");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    return;
            }
            ConexaoSQLite.adicionarTransacaoDB(novaTransacao);
            transacoes.add(novaTransacao);
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
                Transacao transacaoRemover = transacoesUsuario.get(opcaoTransacao - 1);
                ConexaoSQLite.removerTransacaoDB(transacaoRemover);
                transacoesUsuario.remove(opcaoTransacao - 1);
                System.out.println("Transacao removida com sucesso!");
                return;
            }
        }
    }

    public void cancelarTransacaoRecorrente() {
        Scanner scanner = new Scanner(System.in);
        List<TransacaoRecorrente> transacaoRecorrentesUsuario = new ArrayList<>();
        List<Transacao> transacoesUsuario = usuario.getTransacoes();

        for(Transacao transacao : transacoesUsuario) {
            if(transacao instanceof TransacaoRecorrente) {
                transacaoRecorrentesUsuario.add((TransacaoRecorrente) transacao);
            }
        }

        if(transacaoRecorrentesUsuario.isEmpty()) {
            System.out.println("Usuario nao possui transacoes recorrentes");
            return;
        }

        List<TransacaoRecorrente> transacoesFiltradas = new ArrayList<>(transacaoRecorrentesUsuario.stream()
                .collect(Collectors.toMap(
                        TransacaoRecorrente::getDescricao,
                        t -> t,
                        (t1, t2) -> t1.getData().isAfter(t2.getData()) ? t1 : t2
                )).values());


        System.out.println("Digite a transacao que deseja remover: ");
        for(int i = 0; i < transacoesFiltradas.size(); i++) {
            System.out.println("[" + (i+1) + "]" + transacoesFiltradas.get(i).getDescricao());
        }
        int opcaoTransacao = scanner.nextInt();

        if(opcaoTransacao < 1 || opcaoTransacao > transacoesFiltradas.size()) {
            System.out.println("Opcao Invalida");
            return;
        }

        String descricaoTransacaoCancelar = transacoesFiltradas.get(opcaoTransacao - 1).getDescricao();

        for(TransacaoRecorrente transacao: transacaoRecorrentesUsuario) {
            if(transacao.getDescricao().equals(descricaoTransacaoCancelar)) {
                transacao.setAtiva(false);
            }
        }

        // Atualizar banco
        ConexaoSQLite.cancelarRecorrencias(descricaoTransacaoCancelar);
    }

    /*
    // EDITAR
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
                double totalFatura = cartaoCredito.getLimite() - cartaoCredito.getLimiteDisponivel();
                System.out.println("Fatura atual do cartão " + cartaoCredito.getNome() + ": R$" + totalFatura);
            } else {
                System.out.println("Este cartão não é um cartão de crédito.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }
     */
}
