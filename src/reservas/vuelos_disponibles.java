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
public class vuelos_disponibles extends JPanel{
    
    private JLabel lblTitulo,lbloader,lbbusq;
    private JButton btnbusq;
    private JSeparator sep;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
    private JScrollPane jScrollPane1;
    private JTable jTable;
    private JComboBox ruta;
    database db = new database();
    
    public vuelos_disponibles(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));       
    }
    
    public void initComponent(){
        
        Font titulo = new Font("Calibri", 1, 19);
        
        lblTitulo = new JLabel("VUELOS DISPONIBLES");        
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
        
        lbbusq=new JLabel("Busqueda por ruta: ");
        lbbusq.setBounds(10, 70, 120, 25);
        add(lbbusq);
        ruta = new JComboBox();            
        ruta.setBounds(130,70,150,20);
        add(ruta);
        btnbusq=new JButton("Busqueda");
        btnbusq.setBounds(295, 70, 100, 20);
        btnbusq.setBackground(new Color(158,203,242));
        btnbusq.setBorderPainted(false);
        btnbusq.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnbusq.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnbusq.setBackground(new Color(158,203,242));
            }            
        });
        btnbusq.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                lbloader.setVisible(true); 
                busquedaTable(ruta.getSelectedItem().toString());
                lbloader.setVisible(false); 
            }
        });
        add(btnbusq);        
        
        jScrollPane1 = new JScrollPane();
        jTable = new JTable();
        jTable.setRowHeight(20);        
        jScrollPane1.setViewportView(jTable);
        jScrollPane1.setBounds(10, 120, 690, 410);
        add(jScrollPane1);              
        
        lbloader.setVisible(true);        
        setRutas();        
        setTable();
        lbloader.setVisible(false);
    }
    
    private void setTable(){
        try{
            String[] columnas = new String[]{
            "Id",
            "Destino",
            "Fecha",
            "Salida",
            "LLegada",
            "Avion",
            "Estado"
            };
            db.conectar();
            String sql="SELECT vuelos.idvuelos,vuelos.fecha,vuelos.hora_inicio,vuelos.hora_fin, CONCAT(ruta.origen,\"-\",ruta.destino) AS ruta,avion.codigo,estado.estado FROM vuelos,ruta,avion,estado where estado_idestado=1 AND vuelos.estado_idestado=estado.idestado AND vuelos.ruta_idruta=ruta.idruta AND vuelos.avion_idavion=avion.idavion";
            ResultSet rs = db.query(sql);
            rs.last();
            int numrows = rs.getRow();
            Object [][]datos = new Object[numrows][7];
            rs.beforeFirst();
            int i = 0;
            while(rs.next()){
                datos[i][0]=rs.getString("idvuelos");
                datos[i][1]=rs.getString("ruta");
                datos[i][2]=rs.getString("fecha");
                datos[i][3]=rs.getString("hora_inicio");
                datos[i][4]=rs.getString("hora_fin");
                datos[i][5]=rs.getString("codigo");
                datos[i][6]=rs.getString("estado");
                i++;
            }
            db.desconectar();
            jTable.setModel(new DefaultTableModel(datos,columnas));           
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error en vuelos . . ."); 
        }        
    }   
    
    private void setRutas(){
        try{
            db.conectar();
            String sql="SELECT CONCAT(ruta.origen,\"-\",ruta.destino) AS ruta FROM ruta";
            ResultSet rs = db.query(sql);
            while(rs.next()){
                ruta.addItem(rs.getString("ruta"));                 
            }
            db.desconectar();
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error en vuelos . . ."); 
        }    
    }
    
    private void clear_Table(){
        try {
            DefaultTableModel modelo=(DefaultTableModel) jTable.getModel();
            int filas=jTable.getRowCount();
            for (int i = 0;i<filas; i++) {
                modelo.removeRow(i);
            }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    private void busquedaTable(String item){        
        try{
        String[] orgdes=item.split("-");
        String origen=orgdes[0];
        String destino= orgdes[1];
        db.conectar();
        String sql1="SELECT idruta FROM ruta WHERE origen='"+origen+"' AND destino='"+destino+"'";
        ResultSet rs1 = db.query(sql1);
        rs1.first();
        int rutars = rs1.getInt("idruta");
        db.desconectar();
        clear_Table();
        String[] columnas = new String[]{
            "Id",
            "Destino",
            "Fecha",
            "Salida",
            "LLegada",
            "Avion",
            "Estado"
            };
            db.conectar();
            String sql="SELECT vuelos.idvuelos,vuelos.fecha,vuelos.hora_inicio,vuelos.hora_fin, CONCAT(ruta.origen,\"-\",ruta.destino) AS ruta,avion.codigo,estado.estado FROM vuelos,ruta,avion,estado where estado_idestado=1 AND vuelos.estado_idestado=estado.idestado AND vuelos.ruta_idruta=ruta.idruta AND vuelos.avion_idavion=avion.idavion AND vuelos.ruta_idruta="+rutars;
            ResultSet rs = db.query(sql);
            rs.last();
            int numrows = rs.getRow();
            Object [][]datos = new Object[numrows][7];
            rs.beforeFirst();
            int i = 0;
            while(rs.next()){
                datos[i][0]=rs.getString("idvuelos");
                datos[i][1]=rs.getString("ruta");
                datos[i][2]=rs.getString("fecha");
                datos[i][3]=rs.getString("hora_inicio");
                datos[i][4]=rs.getString("hora_fin");
                datos[i][5]=rs.getString("codigo");
                datos[i][6]=rs.getString("estado");
                i++;
            }
            db.desconectar();
            jTable.setModel(new DefaultTableModel(datos,columnas)); 
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error en busqueda . . ."); 
        }
    }
}
