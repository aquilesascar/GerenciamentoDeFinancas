import java.util.ArrayList;
import java.util.List;

public class CartaoDeDebito extends Cartao{

    // Construtor para cartoes de debito
    public CartaoDeDebito(String nome) {
        super(nome);
    }

    @Override
    public ArrayList<String> gerarDadosDB() {
        ArrayList<String> dados = new ArrayList<>();
        dados.add(super.nome);
        dados.add("DEBITO");
        return dados;
    }
}