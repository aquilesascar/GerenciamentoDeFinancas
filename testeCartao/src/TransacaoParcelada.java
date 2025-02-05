import java.util.Date;

public class TransacaoParcelada {
        private int parcelas;
        private double valorParcela;

        public TransacaoParcelada(String descricao, double valor, Date data, int parcelas) {
            super(descricao, valor, data, "Parcelada");
            this.parcelas = parcelas;
            this.valorParcela = valor / parcelas;
        }

        public double getValorParcela() {
            return valorParcela;
        }
}

