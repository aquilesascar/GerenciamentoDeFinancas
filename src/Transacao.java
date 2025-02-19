import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Classe que representa uma transação financeira
public class Transacao {
    protected String tipo; // "receita" ou "despesa"
    protected String descricao;
    protected double valor;
    protected LocalDate data;
    protected Categoria categoria;
    protected String metodoPagamento;

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

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public Transacao(String tipo, String descricao, double valor, LocalDate data, Categoria categoria, String metodoPagamento) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.metodoPagamento = metodoPagamento;
    }

    public List<String> getDados() {
        ArrayList<String> dadosTransacao = new ArrayList<>();
        dadosTransacao.add(tipo);
        dadosTransacao.add(descricao);
        dadosTransacao.add(String.valueOf(valor));
        dadosTransacao.add(data.toString());
        dadosTransacao.add(categoria.getNome());
        // NAO E RECORRENTE

        dadosTransacao.add(String.valueOf(0));
        dadosTransacao.add(metodoPagamento);
        return dadosTransacao;
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

