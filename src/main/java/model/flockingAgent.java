package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hagoterio on 02/05/17.
 */
public class flockingAgent implements Runnable {

    private final static int INITIAL_DIST_NEIGHBORHOOD = 100;
    private final static int INITIAL_MINIMAL_DIST = 10;

    private Model model;
    private Turtle turtle;

    public flockingAgent(Model model, Turtle turtle) {
        this.model = model;
        this.turtle = turtle;
    }

    public void run() {
        while (true) {
            try {
                ArrayList<Turtle> neighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_DIST_NEIGHBORHOOD);
                System.out.println(neighbors.size());
                if(neighbors.size() > 0){
                    doFlockingAction(neighbors);
                } else {
                    doRandomAction();
                }
                model.notifyView();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doRandomAction(){
        Random rand = new Random();
        int speed = rand.nextInt(100) + 1;
        int dir = rand.nextInt(361);
        turtle.setDir(dir);
        turtle.forward(speed,model.getWidth(),model.getHeight());
    }

    private void doFlockingAction(ArrayList<Turtle> neighbors){
        int sumSpeed = 0;
        int sumDir = 0;
        for(Turtle t : neighbors){
            sumSpeed += t.getSpeed();
            sumDir += t.getDir();
        }
        sumSpeed /= neighbors.size();
        sumDir /= neighbors.size();
        turtle.setDir(sumDir);
        turtle.forward(sumSpeed,model.getWidth(),model.getHeight());
    }
}
