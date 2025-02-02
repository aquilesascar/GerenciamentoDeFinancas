// Classe que representa uma transação financeira
public class Transacao {
    private String tipo; // "receita" ou "despesa"
    private String descricao;
    private double valor;
    private String data;
    private Categoria categoria;

    public Transacao(String tipo, String descricao, double valor, String data, Categoria categoria) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }


}
