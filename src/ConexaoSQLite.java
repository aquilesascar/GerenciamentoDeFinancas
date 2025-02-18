import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConexaoSQLite {
    // Talvez nao sera uma classe static
    private static final String URL = "jdbc:sqlite:src/database/banco.sqlite"; // Caminho do arquivo

    /*
    public static void main(String[] args) {
    // Conectar ao banco de dados
    ConexaoSQLite.conectar();

    // Filtrar transações por categoria
    String categoriaFiltro = "Alimentação";
    List<Transacao> transacoesCategoria = ConexaoSQLite.filtrarTransacoesPorCategoria(categoriaFiltro);
    System.out.println("📌 Transações da categoria " + categoriaFiltro + ":");
    transacoesCategoria.forEach(transacao -> System.out.println(
        transacao.getDescricao() + " - " + transacao.getValor() + " - " + transacao.getData()
    ));

    // Filtrar transações por tipo
    String tipoFiltro = "despesa";
    List<Transacao> transacoesTipo = ConexaoSQLite.filtrarTransacoesPorTipo(tipoFiltro);
    System.out.println("📌 Transações do tipo " + tipoFiltro + ":");
    transacoesTipo.forEach(transacao -> System.out.println(
        transacao.getDescricao() + " - " + transacao.getValor() + " - " + transacao.getData()
    ));

    // Desconectar do banco de dados
    ConexaoSQLite.desconectar();
}
     */

    /*
    public static void conectar() {
        try (Connection conexao = DriverManager.getConnection(URL)) {
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    public static void desconectar() {
        //TO DO
    }
     */

    public static void adicionarUsuarioDB(Usuario usuario) {
        try (Connection conexao = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO USUARIO (nome, saldo) VALUES (?, ?)";
            System.out.println(sql);

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, usuario.getNome());
                stmt.setDouble(2, usuario.getSaldo());
                stmt.executeUpdate();
                System.out.println("✅ Usuário cadastrado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
        }
    }

    public static void adicionarCategoriaDB(Categoria categoria) {
        try (Connection conexao = DriverManager.getConnection(URL)) {
            String sql = "INSERT INTO CATEGORIA (nome) VALUES (?)";
            System.out.println("Executando query: " + sql);

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNome());
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("✅ Categoria " + categoria.getNome() + " cadastrada com sucesso!");
                } else {
                    System.out.println("❌ Nenhuma linha foi afetada. Verifique a tabela e os dados.");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace(); // Isso ajudará a identificar o erro exato
        }
    }

    public static void adicionarCartaoBD(Cartao cartao) {
        try (Connection conexao = DriverManager.getConnection(URL)) {

            String sql = "INSERT INTO CARTAO (nome, tipo, data_fechamento, limite, limite_disponivel) VALUES (?, ?, ?, ?, ?)";;
            ArrayList<String> dadosCartao = cartao.gerarDadosDB();
            System.out.println(dadosCartao.toString());
            System.out.println(dadosCartao.get(4));

            boolean isCredito = cartao instanceof CartaoDeCredito;

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, dadosCartao.get(0));
                stmt.setString(2, dadosCartao.get(1));

                if(isCredito) {
                    stmt.setInt(3, Integer.parseInt(dadosCartao.get(2)));
                    stmt.setDouble(4, Double.parseDouble(dadosCartao.get(3)));
                    stmt.setDouble(5, Double.parseDouble(dadosCartao.get(4)));
                }

                stmt.executeUpdate();
                System.out.println("✅ Cartao de " + dadosCartao.get(1) + "adicionado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar cartao: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void atualizarSaldoNoBanco(String nomeUsuario, double novoSaldo) {
        try (Connection conexao = DriverManager.getConnection(URL)) {
            String sql = "UPDATE USUARIO SET saldo = ? WHERE nome = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setDouble(1, novoSaldo);
                stmt.setString(2, nomeUsuario);
                stmt.executeUpdate();
                System.out.println("✅ Saldo atualizado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao atualizar saldo: " + e.getMessage());
        }
    }

        public static List<Transacao> filtrarTransacoesPorCategoria(String nomeCategoria) {
            List<Transacao> transacoes = new ArrayList<>();
    
            try (Connection conexao = DriverManager.getConnection(URL)) {
                String sql = "SELECT t.descricao, t.valor, t.data, t.tipo " +
                        "FROM TRANSACAO t " +
                        "JOIN CATEGORIA c ON t.categoria_id = c.id " +
                        "WHERE c.nome = ?";
    
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setString(1, nomeCategoria);
                    ResultSet rs = stmt.executeQuery();
    
                    while (rs.next()) {
                        String descricao = rs.getString("descricao");
                        double valor = rs.getDouble("valor");
                        LocalDate data = rs.getDate("data").toLocalDate();
                        String tipo = rs.getString("tipo");
    
                        // Supondo que você tenha uma classe Transacao com um construtor adequado
                        Transacao transacao = new Transacao(tipo, descricao, valor, data, new Categoria(nomeCategoria));
                        transacoes.add(transacao);
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Erro ao filtrar transações por categoria: " + e.getMessage());
            }
    
            return transacoes;
        }

    public static List<Transacao> filtrarTransacoesPorTipo(String tipo) {
        List<Transacao> transacoes = new ArrayList<>();

        try (Connection conexao = DriverManager.getConnection(URL)) {
            String sql = "SELECT t.descricao, t.valor, t.data, c.nome AS categoria " +
                    "FROM TRANSACAO t " +
                    "JOIN CATEGORIA c ON t.categoria_id = c.id " +
                    "WHERE t.tipo = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, tipo);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String descricao = rs.getString("descricao");
                    double valor = rs.getDouble("valor");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    String categoriaNome = rs.getString("categoria");

                    Transacao transacao = new Transacao(tipo, descricao, valor, data, new Categoria(categoriaNome));
                    transacoes.add(transacao);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao filtrar transações por tipo: " + e.getMessage());
        }

        return transacoes;
    }

    public static List<Transacao> filtrarTransacoesPorMesAno(int mes, int ano) {
        List<Transacao> transacoes = new ArrayList<>();

        try (Connection conexao = DriverManager.getConnection(URL)) {
            String sql = "SELECT t.descricao, t.valor, t.data, t.tipo, c.nome AS categoria " +
                    "FROM TRANSACAO t " +
                    "JOIN CATEGORIA c ON t.categoria_id = c.id " +
                    "WHERE strftime('%m', t.data) = ? AND strftime('%Y', t.data) = ?";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                // Formata o mês para sempre ter dois dígitos (ex: 01, 02, ..., 12)
                String mesFormatado = String.format("%02d", mes);
                stmt.setString(1, mesFormatado);
                stmt.setString(2, String.valueOf(ano));

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String descricao = rs.getString("descricao");
                    double valor = rs.getDouble("valor");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    String tipo = rs.getString("tipo");
                    String categoriaNome = rs.getString("categoria");

                    Transacao transacao = new Transacao(tipo, descricao, valor, data, new Categoria(categoriaNome));
                    transacoes.add(transacao);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao filtrar transações por mês e ano: " + e.getMessage());
        }

        return transacoes;
    }

    public static Usuario carregarUsuario() {
        try (Connection conexao = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM USUARIO";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String nome = rs.getString("NOME");
                    double saldo = rs.getDouble("SALDO");
                    return new Usuario(nome, saldo);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
        return null;
    }

    public static List<Cartao> carregarCartao() {
        List<Cartao> cartoes = new ArrayList<>();
        Cartao novoCartao = null;

        try (Connection conexao = DriverManager.getConnection(URL)) {
            String sql = "SELECT * FROM CARTAO";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String nome = rs.getString("NOME");
                    String tipo = rs.getString("TIPO");

                    if(tipo.equals("CREDITO")) {
                        int dataFechamento = rs.getInt("DATA_FECHAMENTO");
                        double limite = rs.getDouble("LIMITE");
                        double limiteDisponivel = rs.getDouble("LIMITE_DISPONIVEL");

                        novoCartao = new CartaoDeCredito(nome, limite, limiteDisponivel, dataFechamento);
                    } else {
                        novoCartao = new CartaoDeDebito(nome);
                    }
                    cartoes.add(novoCartao);
                }
                return cartoes;
            }
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
        return cartoes;
    }

    public static List<Categoria> carregarCategorias() {
        try (Connection conexao = DriverManager.getConnection(URL)) {
            List<Categoria> categorias = new ArrayList<>();

            String sql = "SELECT * FROM CATEGORIA";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    String nome = rs.getString("NOME");
                    categorias.add(new Categoria(nome));
                }
                return categorias;
            }
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
        return null;
    }

    public static void removerCategoriaBD(String nomeCategoria) {
        try (Connection conexao = DriverManager.getConnection(URL)) {
         // Categoria encontrada, prossegue com a exclusão
            String sqlDeletar = "DELETE FROM CATEGORIA WHERE NOME = ?";
            try (PreparedStatement stmtDeletar = conexao.prepareStatement(sqlDeletar)) {
                stmtDeletar.setString(1, nomeCategoria);
                int linhasAfetadas = stmtDeletar.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("✅ Categoria com NOME " + nomeCategoria + " deletada com sucesso!");
                } else {
                    System.out.println("❌ Nenhuma categoria foi deletada.");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao deletar categoria: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void removerCartaoBD(String nomeCartao) {
        try (Connection conexao = DriverManager.getConnection(URL)) {
            // Categoria encontrada, prossegue com a exclusão
            String sqlDeletar = "DELETE FROM CARTAO WHERE NOME = ?";
            try (PreparedStatement stmtDeletar = conexao.prepareStatement(sqlDeletar)) {
                stmtDeletar.setString(1, nomeCartao);
                int linhasAfetadas = stmtDeletar.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("✅ Cartao " + nomeCartao + " deletado com sucesso!");
                } else {
                    System.out.println("❌ Nenhum cartao foi deletado.");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Nao foi possivel deletar o cartao: " + e.getMessage());
            e.printStackTrace();
        }
    }
}