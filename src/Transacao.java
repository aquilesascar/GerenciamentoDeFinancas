import java.time.LocalDate;

// Classe que representa uma transação financeira
public class Transacao {
    private String tipo; // "receita" ou "despesa"
    private String descricao;
    private double valor;
    private LocalDate data;
    private Categoria categoria;

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Transacao(String tipo, String descricao, double valor, LocalDate data, Categoria categoria) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }



}
