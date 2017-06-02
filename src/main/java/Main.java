import model.Model;
import view.SimpleLogo;

import javax.swing.*;

/**
 * Created by hagoterio on 01/06/17.
 */
public class Main {

    private static final int NB_TURTLES = 100;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleLogo window = new SimpleLogo(NB_TURTLES);
            window.setVisible(true);
        });
    }
}
