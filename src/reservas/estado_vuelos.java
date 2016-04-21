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
/**
 *
 * @author Familia Aparicio
 */
public class estado_vuelos extends JPanel{
    
    private JLabel lblTitulo,lbloader,lbbusq;
    private JButton btnbusq;
    private JSeparator sep;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
    private JScrollPane jScrollPane1;
    private JTable jTable;
    private JComboBox ruta;
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
    database db = new database();
    
    public estado_vuelos(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
    public void initComponent(){
        
        Font titulo = new Font("Calibri", 1, 19);
        
        lblTitulo = new JLabel("ESTADO DE VUELOS");        
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
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTable.rowAtPoint(e.getPoint());
                int columna = jTable.columnAtPoint(e.getPoint());
                lbloader.setVisible(true);  
                ruta_finalizar(fila,columna);
                setTable();
                lbloader.setVisible(false);  
            }
        });
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
            "Estado",
            "Editar",
            "Editar"
            };
            db.conectar();
            //OBTENER LOS VUELOS QUE SE ENCUENTREN ALMACENADOS
            String sql="CALL Reservas_PA0005()";
            ResultSet rs = db.query(sql);
            rs.last();
            int numrows = rs.getRow();
            Object [][]datos = new Object[numrows][9];
            rs.beforeFirst();
            int i = 0;
            while(rs.next()){
                datos[i][0]=rs.getString("idvuelos");
                datos[i][1]=rs.getString("ruta");
                datos[i][2]=rs.getString("fecha");
                datos[i][3]=rs.getString("hora_inicio");
                datos[i][4]=rs.getString("hora_fin");                
                datos[i][5]=rs.getString("estado");
                datos[i][6]=new JButton("En ruta");
                datos[i][7]=new JButton("Fin ruta");
                i++;
            }
            db.desconectar();
            jTable.setModel(new DefaultTableModel(datos,columnas){
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
            
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error en vuelos . . ."); 
        }        
    }   
    
    private void ruta_finalizar(int row, int col){
        if(col==6){
            String id=String.valueOf(jTable.getValueAt(row,0));
            try{
                db.conectar();
                //ACTUALIZAR EL ESTADO DEL VUELO A EN RUTA
                String sqlupdate="CALL Reservas_PA0006('2','"+id+"')";
                db.queryUpdate(sqlupdate);                                
                db.desconectar();
                JOptionPane.showMessageDialog(null, "Cambio de estado: Vuelo En Ruta");                
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error en cambio de estado . . ."); 
            }            
        }
        if(col==7){
            String id=String.valueOf(jTable.getValueAt(row,0));
            try{
                db.conectar();
                //ACTUALIZAR EL ESTADO DEL VUELO A FINALIZADO
                String sqlupdate="CALL Reservas_PA0006('3','"+id+"')";
                db.queryUpdate(sqlupdate);                                
                //OBTENER EL CODIGO DEL AVION DEL VUELO QUE SE MODIFICA ESTADO
                String sqlidavion="CALL Reservas_PA0007('"+id+"')";
                ResultSet rs22 = db.query(sqlidavion);
                if(rs22.first()){
                    String idavion=rs22.getString("avion_idavion");
                    //LIBERAR TODOS LOS ASIENTOS DEL AVION DE ESTE VUELO
                    String sqlupdate2="CALL Reservas_PA0008('"+idavion+"')";
                    db.queryUpdate(sqlupdate2);
                }
                db.desconectar();
                JOptionPane.showMessageDialog(null, "Cambio de estado: Vuelo Finalizado");                
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error en cambio de estado . . ."); 
            }        
        }        
    }
    
    private void setRutas(){
        try{
            db.conectar();
            //OBTENER LAS RUTAS ALMACENADAS EN BD
            String sql="CALL Reservas_PA0002()";
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
    
    private void busquedaTable(String item){        
        try{
        String[] orgdes=item.split("-");
        String origen=orgdes[0];
        String destino= orgdes[1];
        db.conectar();
        //OBTENER RUTA POR ORIGEN Y DESTINO
        String sql1="CALL Reservas_PA0003('"+origen+"','"+destino+"')";
        ResultSet rs1 = db.query(sql1);
        rs1.first();
        int rutars = rs1.getInt("idruta");
        db.desconectar();
        String[] columnas = new String[]{
            "Id",
            "Destino",
            "Fecha",
            "Salida",
            "LLegada",            
            "Estado",
            "Editar",
            "Editar"
        };        
            db.conectar();
            //OBTENER VUELOS QUE COINCIDEN CON EL PARAMETRO DE BUSQUEDA
            String sql="CALL Reservas_PA0009('"+rutars+"')";
            ResultSet rs = db.query(sql);
            rs.last();
            int numrows = rs.getRow();
            Object [][]datos = new Object[numrows][8];
            rs.beforeFirst();
            int i = 0;
            while(rs.next()){
                datos[i][0]=rs.getString("idvuelos");
                datos[i][1]=rs.getString("ruta");
                datos[i][2]=rs.getString("fecha");
                datos[i][3]=rs.getString("hora_inicio");
                datos[i][4]=rs.getString("hora_fin");                
                datos[i][5]=rs.getString("estado");
                datos[i][6]=new JButton("En ruta");
                datos[i][7]=new JButton("Fin ruta");
                i++;
            }
            db.desconectar();
            jTable.setModel(new DefaultTableModel(datos,columnas){
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
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error en busqueda . . ."); 
        }
    }
}
