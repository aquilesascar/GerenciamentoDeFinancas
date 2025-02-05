public class Parcela {
    public class Parcela {
        private String descricao;
        private double valor;
        private int mesRestante;

        public Parcela(String descricao, double valor, int mesRestante) {
            this.descricao = descricao;
            this.valor = valor;
            this.mesRestante = mesRestante;
        }

        public String getDescricao() {
            return descricao;
        }

        public double getValor() {
            return valor;
        }

        public int getMesRestante() {
            return mesRestante;
        }
    }

}
