import java.time.LocalDate;

// Classe que representa uma transação financeira
public class Transacao {
    private String tipo; // "receita" ou "despesa"
    private String descricao;
    private double valor;
    private LocalDate data;
    private Categoria categoria;

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Transacao(String tipo, String descricao, double valor, LocalDate data, Categoria categoria) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public void gerarTransacoesRecorrentes(Usuario usuario) {
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int anoAtual = hoje.getYear();

        for (Transacao transacao : usuario.getTransacoes()) {
            if (transacao instanceof TransacaoRecorrente) {
                TransacaoRecorrente transacaoRecorrente = (TransacaoRecorrente) transacao;
                LocalDate proximaData = transacaoRecorrente.getProximaData();

                // Verifica se a transação recorrente deve ser gerada este mês
                if (proximaData.getMonthValue() == mesAtual && proximaData.getYear() == anoAtual) {
                    // Cria uma nova transação para o mês atual
                    Transacao novaTransacao = new Transacao(
                            transacaoRecorrente.getTipo(),
                            transacaoRecorrente.getDescricao(),
                            transacaoRecorrente.getValor(),
                            proximaData,
                            transacaoRecorrente.getCategoria()
                    );

                    // Adiciona a nova transação ao usuário
                    usuario.adicionarTransacao(novaTransacao);

                    // Atualiza a próxima data da transação recorrente
                    transacaoRecorrente.atualizarProximaData();

                    // Salva a nova transação no banco de dados
                    // ConexaoSQLite.adicionarTransacaoNoBanco(novaTransacao, usuario.getNome());

                    System.out.println("Transação recorrente gerada: " + novaTransacao.getDescricao());
                }
            }
        }
    }
}
