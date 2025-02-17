import java.time.LocalDate;

public class TransacaoRecorrente extends Transacao {
    private LocalDate proximaData;

    public TransacaoRecorrente(String tipo, String descricao, double valor, LocalDate data, Categoria categoria) {
        super(tipo, descricao, valor, data, categoria);
        this.proximaData = data; // A próxima data inicial é a data da transação
    }

    public LocalDate getProximaData() {
        return proximaData;
    }

    public void atualizarProximaData() {
        this.proximaData = this.proximaData.plusMonths(1); // Avança para o próximo mês
    }


}

