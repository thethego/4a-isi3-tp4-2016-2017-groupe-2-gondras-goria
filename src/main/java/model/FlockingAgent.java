package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hagoterio on 02/05/17.
 */
public class FlockingAgent implements Runnable {

    private final static int INITIAL_DIST_NEIGHBORHOOD = 200;
    private final static int INITIAL_MINIMAL_DIST = 40;
    private final static int INITIAL_TIME_SLEEP = 50;
    private final static double WEIGHT_COHESION = 0.8;
    private final static double WEIGHT_SEPARATION = 1d;
    private final static double WEIGHT_ALIGN = 0.05;
    private final static double WEIGHT_CURRENT = 0.8;

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
        Vector action = Vector.getRandomVector(dimension,turtle);
        turtle.setDir((int) action.getAngle());
        while(!model.forward((int) action.getDist(), this.turtle)){
            action.addAngle(5);
            turtle.setDir((int) action.getAngle());
        }
    }

    private void doFlockingAction(ArrayList<Turtle> neighbors){
        Vector action = getFlockingVector(neighbors);
        turtle.setDir((int) action.getAngle());
        while(!model.forward((int) action.getDist(), this.turtle)){
            action.addAngle(10);
            turtle.setDir((int) action.getAngle());
        }
    }

    private Vector getCohesion(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            int meanDir = 0;
            for(Turtle t : neighbors){
                meanDir += t.getDir();
            }
            meanDir /= neighbors.size();
             return new Vector(10*WEIGHT_COHESION,meanDir,dimension);
        } else {
            return Vector.getRandomVector(dimension,turtle);
        }
    }

    private Vector getAlignment(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            int meanDir = 0;
            Vector v;
            for(Turtle t : neighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                meanDir += v.getAngle();
            }
            meanDir /= neighbors.size();
            return new Vector(10*WEIGHT_ALIGN,meanDir,dimension);
        } else {
            return Vector.getRandomVector(dimension,turtle);
        }
    }

    private Vector getSeparation(){
        ArrayList<Turtle> toCloseNeighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_MINIMAL_DIST);

        if(toCloseNeighbors.size() > 0){
            Vector v;
            int meanDir = 0;
            for(Turtle t : toCloseNeighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                if(v.getDist() == 0){
                    v.setAngle(turtle.getRandomDir());
                } else {
                    v.inverseAngle();
                }
                v.setDist((int) (INITIAL_MINIMAL_DIST - v.getDist()));
                meanDir += v.getAngle();
            }
            meanDir /= toCloseNeighbors.size();
            return new Vector(10*WEIGHT_SEPARATION,meanDir,dimension);
        } else {
            return new Vector(0,0,dimension);
        }
    }

    private Vector getFlockingVector(ArrayList<Turtle> neighbors){
        Vector separation = getSeparation();
        Vector alignment = getAlignment(neighbors);
        Vector cohesion = getCohesion(neighbors);
        Vector current = new Vector(10*WEIGHT_CURRENT,turtle.getDir(),dimension);
        double totalWeight = WEIGHT_ALIGN + WEIGHT_COHESION + WEIGHT_SEPARATION + WEIGHT_CURRENT;
        double dist = ( separation.getDist()
                +alignment.getDist()
                +cohesion.getDist()
                +current.getDist()) / 4;
        double dir = ( separation.getAngle()*WEIGHT_SEPARATION/totalWeight
                +alignment.getAngle()*WEIGHT_ALIGN/totalWeight
                +cohesion.getAngle()*WEIGHT_COHESION/totalWeight
                +current.getAngle()*WEIGHT_CURRENT/totalWeight);
        return new Vector(dist,dir,dimension);
    }
}
