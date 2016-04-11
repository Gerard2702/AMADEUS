/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuelos;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author PC
 */
public class modificar_vuelo extends JPanel
{
    private JLabel lblTitulo;
    private JSeparator sep;
    private JScrollPane jScrollPane1;
    private JTable jTable;
    database db = new database();
    
    public modificar_vuelo(){
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
        
        lblTitulo = new JLabel("ADMINISTRAR VUELOS");
        lblTitulo.setFont(titulo);
        lblTitulo.setBounds(10,10,300,50);
        add(lblTitulo);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setFont(label);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);
            
        jScrollPane1 = new JScrollPane();
        jTable = new JTable();
        
        jScrollPane1.setViewportView(jTable);
        jScrollPane1.setBounds(10, 75, 685, 450);
        add(jScrollPane1);
     
    }
    
    public void initEditForm(String idvuelo){
        Font titulo = new Font("Calibri", 1, 19);
        Font label = new Font("Calibri",0,15);
        Font error = new Font("Calibri",0,12);
        
        lblTitulo = new JLabel("MODIFICAR VUELO "+idvuelo);
        lblTitulo.setFont(titulo);
        lblTitulo.setBounds(10,10,300,50);
        add(lblTitulo);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setFont(label);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);
        
        
    }
    
        private void setTabla() {

        try{ 
            
            String[] columnas = new String[]{
            "id",
            "Destino",
            "Fecha",
            "Hora Despegue",
            "Hora LLegada",
            "Avion",
            "Modificar",
            "Eliminar"};


        final Class[] tiposColumnas = new Class[]{
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            JButton.class,
            JButton.class
        };
        
        db.conectar();
        String sql="SELECT *FROM vuelos where estado_idestado=1;";
        ResultSet rs = db.query(sql);
        rs.last();
        int numrows = rs.getRow();
        Object [][]datos = new Object[numrows][8];
        rs.beforeFirst();
        int i = 0;
        while(rs.next()){
            datos[i][0]=rs.getString("idvuelos");
            String sqldes="SELECT aeropuertos.ciudad FROM vuelos INNER JOIN ruta ON vuelos.ruta_idruta=ruta.idruta INNER JOIN aeropuertos ON aeropuertos.idaeropuertos=ruta.destino WHERE vuelos.idvuelos='"+rs.getString("idvuelos")+"'";
            ResultSet rsdes=db.query(sqldes);
            rsdes.first();
            datos[i][1]=rsdes.getString("ciudad");
            datos[i][2]=rs.getString("fecha");
            datos[i][3]=rs.getString("hora_inicio");
            datos[i][4]=rs.getString("hora_fin");
            String sqlavion= "SELECT avion.codigo FROM vuelos INNER JOIN avion ON vuelos.avion_idavion = avion.idavion WHERE vuelos.idvuelos='"+rs.getString("idvuelos")+"'";
            ResultSet rsavion = db.query(sqlavion);
            rsavion.first();
            datos[i][5]=rsavion.getString("codigo");
            datos[i][6]= new JButton("Modificar");
            datos[i][7]= new JButton("Eliminar");
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

        jTable.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {

                return (Component) objeto;
            }
        });

        
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTable.rowAtPoint(e.getPoint());
                int columna = jTable.columnAtPoint(e.getPoint());

               
                if (jTable.getModel().getColumnClass(columna).equals(JButton.class)) {

                    StringBuilder sb = new StringBuilder();
                    sb.append(jTable.getModel().getValueAt(fila, 0));
                            
                   if(columna==7){
                       try{
                        int dialogResult = JOptionPane.showConfirmDialog (null, "¿Esta seguro que desea Eliminar el vuelo "+sb.toString()+" ?","Mensaje",JOptionPane.YES_NO_OPTION);
                        if(dialogResult == JOptionPane.YES_OPTION){
                            db.conectar();
                            String sqldelete="DELETE FROM vuelos WHERE idvuelos='"+sb.toString()+"'";
                            db.queryUpdate(sqldelete);
                            db.desconectar();
                            jScrollPane1.remove(jTable);
                            jTable = new JTable();
                            setTabla();
                            jScrollPane1.setViewportView(jTable);
                        }           
                       }
                       catch(Exception ex){
                           JOptionPane.showMessageDialog(null,"Error al eliminar el vuelo"+ex);
                       }
                       
                   }
                   if(columna==6){
                    //JOptionPane.showMessageDialog(null, "Seleccionada la fila " + fila +"Columna "+columna + sb.toString());
                    int dialogResult = JOptionPane.showConfirmDialog (null, "¿Esta seguro que desea Modificar el vuelo "+sb.toString()+" ?","Mensaje",JOptionPane.YES_NO_OPTION);
                        if(dialogResult == JOptionPane.YES_OPTION){
                            remove(lblTitulo);
                            remove(sep);
                            remove(jScrollPane1);
                            repaint();
                            initEditForm(sb.toString());
                        }  
                   }
                }
            }
        });
        
        jTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(75);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(6).setPreferredWidth(80);
                
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Seleccionada la fila "+ e); 
        }            
    }  
}
