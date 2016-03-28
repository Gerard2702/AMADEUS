package amadeus;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.database;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Gerard Orellana
 */
public class login extends JFrame{
    
    private JLabel lblUser,lblPass,lblWelcome;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnIngresar;
    private JPanel panel;
    private Dimension dim;
    database db = new database();
    Connection conec = db.conectar();
    
    public login(){
        initComponent();
        this.setExtendedState(MAXIMIZED_BOTH);
        //this.setSize(1280, 720);
        //this.setLocationRelativeTo(null);
        this.setTitle("AMADEUS");
    }
    
    private void initComponent(){
        
        Container conten = getContentPane();
        conten.setLayout(null);
           
        dim=super.getToolkit().getScreenSize();
        Font titulo = new Font("Calibri", 1, 30);
        Font label = new Font("Calibri", 1, 15);
        //dim=conten.getSize();
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds((dim.width/2)-200, (dim.height/2)-175, 400, 250);
        panel.setBackground(Color.WHITE);
        conten.add(panel);
        
        lblWelcome = new JLabel("INICIAR SESIÓN");
        lblWelcome.setFont(titulo);
        lblWelcome.setBounds(110, 25, 200, 50);
        panel.add(lblWelcome);
        
        lblUser = new JLabel("Usuario: ");
        lblUser.setFont(label);
        lblUser.setBounds(75, 100, 60, 30);
        panel.add(lblUser);
        
        txtUser = new JTextField();
        txtUser.setFont(label);
        txtUser.setBounds(150, 100, 200, 25);
        panel.add(txtUser);
        
        lblPass = new JLabel("Contraseña: ");
        lblPass.setFont(label);
        lblPass.setBounds(55, 150, 85, 30);
        panel.add(lblPass);
        
        txtPass = new JPasswordField();
        txtPass.setFont(label);
        txtPass.setBounds(150, 150, 200, 25);
        panel.add(txtPass);
        
        btnIngresar = new JButton("Iniciar Sesión");
        btnIngresar.setFont(label);
        btnIngresar.setMnemonic('I');
        btnIngresar.setBounds(225, 200, 125, 25);
        panel.add(btnIngresar);
        
        btnIngresar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Ingresar();
            }
        });
        
        addWindowListener(new WindowAdapter(){
           public void windowClosing(WindowEvent evt){
               exitForm(evt);
           }
           
           public void windowOpenend(WindowEvent evt){
               formWindowOpened(evt);
           }
       });
    }
    
    private void exitForm(WindowEvent evt){
        System.exit(0);
    }
    
    private void formWindowOpened(WindowEvent evt){
        this.txtUser.requestFocus();
    }
    
    private void Ingresar(){
        try{
            if(this.txtUser.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Debe Ingresar un Usuario");
                this.txtUser.requestFocus();
            }
            else{
                if(this.txtPass.getText().trim().equals("")){
                    JOptionPane.showMessageDialog(null,"Debe Ingresar una Contraseña");
                    this.txtPass.requestFocus();
                }
                else{
                    validarLogin(this.txtUser.getText(),this.txtPass.getText());
                }
            }
        }
        catch(Exception e){
        
        }
        
    }
    
    private void validarLogin(String usuario,String pass){
        String sql = "SELECT * FROM usuarios WHERE usuario='"+usuario+"' AND pass='"+pass+"'";
        String caprol=""; 
        try {
            Statement st = conec.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.first()){
                while(rs.next()){
                    caprol=rs.getString("idrol");
                }
                if(caprol.equals("1")){
                    JOptionPane.showMessageDialog(null,"BIENVENIDO ADMINISTRADOR");
                    this.txtUser.setText("");
                    this.txtPass.setText("");
                    /*Bloque de codigo para Abrir la ventana del Administrador*/
                }
                else if(caprol.equals("2")){
                    JOptionPane.showMessageDialog(null,"BIENVENIDO EMPLEADO");
                    this.txtUser.setText("");
                    this.txtPass.setText("");
                    /*Bloque de codigo para Abrir la ventana del Empleado*/
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"DATOS INCORRECTOS");
                this.txtUser.setText("");
                this.txtUser.requestFocus();
                this.txtPass.setText("");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        new login().setVisible(true);
    }
    
}