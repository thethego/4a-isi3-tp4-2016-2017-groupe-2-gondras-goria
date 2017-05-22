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
    private final static int INITIAL_DIST = 10;
    private final static double WEIGHT_COHESION = 0.8d;
    private final static double WEIGHT_SEPARATION = 1d;
    private final static double WEIGHT_ALIGN = 0.05d;
    private final static double WEIGHT_CURRENT = 2d;
    private final static double WEIGHT_OBJECTIVE = 0d;
    private final static double ADJUST_DIST = 10*INITIAL_DIST/*20*INITIAL_DIST/(WEIGHT_COHESION+WEIGHT_SEPARATION+WEIGHT_ALIGN+WEIGHT_CURRENT+WEIGHT_OBJECTIVE)*/;

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
            int meanX = 0;
            int meanY = 0;
            for(Turtle t : neighbors){
                v = new Vector(t.getDir(),t.getSpeed(),dimension);
                v = new Vector(turtle.getX(),turtle.getY(),v.getX(turtle.getX()),v.getY(turtle.getY()),dimension);
                meanX += v.getXWithoutDimension(turtle.getX());
                meanY += v.getXWithoutDimension(turtle.getY());
            }
            meanX /= neighbors.size();
            meanY /= neighbors.size();
             Vector ret = new Vector(turtle.getX(),turtle.getY(),meanX,meanY,dimension);
             return ret;
        } else {
            return new Vector(0,0,dimension);
        }
    }

    private Vector getAlignment(ArrayList<Turtle> neighbors){
        if(neighbors.size() > 0){
            Vector v;
            int meanX = 0;
            int meanY = 0;
            for(Turtle t : neighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                meanX += v.getXWithoutDimension(turtle.getX());
                meanY += v.getXWithoutDimension(turtle.getY());
            }
            meanX /= neighbors.size();
            meanY /= neighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),meanX,meanY,dimension);
            return ret;
        } else {
            return new Vector(0,0,dimension);
        }
    }

    private Vector getSeparation(){
        ArrayList<Turtle> toCloseNeighbors = (ArrayList<Turtle>) model.getNeighbors(turtle,INITIAL_MINIMAL_DIST);

        if(toCloseNeighbors.size() > 0){
            Vector v;
            int meanX = 0;
            int meanY = 0;
            for(Turtle t : toCloseNeighbors){
                v = new Vector(turtle.getX(),turtle.getY(),t.getX(),t.getY(),dimension);
                if(v.getDist() == 0){
                    Random rand = new Random();
                    v.setAngle(rand.nextInt(360));
                } else {
                    v.inverseAngle();
                }
                v.setDist((INITIAL_MINIMAL_DIST - v.getDist()));
                meanX += v.getXWithoutDimension(turtle.getX());
                meanY += v.getYWithoutDimension(turtle.getY());
            }
            meanX /= toCloseNeighbors.size();
            meanY /= toCloseNeighbors.size();
            Vector ret = new Vector(turtle.getX(),turtle.getY(),meanX,meanY,dimension);
            return ret;
        } else {
            return new Vector(0,0,dimension);
        }
    }

    private Vector getFlockingVector(ArrayList<Turtle> neighbors){
        Vector separation = getSeparation();
        Vector alignment = getAlignment(neighbors);
        Vector cohesion = getCohesion(neighbors);
        Vector current = new Vector(ADJUST_DIST*WEIGHT_CURRENT,turtle.getDir(),dimension);
        Vector objective = new Vector(turtle.getX(),turtle.getY(),100,100,dimension);

        //Calculating direction
        double totalWeight = (separation.getDist() + alignment.getDist() + cohesion.getDist() +current.getDist() + objective.getDist())/100;
        double meanX = (separation.getXWithoutDimension(turtle.getX())*WEIGHT_SEPARATION/totalWeight +
                alignment.getXWithoutDimension(turtle.getX())*WEIGHT_ALIGN/totalWeight +
                cohesion.getXWithoutDimension(turtle.getX())*WEIGHT_COHESION/totalWeight +
                objective.getXWithoutDimension(turtle.getX())*WEIGHT_OBJECTIVE/totalWeight +
                current.getXWithoutDimension(turtle.getX())*WEIGHT_CURRENT/totalWeight);
        double meanY = (separation.getYWithoutDimension(turtle.getX())*WEIGHT_SEPARATION/totalWeight +
                alignment.getYWithoutDimension(turtle.getX())*WEIGHT_ALIGN/totalWeight +
                cohesion.getYWithoutDimension(turtle.getX())*WEIGHT_COHESION/totalWeight +
                objective.getYWithoutDimension(turtle.getX())*WEIGHT_OBJECTIVE/totalWeight +
                current.getYWithoutDimension(turtle.getX())*WEIGHT_CURRENT/totalWeight);
        Vector flockingVector = new Vector(turtle.getX(),turtle.getY(),(int)meanX,(int)meanY,dimension);
        flockingVector.setDist(INITIAL_DIST);
        return flockingVector;
    }
}
