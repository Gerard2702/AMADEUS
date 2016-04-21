package usuarios;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import validaciones.*;

public class usuarios extends JPanel {
    
    //Todos los usuarios
    private JLabel titulo,lbloader;
    private JButton btnEditarUsuario;
    private JScrollPane ScrollPane;
    private JTable Table;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
    private JSeparator sep;
    
    //Editar usuario
    private JLabel lblNombre;
    private JLabel lblCorreo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblCPassword;
    private JLabel lblTelefono;
    private JLabel lblPasaporte;
    private JLabel lblTarjetaCredito;

    private JTextField txtNombre;
    private validacion_correo txtCorreo;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JPasswordField txtCPassword;
    private validacion_telefono txtTelefono;
    private JTextField txtPasaporte;
    private validacion_tarjeta_credito txtTarjetaCredito;
    private JComboBox txtEstado;

    private JButton btnGuardarCambios;
    
    database conn = new database();

    public usuarios() throws SQLException {
        initComponent();
        cargarUsuarios();
        setLayout(null);
        setBounds(235, 30, 705, 540);
        setBackground(new Color(255, 255, 255));
    }

    public void initComponent() {
        Font titulo1 = new Font("Calibri", 1, 19);
        
        titulo = new JLabel("USUARIOS REGISTRADOS");        
        titulo.setBounds(10,10,300,50);
        titulo.setFont(titulo1);
        add(titulo);
        
        lbloader=new JLabel("Cargando...");
        lbloader.setIcon(iconloader);
        lbloader.setBounds(585,15,100,27);
        lbloader.setVisible(false);
        add(lbloader);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);  

        btnEditarUsuario = new JButton("Editar Usuario");
        btnEditarUsuario.setBounds(490, 65, 200, 30);
        btnEditarUsuario.setBackground(new Color(158, 203, 242));
        btnEditarUsuario.setBorderPainted(false);
        btnEditarUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnEditarUsuario.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEditarUsuario.setBackground(new Color(158, 203, 242));
            }
        });
        btnEditarUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnEditarUsuarioActionPerformed(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.add(btnEditarUsuario);

        //Instancias de clase
        ScrollPane = new JScrollPane();
        ScrollPane.setBounds(15, 110, 675, 415);

        Table = new JTable();
        Table.setRowHeight(20);
        
        ScrollPane.setViewportView(Table);

        this.add(ScrollPane);
    }

    public void initComponentsEditarUsuario(String usuario_selecto) throws SQLException {
        titulo.setText("Editar Usuario");

        lblNombre = new JLabel("Nombre:");
        lblCorreo = new JLabel("Correo:");
        lblUsuario = new JLabel("Usuario:");
        lblPassword = new JLabel("Nueva Contraseña:");
        lblCPassword = new JLabel("Confirmar Contraseña:");
        lblTelefono = new JLabel("Telefono:");
        lblPasaporte = new JLabel("Pasaporte:");
        lblTarjetaCredito = new JLabel("Tarjeta de Credito:");

        txtNombre = new JTextField();
        txtCorreo = new validacion_correo();
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        txtCPassword = new JPasswordField();
        txtTelefono = new validacion_telefono();
        txtPasaporte = new JTextField();
        txtTarjetaCredito = new validacion_tarjeta_credito();
        txtEstado = new JComboBox();
        txtEstado.addItem("Activo");
        txtEstado.addItem("Inactivo");

        btnGuardarCambios = new JButton("Guardar Cambios");

        lblNombre.setBounds(100, 100, 250, 30);
        lblCorreo.setBounds(100, 140, 250, 30);
        lblUsuario.setBounds(100, 180, 250, 30);
        lblPassword.setBounds(100, 220, 250, 30);
        lblCPassword.setBounds(100, 260, 250, 30);
        lblTelefono.setBounds(100, 300, 250, 30);
        lblPasaporte.setBounds(100, 340, 250, 30);
        lblTarjetaCredito.setBounds(100, 380, 250, 30);

        txtNombre.setBounds(370, 100, 250, 30);
        txtCorreo.setBounds(370, 140, 250, 30);
        txtUsuario.setBounds(370, 180, 250, 30);
        txtPassword.setBounds(370, 220, 250, 30);
        txtCPassword.setBounds(370, 260, 250, 30);
        txtTelefono.setBounds(370, 300, 250, 30);
        txtPasaporte.setBounds(370, 340, 250, 30);
        txtTarjetaCredito.setBounds(370, 380, 250, 30);
        txtEstado.setBounds(370, 420, 250, 30);

        btnGuardarCambios.setBounds(470, 460, 150, 30);

        btnGuardarCambios.setBackground(new Color(158, 203, 242));
        btnGuardarCambios.setBorderPainted(false);
        btnGuardarCambios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnGuardarCambios.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnGuardarCambios.setBackground(new Color(158, 203, 242));
            }
        });

        btnGuardarCambios.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnGuardarCambiosActionPerformed(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        txtNombre.enable(false);
        txtUsuario.enable(false);
        txtPasaporte.enable(false);

        cargarUsuarioSeleccionado(usuario_selecto);

        this.add(lblNombre);
        this.add(lblCorreo);
        this.add(lblUsuario);
        this.add(lblPassword);
        this.add(lblCPassword);
        this.add(lblTelefono);
        this.add(lblPasaporte);
        this.add(lblTarjetaCredito);

        this.add(txtNombre);
        this.add(txtCorreo);
        this.add(txtUsuario);
        this.add(txtPassword);
        this.add(txtCPassword);
        this.add(txtTelefono);
        this.add(txtPasaporte);
        this.add(txtTarjetaCredito);
        this.add(txtEstado);

        this.add(btnGuardarCambios);
    }

    private void cargarUsuarios() throws SQLException {
        try {
            
            //Definir las columnas del modelo de tabla
            DefaultTableModel objDTM = new DefaultTableModel();
            objDTM.setColumnIdentifiers(new Object[]{"Nombre", "Usuario", "Correo","Telefono","Estado"});
            
            conn.conectar();
            //OBTENER TODOS LOS DATOS QUE SE ENCUENTRAN DE USUARIOS
            ResultSet rs = conn.query("CALL Users_PA0001()");

            while (rs.next()) {
                String estado="";
                String validar=rs.getString("estado");
                if(validar.equals("0")){
                    estado="Inactivo";
                }
                else{
                    estado="Activo";
                }
                objDTM.addRow(new Object[]{rs.getString("nombre"), rs.getString("usuario"), rs.getString("correo"),rs.getString("telefono"),estado});
            }

            Table.setModel(objDTM);
            conn.desconectar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los usuarios. . .");
        }
    }

    private void cargarUsuarioSeleccionado(String usuario_selecto) throws SQLException {
        try {            
            conn.conectar();
            //OBTENER VALORES DE USUARIO SELECCIONADO PARA MODIFICAR
            ResultSet rs = conn.query("CALL Users_PA0002('" + usuario_selecto + "')");

            while (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtCorreo.setText(rs.getString("correo"));
                txtUsuario.setText(rs.getString("usuario"));
                txtTelefono.setText(rs.getString("telefono"));
                txtPasaporte.setText(rs.getString("pasaporte"));
            }
            conn.desconectar();
        } 
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar los usuarios. . .");
            conn.desconectar();
        }
    }

    private void btnEditarUsuarioActionPerformed(ActionEvent evt) throws SQLException {

        if (Table.getSelectedRow() >= 0) {
            String usuarioSeleccionado = (String) Table.getValueAt(Table.getSelectedRow(), 1);
            this.remove(Table);
            this.remove(ScrollPane);
            this.remove(btnEditarUsuario);
            initComponentsEditarUsuario(usuarioSeleccionado);
            repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario para editar su información.");
        }
    }

    private void btnGuardarCambiosActionPerformed(ActionEvent evt) throws SQLException {
        
        md5 cifra = new md5();
        
        char[] arrayPassword = txtPassword.getPassword();
        String password = new String(arrayPassword);
        password = cifra.md5_encode(password);
        
        char[] arrayCPassword = txtCPassword.getPassword();
        String cpassword = new String(arrayCPassword);
        cpassword = cifra.md5_encode(cpassword);   
        
        if(password.length() > 0 & cpassword.length() > 0 & txtCorreo.valido() & txtTelefono.valido() & txtTarjetaCredito.valido()){ //Si se cumplen todas las validaciones
            if (password.equals(cpassword)) {
                try {
                    String nombre = txtNombre.getText();
                    String correo = txtCorreo.getText();
                    String usuario= txtUsuario.getText();
                    String telefono = txtTelefono.getText();
                    String tarjetaCredito = txtTarjetaCredito.getText();
                    tarjetaCredito = cifra.md5_encode(tarjetaCredito);
                    String estadocombo = (String) txtEstado.getSelectedItem();
                    Integer estado = 0;
                    if (estadocombo == "Activo") {
                        estado = 1;
                    }
                    if (estadocombo == "Inactivo") {
                        estado = 0;
                    }
                    conn.conectar();
                    //SQL MODIFICACION DE DATOS DE USUARIO ALMACENADO
                    String sql = "CALL Users_PA0003('" + estado + "','" + correo + "','" + cpassword + "','" + telefono + "','" + tarjetaCredito + "','" + usuario + "')";
                    conn.queryUpdate(sql);
                    conn.desconectar();
                    JOptionPane.showMessageDialog(null, "Usuario modificado corectamente");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al editar usuario. . .");
                    conn.desconectar();
                }
                this.removeAll();
                initComponent();
                cargarUsuarios();
                validate();
                repaint();
            } 
            else {
                JOptionPane.showMessageDialog(null, "Contraseñas no coinciden. . .");
            }
        }
        else{
            String mensaje = "CAMPOS INVALIDOS";
            if(!txtCorreo.valido())
                mensaje += "\n-Campo 'Nombre' invalido.";
            if(password.length() <= 0)
                mensaje += "\n-Campo 'Contraseña' invalido.";
            if(!txtTelefono.valido())
                mensaje += "\n-Campo 'Telefono' invalido.";
            if(!txtTarjetaCredito.valido())
                mensaje += "\n-Campo 'Tarjeta' invalido.";            
            JOptionPane.showMessageDialog(null, mensaje);
        }        
    }
}
