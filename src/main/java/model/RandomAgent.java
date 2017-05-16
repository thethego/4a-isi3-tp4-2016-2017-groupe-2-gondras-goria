package model;


import controler.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hagoterio on 01/05/17.
 */
public class RandomAgent implements Runnable {
    private final static int INITIAL_TIME_SLEEP = 16;
    private final static int DIR_VARIATION = 40;

    private Model model;
    private Turtle turtle;

    public RandomAgent(Model model, Turtle turtle) {
        this.model = model;
        this.turtle = turtle;
    }

    public void run() {
        while (true) {
            try {
                Random rand = new Random();
                int speed = rand.nextInt(10) + 1;
                int oldDir = this.turtle.getDir();
                int dir = oldDir + rand.nextInt(2*DIR_VARIATION) - DIR_VARIATION;
                if(dir < 0) dir = 360 - dir;
                turtle.setDir(dir);
                model.forward(speed, this.turtle);
                model.notifyView();
                Thread.sleep(INITIAL_TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
