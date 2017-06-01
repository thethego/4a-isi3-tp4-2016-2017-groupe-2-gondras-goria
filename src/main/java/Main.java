import view.SimpleLogo;

import javax.swing.*;

/**
 * Created by hagoterio on 01/06/17.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){

                SimpleLogo fenetre = new SimpleLogo();
                fenetre.setVisible(true);
            }
        });
    }
}
