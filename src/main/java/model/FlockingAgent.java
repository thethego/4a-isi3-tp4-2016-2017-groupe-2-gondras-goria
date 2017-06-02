package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hagoterio on 02/05/17.
 */
public class FlockingAgent implements Runnable {

    private final static int INITIAL_DIST_NEIGHBORHOOD = 200;
    private final static int INITIAL_ANGLE_NEIGHBORHOOD = 90;
    private final static int INITIAL_MINIMAL_DIST = 10;
    private final static int INITIAL_TIME_SLEEP = 50;
    private final static int INITIAL_DIST = 10;
    private final static double WEIGHT_COHESION = 0.2;
    private final static double WEIGHT_SEPARATION = 2;
    private final static double WEIGHT_ALIGN = 1;
    private final static double WEIGHT_CURRENT = 1;
    private final static double WEIGHT_OBJECTIVE = 0.5;
    private static int OBJECTIVE_X = 100;
    private static int OBJECTIVE_Y = 100;
    private static boolean OBJECTIVE = false;

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
                ArrayList<Turtle> neighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_DIST_NEIGHBORHOOD, INITIAL_ANGLE_NEIGHBORHOOD);
                doFlockingAction(neighbors);
                model.notifyView();
                Thread.sleep(INITIAL_TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private void doFlockingAction(ArrayList<Turtle> neighbors){
        Vector action = getFlockingVector(neighbors);
        turtle.setDir(action.getAngle());
        while(!model.forward(action.getDist(), this.turtle)){
            action.addAngle(5);
            turtle.setDir(action.getAngle());
        }
    }

    private Vector getCohesion(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            Vector v;
            double meanX = 0;
            double meanY = 0;
            for(Turtle t : neighbors){
                v = new Vector(t.getDir(),t.getSpeed(),dimension);
                meanX += v.getX(turtle.getX());
                meanY += v.getY(turtle.getY());
            }
            meanX /= (double)neighbors.size();
            meanY /= (double)neighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),(int)meanX,(int)meanY,dimension);
            ret.setDist(WEIGHT_COHESION);
            return ret;
        } else {
            return new Vector(0,0,dimension);
        }
    }

    private Vector getAlignment(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            Vector v;
            double meanX = 0;
            double meanY = 0;
            for(Turtle t : neighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                meanX += v.getX(turtle.getX());
                meanY += v.getY(turtle.getY());
            }
            meanX /= (double)neighbors.size();
            meanY /= (double)neighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),(int)meanX,(int)meanY,dimension);
            ret.setDist(WEIGHT_ALIGN);
            return ret;
        } else {
            return new Vector(0,0,dimension);
        }
    }

    private Vector getSeparation(){
        ArrayList<Turtle> toCloseNeighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_MINIMAL_DIST, INITIAL_ANGLE_NEIGHBORHOOD);

        if(toCloseNeighbors.size() > 0){
            Vector v;
            double meanX = 0;
            double meanY = 0;
            for(Turtle t : toCloseNeighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                v.inverseAngle();
                v.setDist((INITIAL_MINIMAL_DIST - v.getDist()));
                meanX += v.getX(turtle.getX());
                meanY += v.getY(turtle.getY());
            }
            meanX /= (double)toCloseNeighbors.size();
            meanY /= (double)toCloseNeighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),(int)meanX,(int)meanY,dimension);
            ret.setDist(WEIGHT_SEPARATION);
            return ret;
        } else {
            return new Vector(0,0,dimension);
        }
    }

    private Vector getFlockingVector(ArrayList<Turtle> neighbors){
        Vector separation = getSeparation();
        Vector alignment = getAlignment(neighbors);
        Vector cohesion = getCohesion(neighbors);
        Vector current = new Vector(100,turtle.getDir(),dimension);
        current.setDist(WEIGHT_CURRENT);
        Vector objective;
        if(OBJECTIVE){
            objective = new Vector(turtle.getX(),turtle.getY(),OBJECTIVE_X,OBJECTIVE_Y,dimension);
        } else {
            objective = new Vector(0,0,dimension);
        }
        objective.setDist(WEIGHT_OBJECTIVE);

        //Calculating direction
        double meanX = (separation.getX(turtle.getX()) +
                alignment.getX(turtle.getX()) +
                cohesion.getX(turtle.getX()) +
                objective.getXWithoutDimension(turtle.getX()) +
                current.getX(turtle.getX()));
        double meanY = (separation.getY(turtle.getY()) +
                alignment.getY(turtle.getY()) +
                cohesion.getY(turtle.getY()) +
                objective.getYWithoutDimension(turtle.getY()) +
                current.getY(turtle.getY()));
        meanX /= 5;
        meanY /= 5;
        Vector flockingVector = new Vector(turtle.getX(),turtle.getY(),meanX,meanY,dimension);
        flockingVector.setDist(INITIAL_DIST);
        return flockingVector;
    }

    public static void setObjective(int objectiveX, int objectiveY) {
        OBJECTIVE_X = objectiveX;
        OBJECTIVE_Y = objectiveY;
        OBJECTIVE = true;
    }

    public static void disableObjective(){
        OBJECTIVE = false;
    }
}
