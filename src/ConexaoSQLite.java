import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConexaoSQLite {
    // Talvez nao sera uma classe static
    private static final String URL = "jdbc:sqlite:src/database/banco.sqlite"; // Caminho do arquivo
    private static Connection conexao;

    public static void main(String[] args) {
        try (Connection conexao = DriverManager.getConnection(URL)) {

            String sql = "SELECT * FROM USUARIO";
            try (PreparedStatement stmt = conexao.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                System.out.println("📌 Lista de Usuários:");
                while (rs.next()) {
                    // int id = rs.getInt("id");
                    String nome = rs.getString("NOME");
                    double saldo = rs.getDouble("SALDO");

                    System.out.println("NOME: " + nome);
                    System.out.println("SALDO: " + saldo);

                }
            }

        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

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
}