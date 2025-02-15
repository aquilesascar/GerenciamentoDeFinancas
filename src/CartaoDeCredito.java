import java.time.LocalDate;
import java.util.List;

public class CartaoDeCredito extends Cartao{
    private double limite;
    private double saldoDisponivel;
    private LocalDate dataFechamento;

    // Construtor para novos cartoes de credito
    public CartaoDeCredito(String nome, String bandeira, double limite, double saldoDisponivel, LocalDate dataFechamento) {
        super(nome, bandeira);
        this.limite = limite;
        this.saldoDisponivel = saldoDisponivel;
        this.dataFechamento = dataFechamento;
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
        double gasto = limite - saldoDisponivel;
        System.out.println("Saldo disponível: R$" + saldoDisponivel);
        System.out.println("Valor já utilizado: R$" + gasto);
        if (saldoDisponivel < limite * 0.1) {
            System.out.println("Atenção! Seu saldo disponível está abaixo de 10% do limite.");
        }
    }
}