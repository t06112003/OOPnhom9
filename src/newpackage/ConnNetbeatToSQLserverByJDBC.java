package newpackage;

import java.sql.*;


public class ConnNetbeatToSQLserverByJDBC {


public static void main(String[] args) {
      String dbURL = "jdbc:sqlserver://LAPTOP-ISQ0LO7D;databaseName=taikhoangame;encrypt=false";
      String username = "sa";
      String password = "06112003";

      try {
         // Load the SQL Server JDBC driver
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

         // Create a connection to the database
         Connection conn = DriverManager.getConnection(dbURL, username, password);

         // Execute a query
         Statement statement = conn.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT * FROM taikhoan");

         // Process the results
         while (resultSet.next()) {
            System.out.println(resultSet.getString("Username") + "\t" + resultSet.getString("Password"));
         }

         // Close the connection and resources
         resultSet.close();
         statement.close();
         conn.close();

      } catch (Exception e) {
         e.printStackTrace();
      }
   } 
}
