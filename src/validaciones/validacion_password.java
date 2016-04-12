package validaciones;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class validacion_password extends JTextField {
    
    private Pattern pattern;

    private Border wrongBorder = BorderFactory.createLineBorder(Color.RED);
    private Border defaultBorder;
    
    public validacion_password() {
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

    private boolean maxLength() {
        if (this.getText().length() >= 30) {
            return true;
        } else {
            return false;
        }
    }
    
    private void validateText() {
        Matcher matcher = pattern.matcher(this.getText());
        if (!matcher.matches()) {
            this.setBorder(wrongBorder);
        } else {
            this.setBorder(defaultBorder);
        }
    }
}
