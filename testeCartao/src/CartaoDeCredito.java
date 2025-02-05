import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartaoDeCredito {

        private String numero;
        private double limite;
        private double saldoDisponivel;
        private List<Transacao> fatura;
        private Date dataFechamento;

        public CartaoDeCredito(String numero, double limite, Date dataFechamento) {
            this.numero = numero;
            this.limite = limite;
            this.saldoDisponivel = limite;
            this.fatura = new ArrayList<>();
            this.dataFechamento = dataFechamento;
        }

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

        public void verificarLimite() {
            double gasto = limite - saldoDisponivel;
            System.out.println("Saldo disponível: R$" + saldoDisponivel);
            System.out.println("Valor já utilizado: R$" + gasto);
            if (saldoDisponivel < limite * 0.1) {
                System.out.println("Atenção! Seu saldo disponível está abaixo de 10% do limite.");
            }
        }
    }
