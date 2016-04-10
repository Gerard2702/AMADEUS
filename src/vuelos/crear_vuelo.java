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
import java.text.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;
/**
 *
 * @author PC
 */
public class crear_vuelo extends JPanel
{
    private JLabel hora, hora2, fecha, ruta, title, avion;
    private JComboBox cbxIni, cbxFin, cbxAvi;
    private JButton limpiar, crear;
    private JFormattedTextField txtFec, txtTime, txtTime2;
    private JLabel errorfec, errorhora1, errorhora2, campos;
    private JSeparator sep;
    database db = new database();
    
    public crear_vuelo(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));          
    }
    
    public void initComponent(){
        
        Font titulo = new Font("Calibri", 1, 19);
        Font label = new Font("Calibri",0,15);
        Font error = new Font("Calibri",0,12);
        
        title = new JLabel("CREACIÓN DE VUELOS");
        title.setBounds(200,10,300,50);
        title.setFont(titulo);
        add(title);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setFont(label);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);
        
        hora = new JLabel("Hora Inicio: ");
        hora.setBounds(150, 100, 100, 25);
        hora.setFont(label);
        add(hora);
        
        MaskFormatter mascara2 = null;
        try { 
            mascara2 = new MaskFormatter("##:##"); 
            } catch (ParseException ex) { 
        }
        txtTime = new JFormattedTextField(mascara2);
        txtTime.setBounds(280, 100, 250, 25);
        txtTime.setHorizontalAlignment(SwingConstants.CENTER);

        add(txtTime);
        
        errorhora1 = new JLabel("Hora Incorrecta");
        errorhora1.setBounds(350,120,200,25);
        errorhora1.setForeground(Color.red);
        errorhora1.setFont(error);
        errorhora1.setVisible(false);
        add(errorhora1);
        
        hora2 = new JLabel("Hora LLegada: ");
        hora2.setBounds(150, 150, 100, 25);
        hora2.setFont(label);
        add(hora2);
        
        MaskFormatter mascara3 = null;
        try { 
            mascara3 = new MaskFormatter("##:##"); 
            } catch (ParseException ex) { 
        }
        txtTime2 = new JFormattedTextField(mascara3);
        txtTime2.setBounds(280, 150, 250, 25);
        txtTime2.setHorizontalAlignment(SwingConstants.CENTER);
        add(txtTime2);
        
        errorhora2 = new JLabel("Hora Incorrecta");
        errorhora2.setBounds(350,170,200,25);
        errorhora2.setForeground(Color.red);
        errorhora2.setFont(error);
        errorhora2.setVisible(false);
        add(errorhora2);
        
        fecha = new JLabel("Fecha: ");
        fecha.setBounds(150, 200, 100, 25);
        fecha.setFont(label);
        add(fecha);
        
        MaskFormatter mascara = null;
        try { 
            mascara = new MaskFormatter("####-##-##"); 
            } catch (ParseException ex) { 
        }
        
        txtFec = new JFormattedTextField(mascara);
        txtFec.setBounds(280,200,250,25);
        txtFec.setHorizontalAlignment(SwingConstants.CENTER);
        add(txtFec);
        
        errorfec = new JLabel("Fecha Incorrecta");
        errorfec.setBounds(350,220,200,25);
        errorfec.setFont(error);
        errorfec.setForeground(Color.red);
        errorfec.setVisible(false);
        add(errorfec);              
        
        avion = new JLabel("Avión: ");
        avion.setBounds(150,250,100,25);
        avion.setFont(label);
        add(avion);
        
        cbxAvi = new JComboBox();
        cbxAvi.setBounds(280,250,250,25);
        try{
        db.conectar();
        String sql="SELECT * FROM avion";
        ResultSet rs = db.query(sql);
        rs.last(); 
        if(rs.getRow()==0){
            cbxAvi.addItem("NO HAY AVIONES AGREGADOS");
        }
        else{
            rs.beforeFirst();
            while(rs.next()){
            String idavion=rs.getString("idavion");
            cbxAvi.addItem(idavion);
            }
        }
        
        db.desconectar();
        }catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        add(cbxAvi);
        
        ruta = new JLabel("Ruta: ");
        ruta.setBounds(150,300,100,25);
        ruta.setFont(label);
        add(ruta);
        
        cbxIni = new JComboBox();
        cbxIni.setBounds(280,300,110,25);
        cbxIni.addItem("El Salvador");
        cbxIni.addItem("Costa Rica");
        cbxIni.addItem("España");
        cbxIni.addItem("México");
        cbxIni.addItem("Brasil");
        add(cbxIni);
        
        cbxFin = new JComboBox();
        cbxFin.setBounds(420,300,110,25);
        try{
        db.conectar();
        String sql="SELECT * FROM aeropuertos";
        ResultSet rs = db.query(sql);
        rs.last(); 
        if(rs.getRow()==0){
            cbxFin.addItem("NO HAY AEROPUERTOS AGREGADOS");
        }
        else{
            rs.beforeFirst();
            while(rs.next()){
            String cid=rs.getString("ciudad");
            cbxFin.addItem(cid);
            }
        }
         db.desconectar();
        }catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }
        add(cbxFin);
        
        crear = new JButton("Crear Vuelo");
        crear.setBounds(150,370,170,25);
        crear.setBackground(new Color(158,203,242));        
        crear.setBorderPainted(false);
        add(crear);
        
        crear.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                crear_vuelo(evt);
            }
        });
        
        limpiar = new JButton("Limpiar");
        limpiar.setBounds(360,370,170,25);
        limpiar.setBackground(new Color(158,203,242));        
        limpiar.setBorderPainted(false);
        add(limpiar);
        
        limpiar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                clear(evt);
            }
        });
        
        campos = new JLabel("Requerido Todos Los Campos");
        campos.setBounds(200,400,170,20);
        campos.setVisible(false);
        campos.setFont(error);
        add(campos);
    }  
    
     private void clear(ActionEvent e)
     {
         this.txtTime.setText("");
         this.txtTime2.setText("");
         this.txtFec.setText(""); 
         this.cbxAvi.setSelectedIndex(0);
         this.cbxFin.setSelectedIndex(0);
         this.cbxIni.setSelectedIndex(0);
         this.errorfec.setVisible(false);
         this.errorhora1.setVisible(false);
         this.errorhora2.setVisible(false);
     }
     
     private void crear_vuelo(ActionEvent e)
     {
         String fecha, horainicio, horafin, avion, rutaorigen, rutadestino;
         int maletas, asientos;
         
         if(!txtFec.getText().trim().equals("") && !txtTime.getText().trim().equals("") && !txtTime2.getText().trim().equals("") && !cbxAvi.getSelectedItem().equals("NO HAY AVIONES AGREGADOS") && !cbxFin.getSelectedItem().equals("NO HAY AEROPUERTOS AGREGADOS"))
         {
            this.campos.setVisible(false); 
            fecha = this.txtFec.getText().toString();
            horainicio = this.txtTime.getText().toString();
            horafin = this.txtTime2.getText().toString();
            avion = this.cbxAvi.getSelectedItem().toString();
            rutaorigen = this.cbxIni.getSelectedItem().toString();
            rutadestino = this.cbxFin.getSelectedItem().toString();
            int idestado = 1;

            if(!isHoraValida(horainicio))
            {
                 this.errorhora1.setVisible(true);
            }
            else
            {
                this.errorhora1.setVisible(false);
            }
            if(!isHoraValida(horafin))
            {
                 this.errorhora2.setVisible(true);
            }
            else
            {
                this.errorhora2.setVisible(false);
            }
             if(!isFechaValida(fecha))
            {
                 this.errorfec.setVisible(true);
            }
            else
            {
                this.errorfec.setVisible(false);
            }
            if(isFechaValida(fecha) && isHoraValida(horainicio) && isHoraValida(horafin))
            {
                 this.errorfec.setVisible(false);
                 this.errorhora1.setVisible(false);
                 this.errorhora2.setVisible(false);
                 try{
                    db.conectar();
                    
                    String sqlidestino = "SELECT * FROM aeropuertos WHERE ciudad='"+rutadestino+"';";
                    ResultSet iddesti = db.query(sqlidestino);
                    iddesti.first();                    
                    int idru = iddesti.getInt("idaeropuertos");
                    
                    
                    String sqlinsert2="INSERT INTO ruta (origen,destino) VALUES ('"+rutaorigen+"','"+idru+"')";
                    db.queryUpdate(sqlinsert2);
                    
                    String sqlid="SELECT * FROM ruta WHERE origen='"+rutaorigen+"' AND destino='"+idru+"';";
                    ResultSet rsid = db.query(sqlid);
                    rsid.first();
                    int idruta = rsid.getInt("idruta");
                    
                    String sqlmale ="SELECT * FROM avion WHERE idavion='"+avion+"';";
                    ResultSet mal = db.query(sqlmale);
                    mal.first();
                    String maletasdis = mal.getString("maletas");
                    String asientosdis = mal.getString("asientos");
                    
                    
                    String sqlinsert="INSERT INTO vuelos (hora_inicio,hora_fin,fecha,asientos_disponibles,maletas_disponibles,avion_idavion,ruta_idruta,estado_idestado) VALUES ('"+horainicio+"','"+horafin+"','"+fecha+"','"+asientosdis+"','"+maletasdis+"','"+avion+"','"+idruta+"','"+idestado+"')";
                    db.queryUpdate(sqlinsert);
                    this.txtFec.setText(""); 
                    this.txtTime.setText("");
                    this.txtTime2.setText("");
                    this.cbxAvi.setSelectedIndex(0);
                    this.cbxFin.setSelectedIndex(0);
                    this.cbxIni.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null,"REGISTRO EXITOSO");
                    db.desconectar();
                    }catch (SQLException ex) {
                        Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                    }    
            }
         }
         else
         {
            this.campos.setVisible(true);
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
