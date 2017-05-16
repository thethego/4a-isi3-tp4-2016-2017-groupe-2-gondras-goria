package model;


import controler.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hagoterio on 01/05/17.
 */
public class RandomAgent implements Runnable {
    private final static int INITIAL_TIME_SLEEP = 100;

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
                int speed = rand.nextInt(100) + 1;
                int dir = rand.nextInt(361);
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
