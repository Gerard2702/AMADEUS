/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static usuarios.usuarios.DB_URL;
import validaciones.*;
/**
 *
 * @author Familia Aparicio
 */
public class nuevo_usuario extends JPanel{
    
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/amadeus";
    
    // Database credentials
    static final String USER = "root";
    static final String PASS = "";
    
    //Nuevo usuario
    private JLabel titulo;
    
    private JLabel lblNombre;
    private JLabel lblCorreo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblTelefono;
    private JLabel lblPasaporte;
    private JLabel lblTarjetaCredito;
    private JLabel lblTipoUsuario;

    private validacion_nombre txtNombre;
    private validacion_correo txtCorreo;
    private validacion_usuario txtUsuario;
    private JPasswordField txtPassword;
    private validacion_telefono txtTelefono;
    private validacion_pasaporte txtPasaporte;
    private validacion_tarjeta_credito txtTarjetaCredito;
    private JComboBox txtTipoUsuario;

    private JButton btnRegistar;
    
    public nuevo_usuario(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));       
    }
    
    public void initComponent(){
        titulo =new JLabel("Nuevo Usuario");
        titulo.setBounds(10, 10, 150, 50);
        add(titulo);
        
        lblNombre = new JLabel("Nombre:");
        lblCorreo = new JLabel("Correo:");
        lblUsuario = new JLabel("Usuario");
        lblPassword = new JLabel("Contraseña");
        lblTelefono = new JLabel("Telefono");
        lblPasaporte = new JLabel("Pasaporte");
        lblTarjetaCredito = new JLabel("Tarjeta de Credito");
        lblTipoUsuario = new JLabel("Tipo de Usuario");

        txtNombre = new validacion_nombre();
        txtCorreo = new validacion_correo();
        txtUsuario = new validacion_usuario();
        txtPassword = new JPasswordField();
        txtTelefono = new validacion_telefono();
        txtPasaporte = new validacion_pasaporte();
        txtTarjetaCredito = new validacion_tarjeta_credito();
        txtTipoUsuario = new JComboBox();
        txtTipoUsuario.addItem("Admnistrador");
        txtTipoUsuario.addItem("Empleado");

        btnRegistar = new JButton("Guardar Cambios");

        lblNombre.setBounds(100, 100, 250, 30);
        lblCorreo.setBounds(100, 140, 250, 30);
        lblUsuario.setBounds(100, 180, 250, 30);
        lblPassword.setBounds(100, 220, 250, 30);
        lblTelefono.setBounds(100, 260, 250, 30);
        lblPasaporte.setBounds(100, 300, 250, 30);
        lblTarjetaCredito.setBounds(100, 340, 250, 30);
        lblTipoUsuario.setBounds(100, 380, 250, 30);

        txtNombre.setBounds(370, 100, 250, 30);
        txtCorreo.setBounds(370, 140, 250, 30);
        txtUsuario.setBounds(370, 180, 250, 30);
        txtPassword.setBounds(370, 220, 250, 30);
        txtTelefono.setBounds(370, 260, 250, 30);
        txtPasaporte.setBounds(370, 300, 250, 30);
        txtTarjetaCredito.setBounds(370, 340, 250, 30);
        txtTipoUsuario.setBounds(370, 380, 250, 30);

        btnRegistar.setBounds(470, 420, 150, 30);

        Font fontLabel = new Font("Dialog", Font.BOLD, 15);
        lblNombre.setFont(fontLabel);
        lblCorreo.setFont(fontLabel);
        lblUsuario.setFont(fontLabel);
        lblPassword.setFont(fontLabel);
        lblTelefono.setFont(fontLabel);
        lblPasaporte.setFont(fontLabel);
        lblTarjetaCredito.setFont(fontLabel);
        lblTipoUsuario.setFont(fontLabel);

        btnRegistar.setBackground(new Color(158, 203, 242));
        btnRegistar.setBorderPainted(false);
        btnRegistar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnRegistar.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRegistar.setBackground(new Color(158, 203, 242));
            }
        });
        btnRegistar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    btnRegistrarActionPerformed(evt);
                } catch (SQLException ex) {
                    Logger.getLogger(usuarios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        this.add(lblNombre);
        this.add(lblCorreo);
        this.add(lblUsuario);
        this.add(lblPassword);
        this.add(lblTelefono);
        this.add(lblPasaporte);
        this.add(lblTarjetaCredito);
        this.add(lblTipoUsuario);

        this.add(txtNombre);
        this.add(txtCorreo);
        this.add(txtUsuario);
        this.add(txtPassword);
        this.add(txtTelefono);
        this.add(txtPasaporte);
        this.add(txtTarjetaCredito);
        this.add(txtTipoUsuario);

        this.add(btnRegistar);
    }
    
    private void limpiarTextField(){
        txtNombre.setText("");
        txtCorreo.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        txtTelefono.setText("");
        txtPasaporte.setText("");
        txtTarjetaCredito.setText("");
    }
    
    private void btnRegistrarActionPerformed(ActionEvent evt) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        
        char[] arrayPassword = txtPassword.getPassword(); 
        String password = new String(arrayPassword);
        if(password.length() > 0 & txtNombre.valido()  & txtCorreo.valido() & txtUsuario.valido() & txtPasaporte.valido() & txtTelefono.valido() & txtTarjetaCredito.valido()){ //Si se cumplen todas las validaciones
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();


                String nombre = txtNombre.getText();
                String correo = txtCorreo.getText();
                String usuario = txtUsuario.getText();
                //String password = new String(arrayPassword);
                String telefono = txtTelefono.getText();
                String pasaporte = txtPasaporte.getText();
                String tarjetaCredito = txtTarjetaCredito.getText();
                String tipoUsuario = (String) txtTipoUsuario.getSelectedItem();
                int tipoUser = 0;
                if(tipoUsuario == "Admnistrador")
                    tipoUser = 1;
                else if(tipoUsuario == "Empleado")
                    tipoUser = 2;

                String sql = "INSERT INTO usuarios (idrol, nombre, correo, usuario, pass, telefono, pasaporte, tarjeta_credito) VALUES ('" + tipoUser + "', '" + nombre + "', '" + correo + "', '" + usuario + "', '" + password + "', '" + telefono + "', '" + pasaporte + "', '" + tarjetaCredito + "')";
                if (stmt.executeUpdate(sql) > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario Registrado correctamente.");
                    limpiarTextField();
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
        }else{
            String mensaje = "CAMPOS INVALIDOS";
            if(!txtNombre.valido())
                mensaje += "\n-Campo 'Nombre' invalido.";
            if(!txtCorreo.valido())
                mensaje += "\n-Campo 'Correo' invalido.";
            if(!txtUsuario.valido())
                mensaje += "\n-Campo 'Usuario' invalido.";
            if(password.length() <= 0)
                mensaje += "\n-Campo 'Password' invalido.";
            if(!txtPasaporte.valido())
                mensaje += "\n-Campo 'Pasaporte' invalido.";
            if(!txtTelefono.valido())
                    mensaje += "\n-Campo 'Telefono' invalido.";
            if(!txtTarjetaCredito.valido())
                    mensaje += "\n-Campo 'Tarjeta de Crédito' invalida.";
            JOptionPane.showMessageDialog(null, mensaje);
        }     
    }
}
