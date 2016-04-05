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
public class vuelos_disponibles extends JPanel{
    
    private JLabel prueba;
    
    public vuelos_disponibles(){
        initComponent();
        setLayout(null);
        setBounds(260,30,705,540);
        setBackground(new Color(255,255,255));       
    }
    
    public void initComponent(){
        prueba =new JLabel("Vuelos disponibles");
        prueba.setBounds(10, 10, 150, 50);
        add(prueba);
    }
}
