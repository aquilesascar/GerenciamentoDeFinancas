import java.time.LocalDate;

public class TransacaoVariavel extends Transacao{
    //Representa despesas ocosionais com prazo de acabar ex: roupa, eletrônicos,etc
    private int quantParcelas;
    private double valorParcela;

    public double getValorParcela() {
        return valorParcela;
    }

    public int getQuantParcelas() {
        return quantParcelas;
    }

    public TransacaoVariavel(String tipo, String descricao, double valor, LocalDate data, Categoria categoria, int quantParcelas) {
        super(tipo, descricao, valor, data, categoria);
        this.quantParcelas = quantParcelas;
        this.valorParcela = calcularValorParcelas(valor,quantParcelas);
    }
    public double calcularValorParcelas(double precoProduto, int qtParcelas) {
        return precoProduto / qtParcelas;
    }
}
