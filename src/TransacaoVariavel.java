import java.time.LocalDate;

public class TransacaoVariavel extends Transacao{
    //Representa despesas ocosionais com prazo de acabar ex: roupa, eletrônicos,etc
    private int quantParcelas;

    public TransacaoVariavel(String tipo, String descricao, double valor, LocalDate data, Categoria categoria, int quantParcelas) {
        super(tipo, descricao, valor, data, categoria);
        this.quantParcelas = quantParcelas;
    }
}
