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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author Familia Aparicio
 */
public class crear_reserva extends JPanel{
    
    private JLabel lblTitulo,lbloader,lbbusquser,lbbusqfecha,lbclasevuelo;
    private JSeparator sep;  
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
    private JTable jTuser,jTvuelos;
    private JScrollPane jScrolluser,jScrollvuelos;
    private JTextField busquser;
    private JFormattedTextField busqfecha;
    private JButton btnuser,btnfecha,btncrear,btnlimpiar;
    private JComboBox cBxrutavuelo,cBxclasevuelo;
    final Class[] tiposcolumnaUser = new Class[]{
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,        
        JButton.class
    };
    final Class[] tiposcolumnaVuelos = new Class[]{
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,        
        JButton.class
    };
    
    public crear_reserva(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
    public void initComponent(){
        
        Font titulo = new Font("Calibri", 1, 19);
        
        lblTitulo = new JLabel("CREACION DE RESERVACION");        
        lblTitulo.setBounds(10,10,300,50);
        lblTitulo.setFont(titulo);
        add(lblTitulo);
        
        lbloader=new JLabel("Cargando...");
        lbloader.setIcon(iconloader);
        lbloader.setBounds(585,15,100,27);
        lbloader.setVisible(false);
        add(lbloader);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);
        
        //BUSQUEDA USUARIO
        lbbusquser=new JLabel("Ingrese nombre de cliente:");
        lbbusquser.setBounds(10, 65, 170, 25);
        add(lbbusquser);
        busquser=new JTextField();
        busquser.setBounds(190, 65, 160, 25);
        add(busquser);
        btnuser=new JButton("Busqueda");
        btnuser.setBounds(360, 65, 100, 25);
        btnuser.setBackground(new Color(158,203,242));
        btnuser.setBorderPainted(false);
        btnuser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnuser.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnuser.setBackground(new Color(158,203,242));
            }            
        });
        add(btnuser);        
        jScrolluser = new JScrollPane();
        jTuser = new JTable();
        jTuser.setRowHeight(20);
        jScrolluser.setViewportView(jTuser);
        jScrolluser.setBounds(10, 100, 690, 120);
        add(jScrolluser);
        
        //BUSQUEDA VUELOS
        lbbusqfecha=new JLabel("Ruta y Fecha de vuelo:");
        lbbusqfecha.setBounds(10, 240, 150, 25);
        add(lbbusqfecha);
        cBxrutavuelo=new JComboBox();
        cBxrutavuelo.setBounds(165, 240, 125, 25);
        add(cBxrutavuelo);
        MaskFormatter mascara =null;
        try{
            mascara =new MaskFormatter("####-##-##"); 
        }
        catch(Exception e){
        }
        busqfecha=new JFormattedTextField(mascara);
        busqfecha.setBounds(300,240,125,25);
        add(busqfecha);
        btnfecha=new JButton("Busqueda");
        btnfecha.setBounds(435, 240, 100, 25);        
        btnfecha.setBackground(new Color(158,203,242));
        btnfecha.setBorderPainted(false);
        btnfecha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnfecha.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnfecha.setBackground(new Color(158,203,242));
            }            
        });
        add(btnfecha);
        jScrollvuelos = new JScrollPane();
        jTvuelos = new JTable();
        jTvuelos.setRowHeight(20);
        jScrollvuelos.setViewportView(jTvuelos);
        jScrollvuelos.setBounds(10, 275, 690, 120);
        add(jScrollvuelos);
        
        //SELECCION DE CLASE DE VUELO
        lbclasevuelo=new JLabel("Seleccione clase de vuelo:");
        lbclasevuelo.setBounds(10, 415, 150, 25);
        add(lbclasevuelo);
        cBxclasevuelo=new JComboBox();
        cBxclasevuelo.setBounds(170, 415, 120, 25);
        add(cBxclasevuelo);
        
        //ACCIONES DE RESERVACION
        btncrear=new JButton("Crear Reservacion");
        btncrear.setBounds(192, 475, 150, 30);
        btncrear.setBackground(new Color(158,203,242));
        btncrear.setBorderPainted(false);
        btncrear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btncrear.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btncrear.setBackground(new Color(158,203,242));
            }            
        });
        add(btncrear);
        btnlimpiar=new JButton("Limpiar");
        btnlimpiar.setBounds(362,475,150,30);
        btnlimpiar.setBackground(new Color(158,203,242));
        btnlimpiar.setBorderPainted(false);
        btnlimpiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnlimpiar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnlimpiar.setBackground(new Color(158,203,242));
            }            
        });
        add(btnlimpiar);
    }    
}
