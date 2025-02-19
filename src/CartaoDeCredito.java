import java.time.LocalDate;
import java.util.ArrayList;

public class CartaoDeCredito extends Cartao{
    private int dataFechamento;

    // Construtor para novos cartoes de credito
    public CartaoDeCredito(String nome, int dataFechamento) {
        super(nome);

        this.dataFechamento = dataFechamento;
    }

    public int getDataFechamento() {
        return dataFechamento;
    }
/*
    public boolean adicionarCompra(Transacao transacao) {
        if (transacao.getValor() <= saldoDisponivel) {
            fatura.add(transacao);
            saldoDisponivel -= transacao.getValor();
            return true;
        }
        return false;
    }

    public void pagarFatura() {
        fatura.clear();
        saldoDisponivel = limite;
    }

    public void gerarFaturaDetalhada() {
        System.out.println("Fatura detalhada:");
        for (Transacao t : fatura) {
            System.out.println(t.getDescricao() + " - R$" + t.getValor());
        }
        System.out.println("Total devido: R$" + (limite - saldoDisponivel));
    }
     */


    @Override
    public ArrayList<String> gerarDadosDB() {
        ArrayList<String> dados = new ArrayList<>();
        dados.add(super.nome);
        dados.add("CREDITO");
        dados.add(String.valueOf(this.dataFechamento));
        return dados;
    }
}