import java.util.Date;

public class Transacao {

        protected String descricao;
        protected double valor;
        protected Date data;
        protected String tipo;

        public Transacao(String descricao, double valor, Date data, String tipo) {
            this.descricao = descricao;
            this.valor = valor;
            this.data = data;
            this.tipo = tipo;
        }

        public double getValor() {
            return valor;
        }

        public String getDescricao() {
            return descricao;
        }
}


