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
public class index_user extends JFrame{
    
    private JPanel indexadmin;
    private JLabel imglogo;
    private JButton reserva,vuelos,usuarios;
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/logo.png"));   
    private ImageIcon iconavion=new ImageIcon(this.getClass().getResource("/config/avion.png")); 
    private ImageIcon iconlista=new ImageIcon(this.getClass().getResource("/config/lista.png")); 
    /**
     * @param args the command line arguments
     */
    public index_user(){
        initComponent();        
        this.setSize(500,480);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus empleado");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(168,168,168));
        this.setResizable(false);
    }
    
    public void initComponent(){
        
        indexadmin=new JPanel();
        indexadmin.setBackground(new Color(230,230,230));
        indexadmin.setBounds(40, 40, 420, 370);
        indexadmin.setLayout(null);
        
        imglogo=new JLabel();
        imglogo.setBounds(10, 10, 263, 65);
        imglogo.setIcon(iconlogo);
        indexadmin.add(imglogo);
        
        vuelos=new JButton("Gestión de Vuelos");
        vuelos.setIcon(iconavion);
        vuelos.setBounds(100, 95, 210, 60);
        indexadmin.add(vuelos);
        
        reserva=new JButton("Gestión de Reservas");
        reserva.setIcon(iconlista);
        reserva.setBounds(100, 175, 210, 60);
        indexadmin.add(reserva);
        
        add(indexadmin);
        
        vuelos.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                modulo_vuelos();
            }
        });
        reserva.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                modulo_reservas();
            }
        });
        
        addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent evt){
               salirmodulo();
           }          
       });
    }
    
    public void modulo_vuelos(){
        //INSTANCIAR CLASE DE MODULO DE VUELOS
    }
    
    public void modulo_reservas(){
        //INSTANCIAR CLASE DE MODULO DE RESERVAS
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
