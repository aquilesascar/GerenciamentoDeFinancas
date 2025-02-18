import java.util.ArrayList;

public abstract class Cartao extends MetodoPagamento {
    // Bandeira: Visa, MasterCard, Elo

    public Cartao(String nome) {
        super(nome);
    }

    public abstract ArrayList<String> gerarDadosDB();
}