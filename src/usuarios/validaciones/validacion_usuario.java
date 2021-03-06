package validaciones;

import config.database;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class validacion_usuario extends JTextField {

    private Pattern pattern;

    private Border wrongBorder = BorderFactory.createLineBorder(Color.RED);
    private Border defaultBorder;

    /**
     * Constructor.
     *
     * @param regEx Expresión regular para evaluar
     */
    public validacion_usuario() {
        super();
        String regEx = "[0-9a-zA-Z_]+";

        this.defaultBorder = this.getBorder();
        this.setColumns(15);
        this.pattern = Pattern.compile(regEx);
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (!Character.isLowerCase(caracter) & !Character.isUpperCase(caracter) & caracter != '_' & ((caracter < '0') || (caracter > '9'))) {
                    e.consume();
                }
                if (maxLength()) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                validateText();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }
        });
    }

    private void validateText() {
        Matcher matcher = pattern.matcher(this.getText());
        if (!matcher.matches()) {
            this.setBorder(wrongBorder);
        } else {
            this.setBorder(defaultBorder);
        }
    }

    private boolean maxLength() {
        if (this.getText().length() >= 30) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean valido() throws SQLException{
        Matcher matcher = pattern.matcher(this.getText());
        if (!matcher.matches()) {
            return false;
        }else{
            if(repetido()){
                return false;
            }else{
                return true;
            }
        }
    }
    
    public boolean repetido() throws SQLException{
        String usuario = this.getText();
        
        database conn = new database();
        conn.conectar();
        //SQL VALIDACION DE NO EXISTENCIA DE USUARIO INGRESADO
        ResultSet rs = conn.query("CALL Users_PA0005('" + usuario + "')");
        int count = 0;
        while (rs.next()) {
            count++;
        }
        if(count > 0){
            conn.desconectar();
            return true;
        }else{
            conn.desconectar();
            return false;
        }        
    }
}
