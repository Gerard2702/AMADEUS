/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amadeus;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import reservas.index_reserva;
/**
 *
 * @author Familia Aparicio
 */
public class index_user extends JFrame{
    
    private JPanel indexadmin;
    private JLabel imglogo,nameuser;
    private JButton reserva,vuelos,usuarios;
    private String nombreuser="",nombreuserin="";
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/icons/logo.png"));   
    private ImageIcon iconavion=new ImageIcon(this.getClass().getResource("/config/icons/avion.png")); 
    private ImageIcon iconlista=new ImageIcon(this.getClass().getResource("/config/icons/lista.png")); 
    /**
     * @param args the command line arguments
     */
    public index_user(String setuser,String setnombreusuario){
        nombreuser=setnombreusuario;
        nombreuserin=setuser;
        initComponent();        
        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus empleado");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(75,75,75));
        this.setResizable(false);        
    }
    
    public void initComponent(){
        
        indexadmin=new JPanel();
        indexadmin.setBackground(new Color(255,255,255));
        indexadmin.setBounds(40, 40, 420, 290);
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
        
        nameuser=new JLabel("Usuario: "+nombreuser);
        nameuser.setBounds(20, 255, 400, 15);
        indexadmin.add(nameuser);
        
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
        this.setVisible(false);
        index_reserva reserva=new index_reserva(nombreuserin,nombreuser,2);
        reserva.setVisible(true);
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
