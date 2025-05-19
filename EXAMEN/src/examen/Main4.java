package examen;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Main4 {

    public static void main(String[] args) throws Exception {
        
        String servidor = "jdbc:mysql://192.168.126.245:3306/";
        String bdades = "gbd";
        String usuari = "root";
        String passwd = "";
        String sql = """
                     SELECT any FROM vehicles WHERE department_id = ?
                     """;
                
        try ( Connection connexio = DriverManager.getConnection(servidor+bdades, usuari, passwd);
              PreparedStatement stmt = connexio.prepareStatement(sql) ) {
            int any = 10;
            
            System.out.println("Connexió amb la base de dades MySQL exitosa.");
            
            stmt.setInt(2020, any);
            try(ResultSet resultSet = stmt.executeQuery()) {
                
                
                while (resultSet.next()) {
                    System.out.println( resultSet.getInt("any") + " " +
                                        resultSet.getString("marca")
                                      );
                }
            } catch (SQLException e) {
                System.err.println("Error al executar la instrucció SQL: " + e.getMessage());
            }
            System.out.println("Connexió tancada.");
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de dades: " + e.getMessage());
        }
             
    }
    
}