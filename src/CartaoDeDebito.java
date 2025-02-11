import java.util.List;

public class CartaoDeDebito extends Cartao{
    private List<Transacao> transacoesDebito;

    // Construtor para novos cartoes de debito
    public CartaoDeDebito(String nome, String bandeira) {
        super(nome, bandeira);
    }

    // Construtor para cartoes de debito ja existente

    public CartaoDeDebito(String nome, String bandeira, String numero, List<Transacao> transacoesDebito) {
        super(nome, bandeira);
        this.transacoesDebito = transacoesDebito;
    }
}