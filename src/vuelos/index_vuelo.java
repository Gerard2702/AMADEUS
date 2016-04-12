/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuelos;
import amadeus.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
/**
 *
 * @author PC
 */
public class index_vuelo extends JFrame
{
    private String nombreuser="",nombreuserin="";
    private Integer roluser;
    private JPanel menu,jframe=new JPanel(),bordenegro;
    private crear_vuelo crear;
    private modificar_vuelo modificar;
    private borrar_vuelo borrar;
    private agregar_avion agregar;
    private JButton btnborrar,btnagregaavion,btnmodvuelo,btncrearvuelo,btnasignar;
    private JLabel logo, pie,cerrar,minimizar,avionhead,separ1,separ2,separ3,separ4;
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/icons/logo_modulos.png"));
    private ImageIcon iconpie=new ImageIcon(this.getClass().getResource("/config/icons/pie_modulo.png"));
    private ImageIcon cerrar1=new ImageIcon(this.getClass().getResource("/config/icons/cerrar.png"));
    private ImageIcon cerrar2=new ImageIcon(this.getClass().getResource("/config/icons/cerrarhover.png"));
    private ImageIcon min1=new ImageIcon(this.getClass().getResource("/config/icons/min.png"));
    private ImageIcon min2=new ImageIcon(this.getClass().getResource("/config/icons/minhover.png"));
    private ImageIcon iconhead=new ImageIcon(this.getClass().getResource("/config/icons/avion_head.png"));
    private ImageIcon iconmodif=new ImageIcon(this.getClass().getResource("/config/icons/modificar_icon.png"));
    private ImageIcon iconavionestado=new ImageIcon(this.getClass().getResource("/config/icons/avion_estado_vuelo.png"));
    private ImageIcon icondel=new ImageIcon(this.getClass().getResource("/config/icons/icon_delete.png"));
    
    public index_vuelo(String setuser,String setnombreusuario,Integer roluss){
        nombreuser=setnombreusuario;
        nombreuserin=setuser;
        roluser=roluss;
        initComponent();        
        this.setSize(945,575);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus - Vuelos");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(75,75,75));
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/config/icons/avion_icon.png")).getImage());
        this.setUndecorated(true);
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
        
        btncrearvuelo =new JButton("Crear Vuelo");        
        btncrearvuelo.setIcon(iconavionestado);
        btncrearvuelo.setBounds(14, 80, 200, 50);
        btncrearvuelo.setBackground(new Color(158,203,242));        
        btncrearvuelo.setBorderPainted(false);
        btncrearvuelo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btncrearvuelo.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btncrearvuelo.setBackground(new Color(158,203,242));
            }            
        });       
        menu.add(btncrearvuelo);
        btncrearvuelo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                crearvuelo_Component();
            }
        });        
        
        btnmodvuelo=new JButton("Administrar Vuelos");
        btnmodvuelo.setBounds(14, 140, 200, 50);
        btnmodvuelo.setIcon(iconmodif);
        btnmodvuelo.setBackground(new Color(158,203,242));        
        btnmodvuelo.setBorderPainted(false);
        btnmodvuelo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnmodvuelo.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnmodvuelo.setBackground(new Color(158,203,242));
            }            
        }); 
        menu.add(btnmodvuelo);
        btnmodvuelo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                modificarvuelo_Component();
            }
        });
        
        
        btnborrar=new JButton("Borrar Vuelos");
        btnborrar.setBounds(14,200,200,50);
        btnborrar.setIcon(icondel);
        btnborrar.setBackground(new Color(158,203,242));        
        btnborrar.setBorderPainted(false);
        btnborrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnborrar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnborrar.setBackground(new Color(158,203,242));
            }            
        }); 
        menu.add(btnborrar);
        btnborrar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                borrarvuelo_Component();
            }
        });
        
        btnagregaavion=new JButton("Agregar Avion");
        btnagregaavion.setBounds(14,260,200,50);
        btnagregaavion.setIcon(iconavionestado);
        btnagregaavion.setBackground(new Color(158,203,242));        
        btnagregaavion.setBorderPainted(false);
        btnagregaavion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnagregaavion.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnagregaavion.setBackground(new Color(158,203,242));
            }            
        }); 
        menu.add(btnagregaavion);
        btnagregaavion.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                agregaravion_Component();
            }
        });
        
        pie=new JLabel();
        pie.setIcon(iconpie);
        pie.setBounds(19, 500, 190, 25);
        menu.add(pie);
        
        jframe.add(menu);       
        crearvuelo_Component();
    }
    
    public void crearvuelo_Component(){
        if(crear!=null){
            jframe.remove(crear);
            repaint();
        }
        if(modificar!=null){
            jframe.remove(modificar);
            repaint();
        }
        if(borrar!=null){
            jframe.remove(borrar);
            repaint();
        }
        if(agregar!=null){
            jframe.remove(agregar);
            repaint();
        }
        crear=new crear_vuelo();
        jframe.add(crear);
    }
    
    public void modificarvuelo_Component(){
        if(crear!=null){
            jframe.remove(crear);
            repaint();
        }
        if(modificar!=null){
            jframe.remove(modificar);
            repaint();
        }
        if(borrar!=null){
            jframe.remove(borrar);
            repaint();
        }
        if(agregar!=null){
            jframe.remove(agregar);
            repaint();
        }
        modificar=new modificar_vuelo();
        jframe.add(modificar);        
    }
    
    public void borrarvuelo_Component(){
        if(crear!=null){
            jframe.remove(crear);
            repaint();
        }
        if(modificar!=null){
            jframe.remove(modificar);
            repaint();
        }
        if(borrar!=null){
            jframe.remove(borrar);
            repaint();
        }
        if(agregar!=null){
            jframe.remove(agregar);
            repaint();
        }
        borrar=new borrar_vuelo();
        jframe.add(borrar);
    }
    
    public void agregaravion_Component(){
        if(crear!=null){
            jframe.remove(crear);
            repaint();
        }
        if(modificar!=null){
            jframe.remove(modificar);
            repaint();
        }
        if(borrar!=null){
            jframe.remove(borrar);
            repaint();
        }
        if(agregar!=null){
            jframe.remove(agregar);
            repaint();
        }
        agregar=new agregar_avion();
        jframe.add(agregar);
    }
    
    private void ventana_form(){
        
        avionhead=new JLabel();
        avionhead.setIcon(iconhead);
        avionhead.setBounds(5, 5, 20, 20);
        add(avionhead);
                
        minimizar=new JLabel();
        minimizar.setIcon(min1);
        minimizar.setBounds(885, 5, 20, 20);
        add(minimizar);
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
        add(cerrar);
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
