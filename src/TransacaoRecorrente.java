import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoRecorrente extends Transacao {
    private boolean ativa;
    private LocalDate proximaData;

    public TransacaoRecorrente(String tipo, String descricao, double valor, LocalDate data, Categoria categoria, String metodoPagamento, boolean ativa) {
        super(tipo, descricao, valor, data, categoria, metodoPagamento);
        this.ativa = ativa;
    }


    public LocalDate getProximaData() {
        return proximaData;
    }

    public void atualizarProximaData() {
        this.proximaData = this.proximaData.plusMonths(1); // Avança para o próximo mês
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public List<String> getDados() {
        ArrayList<String> dadosTransacao = new ArrayList<>();
        dadosTransacao.add(super.tipo);
        dadosTransacao.add(super.descricao);
        dadosTransacao.add(String.valueOf(super.valor));
        dadosTransacao.add(super.data.toString());
        dadosTransacao.add(super.categoria.getNome());

        // VERIFICA SE E RECORRENTE
        dadosTransacao.add(String.valueOf(this.ativa ? 1 : 0));
        dadosTransacao.add(metodoPagamento);
        return dadosTransacao;
    }

    public boolean isAtiva() {
        return ativa;
    }
}

