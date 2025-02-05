import java.util.ArrayList;
import java.util.List;

public class AcompanhamentoParcelas {
        private List<Parcela> parcelas;

        public AcompanhamentoParcelas() {
            this.parcelas = new ArrayList<>();
        }

        public void adicionarParcela(String descricao, double valor, int mesRestante) {
            parcelas.add(new Parcela(descricao, valor, mesRestante));
        }

        public void exibirParcelasPendentes() {
            System.out.println("Parcelas pendentes:");
            for (Parcela p : parcelas) {
                System.out.println(p.getDescricao() + " - R$" + p.getValor() + " - Restam " + p.getMesRestante() + " meses");
            }
        }
}

