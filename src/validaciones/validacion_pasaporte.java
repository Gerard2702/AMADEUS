package validaciones;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class validacion_pasaporte extends JTextField {

    private Pattern pattern;

    private Border wrongBorder = BorderFactory.createLineBorder(Color.RED);
    private Border defaultBorder;

    /**
     * Constructor.
     *
     * @param regEx Expresión regular para evaluar
     */
    public validacion_pasaporte() {
        super();
        String regEx = "[a-zA-Z]{1}[0-9]{6,9}";

        this.defaultBorder = this.getBorder();
        this.setColumns(15);
        this.pattern = Pattern.compile(regEx);
        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (!Character.isUpperCase(caracter) & !Character.isLowerCase(caracter) & ((caracter < '0') || (caracter > '9'))) {
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
        if (this.getText().length() >= 10) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean valido(){
        Matcher matcher = pattern.matcher(this.getText());
        if (!matcher.matches()) {
            return false;
        }else{
            return true;
        }
    }
}
