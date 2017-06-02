package model;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hagoterio on 26/04/17.
 */
public class Model  extends Observable {
    private final int INITIAL_NB_TURTLE = 100;

    private Turtle currentTurtle;
    private CopyOnWriteArrayList<Turtle> turtles;
    private static int height,width;
    private int color;
    private int mode;
    private ArrayList<Obstacle> obstacles;

    public Model(int width, int height, int mode) {
        this.turtles = new CopyOnWriteArrayList<>();
        this.obstacles = new ArrayList<>();
        this.color = 0;
        Model.width = width;
        Model.height = height;
        this.mode = mode;
        this.addObstacleRectangle(new Point(10, 50), 60, 40);
        this.addObstacleCircle(new Point(200, 100), 70);
        this.addObstacleRectangle(new Point(500, 10), 80, 70);
        this.addObstacleCircle(new Point(200, 300), 40);
        this.addObstacleRectangle(new Point(600, 300), 70, 40);

        /* add diferent color turtles*/
        this.setColor(8);
        this.addTurtles(INITIAL_NB_TURTLE/4);
        this.setColor(1);
        this.addTurtles(INITIAL_NB_TURTLE/4);
        this.setColor(4);
        this.addTurtles(INITIAL_NB_TURTLE/4);
        this.setColor(5);
        this.addTurtles(INITIAL_NB_TURTLE/4);
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public synchronized void setPosition(double newX, double newY) {
        currentTurtle.setPosition((int)newX,(int)newY);
        notifyView();
    }

    public synchronized void setDir(int newDir){
        currentTurtle.setDir(newDir);
        notifyView();
    }

    public synchronized void addTurtles(int n){
        for(int i = 0 ; i < n ; i++){
            this.addTurtle();
        }
    }

    public synchronized void addTurtle(){
        Turtle turtle = new Turtle( width/2,height/2);
        if(mode == 2){
            new Thread(new RandomAgent(this, turtle)).start();
        } else if(mode == 3) {
            new Thread(new FlockingAgent(this, turtle)).start();
        }
        this.turtles.add(turtle);
        this.currentTurtle = turtle;
        this.currentTurtle.setColor(color);
        notifyView(turtle);
    }

    public synchronized void addObstacleRectangle(Point point, int height, int width){
        obstacles.add(new ObstacleRectangle(point, height, width));
        notifyView(obstacles.get(this.obstacles.size()-1));
    }

    public synchronized void addObstacleCircle(Point point, int diameter){
        obstacles.add(new ObstacleCircle(point, diameter));
        notifyView(obstacles.get(this.obstacles.size()-1));
    }

    public synchronized void forward(double dist) {
        this.forward(dist, this.currentTurtle);
    }

    public synchronized boolean forward(double dist, Turtle turtle) {
        Vector vect = new Vector(dist, turtle.getDir());
        Point startPoint = new Point(turtle.getX(), turtle.getY());
        Point endPoint = new Point((int)Math.round(vect.getX(startPoint.getX())), (int)Math.round(vect.getY(startPoint.getY())));
        Boolean isInObstacle = false;

        for (Obstacle o : this.obstacles) {
            isInObstacle = (isInObstacle || o.isInObstacle(endPoint));
        }
        if(!isInObstacle){
            turtle.forward(dist);
            notifyView();
        }
        return !isInObstacle;
    }

    public synchronized void right(int ang) {
        this.currentTurtle.right(ang);
        notifyView();
    }

    public synchronized void left(int ang) {
        this.currentTurtle.left(ang);
        notifyView();
    }

    public synchronized void pencilDown() {
        this.currentTurtle.setVisible();
        notifyView();
    }

    public synchronized void pencilUp() {
        this.currentTurtle.setInvisible();
        notifyView();
    }

    public synchronized void square() {
        this.currentTurtle.square();
        notifyView();
    }

    public synchronized void poly(int n, int a) {
        this.currentTurtle.poly(n,a);
        notifyView();
    }

    public synchronized void spiral(int n, int k, int a) {
        this.currentTurtle.spiral(n,k,a);
        notifyView();
    }

    public void setColor(int color){
        this.color = color;
        if(currentTurtle != null)
            currentTurtle.setColor(color);
    }

    public synchronized void reset(){
        for (Iterator it = turtles.iterator(); it.hasNext();) {
            Turtle t = (Turtle) it.next();
            t.reset();
            t.setPosition(width/2,height/2);
        }
        notifyView();
    }

    public Turtle getCurrentTurtle() {
        return currentTurtle;
    }

    public void setCurrentTurtle (Turtle turtle){
        this.currentTurtle=turtle;
    }

    public void setCurrentTurtle(int X, int Y){
        for(Turtle turtle : turtles) {
            if (Math.sqrt(Math.pow(turtle.getX() - X, 2) + Math.pow(turtle.getY() - Y, 2)) < 10) {
                currentTurtle = turtle;
                break;
            }
        }
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    public CopyOnWriteArrayList<Turtle> getTurtles() {
        return turtles;
    }

    public List<Turtle> getNeighbors(Turtle turtle, int dist, int angle){
        ArrayList<Turtle> neighbors = new ArrayList<>();
        Vector vector;
        for(Turtle t : turtles){
            if(t != turtle){
                if(t.getColor() == turtle.getColor()) {
                    vector = new Vector((turtle.getX()),
                            turtle.getY(),
                            t.getX(),
                            t.getY());
                    if (vector.getDist() < dist) {
                        if(Math.abs(turtle.getDir() - vector.getAngle()) <= angle/2)
                            neighbors.add(t);
                    }
                }
            }
        }
        return neighbors;
    }

    public void mouseMoved(int X, int Y){
        FlockingAgent.setObjective(X,Y);
    }

    public void disableObjective(){
        FlockingAgent.disableObjective();
    }

    public void setAllVisible(){
        turtles.forEach(Turtle::setVisible);
    }

    public void setAllInvisible(){
        turtles.forEach(Turtle::setInvisible);
    }

    public void notifyView(Object arg){
        setChanged();
        notifyObservers(arg);
    }

    public void notifyView(){
        this.notifyView(null);
    }
}
