/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuelos;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;
/**
 *
 * @author PC
 */
public class crear_vuelo extends JPanel
{
    private JLabel hora, hora2, fecha, asientos, maletas, ruta, title, avion;
    private JTextField time, time2, fec, numasi, mal;
    private JComboBox ini, fin, avi;
    private JButton limpiar, crear;
    
    public crear_vuelo(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
    public void initComponent(){
        
        title = new JLabel("CREACIÓN DE VUELOS");
        title.setBounds(200,20,300,40);
        title.setFont(new Font("TImes New Roman", Font.BOLD,25));
        add(title);
        
        hora = new JLabel("Hora Inicio: ");
        hora.setBounds(150, 100, 100, 20);
        add(hora);
        
        time = new JTextField();
        time.setBounds(280, 100, 250, 20);
        add(time);
        
        hora2 = new JLabel("Hora LLegada: ");
        hora2.setBounds(150, 145, 100, 20);
        add(hora2);
        
        time2 = new JTextField();
        time2.setBounds(280, 145, 250, 20);
        add(time2);
        
        fecha = new JLabel("Fecha: ");
        fecha.setBounds(150, 190, 100, 20);
        add(fecha);
        
        fec = new JTextField();
        fec.setBounds(280,190,250,20);
        add(fec);
        
        asientos = new JLabel("Asientos: ");
        asientos.setBounds(150,235,100,20);
        add(asientos);
        
        numasi = new JTextField();
        numasi.setBounds(280,235,250,20);
        add(numasi);
        
        maletas = new JLabel("Maletas: ");
        maletas.setBounds(150,280,100,20);
        add(maletas);
        
        mal = new JTextField();
        mal.setBounds(280,280,250,20);
        add(mal);
        
        avion = new JLabel("Avión: ");
        avion.setBounds(150,325,100,20);
        add(avion);
        
        avi = new JComboBox();
        avi.setBounds(280,325,250,20);
        add(avi);
        
        ruta = new JLabel("Ruta: ");
        ruta.setBounds(150,370,100,20);
        add(ruta);
        
        ini = new JComboBox();
        ini.setBounds(280,370,110,20);
        add(ini);
        
        fin = new JComboBox();
        fin.setBounds(420,370,110,20);
        add(fin);
        
        crear = new JButton("Crear Vuelo");
        crear.setBounds(150,450,170,20);
        crear.setBackground(new Color(158,203,242));        
        crear.setBorderPainted(false);
        add(crear);
        
        limpiar = new JButton("Limpiar");
        limpiar.setBounds(360,450,170,20);
        limpiar.setBackground(new Color(158,203,242));        
        limpiar.setBorderPainted(false);
        add(limpiar);
    }    
}
