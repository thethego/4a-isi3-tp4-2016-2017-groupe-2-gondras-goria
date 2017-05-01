package model;


import controler.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hagoterio on 01/05/17.
 */
public class randomAgent implements Runnable{
     public static List<String> ACTIONLIST = new ArrayList<String>();
    {
        ACTIONLIST.add("Avancer");
        ACTIONLIST.add("Droite");
        ACTIONLIST.add("Gauche");
    }

    private Controller controller;

    public randomAgent(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        /*while (true) {
            try {
                Random rand = new Random();
                int dist = rand.nextInt(100) + 1;
                int action = rand.nextInt(ACTIONLIST.size());
                controller.handleAction();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }*/
    }
}
