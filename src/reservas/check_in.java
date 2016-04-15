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

    private JLabel lblTitulo,lbloader,lbselasiento;
    private JSeparator sep,sep2,sep3;
    private ImageIcon iconloader=new ImageIcon(this.getClass().getResource("/config/icons/loader.gif"));

    private JLabel jlbcod_reserva, jlbnombre, jlbpasaporte, jlbclase;
    private JTextField jtfcod_reserva, jtfnombre, jtfpasaporte, jtfclase;

    private JLabel jlborigen, jlbdestino, jlbhora_inicio, jlbhora_fin;
    private JTextField jtforigen, jtfdestino, jtfhora_inicio, jtfhora_fin;
    private JButton jbtnconsultar, jbtnaceptar, jbtncancelar;
    private JComboBox Cbxasientos;
    private String iduser,idavion;
    
    private JCheckBox checkin;
    database db = new database();

    public check_in() {
        initComponent();
        setLayout(null);
        setBounds(235, 30, 705, 540);
        setBackground(new Color(255, 255, 255));
    }

    public void initComponent() {
        
        Font titulo = new Font("Calibri", 1, 19);
        
        lblTitulo = new JLabel("CHECK-IN");        
        lblTitulo.setBounds(10,10,300,50);
        lblTitulo.setFont(titulo);
        add(lblTitulo);
        
        lbloader=new JLabel("Cargando...");
        lbloader.setIcon(iconloader);
        lbloader.setBounds(585,15,100,27);
        lbloader.setVisible(false);
        add(lbloader);
        
        sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setBounds(10, 50, 685, 5);
        sep.setForeground(new Color(220,220,220));
        add(sep);

        jlbcod_reserva = new JLabel("Codigo de Reserva:");
        jlbcod_reserva.setBounds(30, 80, 114, 14);
        add(jlbcod_reserva);

        jtfcod_reserva = new JTextField();
        jtfcod_reserva.setBounds(150, 80, 100, 25);
        add(jtfcod_reserva);

        jbtnconsultar = new JButton("Consultar");
        jbtnconsultar.setBounds(270, 80, 100, 20);
        jbtnconsultar.setBackground(new Color(158,203,242));
        jbtnconsultar.setBorderPainted(false);
        jbtnconsultar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                jbtnconsultar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jbtnconsultar.setBackground(new Color(158,203,242));
            }            
        });
        add(jbtnconsultar);

        jbtnconsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                if ("".equals(jtfcod_reserva.getText())) {
                    JOptionPane.showMessageDialog(null, "Campo vacio, Favor ingrese un codigo de reserva");
                } else {
                    try {
                        db.conectar();
                        String sql = "SELECT usuarios_has_vuelos.checkin, usuarios.nombre, usuarios.pasaporte, vuelos.hora_inicio, vuelos.hora_fin, ruta.origen, ruta.destino, clase_vuelo.clase\n"
                                + "FROM usuarios usuarios\n"
                                + "INNER JOIN usuarios_has_vuelos usuarios_has_vuelos ON usuarios.idusuarios = usuarios_has_vuelos.usuarios_idusuarios\n"
                                + "INNER JOIN vuelos vuelos ON usuarios_has_vuelos.vuelos_idvuelos = vuelos.idvuelos\n"
                                + "INNER JOIN clase_vuelo clase_vuelo ON clase_vuelo.idclase_vuelo = usuarios_has_vuelos.clase_vuelo_idclase_vuelo\n"
                                + "INNER JOIN ruta ruta ON vuelos.ruta_idruta = ruta.idruta\n"
                                + "WHERE usuarios_has_vuelos.codigo = '" + jtfcod_reserva.getText() + "';";
                        ResultSet rs = db.query(sql);
                        if (rs.first()) {
                            if (Integer.parseInt(rs.getString("usuarios_has_vuelos.checkin")) == 1) {
                                JOptionPane.showMessageDialog(null, "La reserva se encuentra confirmada");
                            } else {

                                jtfcod_reserva.setEnabled(false);
                                jtfnombre.setText(rs.getString("usuarios.nombre"));
                                jtfnombre.setEnabled(true);
                                jtfpasaporte.setText(rs.getString("usuarios.pasaporte"));
                                jlbpasaporte.setEnabled(true);
                                jtforigen.setText(rs.getString("ruta.origen"));
                                jtfhora_inicio.setText(rs.getString("vuelos.hora_inicio"));
                                jtfdestino.setText(rs.getString("ruta.destino"));
                                jtfhora_fin.setText(rs.getString("vuelos.hora_fin"));
                                jtfclase.setText(rs.getString("clase_vuelo.clase"));
                                
                                String query11="SELECT vuelos.avion_idavion, usuarios_has_vuelos.usuarios_idusuarios FROM vuelos,usuarios_has_vuelos WHERE usuarios_has_vuelos.codigo='"+ jtfcod_reserva.getText() +"' AND usuarios_has_vuelos.vuelos_idvuelos=vuelos.idvuelos ";
                                ResultSet rs22 = db.query(query11);
                                if (rs22.first()) {
                                    iduser=rs22.getString("usuarios_idusuarios");
                                    idavion=rs22.getString("avion_idavion");
                                    asientos_disponibles();
                                }
                            }
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
        
        sep2 = new JSeparator(SwingConstants.HORIZONTAL);
        sep2.setBounds(10, 115, 685, 1);
        sep2.setForeground(new Color(220,225,220));
        add(sep2);
        
        jlbnombre = new JLabel("Nombre de Pasajero:");
        jlbnombre.setBounds(150, 130, 130, 20);
        add(jlbnombre);

        jtfnombre = new JTextField();
        jtfnombre.setBounds(295, 130, 300, 20);
        jtfnombre.setEnabled(false);
        add(jtfnombre);
        jtfnombre.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar()) && !Character.isWhitespace(e.getKeyChar())) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Ingrese solo numeros");
                } else {

                }
            }
        });

        jlbpasaporte = new JLabel("Numero de pasaporte:");
        jlbpasaporte.setBounds(150, 180, 200, 20);
        add(jlbpasaporte);

        jtfpasaporte = new JTextField();
        jtfpasaporte.setBounds(295, 180, 145, 20);
        jtfpasaporte.setEnabled(false);
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
        jtfclase.setEnabled(false);
        add(jtfclase);

        sep3 = new JSeparator(SwingConstants.HORIZONTAL);
        sep3.setBounds(10, 365, 685, 1);
        sep3.setForeground(new Color(220,220,220));
        add(sep3);
        
        lbselasiento=new JLabel("Seleccione Asiento: ");
        lbselasiento.setBounds(10, 385, 140, 30);
        add(lbselasiento);
        
        Cbxasientos=new JComboBox();
        Cbxasientos.setBounds(155, 387, 150, 25);
        add(Cbxasientos);
        
        checkin = new JCheckBox("Confirmar Reserva");
        checkin.setBounds(335, 385, 150, 30);
        add(checkin);

        jbtnaceptar = new JButton("Aceptar");
        jbtnaceptar.setBounds(230, 445, 100, 30);
        jbtnaceptar.setBackground(new Color(158,203,242));
        jbtnaceptar.setBorderPainted(false);
        jbtnaceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                jbtnaceptar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jbtnaceptar.setBackground(new Color(158,203,242));
            }            
        });
        add(jbtnaceptar);
        jbtnaceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                lbloader.setVisible(true);
                try {
                    if (checkin.isSelected() == false || "".equals(jtfnombre.getText()) || "".equals(jtfpasaporte.getText()) || "".equals(jtfcod_reserva.getText())) {
                        JOptionPane.showMessageDialog(null, "Favor validar campos, campos vacios");
                    } else {
                        db.conectar();
                        String sqlupdate = "UPDATE usuarios_has_vuelos SET usuarios_has_vuelos.checkin=1 WHERE usuarios_has_vuelos.codigo='" + jtfcod_reserva.getText() + "'";
                        db.queryUpdate(sqlupdate);
                        db.desconectar();
                        
                        String asientoselect=Cbxasientos.getSelectedItem().toString();
                        String[] partes;
                        partes=asientoselect.split("-");
                        String idasientoinsert=partes[0];
                        db.conectar();
                        String sql890 = "UPDATE Asientos SET Estado=1,Usuario='"+iduser+"' WHERE idAsientos='"+idasientoinsert+"' AND avion_idavion='"+idavion+"'";
                        db.queryUpdate(sql890);
                        db.desconectar();
                        
                        JOptionPane.showMessageDialog(null, "Check-In Realizado");
                        PDF_checkin pdf=new PDF_checkin(jtfcod_reserva.getText());
                        
                        lbloader.setVisible(false);
                        jtfcod_reserva.setEnabled(true);
                        jtfcod_reserva.setText("");
                        jtfnombre.setText("");
                        jtfnombre.setEnabled(false);
                        jtfpasaporte.setText("");
                        jtfpasaporte.setEnabled(false);
                        jtforigen.setText("");
                        jtfhora_inicio.setText("");
                        jtfdestino.setText("");
                        jtfhora_fin.setText("");
                        jtfclase.setText("");
                        Cbxasientos.removeAllItems();
                        checkin.setSelected(false);
                    }
                } 
                catch (Exception e) {
                }
            }
        });

        jbtncancelar = new JButton("Limpiar");
        jbtncancelar.setBounds(350, 445, 100, 30);
        jbtncancelar.setBackground(new Color(158,203,242));
        jbtncancelar.setBorderPainted(false);
        jbtncancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                jbtncancelar.setBackground(new Color(200,200,200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                jbtncancelar.setBackground(new Color(158,203,242));
            }            
        });
        add(jbtncancelar);
        jbtncancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jtfcod_reserva.setEnabled(true);
                jtfcod_reserva.setText("");
                jtfnombre.setText("");
                jtfpasaporte.setText("");
                jtforigen.setText("");
                jtfhora_inicio.setText("");
                jtfdestino.setText("");
                jtfhora_fin.setText("");
                jtfclase.setText("");
            }
        });
    }
    
    public void asientos_disponibles(){
        try{
            db.conectar();
            String query22="SELECT CONCAT(Asientos.idAsientos,\"-\",Asientos.Nombre_Asiento) AS asiento FROM Asientos WHERE Asientos.Estado=0 AND Asientos.avion_idavion='"+idavion+"'";
            ResultSet rs33 = db.query(query22);
            while(rs33.next()){
                Cbxasientos.addItem(rs33.getString("asiento"));
            }
            db.desconectar();
        }
        catch(Exception e){
            System.out.println("Error en asientos disponibles . . ."+e);
        }
    }
}
