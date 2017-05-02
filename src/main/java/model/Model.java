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

    public Model(int width, int height, int mode) {
        this.turtles = new ArrayList<Turtle>();
        this.color = 0;
        this.width = width;
        this.height = height;
        this.mode = mode;
        this.addTurtle();
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
            new Thread(new randomAgent(this,currentTurtle)).start();
        } else if(mode == 3) {
            new Thread(new flockingAgent(this,currentTurtle)).start();
        }
        this.turtles.add(currentTurtle);
        notifyView(currentTurtle);
    }

    public synchronized void forward(int dist) {
        this.currentTurtle.forward(dist,width,height);
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
        }
        this.setPosition((int)width/2,(int)height/2);
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
        ArrayList<Turtle> neighbors = new ArrayList<Turtle>();
        for(Turtle t : turtles){
            if (t != turtle
                    && Math.sqrt(Math.pow(turtle.getX() - t.getX(), 2) + Math.pow(turtle.getY() - t.getY(), 2)) < dist){
                neighbors.add(t);
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
