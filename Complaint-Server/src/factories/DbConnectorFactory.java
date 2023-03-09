package factories;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectorFactory {
    private static Connection dbConn = null;
    public static  Connection getDatabaseConnection(){
        if (dbConn==null){
            String url = "Jdbc:mysql://localhost:3306/utechcomplaintdb";
            try {
                dbConn = DriverManager.getConnection(url,"root","");
                //replace with log messages
                JOptionPane.showMessageDialog(null,"Connection Established","JBDC CONNECTION STATUS",JOptionPane.INFORMATION_MESSAGE);

            }catch (SQLException e){
                //replace with log messages

                System.err.println("SQL EXCEPTION"+ e.getMessage());
            }catch (Exception e){
                //replace with log messages

                System.err.println("Unexpected error"+ e.getMessage());
            }
        }
        return dbConn;
    }
}
