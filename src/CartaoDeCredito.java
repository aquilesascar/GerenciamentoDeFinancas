import java.time.LocalDate;
import java.util.ArrayList;

public class CartaoDeCredito extends Cartao{
    private double limite;
    private double limiteDisponivel;
    private int dataFechamento;

    // Construtor para novos cartoes de credito
    public CartaoDeCredito(String nome, double limite, double limiteDisponivel, int dataFechamento) {
        super(nome);
        this.limite = limite;
        this.limiteDisponivel = limiteDisponivel;
        this.dataFechamento = dataFechamento;
    }

    public double getLimite() {
        return limite;
    }

    public double getLimiteDisponivel() {
        return limiteDisponivel;
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

    public void verificarLimite() {
        double gasto = limite - limiteDisponivel;
        System.out.println("Saldo disponível: R$" + limiteDisponivel);
        System.out.println("Valor já utilizado: R$" + gasto);
        if (limiteDisponivel < limite * 0.1) {
            System.out.println("Atenção! Seu saldo disponível está abaixo de 10% do limite.");
        }
    }


    @Override
    public ArrayList<String> gerarDadosDB() {
        ArrayList<String> dados = new ArrayList<>();
        dados.add(super.nome);
        dados.add("CREDITO");
        dados.add(String.valueOf(this.dataFechamento));
        dados.add(String.valueOf(this.limite));
        dados.add(String.valueOf(this.limiteDisponivel));

        return dados;
    }
}