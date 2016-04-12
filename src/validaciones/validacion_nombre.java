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
import javax.swing.table.DefaultTableModel;

public class validacion_nombre extends JTextField {

    private Pattern pattern;

    private Border wrongBorder = BorderFactory.createLineBorder(Color.RED);
    private Border defaultBorder;

    /**
     * Constructor.
     *
     * @param regEx Expresión regular para evaluar
     */
    public validacion_nombre() {
        super();
        String regEx = "[a-zA-Z x0Bf ñáéíóúÁÉÍÓÚ]+";

        this.defaultBorder = this.getBorder();
        this.setColumns(15);
        this.pattern = Pattern.compile(regEx);
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (!Character.isLowerCase(caracter) & !Character.isUpperCase(caracter) & caracter != ' ') {
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
        if (this.getText().length() >= 100) {
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
        String nombre = this.getText();
        
        database conn = new database();
        conn.conectar();
        ResultSet rs = conn.query("SELECT nombre FROM usuarios WHERE nombre = '" + nombre + "'");
        int count = 0;
        while (rs.next()) {
            count++;
        }
        if(count > 0){
            return true;
        }else{
            return false;
        }
        //conn.desconectar();
    }
}
