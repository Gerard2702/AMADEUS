/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;
import java.sql.*;
/**
 *
 * @author Gerard Orellana
 */
public class database {
    
    private static Connection conex = null;
    
    public static Connection conectar(){
        if(conex==null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conex = DriverManager.getConnection("jdbc:mysql://localhost/amadeus", "root", "");
            }
            catch(SQLException e){
                //throw new SQLException(e);
            }
            catch(ClassNotFoundException e){
                throw new ClassCastException(e.getMessage());
            }
        }
        return conex;
    }
    
    public static void cerrar() throws SQLException{
        if(conex!=null){
            conex.close();
        }
    }
}
