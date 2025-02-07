import java.time.MonthDay;

public class CartaoCredito extends MetodoPagamento {
    MonthDay dataFechamento;
    String instituicaoFinanceira;

    public CartaoCredito(MonthDay dataFechamento, String instituicaoFinanceira) {
        super("Cartão de Crédito");
        this.dataFechamento = dataFechamento;
        this.instituicaoFinanceira = instituicaoFinanceira;
    }
}