public abstract class Cartao extends MetodoPagamento {
    // Bandeira: Visa, MasterCard, Elo
    protected String bandeira;
    protected String numero;

    public Cartao(String nome, String bandeira, String numero) {
        super(nome);
        this.bandeira = bandeira;
        this.numero = numero;
    }
}