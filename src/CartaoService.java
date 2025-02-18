import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CartaoService {
    private List<Cartao> listaCartao;
    private final Usuario usuario;

    public CartaoService(Usuario usuario) {
        this.usuario = usuario;
    }


    public void adicionarCartao() {
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

        if(isCartaoCadastrado(nomeCartao)) {
            System.out.println("Esse cartao ja se encontra cadastrado no sistema!");
            return;
        }

        if(opcaoCartao == CREDITO) {
            System.out.println("Limite do cartao: ");
            double limiteCartao = scanner.nextDouble();

            System.out.println("Limite disponivel: ");
            double limiteDisponivel = scanner.nextDouble();

            System.out.println("Data de Fechamento da Fatura do Cartao");
            System.out.println("Dia: ");
            // Validar
            int dataFechamento = scanner.nextInt();

            /*
            //Validar
            System.out.println("Mes de Fechamento do cartao (1...12): ");
            int mesFechamentoFatura = scanner.nextInt();

            int anoAtual = LocalDate.now().getYear();
            LocalDate dataFechamento = LocalDate.(diaFechamentoFatura);
             */

            novoCartao = new CartaoDeCredito(nomeCartao, limiteCartao, limiteDisponivel, dataFechamento);
        }

        else if (opcaoCartao == DEBITO) {
            novoCartao = new CartaoDeDebito(nomeCartao);
        }

        this.usuario.adicionarCartao(novoCartao);
        ConexaoSQLite.adicionarCartaoBD(novoCartao);
    }

    public void listarCartao() {
        if(this.usuario.getCartoes().isEmpty()) {
            System.out.println("Usuario nao possui cartoes cadastrados!");
            return;
        }

        for(int i = 0; i < this.usuario.getCartoes().size(); i++) {
            System.out.println("[" + (i+1) + "] " + usuario.getCartoes().get(i).getNome());
        }
    }

    public void removerCartao() {
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
                String cartaoDeletarNome = usuario.getCartoes().get(opcaoCartao - 1).getNome();
                ConexaoSQLite.removerCartaoBD(cartaoDeletarNome);

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

    public boolean isCartaoCadastrado(String nomeCartao) {
        List<Cartao> cartoesCadastrados = this.usuario.getCartoes();
        for(Cartao cartao : cartoesCadastrados) {
            if(cartao.getNome().equals(nomeCartao)) {
                return true;
            }
        }
        return false;
    }
}
