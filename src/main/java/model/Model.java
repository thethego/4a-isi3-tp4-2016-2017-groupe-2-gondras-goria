package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by hagoterio on 26/04/17.
 */
public class Model  extends Observable {
    private Turtle currentTurtle;
    private ArrayList<Turtle> turtles;
    private int color;

    public Model(double x, double y) {
        this.turtles = new ArrayList<Turtle>();
        this.color = 0;
        this.addTurtle(x,y);
    }

    public Model() {
        this(10,10);
    }

    public synchronized void reset() {
        currentTurtle.reset();
        notifyView();
    }

    public synchronized void setPosition(double newX, double newY) {
        currentTurtle.setPosition((int)newX,(int)newY);
        notifyView();
    }

    public synchronized void setDir(int newDir){
        currentTurtle.setDir(newDir);
        notifyView();
    }

    public synchronized void addTurtle(double newX, double newY){
        this.currentTurtle = new Turtle((int)newX,(int)newY);
        this.currentTurtle.setColor(color);
        this.turtles.add(currentTurtle);
        notifyView(currentTurtle);
    }

    public synchronized void avancer(int dist) {
        this.currentTurtle.avancer(dist);
        notifyView();
    }

    public synchronized void droite(int ang) {
        this.currentTurtle.droite(ang);
        notifyView();
    }

    public synchronized void gauche(int ang) {
        this.currentTurtle.gauche(ang);
        notifyView();
    }

    public synchronized void baisserCrayon() {
        this.currentTurtle.baisserCrayon();
        notifyView();
    }

    public synchronized void leverCrayon() {
        this.currentTurtle.leverCrayon();
        notifyView();
    }

    public synchronized void carre() {
        this.currentTurtle.carre();
        notifyView();
    }

    public void poly(int n, int a) {
        this.currentTurtle.poly(n,a);
        notifyView();
    }

    public void spiral(int n, int k, int a) {
        this.currentTurtle.spiral(n,k,a);
        notifyView();
    }

    public void setColor(int color){
        this.color = color;
        currentTurtle.setColor(color);
    }

    public void reset(double newX, double newY){
        for (Iterator it = turtles.iterator(); it.hasNext();) {
            Turtle t = (Turtle) it.next();
            t.reset();
        }
        this.setPosition((int)newX,(int)newY);
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

    public void notifyView(Object arg){
        setChanged();
        notifyObservers(arg);
    }

    public void notifyView(){
        this.notifyView(null);
    }
}
