public abstract class Cartao extends MetodoPagamento {
    // Bandeira: Visa, MasterCard, Elo
    protected String bandeira;

    public Cartao(String nome, String bandeira) {
        super(nome);
        this.bandeira = bandeira;
    }
}