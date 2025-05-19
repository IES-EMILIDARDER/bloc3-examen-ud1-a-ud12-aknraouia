package examen;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Main5 {
    
  
         public static void main(String[] args) throws FileNotFoundException, IOException {
        // Establir la connexió
        try ( Connection connexio = getConnectionFromFile("c:\\temp\\mysql.con")  ) {
            System.out.println("Connexió establerta.");
 
            connexio.setAutoCommit(false);
            llegeixArxiuABBDD(connexio, "c:\\temp\\vehicles.txt");
            
            System.out.println("Connexió tancada.");
        } catch (Exception e) {
            System.err.println("S'ha produït l'error general: " + e.getMessage());
        }
    }
    
    private static Connection getConnectionFromFile(String filename) throws SQLException, IOException {
        String servidor = "";
        String bdades = "";
        String usuari = "";
        String passwd = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("=");
                    String clau = parts[0].trim();
                    String valor = parts[1].trim();
                    
                    switch (clau) {
                        case "SERVER" -> servidor = valor;
                        case "DBASE" -> bdades = valor;
                        case "USER" -> usuari = valor;
                        case "PASSWD" -> passwd = valor;
                        default -> System.err.println("Clau no vàlida: " + clau);
                    }
                } catch (IndexOutOfBoundsException e) {
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Error llegint l'arxiu: " + e.getMessage());
            throw e;  //
        }

        // 
        return DriverManager.getConnection(servidor + bdades, usuari, passwd);
    }
    
    private static void llegeixArxiuABBDD(Connection connexio, String filename) throws SQLException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // 
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String matricula = parts[0];
                String marca = parts[1];
                String model = parts[2];
                String any = parts[3];
                String preu = parts[4];
                

                try {
                    
                    if (!SQLCheckPK(connexio, "vehicles", Integer.parseInt(matricula)))
                        SQLInsert(connexio, "vehicles", parts[2], "S/D","S/D", " ");
                  
                    connexio.commit();
                } catch (SQLException e) {
                    connexio.rollback();
                    System.err.println("Error executant la instrucció SQL: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error llegint l'arxiu: " + e.getMessage());
            throw e; // Es propaga l'excepció al mètode anterior
        }
    }
    
    private static void SQLInsert(Connection connexio, String table, String... valors) throws SQLException  {
        String sql="";
        PreparedStatement statement;
        


        try {
            if (table.equals("vehicles")) {
                sql = "INSERT INTO vehicles (matricula, marca, model, any, preu) VALUES (?, ?, ?, ?, ?)";
                statement = connexio.prepareStatement(sql);
                statement.setInt(1, Integer.parseInt(valors[0]));
                statement.setString(2, valors[1]);
                statement.setString(3, valors[2]);
                statement.setString(4, valors[3]);
                statement.executeUpdate();
 
            }
        } catch (SQLException e) {
            throw e;
        } 

    }
    
    private static boolean SQLCheckPK(Connection connexio, String taula, int primaryKey) throws SQLException  {
        String sql="";
        
        // Cal millorar aquest mètode accedint al diccionari MySQL
        try {
            if (taula.equals("vehicles"))
                sql = "SELECT '1' FROM vehicles WHERE matricula = ?";
            
            PreparedStatement statement = connexio.prepareStatement(sql);
            statement.setInt(1, primaryKey);

            ResultSet resultSet = statement.executeQuery();
            
            return resultSet.next(); // si existeix al manco 1 fila ?
        } catch (SQLException e) {
            throw new SQLException (e.getMessage() );
        } 
    }
}
