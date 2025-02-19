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
}
