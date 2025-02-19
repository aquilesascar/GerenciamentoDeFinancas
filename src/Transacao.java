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

    @Override



    public String toString() {
        return String.format("%-30s %-16s %-20s %-10s",
                formatarTexto(descricao, 30), // Garante que a descrição tenha 30 caracteres
                formatarTexto(String.format("Data: %02d/%02d/%d", data.getDayOfMonth(), data.getMonthValue(), data.getYear()), 16),
                formatarTexto("Categoria: " + categoria.getNome(), 20),
                String.format("R$ %7.2f", valor) // Valor monetário alinhado à direita
        );
    }

    // Método auxiliar para garantir tamanho fixo nas strings
    private String formatarTexto(String texto, int tamanho) {
        if (texto.length() > tamanho) {
            return texto.substring(0, tamanho - 3) + "..."; // Corta e adiciona "..." se for maior que o limite
        }
        while (texto.length() < tamanho) {
            texto = texto + " ";
    }
        return texto;

}

}

