import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {
    private static final String URL = "jdbc:sqlite:src/database/banco.sqlite"; // Caminho do arquivo

    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL);
            System.out.println("Conectado ao SQLite!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
        return conexao;
    }

    public static void main(String[] args) {
        conectar(); // Teste a conexão
    }
}