import java.sql.*;

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
}