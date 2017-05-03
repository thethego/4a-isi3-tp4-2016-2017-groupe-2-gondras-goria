package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * Created by hagoterio on 26/04/17.
 */
public class Model  extends Observable {
    private Turtle currentTurtle;
    private ArrayList<Turtle> turtles;
    private int height,width;
    private int color;
    private int mode;
    private ArrayList<Obstacle> obstacles;

    public Model(int width, int height, int mode) {
        this.turtles = new ArrayList<Turtle>();
        this.color = 0;
        this.width = width;
        this.height = height;
        this.mode = mode;
        this.addTurtle();
        this.obstacles = new ArrayList<>();
        this.addObstacle(new Point(0, 0), 120, 200);
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

    public synchronized void addTurtle(){
        this.currentTurtle = new Turtle( width/2,height/2);
        this.currentTurtle.setColor(color);
        if(mode == 2){
            new Thread(new RandomAgent(this,currentTurtle)).start();
        } else if(mode == 3) {
            new Thread(new FlockingAgent(this,currentTurtle)).start();
        }
        this.turtles.add(currentTurtle);
        notifyView(currentTurtle);
    }

    public synchronized void addObstacle(Point point, int height, int width){
        this.addObstacle(point, height, width, this.color);
    }

    public synchronized void addObstacle(Point point, int height, int width, int color){
        obstacles.add(new Obstacle(point, height, width, color));
        notifyView(obstacles.get(this.obstacles.size()-1));
    }

    public synchronized void forward(int dist) {
        this.currentTurtle.forward(dist,width,height);
        this.obstacles.get(0).isInObstacle(new Point(this.currentTurtle.getX(), this.currentTurtle.getY()));
        notifyView();
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
        this.currentTurtle.square(width,height);
        notifyView();
    }

    public synchronized void poly(int n, int a) {
        this.currentTurtle.poly(n,a,width,height);
        notifyView();
    }

    public synchronized void spiral(int n, int k, int a) {
        this.currentTurtle.spiral(n,k,a,width,height);
        notifyView();
    }

    public void setColor(int color){
        this.color = color;
        currentTurtle.setColor(color);
    }

    public synchronized void reset(){
        for (Iterator it = turtles.iterator(); it.hasNext();) {
            Turtle t = (Turtle) it.next();
            t.reset();
            t.setPosition((int)width/2,(int)height/2);
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Turtle> getNeighbors(Turtle turtle, int dist){
        int[] dimension = {this.getWidth(), this.getHeight()};
        ArrayList<Turtle> neighbors = new ArrayList<>();
        Vector vector;
        for(Turtle t : turtles){
            if(t != turtle){
                vector = new Vector((turtle.getX()),
                        turtle.getY(),
                        t.getX(),
                        t.getY(),
                        dimension);
                if(vector.getDist()<dist){
                    neighbors.add(t);
                }
            }
        }
        return neighbors;
    }

    public void notifyView(Object arg){
        setChanged();
        notifyObservers(arg);
    }

    public void notifyView(){
        this.notifyView(null);
    }
}
