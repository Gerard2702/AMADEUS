package amadeus;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author Gerard Orellana
 */
public class login extends JFrame{
    
    private JLabel lblUser,lblPass,lblWelcome,imagen,cerrar,minimizar,avionhead;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnIngresar;
    private JPanel panel,logo,jframe=new JPanel();
    private ImageIcon iconlogo=new ImageIcon(this.getClass().getResource("/config/icons/logo.png"));
    private ImageIcon cerrar1=new ImageIcon(this.getClass().getResource("/config/icons/cerrar.png"));
    private ImageIcon cerrar2=new ImageIcon(this.getClass().getResource("/config/icons/cerrarhover.png"));
    private ImageIcon min1=new ImageIcon(this.getClass().getResource("/config/icons/min.png"));
    private ImageIcon min2=new ImageIcon(this.getClass().getResource("/config/icons/minhover.png"));
    private ImageIcon iconhead=new ImageIcon(this.getClass().getResource("/config/icons/avion_head.png"));
    database db = new database();
    
    public login(){
        initComponent();        
        this.setSize(410,385);
        this.setLocationRelativeTo(null);
        this.setTitle("Amadeus-UDB");
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255,255,255));
        this.setResizable(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/config/icons/avion_icon.png")).getImage());
        this.setUndecorated(true);
        UIManager UI=new UIManager();
        UI.put("OptionPane.background",Color.WHITE);
        UI.put("Panel.background",Color.WHITE);
    }
    
    private void initComponent(){

        Font titulo = new Font("Calibri", 1, 19);
        Font label = new Font("Calibri", 1, 15);         
        
        jframe.setBounds(0,0,410,385);
        jframe.setBorder(BorderFactory.createLineBorder(new Color(190,190,190), 1));
        jframe.setLayout(null);
        jframe.setBackground(new Color(255,255,255)); 
        
        ventana_form();
        
        logo=new JPanel();
        logo.setLayout(null);
        logo.setBounds(5,30,400,70);
        logo.setBackground(new Color(255,255,255));       
        
        imagen=new JLabel();
        imagen.setBounds(90, 10, 263, 65);        
        imagen.setIcon(iconlogo);
        logo.add(imagen);
        
        jframe.add(logo);
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(5,100,400,280);
        panel.setBackground(new Color(255,255,255));        
        
        lblWelcome = new JLabel("INICIAR SESION");
        lblWelcome.setFont(titulo);
        lblWelcome.setBounds(130, 25, 200, 50);
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
        btnIngresar.setBounds(225, 200, 125, 30);   
        btnIngresar.setBackground(new Color(158,203,242));
        btnIngresar.setBorderPainted(false);
        btnIngresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnIngresar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnIngresar.setBackground(new Color(158,203,242));
            }            
        });       
        panel.add(btnIngresar);
        
        jframe.add(panel);
        
        add(jframe);
        
        btnIngresar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Ingresar();
            }
        });        
        txtPass.addKeyListener(new KeyListener(){   
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    Ingresar();
                }
            }
            @Override
            public void keyTyped(KeyEvent ke) {                
            }
            @Override
            public void keyReleased(KeyEvent ke) {                
            }
        });
                
        addWindowListener(new WindowAdapter(){           
           public void windowOpenend(WindowEvent evt){
               formWindowOpened(evt);
           }
       });
    }
    
    private void ventana_form(){
        
        avionhead=new JLabel();
        avionhead.setIcon(iconhead);
        avionhead.setBounds(5, 5, 20, 20);
        jframe.add(avionhead);
                
        minimizar=new JLabel();
        minimizar.setIcon(min1);
        minimizar.setBounds(348, 5, 20, 20);
        jframe.add(minimizar);
        minimizar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                minimizar.setIcon(min2);
            }
             @Override
            public void mouseExited(MouseEvent e) {
                minimizar.setIcon(min1);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setExtendedState(JFrame.CROSSHAIR_CURSOR); 
            }
        });       
        
        cerrar=new JLabel();
        cerrar.setIcon(cerrar1);
        cerrar.setBounds(380, 5, 20, 20);
        jframe.add(cerrar);
        cerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                cerrar.setIcon(cerrar2);
            }
             @Override
            public void mouseExited(MouseEvent e) {
                cerrar.setIcon(cerrar1);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                exitForm();
            }
        });
    } 
    
    private void exitForm(){
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
        String caprol="";
        String nombreusuario="";
        try {
            db.conectar();
            String sql = "SELECT * FROM usuarios WHERE usuario='"+usuario+"' AND pass='"+pass+"'";
            ResultSet rs=db.query(sql);            
            if(rs.first()){
                
                caprol=rs.getString("idrol");
                nombreusuario=rs.getString("nombre");
                
                if(caprol.equals("1")){                    
                    this.txtUser.setText("");
                    this.txtPass.setText("");
                    db.desconectar();
                    /*Bloque de codigo para Abrir la ventana del Administrador*/
                    this.setVisible(false);
                    index_admin admin=new index_admin(usuario,nombreusuario);                    
                    admin.setVisible(true);
                }
                else if(caprol.equals("2")){                    
                    this.txtUser.setText("");
                    this.txtPass.setText("");
                    db.desconectar();
                    /*Bloque de codigo para Abrir la ventana del Empleado*/
                     this.setVisible(false);
                    index_user user=new index_user(usuario,nombreusuario);                    
                    user.setVisible(true);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"Usuario o contraseña incorrecta");
                this.txtUser.setText("");
                this.txtUser.requestFocus();
                this.txtPass.setText("");
                db.desconectar();                
            }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        new login().setVisible(true);
    }    
}