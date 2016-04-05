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
public class crear_reserva extends JPanel{
    
    private JLabel prueba;
    
    public crear_reserva(){
        initComponent();
        setLayout(null);
        setBounds(260,30,705,540);
        setBackground(new Color(225,165,165));  
    }
    
    public void initComponent(){
        prueba =new JLabel("Reservaciones");
        prueba.setBounds(10, 10, 100, 50);
        add(prueba);
    }    
}
