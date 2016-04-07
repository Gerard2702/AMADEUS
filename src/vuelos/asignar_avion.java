/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuelos;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;

/**
 *
 * @author PC
 */
public class asignar_avion extends JPanel
{
    private JLabel prueba;
    
    public asignar_avion(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
    public void initComponent(){
        prueba =new JLabel("Asignar Avion");
        prueba.setBounds(10, 10, 100, 50);
        add(prueba);
    }
}
