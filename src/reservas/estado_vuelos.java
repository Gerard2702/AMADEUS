/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
/**
 *
 * @author Familia Aparicio
 */
public class estado_vuelos extends JPanel{
    
    private JLabel prueba;
    
    public estado_vuelos(){
        initComponent();
        setLayout(null);
        setBounds(260,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
    public void initComponent(){
        prueba =new JLabel("Estado de Vuelos");
        prueba.setBounds(10, 10, 100, 50);
        add(prueba);
    }    
}
