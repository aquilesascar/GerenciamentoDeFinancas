import java.sql.*;

public class ConexaoSQLite {
    private static final String URL = "jdbc:sqlite:src/database/banco.sqlite"; // Caminho do arquivo

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
}