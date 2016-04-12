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

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/amadeus";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "";

    //Todos los usuarios
    private JLabel titulo;
    private JButton btnEditarUsuario;
    private JScrollPane ScrollPane;
    private JTable Table;

    //Editar usuario
    private JLabel lblNombre;
    private JLabel lblCorreo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblTelefono;
    private JLabel lblPasaporte;
    private JLabel lblTarjetaCredito;

    private JTextField txtNombre;
    private validacion_correo txtCorreo;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private validacion_telefono txtTelefono;
    private JTextField txtPasaporte;
    private validacion_tarjeta_credito txtTarjetaCredito;

    private JButton btnGuardarCambios;

    public usuarios() throws SQLException {
        initComponent();
        cargarUsuarios();
        setLayout(null);
        setBounds(235, 30, 705, 540);
        setBackground(new Color(255, 255, 255));
    }

    public void initComponent() {
        titulo = new JLabel("Todos los Usuarios");
        titulo.setBounds(10, 10, 150, 50);
        add(titulo);

        btnEditarUsuario = new JButton("Editar Usuario");
        btnEditarUsuario.setBounds(480, 60, 200, 40);
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
        ScrollPane.setBounds(10, 110, 670, 400);

        Table = new JTable();
        ScrollPane.setViewportView(Table);

        this.add(ScrollPane);
    }

    public void initComponentsEditarUsuario(String usuario_selecto) throws SQLException {
        titulo.setText("Editar Usuario");

        lblNombre = new JLabel("Nombre:");
        lblCorreo = new JLabel("Correo:");
        lblUsuario = new JLabel("Usuario");
        lblPassword = new JLabel("Contraseña");
        lblTelefono = new JLabel("Telefono");
        lblPasaporte = new JLabel("Pasaporte");
        lblTarjetaCredito = new JLabel("Tarjeta de Credito");

        txtNombre = new JTextField();
        txtCorreo = new validacion_correo();
        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();
        txtTelefono = new validacion_telefono();
        txtPasaporte = new JTextField();
        txtTarjetaCredito = new validacion_tarjeta_credito();

        btnGuardarCambios = new JButton("Guardar Cambios");

        lblNombre.setBounds(100, 100, 250, 30);
        lblCorreo.setBounds(100, 140, 250, 30);
        lblUsuario.setBounds(100, 180, 250, 30);
        lblPassword.setBounds(100, 220, 250, 30);
        lblTelefono.setBounds(100, 260, 250, 30);
        lblPasaporte.setBounds(100, 300, 250, 30);
        lblTarjetaCredito.setBounds(100, 340, 250, 30);

        txtNombre.setBounds(370, 100, 250, 30);
        txtCorreo.setBounds(370, 140, 250, 30);
        txtUsuario.setBounds(370, 180, 250, 30);
        txtPassword.setBounds(370, 220, 250, 30);
        txtTelefono.setBounds(370, 260, 250, 30);
        txtPasaporte.setBounds(370, 300, 250, 30);
        txtTarjetaCredito.setBounds(370, 340, 250, 30);

        btnGuardarCambios.setBounds(470, 380, 150, 30);

        Font fontLabel = new Font("Dialog", Font.BOLD, 15);
        lblNombre.setFont(fontLabel);
        lblCorreo.setFont(fontLabel);
        lblUsuario.setFont(fontLabel);
        lblPassword.setFont(fontLabel);
        lblTelefono.setFont(fontLabel);
        lblPasaporte.setFont(fontLabel);
        lblTarjetaCredito.setFont(fontLabel);

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
        this.add(lblTelefono);
        this.add(lblPasaporte);
        this.add(lblTarjetaCredito);

        this.add(txtNombre);
        this.add(txtCorreo);
        this.add(txtUsuario);
        this.add(txtPassword);
        this.add(txtTelefono);
        this.add(txtPasaporte);
        this.add(txtTarjetaCredito);

        this.add(btnGuardarCambios);
    }

    private void cargarUsuarios() throws SQLException {
        try {
            //Definir las columnas del modelo de tabla
            DefaultTableModel objDTM = new DefaultTableModel();
            objDTM.setColumnIdentifiers(new Object[]{"Nombre", "Usuario", "Correo"});

            database conn = new database();
            conn.conectar();
            ResultSet rs = conn.query("SELECT nombre, usuario, correo FROM usuarios");

            while (rs.next()) {
                objDTM.addRow(new Object[]{rs.getString("nombre"), rs.getString("usuario"), rs.getString("correo")});
            }

            Table.setModel(objDTM);
            conn.desconectar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro al cargar los usuarios.");
        }
    }

    private void cargarUsuarioSeleccionado(String usuario_selecto) throws SQLException {
        try {
            database conn = new database();
            conn.conectar();
            ResultSet rs = conn.query("SELECT nombre, correo, usuario, pass, telefono, pasaporte, tarjeta_credito FROM usuarios WHERE usuario = '" + usuario_selecto + "'");

            while (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtCorreo.setText(rs.getString("correo"));
                txtUsuario.setText(rs.getString("usuario"));
                txtPassword.setText(rs.getString("pass"));
                txtTelefono.setText(rs.getString("telefono"));
                txtPasaporte.setText(rs.getString("pasaporte"));
                txtTarjetaCredito.setText(rs.getString("tarjeta_credito"));
            }
            conn.desconectar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro al cargar los usuarios." + ex);
        }
    }

    private void guardarCambiosUsuario(String usuario_selecto) throws SQLException {
        try {
            database conn = new database();
            conn.conectar();
            ResultSet rs = conn.query("SELECT nombre, correo, usuario, pass, telefono, pasaporte, tarjeta_credito FROM usuarios WHERE usuario = '" + usuario_selecto + "'");

            while (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtCorreo.setText(rs.getString("correo"));
                txtUsuario.setText(rs.getString("usuario"));
                txtPassword.setText(rs.getString("pass"));
                txtTelefono.setText(rs.getString("telefono"));
                txtPasaporte.setText(rs.getString("pasaporte"));
                txtTarjetaCredito.setText(rs.getString("tarjeta_credito"));
            }
            conn.desconectar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro al cargar los usuarios." + ex);
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
        Connection conn = null;
        Statement stmt = null;
        
        char[] arrayPassword = txtPassword.getPassword(); 
        String password = new String(arrayPassword);
        if(password.length() > 0 & txtCorreo.valido() & txtTelefono.valido() & txtTarjetaCredito.valido()){ //Si se cumplen todas las validaciones
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                String nombre = txtNombre.getText();
                String correo = txtCorreo.getText();
                
                String telefono = txtTelefono.getText();
                String tarjetaCredito = txtTarjetaCredito.getText();

                String sql = "UPDATE usuarios SET correo = '" + correo + "', pass = '" + password + "', telefono = '" + telefono + "', tarjeta_credito = '" + tarjetaCredito + "' WHERE nombre = '" + nombre + "'";
                if (stmt.executeUpdate(sql) > 0) {
                    JOptionPane.showMessageDialog(null, "Datos modificados correctamente.");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            this.removeAll();
            initComponent();
            cargarUsuarios();
            repaint();
        }else{
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
