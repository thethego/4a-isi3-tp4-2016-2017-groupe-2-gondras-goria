package model;

import java.util.ArrayList;

/**
 * Created by hagoterio on 02/05/17.
 */
public class FlockingAgent implements Runnable {

    private final static int INITIAL_DIST_NEIGHBORHOOD = 200;
    private final static int INITIAL_ANGLE_OF_VIEW = 140;
    private final static int INITIAL_MINIMAL_DIST = 10;
    private final static int INITIAL_TIME_SLEEP = 50;
    private final static int INITIAL_DIST = 10;
    private final static double WEIGHT_COHESION = 0.2;
    private final static double WEIGHT_SEPARATION = 2;
    private final static double WEIGHT_ALIGN = 1.5;
    private final static double WEIGHT_CURRENT = 1;
    private final static double WEIGHT_OBJECTIVE = 0.8;
    private static int OBJECTIVE_X = 100;
    private static int OBJECTIVE_Y = 100;
    private static boolean OBJECTIVE = false;

    private Model model;
    private Turtle turtle;
    private int[] dimension;

    public FlockingAgent(Turtle turtle) {
        this.model = Model.getInstance();
        this.turtle = turtle;
    }

    public void run() {
        while (true) {
            try {
                ArrayList<Turtle> neighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_DIST_NEIGHBORHOOD, INITIAL_ANGLE_OF_VIEW);
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
                v = new Vector(t.getDir(),t.getSpeed());
                meanX += v.getXWithoutDimension(turtle.getX());
                meanY += v.getYWithoutDimension(turtle.getY());
            }
            meanX /= (double)neighbors.size();
            meanY /= (double)neighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),(int)meanX,(int)meanY);
            ret.setDist(WEIGHT_COHESION);
            return ret;
        } else {
            return new Vector(0,0);
        }
    }

    private Vector getAlignment(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            Vector v;
            double meanX = 0;
            double meanY = 0;
            for(Turtle t : neighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY());
                meanX += v.getXWithoutDimension(turtle.getX());
                meanY += v.getYWithoutDimension(turtle.getY());
            }
            meanX /= (double)neighbors.size();
            meanY /= (double)neighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),(int)meanX,(int)meanY);
            ret.setDist(WEIGHT_ALIGN);
            return ret;
        } else {
            return new Vector(0,0);
        }
    }

    private Vector getSeparation(){
        ArrayList<Turtle> toCloseNeighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_MINIMAL_DIST, INITIAL_ANGLE_OF_VIEW);

        if(toCloseNeighbors.size() > 0){
            Vector v;
            double meanX = 0;
            double meanY = 0;
            for(Turtle t : toCloseNeighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY());
                v.inverseAngle();
                v.setDist((INITIAL_MINIMAL_DIST - v.getDist()));
                meanX += v.getXWithoutDimension(turtle.getX());
                meanY += v.getYWithoutDimension(turtle.getY());
            }
            meanX /= (double)toCloseNeighbors.size();
            meanY /= (double)toCloseNeighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),(int)meanX,(int)meanY);
            ret.setDist(WEIGHT_SEPARATION);
            return ret;
        } else {
            return new Vector(0,0);
        }
    }

    private Vector getFlockingVector(ArrayList<Turtle> neighbors){
        Vector separation = getSeparation();
        Vector alignment = getAlignment(neighbors);
        Vector cohesion = getCohesion(neighbors);
        Vector current = new Vector(0,turtle.getDir());
        current.setDist(WEIGHT_CURRENT);
        Vector objective;
        if(OBJECTIVE){
            objective = new Vector(turtle.getX(),turtle.getY(),OBJECTIVE_X,OBJECTIVE_Y);
            objective.setDist(WEIGHT_OBJECTIVE);
        } else {
            objective = new Vector(0,0);
        }

        //Calculating direction
        double meanX = (separation.getX(turtle.getX()) +
                alignment.getXWithoutDimension(turtle.getX()) +
                cohesion.getXWithoutDimension(turtle.getX()) +
                objective.getXWithoutDimension(turtle.getX()) +
                current.getXWithoutDimension(turtle.getX()));
        double meanY = (separation.getY(turtle.getY()) +
                alignment.getYWithoutDimension(turtle.getY()) +
                cohesion.getYWithoutDimension(turtle.getY()) +
                objective.getYWithoutDimension(turtle.getY()) +
                current.getYWithoutDimension(turtle.getY()));
        meanX /= 5;
        meanY /= 5;
        Vector flockingVector = new Vector(turtle.getX(),turtle.getY(),meanX,meanY);
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
