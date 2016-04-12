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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class agregar_avion extends JPanel
{
    private JLabel lbltitulo,lblasientos,lblmaletas,lblmodelo;
    private JLabel lblvalasientos,lblvalmaletaslblmaletas,lblvalmodelo;
    private JTextField txtasientos,txtmaletas;
    private JComboBox combomodelos;
    private JButton btnAceptar,btnLimpiar,btnAgregar;
    private JSeparator sep;
    database db = new database();
            
    public agregar_avion(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255)); 
    }
    
    public void initComponent(){
        Font titulo = new Font("Calibri", 1, 19);
        Font label = new Font("Calibri",1,15);
        Font error = new Font("Calibri",1,12);
        
        lbltitulo = new JLabel("AGREGAR AVIÓN");
        lbltitulo.setFont(titulo);
        lbltitulo.setBounds(10,10,300,50);
        add(lbltitulo);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setFont(label);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);
             
        lblasientos = new JLabel("Numero de Asientos: ");
        lblasientos.setFont(label);
        lblasientos.setBounds(10, 100, 200, 25);
        add(lblasientos);
        
        txtasientos = new JTextField();
        txtasientos.setFont(label);
        txtasientos.setBounds(175, 100, 300, 25);
        add(txtasientos);
        
        txtasientos.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                if(!Character.isDigit(e.getKeyChar())){
                    e.consume();
                    lblvalasientos.setText("Ingrese solo numeros");
                } 
                else{
                    lblvalasientos.setText("");
                }
            }
        });
        
        lblvalasientos = new JLabel("");
        lblvalasientos.setFont(error);
        lblvalasientos.setBounds(175, 122, 300, 25);
        lblvalasientos.setForeground(Color.red);
        add(lblvalasientos);
        
        lblmaletas = new JLabel("Numero de Maletas");
        lblmaletas.setFont(label);
        lblmaletas.setBounds(10, 150, 200, 25);
        add(lblmaletas);
        
        txtmaletas = new JTextField();
        txtmaletas.setFont(label);
        txtmaletas.setBounds(175, 150, 300, 25);
        add(txtmaletas);
        txtmaletas.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                if(!Character.isDigit(e.getKeyChar())){
                    e.consume();
                    lblvalmaletaslblmaletas.setText("Ingrese solo numeros");
                } 
                else{
                    lblvalmaletaslblmaletas.setText("");
                }
            }
        });
        
        lblvalmaletaslblmaletas = new JLabel("");
        lblvalmaletaslblmaletas.setFont(error);
        lblvalmaletaslblmaletas.setBounds(175, 172, 300, 25);
        lblvalmaletaslblmaletas.setForeground(Color.red);
        add(lblvalmaletaslblmaletas);
        
        lblmodelo = new JLabel("Modelo: ");
        lblmodelo.setFont(label);
        lblmodelo.setBounds(10, 200, 200, 25);
        add(lblmodelo);
        
        lblvalmodelo = new JLabel("");
        lblvalmodelo.setFont(error);
        lblvalmodelo.setBounds(175, 222, 300, 25);
        lblvalmodelo.setForeground(Color.red);
        add(lblvalmodelo);
        
        
        combomodelos = new JComboBox();
        combomodelos.setFont(label);
        combomodelos.setBounds(175, 200, 300, 25);  
        try{
        db.conectar();
        String sql="SELECT * FROM modelo";
        ResultSet rs = db.query(sql);
        rs.last(); 
        if(rs.getRow()==0){
            combomodelos.addItem("NO HAY MODELOS AGREGADOS");
        }
        else{
            rs.beforeFirst();
            while(rs.next()){
            String modelo=rs.getString("modelo");
            combomodelos.addItem(modelo);
            }
        }
        
        db.desconectar();
        }catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        add(combomodelos);
        
        btnAgregar = new JButton("Agregar Nuevo");
        btnAgregar.setBounds(350, 230, 125, 20);
        btnAgregar.setBackground(new Color(158,203,242));        
        btnAgregar.setBorderPainted(false);
        btnAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnAgregar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnAgregar.setBackground(new Color(158,203,242));
            }            
        });
        add(btnAgregar);
        btnAgregar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                
                try{
                    String modelo = JOptionPane.showInputDialog("Ingrese el nuevo modelo" );
                    if(modelo.trim().equals("")){}
                    else{
                    db.conectar();
                    String sql="INSERT INTO modelo (modelo) VALUES('"+modelo+"')";
                    db.queryUpdate(sql);
                    db.desconectar();
                    combomodelos.addItem(modelo);
                    combomodelos.setSelectedItem(modelo);
                    }
                }
                catch(Exception e){}
            }
        });
        
      
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 275, 100, 25);
        btnAceptar.setBackground(new Color(158,203,242));        
        btnAceptar.setBorderPainted(false);
        btnAceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnAceptar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnAceptar.setBackground(new Color(158,203,242));
            }            
        });
        add(btnAceptar);
        btnAceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                IngresarVuelo();
            }
        });
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(120, 275, 100, 25);
        btnLimpiar.setBackground(new Color(158,203,242));        
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                btnLimpiar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnLimpiar.setBackground(new Color(158,203,242));
            }            
        });
        add(btnLimpiar);
        add(btnLimpiar);
        btnLimpiar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                LimpiarTextos();
            }
        });
        
        
    }
    private void LimpiarTextos(){
    
        
        txtasientos.setText("");
        txtmaletas.setText("");
        combomodelos.setSelectedIndex(0);
        
    }
    private void IngresarVuelo(){
        try{
            if(txtasientos.getText().trim().equals("")){
                lblvalasientos.setText("Debe Asignar el numero de Asientos");
            }else{lblvalasientos.setText("");}            
            if(txtmaletas.getText().trim().equals("")){
                lblvalmaletaslblmaletas.setText("Debe Asignar el numero de maletas");
            }else{lblvalmaletaslblmaletas.setText("");}
            if(combomodelos.getSelectedItem().equals("NO HAY MODELOS AGREGADOS")){
                lblvalmodelo.setText("Debe Asignar un Modelo");
            }else{lblvalmodelo.setText("");}
            if(!txtasientos.getText().trim().equals("")&&!txtmaletas.getText().trim().equals("")&&!combomodelos.getSelectedItem().equals("NO HAY MODELOS AGREGADOS"))
            {
                int asientos = Integer.parseInt(txtasientos.getText());
                int maletas = Integer.parseInt(txtmaletas.getText());
                String modelo = combomodelos.getSelectedItem().toString();
                try{
                    db.conectar();
                    
                    String sqlid="SELECT * FROM modelo WHERE modelo='"+modelo+"';";
                    ResultSet rsid = db.query(sqlid);
                    rsid.first();
                    String idmodelo = rsid.getString("idmodelo");
                    
                    String sqlcod="SELECT * FROM avion ORDER BY idavion";
                    ResultSet rscod= db.query(sqlcod);
                    rscod.last();
                    int idlast;
                    if(rscod.getRow()==0){idlast=0;}
                    else{ idlast = Integer.parseInt(rscod.getString("idavion"));}
                    
                    String codigo = "AM-ARL-"+(idlast+1);
                    String sqlinsert="INSERT INTO avion (codigo,asientos,maletas,modelo_idmodelo) VALUES ('"+codigo+"','"+asientos+"','"+maletas+"','"+idmodelo+"')";
                    db.queryUpdate(sqlinsert);
                    
                    String sqlcodf="SELECT * FROM avion ORDER BY idavion";
                    ResultSet rscodf= db.query(sqlcodf);
                    rscodf.last();
                    int idavi = rscodf.getInt("idavion");                    
                    int turista = (int)(asientos * 0.5);
                    int bussines = (int)(asientos * 0.3);
                    int firtclass = (int)(asientos * 0.2);                    
		    String nomb;
                    for(int i=0; i < turista; i++)
                    {
			nomb = "TR-"+(i+1);
                        String sqlasi1 = "INSERT INTO asientos (nombre_asiento, estado, avion_idavion) VALUES ('"+nomb+"','"+0+"','"+idavi+"')";
                        db.queryUpdate(sqlasi1);
                    }
                    for(int j=0; j < bussines; j++)
                    {
			nomb = "BS-"+(j+1);
                        String sqlasi2 = "INSERT INTO asientos (nombre_asiento, estado, avion_idavion) VALUES ('"+nomb+"','"+0+"','"+idavi+"')";
                        db.queryUpdate(sqlasi2);
                    }
                    for(int k=0; k < firtclass; k++)
                    {
			nomb = "FC-"+(k+1);
                        String sqlasi3 = "INSERT INTO asientos (nombre_asiento, estado, avion_idavion) VALUES ('"+nomb+"','"+0+"','"+idavi+"')";
                        db.queryUpdate(sqlasi3);
                    }                    
                    db.desconectar();
                    JOptionPane.showMessageDialog(null,"REGISTRO EXITOSO");
                    txtasientos.setText("");
                    txtmaletas.setText("");
                    combomodelos.setSelectedIndex(0);
                }catch (SQLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }    
            }
            
            
        }
        catch(Exception e){}
    }
    private String generarCodigo(){
        String cod="";
            
        return cod;
    }
    
}

