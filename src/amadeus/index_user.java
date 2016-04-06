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
    
    private JPanel indexadmin,jframe=new JPanel();
    private JLabel imglogo,nameuser,cerrar,minimizar,avionhead;
    private JButton reserva,vuelos,usuarios;
    private String nombreuser="",nombreuserin="";
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/icons/logo.png"));   
    private ImageIcon iconavion=new ImageIcon(this.getClass().getResource("/config/icons/avion.png")); 
    private ImageIcon iconlista=new ImageIcon(this.getClass().getResource("/config/icons/lista.png"));
    private ImageIcon cerrar1=new ImageIcon(this.getClass().getResource("/config/icons/cerrar.png"));
    private ImageIcon cerrar2=new ImageIcon(this.getClass().getResource("/config/icons/cerrarhover.png"));
    private ImageIcon min1=new ImageIcon(this.getClass().getResource("/config/icons/min.png"));
    private ImageIcon min2=new ImageIcon(this.getClass().getResource("/config/icons/minhover.png"));
    private ImageIcon iconhead=new ImageIcon(this.getClass().getResource("/config/icons/avion_head.png"));
    /**
     * @param args the command line arguments
     */
    public index_user(String setuser,String setnombreusuario){
        nombreuser=setnombreusuario;
        nombreuserin=setuser;
        initComponent();        
        this.setSize(430,325);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus empleado");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(75,75,75));
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/config/icons/avion_icon.png")).getImage());
        this.setUndecorated(true);        
    }
    
    public void initComponent(){
        
        jframe.setBounds(0,0,430,420);
        jframe.setBorder(BorderFactory.createLineBorder(new Color(220,220,220), 1));
        jframe.setLayout(null);
        jframe.setBackground(new Color(255,255,255)); 
        
        ventana_form();
        
        indexadmin=new JPanel();
        indexadmin.setBackground(new Color(255,255,255));
        indexadmin.setBounds(5, 30, 420, 290);
        indexadmin.setLayout(null);
        
        imglogo=new JLabel();
        imglogo.setBounds(85, 10, 263, 65);
        imglogo.setIcon(iconlogo);
        indexadmin.add(imglogo);
        
        vuelos=new JButton("Gestión de Vuelos");
        vuelos.setIcon(iconavion);
        vuelos.setBounds(100, 95, 210, 60);
        vuelos.setBackground(new Color(158,203,242));
        vuelos.setBorderPainted(false);
        vuelos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                vuelos.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                vuelos.setBackground(new Color(158,203,242));
            }            
        });       
        indexadmin.add(vuelos);
        
        reserva=new JButton("Gestión de Reservas");
        reserva.setIcon(iconlista);
        reserva.setBounds(100, 175, 210, 60);
        reserva.setBackground(new Color(158,203,242));
        reserva.setBorderPainted(false);
        reserva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                reserva.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                reserva.setBackground(new Color(158,203,242));
            }            
        });       
        indexadmin.add(reserva);
        
        nameuser=new JLabel("Usuario: "+nombreuser);
        nameuser.setBounds(20, 255, 400, 15);
        indexadmin.add(nameuser);
        
        jframe.add(indexadmin);
        
        add(jframe);
        
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
    
    private void ventana_form(){
        
        avionhead=new JLabel();
        avionhead.setIcon(iconhead);
        avionhead.setBounds(5, 5, 20, 20);
        jframe.add(avionhead);
                
        minimizar=new JLabel();
        minimizar.setIcon(min1);
        minimizar.setBounds(370, 5, 20, 20);
        jframe.add(minimizar);
        minimizar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                minimizar.setIcon(min2);
            }
             @Override
            public void mouseExited(MouseEvent e) {
                minimizar.setIcon(min1);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setExtendedState(JFrame.CROSSHAIR_CURSOR); 
            }
        });       
        
        cerrar=new JLabel();
        cerrar.setIcon(cerrar1);
        cerrar.setBounds(402, 5, 20, 20);
        jframe.add(cerrar);
        cerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                cerrar.setIcon(cerrar2);
            }
             @Override
            public void mouseExited(MouseEvent e) {
                cerrar.setIcon(cerrar1);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
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
