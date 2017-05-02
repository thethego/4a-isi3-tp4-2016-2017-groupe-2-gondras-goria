package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hagoterio on 02/05/17.
 */
public class flockingAgent implements Runnable {

    private final static int INITIAL_DIST_NEIGHBORHOOD = 100;
    private final static int INITIAL_MINIMAL_DIST = 30;

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
        Vector action = getFlockingVector(neighbors);
        turtle.setDir(action.getAngle());
        turtle.forward(action.getDist(),model.getWidth(),model.getHeight());
    }

    private Vector getCohesion(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            int meanSpeed = 0;
            int meanDir = 0;
            for(Turtle t : neighbors){
                meanSpeed += t.getSpeed();
                meanDir += t.getDir();
            }
            meanSpeed /= neighbors.size();
            meanDir /= neighbors.size();
             return new Vector(meanSpeed,meanDir);
        } else {
            return new Vector(0,0);
        }
    }

    private Vector getAlignment(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            int MeanX = 0;
            int MeanY = 0;
            for(Turtle t : neighbors){
                MeanX += t.getX();
                MeanY += t.getY();
            }
            MeanX /= neighbors.size();
            MeanY /= neighbors.size();
            return new Vector(turtle.getX(),turtle.getY(),MeanX,MeanY);
        } else {
            return new Vector(0,0);
        }
    }

    private Vector getSeparation(){
        ArrayList<Turtle> toCloseNeighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_MINIMAL_DIST);
        if(toCloseNeighbors.size() > 0){
            Vector v;
            int MeanX = 0;
            int MeanY = 0;
            for(Turtle t : toCloseNeighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY());
                v.inverseAngle();
                v.setDist(INITIAL_MINIMAL_DIST - v.getDist());
                MeanX += v.getX(turtle.getX());
                MeanY += v.getX(turtle.getY());
            }
            MeanX /= toCloseNeighbors.size();
            MeanY /= toCloseNeighbors.size();
            return new Vector(turtle.getX(),turtle.getY(),MeanX,MeanY);
        } else {
            return new Vector(0,0);
        }
    }

    private Vector getFlockingVector(ArrayList<Turtle> neighbors){
        ArrayList<Double> coefs = new ArrayList<>();
        Vector separation = getSeparation();
        Vector alignment = getAlignment(neighbors);
        Vector cohesion = getCohesion(neighbors);
        if(separation.getDist()>0){
            coefs.add(0.5);
            coefs.add(0.3);
            coefs.add(0.2);
        } else {
            coefs.add(0.0);
            coefs.add(0.9);
            coefs.add(0.1);
        }
        int newX = (int) (separation.getX(turtle.getX())*coefs.get(0)
                + alignment.getX(turtle.getX())*coefs.get(1)
                + cohesion.getX(turtle.getX())*coefs.get(2));
        int newY = (int) (separation.getY(turtle.getY())*coefs.get(0)
                + alignment.getY(turtle.getY())*coefs.get(1)
                + cohesion.getY(turtle.getY())*coefs.get(2));
        return new Vector(turtle.getX(),turtle.getY(),newX,newY);
    }
}
