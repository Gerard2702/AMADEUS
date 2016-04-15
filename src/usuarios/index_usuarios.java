package usuarios;

import amadeus.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class index_usuarios extends JFrame{
    
    private String nombreuser="",nombreuserin="";
    private Integer roluser;
    private JPanel menu,jframe=new JPanel(),bordenegro;
    private usuarios usuarios;
    private nuevo_usuario nuevoUsuario;
    private JButton btnNuevoUsuario,btnUsuarios;
    private JLabel logo, pie,cerrar,minimizar,avionhead;
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/icons/logo_modulos.png"));
    private ImageIcon iconpie=new ImageIcon(this.getClass().getResource("/config/icons/pie_modulo.png"));
    private ImageIcon cerrar1=new ImageIcon(this.getClass().getResource("/config/icons/cerrar.png"));
    private ImageIcon cerrar2=new ImageIcon(this.getClass().getResource("/config/icons/cerrarhover.png"));
    private ImageIcon min1=new ImageIcon(this.getClass().getResource("/config/icons/min.png"));
    private ImageIcon min2=new ImageIcon(this.getClass().getResource("/config/icons/minhover.png"));
    private ImageIcon iconhead=new ImageIcon(this.getClass().getResource("/config/icons/avion_head.png"));
    private ImageIcon iconuser=new ImageIcon(this.getClass().getResource("/config/icons/user_icon.png")); 
    
    public index_usuarios(String setuser,String setnombreusuario,Integer roluss){
        nombreuser=setnombreusuario;
        nombreuserin=setuser;
        roluser=roluss;
        try {        
            initComponent();
        } catch (SQLException ex) {
            Logger.getLogger(index_usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setSize(945,575);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus - Gestión de Usuarios");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(75,75,75));
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/config/icons/avion_icon.png")).getImage());
        this.setUndecorated(true);
    }
    
    public void initComponent() throws SQLException{
        
        jframe.setBounds(0,0,945,575);
        jframe.setBorder(BorderFactory.createLineBorder(new Color(220,220,220), 1));
        jframe.setLayout(null);
        jframe.setBackground(new Color(255,255,255)); 
        
        ventana_form();
        menu();
        
        add(jframe);
    }
    
    public void menu() throws SQLException{
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
        
        btnUsuarios = new JButton("Usuarios Registrados");
        btnUsuarios.setBounds(14, 80, 200, 50);
        btnUsuarios.setIcon(iconuser);
        btnUsuarios.setBackground(new Color(158,203,242));
        btnUsuarios.setBorderPainted(false);
        btnUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnUsuarios.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnUsuarios.setBackground(new Color(158,203,242));
            }            
        });
        menu.add(btnUsuarios);
        
        btnUsuarios.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                try {
                    usuarios_Component();
                } catch (SQLException ex) {
                    Logger.getLogger(index_usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnNuevoUsuario = new JButton("Nuevo Usuario");
        btnNuevoUsuario.setBounds(14, 131, 200, 50);
        btnNuevoUsuario.setIcon(iconuser);
        btnNuevoUsuario.setBackground(new Color(158,203,242));
        btnNuevoUsuario.setBorderPainted(false);
        btnNuevoUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnNuevoUsuario.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnNuevoUsuario.setBackground(new Color(158,203,242));
            }            
        });
        menu.add(btnNuevoUsuario);
        
        btnNuevoUsuario.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                nuevo_usuario_Component();
            }
        });
        
        pie=new JLabel();
        pie.setIcon(iconpie);
        pie.setBounds(19, 500, 190, 25);
        menu.add(pie);
        
        jframe.add(menu);    
        usuarios_Component();
    }
    
    public void usuarios_Component() throws SQLException{
        if(usuarios!=null){
            jframe.remove(usuarios);
            repaint();
        }
        if(nuevoUsuario!=null){
            jframe.remove(nuevoUsuario);
            repaint();
        }
        usuarios=new usuarios();
        jframe.add(usuarios);
    }
    
    public void nuevo_usuario_Component(){
        if(usuarios!=null){
            jframe.remove(usuarios);
            repaint();
        }
        if(nuevoUsuario!=null){
            jframe.remove(nuevoUsuario);
            repaint();
        }
        nuevoUsuario=new nuevo_usuario();
        jframe.add(nuevoUsuario);        
    }
    
    public void crearreserva_Component(){
        if(usuarios!=null){
            jframe.remove(usuarios);
            repaint();
        }
        if(nuevoUsuario!=null){
            jframe.remove(nuevoUsuario);
            repaint();
        }
    }
    
    public void checkin_Component(){
        if(usuarios!=null){
            jframe.remove(usuarios);
            repaint();
        }
        if(nuevoUsuario!=null){
            jframe.remove(nuevoUsuario);
            repaint();
        }
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
