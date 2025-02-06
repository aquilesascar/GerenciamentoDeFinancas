import java.time.LocalDate;

// Classe que representa uma transação financeira
public class Transacao {
    private String tipo; // "receita" ou "despesa"
    private String descricao;
    private double valor;
    private LocalDate data;
    private Categoria categoria;

    // Adicionar depois no construtor
    private boolean recorrente;

    public Transacao(String tipo, String descricao, double valor, LocalDate data, Categoria categoria) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }


}
