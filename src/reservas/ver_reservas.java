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
import java.util.*;
import config.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Familia Aparicio
 */
public class ver_reservas extends JPanel{
    
    private JLabel lblTitulo,lbloader,lbvuelo;
    private JSeparator sep;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
    private JComboBox cBxvuelos;
    private JScrollPane jScrollPane1;
    private JTable jTable;
    private JButton busqreserva;
    database db = new database();
    String[] colreservas = new String[]{
        "Código",
        "Nombre",
        "Clase",
        "Ruta",
        "Salida",        
        "Llegada",
        "Fecha"
    };
    
    
    public ver_reservas(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
    public void initComponent(){
        
        Font titulo = new Font("Calibri", 1, 19);
        
        lblTitulo = new JLabel("RESERVACIONES CREADAS");        
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
        
        lbvuelo=new JLabel("Seleccione vuelo:");
        lbvuelo.setBounds(10, 70, 125, 25);
        add(lbvuelo);
        cBxvuelos=new JComboBox();
        cBxvuelos.setBounds(145, 70, 150, 25);
        mostrar_vuelos();
        add(cBxvuelos);
        busqreserva=new JButton("Buscar Reservas");
        busqreserva.setBounds(315,70,175,25);
        add(busqreserva);
        busqreserva.setBackground(new Color(158,203,242));
        busqreserva.setBorderPainted(false);
        busqreserva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                busqreserva.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                busqreserva.setBackground(new Color(158,203,242));
            }            
        });
        busqreserva.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                buscar_reservas();
            }
        });
        
        jScrollPane1 = new JScrollPane();
        DefaultTableModel datosdb1= new DefaultTableModel(null,colreservas);
        jTable = new JTable(datosdb1);
        jTable.setRowHeight(20);        
        jScrollPane1.setViewportView(jTable);
        jScrollPane1.setBounds(10, 120, 690, 410);
        add(jScrollPane1);        
    }
    
    public void buscar_reservas(){
        String valorselect=cBxvuelos.getSelectedItem().toString();
        String[]partes;
        partes=valorselect.split("-");
        String idvuelo=partes[0];
        
         try{
            db.conectar();
            String sql="SELECT vuelos.fecha,usuarios_has_vuelos.codigo,usuarios.nombre,clase_vuelo.clase,CONCAT(ruta.origen,\"-\",ruta.destino) AS ruta,vuelos.hora_inicio,vuelos.hora_fin FROM usuarios_has_vuelos,usuarios,clase_vuelo,ruta,vuelos WHERE usuarios_has_vuelos.vuelos_idvuelos='"+idvuelo+"' AND usuarios_has_vuelos.usuarios_idusuarios=usuarios.idusuarios AND usuarios_has_vuelos.clase_vuelo_idclase_vuelo=clase_vuelo.idclase_vuelo AND usuarios_has_vuelos.vuelos_idvuelos=vuelos.idvuelos AND vuelos.ruta_idruta=ruta.idruta AND vuelos.estado_idestado=1";
            ResultSet rs = db.query(sql);
            rs.last();
            int numrows = rs.getRow();
            Object [][]datos = new Object[numrows][7];
            rs.beforeFirst();
            int i = 0;
            while(rs.next()){
                datos[i][0]=rs.getString("codigo");
                datos[i][1]=rs.getString("nombre");
                datos[i][2]=rs.getString("clase");
                datos[i][3]=rs.getString("ruta");
                datos[i][4]=rs.getString("hora_inicio");
                datos[i][5]=rs.getString("hora_fin");
                datos[i][6]=rs.getString("fecha");
                i++;
            }
            db.desconectar();
            jTable.setModel(new DefaultTableModel(datos,colreservas));               
        }
        catch(Exception e){
            System.out.println("Error obteniendo Reservas. . .");
        }
    }
    
    public void mostrar_vuelos(){
        try{
            db.conectar();
            String sql="SELECT CONCAT(vuelos.idvuelos,\"-\",ruta.origen,\" \",ruta.destino) AS vuelo FROM vuelos,ruta WHERE vuelos.estado_idestado=1 AND vuelos.ruta_idruta=ruta.idruta";
            ResultSet rs = db.query(sql);            
            while(rs.next()){
                cBxvuelos.addItem(rs.getString("vuelo"));
            }
            db.desconectar();            
        }
        catch(Exception e){
            System.out.println("Error obteniendo vuelos. . .");
        }
    }
}
