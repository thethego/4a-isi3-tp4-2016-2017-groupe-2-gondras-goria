package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hagoterio on 02/05/17.
 */
public class FlockingAgent implements Runnable {

    private final static int INITIAL_DIST_NEIGHBORHOOD = 100;
    private final static int INITIAL_MINIMAL_DIST = 50;
    private final static int INITIAL_TIME_SLEEP = 100;

    private Model model;
    private Turtle turtle;
    private int[] dimension;

    public FlockingAgent(Model model, Turtle turtle) {
        this.model = model;
        this.turtle = turtle;
        this.dimension = new int[]{model.getWidth(), model.getHeight()};
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
                Thread.sleep(INITIAL_TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doRandomAction(){
        Vector v = Vector.getRandomVector(dimension);
        turtle.setDir(v.getAngle());
        model.forward(v.getDist(), this.turtle);
    }

    private void doFlockingAction(ArrayList<Turtle> neighbors){
        Vector action = getFlockingVector(neighbors);
        turtle.setDir(action.getAngle());
        model.forward(action.getDist(), this.turtle);
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
             return new Vector(meanSpeed,meanDir,dimension);
        } else {
            return Vector.getRandomVector(dimension);
        }
    }

    private Vector getAlignment(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            int meanSpeed = 0;
            int meanDir = 0;
            Vector v;
            for(Turtle t : neighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                meanDir += v.getAngle();
                meanSpeed += v.getDist();
            }
            meanDir /= neighbors.size();
            meanSpeed /= neighbors.size();
            return new Vector(meanSpeed,meanDir,dimension);
        } else {
            return Vector.getRandomVector(dimension);
        }
    }

    private Vector getSeparation(){
        ArrayList<Turtle> toCloseNeighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_MINIMAL_DIST);

        if(toCloseNeighbors.size() > 0){
            Vector v;
            int meanSpeed = 0;
            int meanDir = 0;
            for(Turtle t : toCloseNeighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                v.inverseAngle();
                v.setDist(INITIAL_MINIMAL_DIST - v.getDist());
                meanDir += v.getAngle();
                meanSpeed += v.getDist();
            }
            meanDir /= toCloseNeighbors.size();
            meanSpeed /= toCloseNeighbors.size();
            return new Vector(meanSpeed,meanDir,dimension);
        } else {
            return Vector.getRandomVector(dimension);
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
        int newX = (int) ((float) separation.getX(turtle.getX())*coefs.get(0)
                + (float) alignment.getX(turtle.getX())*coefs.get(1)
                + (float) cohesion.getX(turtle.getX())*coefs.get(2));
        int newY = (int) ((float) separation.getY(turtle.getY())*coefs.get(0)
                + (float) alignment.getY(turtle.getY())*coefs.get(1)
                + (float) cohesion.getY(turtle.getY())*coefs.get(2));
        return new Vector(turtle.getX(),turtle.getY(),newX,newY,dimension);
    }
}
