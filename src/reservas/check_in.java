/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import config.*;
import java.sql.*;

/**
 *
 * @author Familia Aparicio
 */
public class check_in extends JPanel {

    private JLabel prueba;

    private JLabel jlbcod_reserva, jlbnombre, jlbpasaporte, jlbclase;
    private JTextField jtfcod_reserva, jtfnombre, jtfpasaporte, jtfclase;

    private JLabel jlborigen, jlbdestino, jlbhora_inicio, jlbhora_fin;
    private JTextField jtforigen, jtfdestino, jtfhora_inicio, jtfhora_fin;
    private JButton jbtnconsultar, jbtnaceptar, jbtncancelar;

    private JCheckBox checkin;
    database db = new database();

    public check_in() {
        initComponent();
        setLayout(null);
        setBounds(235, 30, 705, 540);
        setBackground(new Color(255, 255, 255));
    }

    public void initComponent() {
        prueba = new JLabel("Check in");
        prueba.setBounds(10, 10, 100, 50);
        add(prueba);

        prueba = new JLabel("Check in");
        prueba.setBounds(10, 10, 100, 50);
        add(prueba);

        jlbcod_reserva = new JLabel("Codigo de Reserva:");
        jlbcod_reserva.setBounds(30, 80, 114, 14);
        add(jlbcod_reserva);

        jtfcod_reserva = new JTextField();
        jtfcod_reserva.setBounds(150, 80, 100, 20);
        add(jtfcod_reserva);

        jbtnconsultar = new JButton("Consultar");
        jbtnconsultar.setBounds(270, 80, 100, 20);
        add(jbtnconsultar);
        
        jbtnconsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if ("".equals(jtfcod_reserva.getText())) {
                    JOptionPane.showMessageDialog(null, "Campo vacio, Favor ingrese un codigo de reserva");
                } else {
                    try {
                        db.conectar();
                        String sql = "SELECT usuarios.nombre, usuarios.pasaporte, vuelos.hora_inicio, vuelos.hora_fin, ruta.origen, ruta.destino, clase_vuelo.clase\n"
                                + "FROM usuarios usuarios\n"
                                + "INNER JOIN usuarios_has_vuelos usuarios_has_vuelos ON usuarios.idusuarios = usuarios_has_vuelos.usuarios_idusuarios\n"
                                + "INNER JOIN vuelos vuelos ON usuarios_has_vuelos.vuelos_idvuelos = vuelos.idvuelos\n"
                                + "INNER JOIN clase_vuelo clase_vuelo ON clase_vuelo.idclase_vuelo = usuarios_has_vuelos.clase_vuelo_idclase_vuelo\n"
                                + "INNER JOIN ruta ruta ON vuelos.ruta_idruta = ruta.idruta\n"
                                + "WHERE usuarios_has_vuelos.codigo = '" + jtfcod_reserva.getText() + "';";
                        ResultSet rs = db.query(sql);
                        if (rs.first()) {
                            jtfcod_reserva.setEnabled(false);
                            jtfnombre.setText(rs.getString("usuarios.nombre"));
                            jlbpasaporte.setText(rs.getString("usuarios.pasaporte"));
                            jtforigen.setText(rs.getString("ruta.origen"));
                            jtfhora_inicio.setText(rs.getString("vuelos.hora_inicio"));
                            jlbdestino.setText(rs.getString("ruta.destino"));
                            jtfhora_fin.setText(rs.getString("vuelos.hora_fin"));
                            jtfclase.setText(rs.getString("clase_vuelo.clase"));
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontro el codigo de reserva digitado");
                        }
                        db.desconectar();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error en la consulta");
                    }
                }
            }
        });

        jlbnombre = new JLabel("Nombre de Pasajero:");
        jlbnombre.setBounds(150, 130, 130, 20);
        add(jlbnombre);

        jtfnombre = new JTextField();
        jtfnombre.setBounds(295, 130, 300, 20);
        add(jtfnombre);

        jlbpasaporte = new JLabel("Numero de pasaporte:");
        jlbpasaporte.setBounds(150, 180, 200, 20);
        add(jlbpasaporte);

        jtfpasaporte = new JTextField();
        jtfpasaporte.setBounds(295, 180, 145, 20);
        add(jtfpasaporte);

        jlborigen = new JLabel("Origen:");
        jlborigen.setBounds(150, 230, 70, 20);
        add(jlborigen);

        jtforigen = new JTextField();
        jtforigen.setBounds(210, 230, 50, 20);
        jtforigen.setEnabled(false);
        add(jtforigen);

        jlbhora_inicio = new JLabel("Hora Partida:");
        jlbhora_inicio.setBounds(295, 230, 100, 20);
        add(jlbhora_inicio);

        jtfhora_inicio = new JTextField();
        jtfhora_inicio.setBounds(380, 230, 70, 20);
        jtfhora_inicio.setEnabled(false);
        add(jtfhora_inicio);

        jlbdestino = new JLabel("Destino:");
        jlbdestino.setBounds(150, 280, 70, 20);
        add(jlbdestino);

        jtfdestino = new JTextField();
        jtfdestino.setBounds(210, 280, 50, 20);
        jtfdestino.setEnabled(false);
        add(jtfdestino);

        jlbhora_fin = new JLabel("Hora Llegada:");
        jlbhora_fin.setBounds(295, 280, 100, 20);
        add(jlbhora_fin);

        jtfhora_fin = new JTextField();
        jtfhora_fin.setBounds(380, 280, 70, 20);
        jtfhora_fin.setEnabled(false);
        add(jtfhora_fin);

        jlbclase = new JLabel("Clase:");
        jlbclase.setBounds(150, 330, 60, 20);
        add(jlbclase);

        jtfclase = new JTextField();
        jtfclase.setBounds(210, 330, 120, 20);
        add(jtfclase);

        checkin = new JCheckBox("Confirmar Reserva");
        checkin.setBounds(260, 380, 150, 20);
        add(checkin);

        jbtnaceptar = new JButton("Aceptar");
        jbtnaceptar.setBounds(200, 430, 100, 20);
        add(jbtnaceptar);
        jbtnaceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                try {
                    db.conectar();
                    String sqlupdate = "UPDATE usuarios_has_vuelos SET checkin=1 WHERE codigo='" + jlbcod_reserva.getText() + "';";
                    db.queryUpdate(sqlupdate);
                    db.desconectar();
                    JOptionPane.showMessageDialog(null, "Cambio de estado: Check-In Realizado");
                } catch (Exception e) {
                }
            }
        });

        jbtncancelar = new JButton("Cancelar");
        jbtncancelar.setBounds(340, 430, 100, 20);
        add(jbtncancelar);

    }
}
