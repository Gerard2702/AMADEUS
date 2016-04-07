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
    private JLabel hora, hora2, fecha, asientos, maletas, ruta;
    private JTextField time, time2, fec, numasi, mal;
    private JComboBox ini, fin;
    
    public crear_vuelo(){
        initComponent();
        setLayout(null);
        setBounds(235,30,705,540);
        setBackground(new Color(255,255,255));  
    }
    
    public void initComponent(){
        hora = new JLabel("Hora Inicio: ");
        hora.setBounds(150, 30, 100, 20);
        add(hora);
        
        time = new JTextField();
        time.setBounds(280, 30, 250, 20);
        add(time);
        
        hora2 = new JLabel("Hora LLegada: ");
        hora2.setBounds(150, 100, 100, 20);
        add(hora2);
        
        time2 = new JTextField();
        time2.setBounds(280, 100, 250, 20);
        add(time2);
        
        fecha = new JLabel("Fecha: ");
        fecha.setBounds(150, 170, 100, 20);
        add(fecha);
        
        fec = new JTextField();
        fec.setBounds(280,170,250,20);
        add(fec);
        
        asientos = new JLabel("Asientos: ");
        asientos.setBounds(150,240,100,20);
        add(asientos);
        
        numasi = new JTextField();
        numasi.setBounds(280,240,250,20);
        add(numasi);
        
        maletas = new JLabel("Maletas: ");
        maletas.setBounds(150,310,100,20);
        add(maletas);
        
        mal = new JTextField();
        mal.setBounds(280,310,250,20);
        add(mal);
        
        ruta = new JLabel("Ruta: ");
        ruta.setBounds(150,380,100,20);
        add(ruta);
        
        ini = new JComboBox();
        ini.setBounds(280,380,110,20);
        add(ini);
        
        fin = new JComboBox();
        fin.setBounds(420,380,110,20);
        add(fin);
    }    
}
