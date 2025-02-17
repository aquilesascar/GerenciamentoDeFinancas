import java.util.ArrayList;
import java.util.List;
// Classe que representa um usuário e suas transações
public class Usuario {
    private String nome;
    private double saldo;
    private List<Transacao> transacoes;
    private List<Cartao> cartoes;

    // Construtor para novo Usuario
    public Usuario(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.transacoes = new ArrayList<Transacao>();
        this.cartoes = new ArrayList<>();
    }

    public void adicionarTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
    }

    public void adicionarCartao(Cartao cartao) {
        this.cartoes.add(cartao);
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
}
