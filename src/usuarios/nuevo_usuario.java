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
import validaciones.*;
/**
 *
 * @author Familia Aparicio
 */
public class nuevo_usuario extends JPanel{
    
    //Nuevo usuario
    private JLabel titulo,lbloader;    
    private JLabel lblNombre;
    private JLabel lblCorreo;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblTelefono;
    private JLabel lblPasaporte;
    private JLabel lblTarjetaCredito;
    private JLabel lblTipoUsuario;
    private JSeparator sep;
    private validacion_nombre txtNombre;
    private validacion_correo txtCorreo;
    private validacion_usuario txtUsuario;
    private JPasswordField txtPassword;
    private validacion_telefono txtTelefono;
    private validacion_pasaporte txtPasaporte;
    private validacion_tarjeta_credito txtTarjetaCredito;
    private JComboBox txtTipoUsuario;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));
    private JButton btnRegistar;
    
    database conn = new database();
    
    public nuevo_usuario(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));       
    }
    
    public void initComponent(){
        Font titulo1 = new Font("Calibri", 1, 19);
        
        titulo = new JLabel("NUEVO USUARIO");        
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
        
        lblNombre = new JLabel("Nombre:");
        lblCorreo = new JLabel("Correo:");
        lblUsuario = new JLabel("Usuario:");
        lblPassword = new JLabel("Contraseña:");
        lblTelefono = new JLabel("Telefono:");
        lblPasaporte = new JLabel("Pasaporte:");
        lblTarjetaCredito = new JLabel("Tarjeta de Credito:");
        lblTipoUsuario = new JLabel("Tipo de Usuario:");

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
        txtTipoUsuario.addItem("Cliente");

        btnRegistar = new JButton("Guardar Usuario");

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

        btnRegistar.setBounds(470, 435, 150, 30);        

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
         
        char[] arrayPassword = txtPassword.getPassword(); 
        String password = new String(arrayPassword);
        if(password.length() > 0 & txtNombre.valido()  & txtCorreo.valido() & txtUsuario.valido() & txtPasaporte.valido() & txtTelefono.valido() & txtTarjetaCredito.valido()){ //Si se cumplen todas las validaciones
            try {
                
                md5 cifra = new md5();

                String nombre = txtNombre.getText();
                String correo = txtCorreo.getText();
                String usuario = txtUsuario.getText();
                password = cifra.md5_encode(password);
                String telefono = txtTelefono.getText();
                String pasaporte = txtPasaporte.getText();
                String tarjetaCredito = txtTarjetaCredito.getText();
                tarjetaCredito = cifra.md5_encode(tarjetaCredito);
                String tipoUsuario = (String) txtTipoUsuario.getSelectedItem();
                int tipoUser = 0;
                if(tipoUsuario == "Admnistrador")
                    tipoUser = 1;
                else if(tipoUsuario == "Empleado")
                    tipoUser = 2;
                else if(tipoUsuario == "Cliente")
                    tipoUser = 3;
                
                conn.conectar();
                //SQL PARA INGRESAR NUEVO USUARIO A BD
                String sql = "CALL Users_PA0004('" + tipoUser + "','" + nombre + "','" + correo + "','" + usuario + "','" + password + "','" + telefono + "','" + pasaporte + "','" + tarjetaCredito + "')";                
                JOptionPane.showMessageDialog(null, "Usuario Registrado correctamente.");
                conn.queryUpdate(sql);
                conn.desconectar();
                limpiar_ingreso();
            } 
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al almacenar usuario. . .");
            } 
        }
        else{
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
            if(txtNombre.repetido())
                mensaje += "\n-Campo 'Nombre' ya existe, ingrese un 'Nombre' diferente.";
            if(txtUsuario.repetido())
                mensaje += "\n-Campo 'Usuario' ya existe, ingrese un 'Usuario' diferente.";
            JOptionPane.showMessageDialog(null, mensaje);
        }     
    }
    
    private void limpiar_ingreso(){
        txtNombre.setText("");
        txtCorreo.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        txtTelefono.setText("");
        txtPasaporte.setText("");
        txtTarjetaCredito.setText("");
    }
}
