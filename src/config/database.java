/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Gerard Orellana
 */
public class database {
    
    private Connection conex;
    private String user="root";
    private String pass="";
    private String host="jdbc:mysql://localhost:3306/amadeus";
    private ResultSet rs;
    private Statement st;
    
    public void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conex = DriverManager.getConnection(host,user,pass);
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No se pudo conectar con la Base de Datos");
        }
        catch(ClassNotFoundException e){
            throw new ClassCastException(e.getMessage());
        }
    }
    
    public ResultSet query(String consulta) throws SQLException{
        st = conex.createStatement();
        rs = st.executeQuery(consulta);
        return rs;
    }   
    
    public void desconectar() throws SQLException{
        if(conex!=null){
            conex.close();
        }
    }
}
