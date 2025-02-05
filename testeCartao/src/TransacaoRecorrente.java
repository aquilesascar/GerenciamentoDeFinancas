import java.util.Date;
    public class TransacaoRecorrente extends Transacao {
        private int meses;

        public TransacaoRecorrente(String descricao, double valor, Date data, int meses) {
            super(descricao, valor, data, "Recorrente");
            this.meses = meses;
        }
    }


