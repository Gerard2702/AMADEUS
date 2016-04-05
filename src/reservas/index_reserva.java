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
    private JPanel menu;
    private vuelos_disponibles vuelos;
    private estado_vuelos estado;
    private crear_reserva reserva;
    private check_in checkin;
    private JButton btnreserva,btncheckin,btnestadovuel,btnvuelosdispo;
    private JLabel logo, pie;
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/icons/logo_modulos.png"));
    private ImageIcon iconpie=new ImageIcon(this.getClass().getResource("/config/icons/pie_modulo.png"));        
    
    public index_reserva(String setuser,String setnombreusuario,Integer roluss){
        nombreuser=setnombreusuario;
        nombreuserin=setuser;
        roluser=roluss;
        initComponent();        
        this.setSize(1000,625);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus - Reservas");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.getContentPane().setBackground(new Color(168,168,168));
        this.setResizable(false);        
    }
    
    public void initComponent(){
        menu();        
        //EVENTO PARA NO CERRAR LA APLICACION TOTALMENTE
        addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent evt){
               salirmodulo();
           }          
        });
    }
    
    public void menu(){
        menu=new JPanel();
        menu.setLayout(null);
        menu.setBounds(30, 30, 229, 540);
        menu.setBackground(new Color(225,165,165));
        
        logo=new JLabel();
        logo.setIcon(iconlogo);
        logo.setBounds(19, 10, 190, 55);
        menu.add(logo); 
        
        btnvuelosdispo =new JButton("Vuelos Disponibles");
        btnvuelosdispo.setBounds(14, 80, 200, 50);
        menu.add(btnvuelosdispo);
        btnvuelosdispo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                vuelosdisp_Component();
            }
        });
        
        btnestadovuel=new JButton("Estado Vuelos");
        btnestadovuel.setBounds(14, 140, 200, 50);
        menu.add(btnestadovuel);
        btnestadovuel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                estadovuelos_Component();
            }
        });
        
        btnreserva=new JButton("Reservaciones");
        btnreserva.setBounds(14,200,200,50);
        menu.add(btnreserva);
        btnreserva.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                crearreserva_Component();
            }
        });
        
        btncheckin=new JButton("Check-In");
        btncheckin.setBounds(14,260,200,50);
        menu.add(btncheckin);
        btncheckin.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                checkin_Component();
            }
        });
        
        pie=new JLabel();
        pie.setIcon(iconpie);
        pie.setBounds(19, 500, 190, 25);
        menu.add(pie);
        
        getContentPane().add(menu);    
        vuelosdisp_Component();
    }
    
    public void vuelosdisp_Component(){
        if(vuelos!=null){
            getContentPane().remove(vuelos);
            repaint();
        }
        if(estado!=null){
            getContentPane().remove(estado);
            repaint();
        }
        if(reserva!=null){
            getContentPane().remove(reserva);
            repaint();
        }
        if(checkin!=null){
            getContentPane().remove(checkin);
            repaint();
        }
        vuelos=new vuelos_disponibles();
        getContentPane().add(vuelos);
    }
    
    public void estadovuelos_Component(){
        if(vuelos!=null){
            getContentPane().remove(vuelos);
            repaint();
        }
        if(estado!=null){
            getContentPane().remove(estado);
            repaint();
        }
        if(reserva!=null){
            getContentPane().remove(reserva);
            repaint();
        }
        if(checkin!=null){
            getContentPane().remove(checkin);
            repaint();
        }
        estado=new estado_vuelos();
        getContentPane().add(estado);        
    }
    
    public void crearreserva_Component(){
        if(vuelos!=null){
            getContentPane().remove(vuelos);
            repaint();
        }
        if(estado!=null){
            getContentPane().remove(estado);
            repaint();
        }
        if(reserva!=null){
            getContentPane().remove(reserva);
            repaint();
        }
        if(checkin!=null){
            getContentPane().remove(checkin);
            repaint();
        }
        reserva=new crear_reserva();
        getContentPane().add(reserva);
    }
    
    public void checkin_Component(){
        if(vuelos!=null){
            getContentPane().remove(vuelos);
            repaint();
        }
        if(estado!=null){
            getContentPane().remove(estado);
            repaint();
        }
        if(reserva!=null){
            getContentPane().remove(reserva);
            repaint();
        }
        if(checkin!=null){
            getContentPane().remove(checkin);
            repaint();
        }
        checkin=new check_in();
        getContentPane().add(checkin);
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
