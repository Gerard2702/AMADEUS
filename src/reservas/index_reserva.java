/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;
import amadeus.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
/**
 *
 * @author Familia Aparicio
 */
public class index_reserva extends JFrame{
    
    private String nombreuser="",nombreuserin="";
    private Integer roluser;
    private JPanel menu,jframe=new JPanel(),bordenegro;
    private vuelos_disponibles vuelos;
    private estado_vuelos estado;
    private crear_reserva reserva;
    private check_in checkin;
    private ver_reservas vereserva;
    private JButton btnreserva,btncheckin,btnestadovuel,btnvuelosdispo,btnvereservas;
    private JLabel logo, pie,cerrar,minimizar,avionhead,separ1,separ2,separ3,separ4;
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/icons/logo_modulos.png"));
    private ImageIcon iconpie=new ImageIcon(this.getClass().getResource("/config/icons/pie_modulo.png"));
    private ImageIcon cerrar1=new ImageIcon(this.getClass().getResource("/config/icons/cerrar.png"));
    private ImageIcon cerrar2=new ImageIcon(this.getClass().getResource("/config/icons/cerrarhover.png"));
    private ImageIcon min1=new ImageIcon(this.getClass().getResource("/config/icons/min.png"));
    private ImageIcon min2=new ImageIcon(this.getClass().getResource("/config/icons/minhover.png"));
    private ImageIcon iconhead=new ImageIcon(this.getClass().getResource("/config/icons/avion_head.png"));
    private ImageIcon iconcheckin=new ImageIcon(this.getClass().getResource("/config/icons/checkin_icon.png"));
    private ImageIcon iconavionestado=new ImageIcon(this.getClass().getResource("/config/icons/avion_estado_vuelo.png"));
    private ImageIcon iconreserva=new ImageIcon(this.getClass().getResource("/config/icons/reserva_icon.png"));
    private static Point mouseDownCompCoords;
    
    public index_reserva(String setuser,String setnombreusuario,Integer roluss){
        nombreuser=setnombreusuario;
        nombreuserin=setuser;
        roluser=roluss;
        initComponent();        
        this.setSize(945,575);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus - Reservas");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(75,75,75));
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/config/icons/avion_icon.png")).getImage());
        //MOVIMIENTO DE VENTANA
        this.setUndecorated(true);
        this.setVisible(true);
        this.addMouseListener(new MouseListener(){
            public void mouseReleased(MouseEvent e) {
                mouseDownCompCoords = null;
            }
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
            public void mouseExited(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseClicked(MouseEvent e) {
            }
        });
        this.addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
            }
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });       
    }
    
    public void initComponent(){
        
        jframe.setBounds(0,0,945,575);
        jframe.setBorder(BorderFactory.createLineBorder(new Color(220,220,220), 1));
        jframe.setLayout(null);
        jframe.setBackground(new Color(255,255,255)); 
        
        ventana_form();
        menu();
        
        add(jframe);
    }
    
    public void menu(){
        menu=new JPanel();
        menu.setLayout(null);
        menu.setBounds(5, 30, 229, 540);
        menu.setBackground(new Color(255,255,255));
        
        bordenegro=new JPanel();
        bordenegro.setLayout(null);
        bordenegro.setBounds(234,30,1,540);
        bordenegro.setBackground(new Color(220,220,220));
        jframe.add(bordenegro);
        
        logo=new JLabel();
        logo.setIcon(iconlogo);
        logo.setBounds(19, 10, 190, 55);
        menu.add(logo); 
        
        btnvuelosdispo =new JButton("Vuelos Disponibles");
        btnvuelosdispo.setIcon(iconavionestado);
        btnvuelosdispo.setBounds(14, 80, 200, 50);        
        btnvuelosdispo.setBackground(new Color(158,203,242));
        btnvuelosdispo.setBorderPainted(false);
        btnvuelosdispo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnvuelosdispo.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnvuelosdispo.setBackground(new Color(158,203,242));
            }            
        });       
        menu.add(btnvuelosdispo);
        btnvuelosdispo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                vuelosdisp_Component();
            }
        });
        
        separ1=new JLabel();
        separ1.setBackground(new Color(220,220,220));
        separ1.setBounds(14, 130, 200, 1);
        menu.add(separ1);
        
        btnestadovuel=new JButton("Estado Vuelos         ");
        btnestadovuel.setIcon(iconavionestado);
        btnestadovuel.setBounds(14, 131, 200, 50);
        btnestadovuel.setBackground(new Color(158,203,242));
        btnestadovuel.setBorderPainted(false);
        btnestadovuel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnestadovuel.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnestadovuel.setBackground(new Color(158,203,242));
            }            
        });       
        menu.add(btnestadovuel);
        btnestadovuel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                estadovuelos_Component();
            }
        });
        
        separ2=new JLabel();
        separ2.setBackground(new Color(220,220,220));
        separ2.setBounds(14, 180, 200, 1);
        menu.add(separ2);
        
        btnreserva=new JButton("Reservaciones         ");
        btnreserva.setIcon(iconreserva);
        btnreserva.setBounds(14,182,200,50);
        btnreserva.setBackground(new Color(158,203,242));
        btnreserva.setBorderPainted(false);
        btnreserva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnreserva.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnreserva.setBackground(new Color(158,203,242));
            }            
        });       
        menu.add(btnreserva);
        btnreserva.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                crearreserva_Component();
            }
        });
        
        separ3=new JLabel();
        separ3.setBackground(new Color(220,220,220));
        separ3.setBounds(14, 230, 200, 1);
        menu.add(separ3);
        
        btncheckin=new JButton("Check-In                  ");
        btncheckin.setIcon(iconcheckin);
        btncheckin.setBounds(14,233,200,50);
        btncheckin.setBackground(new Color(158,203,242));
        btncheckin.setBorderPainted(false);
        btncheckin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btncheckin.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btncheckin.setBackground(new Color(158,203,242));
            }            
        });       
        menu.add(btncheckin);
        btncheckin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                checkin_Component();
            }
        });
        
        separ4=new JLabel();
        separ4.setBackground(new Color(220,220,220));
        separ4.setBounds(14, 283, 200, 1);
        menu.add(separ4);
        
        btnvereservas=new JButton("Ver reservas         ");
        btnvereservas.setBounds(14, 284, 200, 50);
        btnvereservas.setIcon(iconreserva);
        btnvereservas.setBackground(new Color(158,203,242));
        btnvereservas.setBorderPainted(false);
        btnvereservas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnvereservas.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnvereservas.setBackground(new Color(158,203,242));
            }            
        });       
        menu.add(btnvereservas);
        btnvereservas.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                verReserva_Component();
            }
        });
        
        pie=new JLabel();
        pie.setIcon(iconpie);
        pie.setBounds(19, 500, 190, 25);
        menu.add(pie);        
        
        jframe.add(menu);    
        vuelosdisp_Component();
    }
    
    public void vuelosdisp_Component(){
        if(vuelos!=null){
            jframe.remove(vuelos);
            validate();
            repaint();
        }
        if(estado!=null){
            jframe.remove(estado);
            validate();
            repaint();
        }
        if(reserva!=null){
            jframe.remove(reserva);
            validate();
            repaint();
        }
        if(checkin!=null){
            jframe.remove(checkin);
            validate();
            repaint();
        }
        if(vereserva!=null){
            jframe.remove(vereserva);
            validate();
            repaint();
        }
        vuelos=new vuelos_disponibles();
        jframe.add(vuelos);
        validate();
        repaint();
    }
    
    public void estadovuelos_Component(){
        if(vuelos!=null){
            jframe.remove(vuelos);
            validate();
            repaint();
        }
        if(estado!=null){
            jframe.remove(estado);
            validate();
            repaint();
        }
        if(reserva!=null){
            jframe.remove(reserva);
            validate();
            repaint();
        }
        if(checkin!=null){
            jframe.remove(checkin);
            validate();
            repaint();
        }
        if(vereserva!=null){
            jframe.remove(vereserva);
            validate();
            repaint();
        }
        estado=new estado_vuelos();
        jframe.add(estado);
        validate();
        repaint();        
    }
    
    public void crearreserva_Component(){
        if(vuelos!=null){
            jframe.remove(vuelos);
            validate();
            repaint();
        }
        if(estado!=null){
            jframe.remove(estado);
            validate();
            repaint();
        }
        if(reserva!=null){
            jframe.remove(reserva);
            validate();
            repaint();
        }
        if(checkin!=null){
            jframe.remove(checkin);
            validate();
            repaint();
        }
        if(vereserva!=null){
            jframe.remove(vereserva);
            validate();
            repaint();
        }
        reserva=new crear_reserva();
        jframe.add(reserva);
        validate();
        repaint();
    }
    
    public void checkin_Component(){
        if(vuelos!=null){
            jframe.remove(vuelos);
            validate();
            repaint();
        }
        if(estado!=null){
            jframe.remove(estado);
            validate();
            repaint();
        }
        if(reserva!=null){
            jframe.remove(reserva);
            validate();
            repaint();
        }
        if(checkin!=null){
            jframe.remove(checkin);
            validate();
            repaint();
        }
        if(vereserva!=null){
            jframe.remove(vereserva);
            validate();
            repaint();
        }
        checkin=new check_in();
        jframe.add(checkin);
        validate();
        repaint();
    }
    
    public void verReserva_Component(){
        if(vuelos!=null){
            jframe.remove(vuelos);
            validate();
            repaint();
        }
        if(estado!=null){
            jframe.remove(estado);
            validate();
            repaint();
        }
        if(reserva!=null){
            jframe.remove(reserva);
            validate();
            repaint();
        }
        if(checkin!=null){
            jframe.remove(checkin);
            validate();
            repaint();
        }
        if(vereserva!=null){
            jframe.remove(vereserva);
            validate();
            repaint();
        }
        vereserva=new ver_reservas();
        jframe.add(vereserva);
        validate();
        repaint();
    }
    
    private void ventana_form(){
        
        avionhead=new JLabel();
        avionhead.setIcon(iconhead);
        avionhead.setBounds(5, 5, 20, 20);
        jframe.add(avionhead);
                
        minimizar=new JLabel();
        minimizar.setIcon(min1);
        minimizar.setBounds(885, 5, 20, 20);
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
        cerrar.setBounds(917, 5, 20, 20);
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
        int dialogResult = JOptionPane.showConfirmDialog (rootPane, "¿Esta seguro de salir?","Mensaje",JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            this.setVisible(false);
            if(roluser==1){
                index_admin admin=new index_admin(nombreuserin,nombreuser);
                admin.setVisible(true);
            }
            else if(roluser==2){
                index_user user=new index_user(nombreuserin,nombreuser);
                user.setVisible(true);
            }
            else{
                this.setVisible(true);
            }
        }    
    }
}
