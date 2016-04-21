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
    private String idusuario="",idvuelo="",idclasevuelo="",codigoreserva="";
    final Class[] tiposcolumnaUser = new Class[]{
        String.class,
        String.class,
        String.class,
        String.class,          
        JButton.class
    };
    String[] colUser = new String[]{
        "Id",
        "Nombre",
        "Correo",
        "Telefono",
        "Seleccionar"
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
    String[] colVuelo = new String[]{
        "Id",
        "Destino",
        "Fecha",
        "Salida",
        "LLegada",        
        "Asientos",
        "Seleccionar"
    };
    database db = new database();
    
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
        btnuser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                buscar_usuario();
            }
        });
        add(btnuser);        
        jScrolluser = new JScrollPane();
        DefaultTableModel datosdb1= new DefaultTableModel(null,colUser);
        jTuser = new JTable(datosdb1);        
        jTuser.setRowHeight(20);
        jTuser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTuser.rowAtPoint(e.getPoint());
                int columna = jTuser.columnAtPoint(e.getPoint());
                lbloader.setVisible(true);  
                idusuario=String.valueOf(jTuser.getValueAt(fila,0));
                JOptionPane.showMessageDialog(null, "Seleccionado usuario con Id: "+idusuario);
                lbloader.setVisible(false);  
            }
        });
        jScrolluser.setViewportView(jTuser);
        jScrolluser.setBounds(10, 100, 690, 120);
        add(jScrolluser);
        
        //BUSQUEDA VUELOS
        lbbusqfecha=new JLabel("Ruta y Fecha de vuelo:");
        lbbusqfecha.setBounds(10, 240, 150, 25);
        add(lbbusqfecha);
        cBxrutavuelo=new JComboBox();
        cBxrutavuelo.setBounds(165, 240, 125, 25);
        rutas_disponibles();
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
        btnfecha.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                buscar_vuelo();
            }
        });
        add(btnfecha);
        jScrollvuelos = new JScrollPane();
        DefaultTableModel datosdb2= new DefaultTableModel(null,colVuelo);
        jTvuelos = new JTable(datosdb2);
        jTvuelos.setRowHeight(20);
        jTvuelos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = jTvuelos.rowAtPoint(e.getPoint());
                int columna = jTvuelos.columnAtPoint(e.getPoint());
                lbloader.setVisible(true);  
                idvuelo=String.valueOf(jTvuelos.getValueAt(fila,0));
                JOptionPane.showMessageDialog(null, "Seleccionado vuelo con Id: "+idvuelo);
                lbloader.setVisible(false);  
            }
        });
        jTvuelos.getColumnModel().getColumn(0).setPreferredWidth(30);
        jScrollvuelos.setViewportView(jTvuelos);
        jScrollvuelos.setBounds(10, 275, 690, 120);
        add(jScrollvuelos);
        
        //SELECCION DE CLASE DE VUELO
        lbclasevuelo=new JLabel("Seleccione clase de vuelo:");
        lbclasevuelo.setBounds(10, 415, 150, 25);
        add(lbclasevuelo);
        cBxclasevuelo=new JComboBox();
        cBxclasevuelo.setBounds(170, 415, 160, 25);
        clases_disponibles();
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
        btncrear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                guardar_reserva();
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
        btnlimpiar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                limpiar_reserva();
            }
        });
        add(btnlimpiar);
    }
    
    public void guardar_reserva(){
        if(!idusuario.equals("") && !idvuelo.equals("")){
            String[] partes;
            String vuelostring=cBxclasevuelo.getSelectedItem().toString();
            partes=vuelostring.split("-");
            idclasevuelo=partes[0];
            codigoreserva=generar_codigo();
            try{
                lbloader.setVisible(true);
                db.conectar();
                //ALMACENAR UNA NUEVA RESERVA EN BD
                String sqlupdate="CALL Reservas_PA0012('"+idusuario+"','"+idvuelo+"','"+idclasevuelo+"','0','"+codigoreserva+"')";
                db.queryUpdate(sqlupdate);                                
                db.desconectar(); 
                db.conectar();
                //DISMINUIR EL NUMERO DE ASIENTOS DISPONIBLES EN AVION DE VUELO
                String sqlupdate2="CALL Reservas_PA0013('"+idvuelo+"')";
                db.queryUpdate(sqlupdate2);                                
                db.desconectar(); 
                JOptionPane.showMessageDialog(null, "Se ha creado la Resevacion");
                PDF_create pdf=new PDF_create(codigoreserva);
                limpiar_reserva();
                lbloader.setVisible(false);
                
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error creacion de Reserva . . .");
                lbloader.setVisible(false);
            }            
        }
        else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar un Usuario y un Vuelo");
        }
    }
    
    public String generar_codigo(){
        String codigo="";
        String[] letras={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        Random r = new Random();
        int v1;
        for(int i=0;i<8;i++){
            v1 = r.nextInt(26);        
            codigo=codigo+letras[v1];
        }        
        return codigo;
    }
    
    public void limpiar_reserva(){
        idusuario="";
        idvuelo="";
        idclasevuelo="";
        codigoreserva="";       
    }
    
    public void buscar_usuario(){
        if(!busquser.getText().equals("")){
            try{
                db.conectar();
                //BUSCAR USUARIO A RESERVAR EN BD
                String sql="CALL Reservas_PA0014('%"+busquser.getText()+"%')";
                ResultSet rs = db.query(sql);
                rs.last();
                int numrows = rs.getRow();
                Object [][]datos = new Object[numrows][5];
                rs.beforeFirst();
                int i = 0;
                while(rs.next()){
                    datos[i][0]=rs.getString("idusuarios");
                    datos[i][1]=rs.getString("nombre");
                    datos[i][2]=rs.getString("correo");
                    datos[i][3]=rs.getString("telefono");                    
                    datos[i][4]=new JButton("Seleccionar");
                    i++;
                }
                db.desconectar();
                jTuser.setModel(new DefaultTableModel(datos,colUser){
                Class[] tipos = tiposcolumnaUser;
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

                jTuser.setDefaultRenderer(JButton.class, new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                        return (Component) objeto;
                    }
                });            
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error en busqueda de usuario . . .");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Debe escribir un nombre de cliente");
        }
    }
    
    public void buscar_vuelo(){
        if(!busqfecha.getText().equals("    -  -  ") && !cBxrutavuelo.getSelectedItem().equals("")){
            try{
                String[] partes;
                String selectvuelo=cBxrutavuelo.getSelectedItem().toString();
                partes=selectvuelo.split("-");                
                db.conectar();
                //BUSCAR VUELOS POR RUTA PARA RESERVACION
                String sql="CALL Reservas_PA0015('"+busqfecha.getText()+"','"+partes[0]+"')";
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
                    datos[i][5]=rs.getString("asientos_disponibles");
                    datos[i][6]=new JButton("Seleccionar");
                    i++;                    
                }
                db.desconectar();
                jTvuelos.setModel(new DefaultTableModel(datos,colVuelo){
                Class[] tipos = tiposcolumnaVuelos;
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
                
                jTvuelos.setDefaultRenderer(JButton.class, new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                        return (Component) objeto;
                    }
                });    
                
                jTvuelos.getColumnModel().getColumn(0).setPreferredWidth(30);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error en busqueda de vuelo . . .");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Debe Seleccionar ruta y escribir Fecha");
        }
    }
    
    public void clases_disponibles(){
        try{
            db.conectar();
            //CLASES DE PARA VIAJAR EN VUELO
            String sql="CALL Reservas_PA0016()";
            ResultSet rs = db.query(sql);
            while(rs.next()){
                cBxclasevuelo.addItem(rs.getString("clase"));                 
            }
            db.desconectar();
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null,"Error en Clases de vuelo . . ."); 
        }    
    }
    
    public void rutas_disponibles(){
        try{
            db.conectar();
            //OBTENER LAS RUTAS DISPONIBLES PARA VUELO
            String sql="CALL Reservas_PA0017()";
            ResultSet rs = db.query(sql);
            while(rs.next()){
                cBxrutavuelo.addItem(rs.getString("ruta"));                 
            }
            db.desconectar();
        }
        catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error en Rutas de vuelo . . ."); 
        }    
    }
}
