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
 * @author PC
 */
public class modificar_vuelo extends JPanel
{
    private JLabel lblTitulo, lblhora1, lblhora2, lblruta, lblavion, lblfecha, x,lbloader;
    private JComboBox cbxRuta, cbxAvion;
    private JFormattedTextField txtHora1, txtHora2, txtFecha;
    private JButton btnModificar;
    private JSeparator sep;
    private JScrollPane jScrollPane1;
    private JTable jTable;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
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
        Font label = new Font("Calibri",1,15);
        Font error = new Font("Calibri",1,12);
        
        lblTitulo = new JLabel("ADMINISTRAR VUELOS DISPONIBLES");
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
            
            lblhora1 = new JLabel("Hora Inicio: ");
            lblhora1.setFont(label);
            lblhora1.setBounds(30,150,100,25);
            add(lblhora1);

            MaskFormatter mascara2 = null;
            try { 
                mascara2 = new MaskFormatter("##:##"); 
                } catch (ParseException ex) { 
            }
            txtHora1 = new JFormattedTextField(mascara2);
            txtHora1.setBounds(150,150,250,25);            
            add(txtHora1);

            lblhora2 = new JLabel("Hora LLegada: ");
            lblhora2.setFont(label);
            lblhora2.setBounds(30,190,100,25);
            add(lblhora2);

            txtHora2 = new JFormattedTextField(mascara2);
            txtHora2.setBounds(150,190,250,25);            
            add(txtHora2);

            lblfecha = new JLabel("Fecha: ");
            lblfecha.setFont(label);
            lblfecha.setBounds(30,230,100,25);
            add(lblfecha);

            MaskFormatter mascara = null;
            try { 
                mascara = new MaskFormatter("####-##-##"); 
                } catch (ParseException ex) { 
            }
            txtFecha = new JFormattedTextField(mascara);
            txtFecha.setBounds(150,230,250,25);
            add(txtFecha);

            lblavion = new JLabel("Avion: ");
            lblavion.setFont(label);
            lblavion.setBounds(30,270,100,25);
            add(lblavion);

            cbxAvion = new JComboBox();
            cbxAvion.setBounds(150,270,250,25);
            add(cbxAvion);

            lblruta = new JLabel("Ruta: ");
            lblruta.setFont(label);
            lblruta.setBounds(30,310,100,25);
            add(lblruta);

            cbxRuta = new JComboBox();
            cbxRuta.setBounds(150,310,250,25);
            add(cbxRuta);

            btnModificar = new JButton("Modificar");
            btnModificar.setBounds(125,375,200,25);
            btnModificar.setBackground(new Color(158,203,242));        
            btnModificar.setBorderPainted(false);
            btnModificar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent me) {
                    btnModificar.setBackground(new Color(200,200,200));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    btnModificar.setBackground(new Color(158,203,242));
                }            
            });
            add(btnModificar);
            btnModificar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                modificar_datos_vuelo(idvuelo);
            }
        });
        try{
            db.conectar();
            int idvuelo2 = Integer.parseInt(idvuelo);
            //OBTENER LOS VUELOS ALMACENADOS EN CUALQUIER ESTADO
            String sql = "CALL Vuelos_PA0007('"+idvuelo2+"')";
            ResultSet sd = db.query(sql);
            sd.first();            
            String fecha = sd.getString("fecha");
            int idavion = sd.getInt("avion_idavion");
            int idruta = sd.getInt("ruta_idruta"); 
            String hora1 = sd.getString("hora_inicio");
            String hora2 = sd.getString("hora_fin");
            //OBTENER VALORES DE RUTAS PARA VUELOS
            String sql2 = "CALL Vuelos_PA0008('"+idruta+"')";
            ResultSet sd2 = db.query(sql2);
            sd2.first();
            String destino = sd2.getString("ciudad");
            //OBTENER TODOS LOS DATOS DE AVION
            String sql3 = "CALL Vuelos_PA0009('"+idavion+"')";
            ResultSet sd3 = db.query(sql3);
            sd3.first();
            String codavion = sd3.getString("codigo");
            txtHora1.setText(hora1);
            txtHora2.setText(hora2);
            txtFecha.setText(fecha);
            cbxAvion.addItem(codavion);
            cbxAvion.setSelectedIndex(0);
            int indiceruta = 0, rutafinal = 0;
            //OBTENER AVIONES DISPONIBLES PARA VUELOS
            String sql4="CALL Vuelos_PA0001()";
            ResultSet rs = db.query(sql4);
            rs.last(); 
            if(rs.getRow()==0){
                cbxAvion.addItem("NO HAY AVIONES AGREGADOS");
            }
            else{
                rs.beforeFirst();
                while(rs.next()){                    
                    String idavion2=rs.getString("codigo");
                     cbxAvion.addItem(idavion2);
                }
            }
            //OBTENER TODOS LOS VALORES DE DESTINO DE VUELOS
            String sql5="CALL Vuelos_PA0010()";
            ResultSet rs2 = db.query(sql5);
            rs2.last(); 
            if(rs2.getRow()==0){
                cbxRuta.addItem("NO HAY RUTAS AGREGADOS");
            }
            else{
                rs2.beforeFirst();
                while(rs2.next()){
                String cid=rs2.getString("ciudad");
                if(cid.equals(destino))
                {
                    rutafinal = indiceruta;
                    cbxRuta.addItem("San Salvador - "+cid);
                }
                else{
                    indiceruta++;
                    cbxRuta.addItem("San Salvador - "+cid);
                }
                }
            }
            cbxRuta.setSelectedIndex(rutafinal);
            db.desconectar();   
        }catch(SQLException e){}
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
        //OBTENER TODOS LOS VUELOS EN ESTADO DISPONIBLE
        String sql="CALL Vuelos_PA0011()";
        ResultSet rs = db.query(sql);
        rs.last();
        int numrows = rs.getRow();
        Object [][]datos = new Object[numrows][8];
        rs.beforeFirst();
        int i = 0;
        while(rs.next()){
            datos[i][0]=rs.getString("idvuelos");
            //OBTENER VALOR DE LA CIUDAD DEL AEROPUERTO
            String sqldes="CALL Vuelos_PA0012('"+rs.getString("idvuelos")+"')";
            ResultSet rsdes=db.query(sqldes);
            rsdes.first();
            datos[i][1]=rsdes.getString("ciudad");
            datos[i][2]=rs.getString("fecha");
            datos[i][3]=rs.getString("hora_inicio");
            datos[i][4]=rs.getString("hora_fin");
            //OBTENER EL CODIGO DEL AVION DEL VUELO
            String sqlavion= "CALL Vuelos_PA0013('"+rs.getString("idvuelos")+"')";
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
                            //ELIMINAR VALOR DE VUELO DE BD
                            String sqldelete="CALL Vuelos_PA0014('"+sb.toString()+"')";
                            db.queryUpdate(sqldelete);
                            db.desconectar();
                            jScrollPane1.remove(jTable);
                            jTable = new JTable();
                            jTable.setRowHeight(20);
                            setTabla();
                            jScrollPane1.setViewportView(jTable);
                        }           
                       }
                       catch(Exception ex){
                           JOptionPane.showMessageDialog(null,"Error al eliminar Vuelo: Tiene Reservaciones");
                       }
                       
                   }
                   if(columna==6){                    
                    int dialogResult = JOptionPane.showConfirmDialog (null, "¿Esta seguro que desea Modificar el vuelo "+sb.toString()+" ?","Mensaje",JOptionPane.YES_NO_OPTION);
                        if(dialogResult == JOptionPane.YES_OPTION){
                            removeAll();
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
        
    private void modificar_datos_vuelo(String idvuelo)
    {
        String fecha = this.txtFecha.getText().toString();
        String horainicio = this.txtHora1.getText().toString();
        String horafin = this.txtHora2.getText().toString();
        String avion = this.cbxAvion.getSelectedItem().toString();
        String rutaorigen = "SAL";
        String rutadestinoop = this.cbxRuta.getSelectedItem().toString();
        String rutadestino = rutadestinoop.substring(15);
        int idvl = Integer.parseInt(idvuelo);
        if(!isFechaValida(fecha))
        {
            JOptionPane.showMessageDialog(null, "Fecha Invalida");
        }
        if(!isHoraValida(horainicio))
        {
            JOptionPane.showMessageDialog(null, "Hora Inicio Invalida");
        }
        if(!isHoraValida(horafin))
        {
            JOptionPane.showMessageDialog(null, "Hora Llegada Invalida");
        }
        if(isFechaValida(fecha) && isHoraValida(horainicio) && isHoraValida(horafin))
        {
            try{
                    db.conectar();
                    //OBTENER TODOS LOS DESTINOS O CIUDADES PARA VUELOS
                    String sqlidestino = "CALL Vuelos_PA0015('"+rutadestino+"')";
                    ResultSet iddesti = db.query(sqlidestino);
                    iddesti.first();                    
                    String idru = iddesti.getString("idaeropuertos");                    
                    //OBTENER VALORES DE RUTA DE VUELOS
                    String sqlid="CALL Vuelos_PA0016('"+rutaorigen+"','"+idru+"')";
                    ResultSet rsid = db.query(sqlid);
                    rsid.first();
                    int idruta = rsid.getInt("idruta");
                    //OBTENER VALORES DE AVION POR EL CODIGO
                    String sqlmale ="CALL Vuelos_PA0017('"+avion+"')";
                    ResultSet mal = db.query(sqlmale);
                    mal.first();
                    String maletasdis = mal.getString("maletas");
                    String asientosdis = mal.getString("asientos");
                    int idmod = mal.getInt("modelo_idmodelo");
                    int idavion = mal.getInt("idavion");                    
                    //SQL PARA MODIFICAR EL VUELO EN BD
                    String sqlupdate="CALL Vuelos_PA0018('"+horainicio+"','"+horafin+"','"+fecha+"','"+asientosdis+"','"+maletasdis+"','"+idavion+"','"+idmod+"','"+idruta+"','"+idvl+"')";
                    db.queryUpdate(sqlupdate);
                    JOptionPane.showMessageDialog(null,"MODIFICACIÓN EXITOSA");                    
                    db.desconectar();
                    
                    removeAll();
                    repaint();
                    initComponent();
                    setTabla();
                    }catch (SQLException ex) {
                        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                    } 
        }
    }
    
     public static boolean isFechaValida(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        }catch(ParseException e) {
            return false;
        }
        return true;
    }
     
    public static boolean isHoraValida(String hora) {
            SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm");
            formatohora.setLenient(false);
            try{
                formatohora.parse(hora);
            }catch(ParseException e)            
            {
             return false;
            }
            return true;
    }
}
