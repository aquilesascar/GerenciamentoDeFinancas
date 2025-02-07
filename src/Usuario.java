import java.util.ArrayList;
import java.util.List;
// Classe que representa um usuário e suas transações
public class Usuario {
    private String nome;
    private double saldo;
    private List<Transacao> transacoes;

    public Usuario(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.transacoes = new ArrayList<Transacao>();
    }

    public void adicionarTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
    }


    // Métodos para adicionar, remover e listar transações

}
