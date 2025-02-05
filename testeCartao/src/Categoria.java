public class Categoria {
        protected String nome;
        private double limite;
        private double gastoAtual;

        public Categoria(String nome, double limite) {
            this.nome = nome;
            this.limite = limite;
            this.gastoAtual = 0;
        }

        public void adicionarGasto(double valor) {
            gastoAtual += valor;
        }

        public boolean estourouOrcamento() {
            return gastoAtual > limite;
        }

        public void exibirStatus() {
            System.out.println("Categoria: " + nome + " | Gasto Atual: R$" + gastoAtual + " / Limite: R$" + limite);
            if (estourouOrcamento()) {
                System.out.println("Atenção! Você ultrapassou o orçamento desta categoria.");
            }
        }
}

