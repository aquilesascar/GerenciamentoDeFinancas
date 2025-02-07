import java.time.LocalDate;

public class TransacaoRecorrente extends Transacao {
    //Representa despesas sem prazo de acaber ex:netflix, alguel, spotify,etc
    public TransacaoRecorrente(String tipo, String descricao, double valor, LocalDate data, Categoria categoria) {
        super(tipo, descricao, valor, data, categoria);
    }
}
