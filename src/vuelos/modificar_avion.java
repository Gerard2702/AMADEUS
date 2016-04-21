/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuelos;
import amadeus.login;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Gerard
 */
public class modificar_avion extends JPanel{
    
    private JLabel lblTitulo, lblhora1, lblhora2, lblruta, lblavion, lblfecha, x,lbloader;
    private JComboBox cbxRuta, cbxAvion;
    private JFormattedTextField txtHora1, txtHora2, txtFecha;
    private JButton btnModificar;
    private JSeparator sep;
    private JScrollPane jScrollPane1;
    private JTable jTable;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
    database db = new database();
    
    public modificar_avion(){
        initComponent();
        setTabla();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
     public void initComponent(){
        Font titulo = new Font("Calibri", 1, 19);
        Font label = new Font("Calibri",0,15);
        Font error = new Font("Calibri",0,12);
        
        lblTitulo = new JLabel("REGISTRO DE AVIONES");
        lblTitulo.setFont(titulo);
        lblTitulo.setBounds(10,10,300,50);
        add(lblTitulo);
        
        lbloader=new JLabel("Cargando...");
        lbloader.setIcon(iconloader);
        lbloader.setBounds(585,15,100,27);
        lbloader.setVisible(false);
        add(lbloader);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setFont(label);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);
            
        jScrollPane1 = new JScrollPane();
        jTable = new JTable();
        jTable.setRowHeight(20);
        
        jScrollPane1.setViewportView(jTable);
        jScrollPane1.setBounds(10, 75, 685, 450);
        add(jScrollPane1); 
    }
     
     private void setTabla() {

        try{ 
            
        String[] columnas = new String[]{
            "No",
            "Codigo",
            "Asientos",
            "Maletas",
            "Modelo",
            "Estado"
        };

        final Class[] tiposColumnas = new Class[]{
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class
        };
        
        db.conectar();
        //OBTENER LOS AVIONES ALMACENADOS EN BD
        String sql="CALL Vuelos_PA0025()";
        ResultSet rs = db.query(sql);
        rs.last();
        int numrows = rs.getRow();
        Object [][]datos = new Object[numrows][6];
        rs.beforeFirst();
        int i = 0;
        String estado; 
        while(rs.next()){
            datos[i][0]=i+1;           
            datos[i][1]=rs.getString("codigo");
            datos[i][2]=rs.getString("asientos");
            datos[i][3]=rs.getString("maletas");
            datos[i][4]=rs.getString("modelo");
            estado=rs.getString("estado");
            if(estado==null){
                datos[i][5]="Disponible";
            }
            else{                
                datos[i][5]="Ocupado";
            }           
            i++;
        }
        db.desconectar();
            
        jTable.setModel(new DefaultTableModel(datos,columnas) {
            Class[] tipos = tiposColumnas;

            @Override
            public Class getColumnClass(int columnIndex) {

                return tipos[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                this.getColumnClass(column).equals(String.class);
                return !(this.getColumnClass(column).equals(String.class));
            }
            
        });               
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "ERROR OBTENIENDO INFORMACION DE AVIONES. . ."); 
        }            
    }
          
}
