import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDAO {
    public Connection conectar() {
        Connection con = null;
        try {
            String url = "jdbc:mysql://localhost:3306/bancoDeDados?user=root&password=root";
            con = DriverManager.getConnection(url);
        }catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "ConexaoDAO" + erro.getMessage());
        }
        return con;
    }
}
