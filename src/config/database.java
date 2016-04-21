/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;
import java.sql.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author Gerard Orellana
 */
public class database {
    
    private Connection conex;
    private String user;
    private String pass;
    private String db;
    private String host;
    private String conexion_string;
    private ResultSet rs;
    private Statement st;
    File archivo = new File("config.txt");
    Scanner scan;    
    public database(){
        try{
            scan = new Scanner(archivo);
            while (scan.hasNextLine()) {
                String linea = scan.nextLine();
                if(!linea.contains("/*") && !linea.equals("")){
                    if (linea.contains("=")) {
                        String[] partes = linea.split("=");
                        if (partes[0].equals("host")) {
                            try {
                                host = partes[1];
                            } catch (Exception e) {
                                host = "";
                            }                            
                        }
                        if (partes[0].equals("db")) {
                            try {
                                db = partes[1];
                            } catch (Exception e) {
                                db = "";
                            }                            
                        }
                        if (partes[0].equals("user")) {
                            try {
                                user = partes[1];
                            } catch (Exception e) {
                                user = "";
                            }                            
                        }
                        if (partes[0].equals("pass")) {
                            try {
                                pass = partes[1];
                            } catch (Exception e) {
                                pass = "";
                            }                            
                        }
                    }
                }
            }
            conexion_string="jdbc:mysql://"+host+"/"+db;            
        }
        catch(Exception e){            
            JOptionPane.showMessageDialog(null,"Error de archivo config.txt "+e);
        }        
    }
    
    public void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conex = DriverManager.getConnection(conexion_string,user,pass);
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
    
    public void queryUpdate(String consulta) throws SQLException{
        st = conex.createStatement();
        st.executeUpdate(consulta);
    }
    
    public void desconectar() throws SQLException{
        if(conex!=null){
            conex.close();
        }
    }
}
