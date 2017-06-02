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

    private Model model;
    private Turtle turtle;

    public RandomAgent(Turtle turtle) {
        this.model = Model.getInstance();
        this.turtle = turtle;
    }

    public void run() {
        while (true) {
            try {
                Random rand = new Random();
                int speed = rand.nextInt(10) + 1;
                turtle.setRandomDir();
                while(!model.forward(speed, this.turtle)){
                    turtle.setRandomDir();
                }
                model.notifyView();
                Thread.sleep(INITIAL_TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
