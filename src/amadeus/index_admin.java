/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amadeus;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
import javax.swing.*;
/**
 *
 * @author Familia Aparicio
 */
public class index_admin extends JFrame {

    /**
     * @param args the command line arguments
     */
    public index_admin(){
        initComponent();        
        this.setSize(500,460);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus Administrador");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(168,168,168));
        this.setResizable(false);
    }
    
    public void initComponent(){
        addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent evt){
               salirmodulo();
           }          
       });
    }
    
    public void salirmodulo(){
        int dialogResult = JOptionPane.showConfirmDialog (rootPane, "¿Esta seguro de salir de aplicación?","Cerrar Sesión",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            this.setVisible(false);
            login iniciar=new login();
            iniciar.setVisible(true);
        }    
    }
}
