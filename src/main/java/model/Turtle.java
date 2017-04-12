package model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by theo on 12/04/17.
 */
public class Turtle extends Observable {

    protected static final double ratioDegRad = 0.0174533; // Rapport radians/degres (pour la conversion)

    private ArrayList<Segment> segments;
    protected int x, y;
    protected int dir;
    protected boolean crayon;
    protected int color;

    public void setColor(int n) {
        color = n;
    }

    public int getColor() {
        return color;
    }

    public Turtle() {
        segments = new ArrayList<Segment>();
        reset();
    }

    public Turtle(int newX, int newY) {
        this();
        x = newX;
        y = newY;
    }

    public synchronized void reset() {
        x = 0;
        y = 0;
        dir = -90;
        color = 0;
        crayon = true;
        segments.clear();
        notifyObservers();
    }

    public synchronized void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
        notifyObservers();
    }

    public synchronized void setDir(int newDir){
        dir = newDir;
        notifyObservers();
    }

    public void avancer(int dist) {
        int newX = (int) Math.round(x+dist*Math.cos(ratioDegRad*dir));
        int newY = (int) Math.round(y+dist*Math.sin(ratioDegRad*dir));

        if (crayon==true) {
            Segment seg = new Segment();

            seg.getPtStart().setX(x);
            seg.getPtStart().setY(y);
            seg.getPtEnd().setX(newX);
            seg.getPtEnd().setY(newY);
            seg.setColor(color);

            segments.add(seg);
        }

        setPosition(newX,newY);
    }

    public void droite(int ang) {
        setDir((dir + ang) % 360);
    }

    public void gauche(int ang) {
        setDir((dir - ang) % 360);
    }

    public void baisserCrayon() {
        crayon = true;
    }

    public void leverCrayon() {
        crayon = false;
    }

    public void couleur(int n) {
        color = n % 12;
    }

    public void couleurSuivante() {
        couleur(color +1);
    }

    /** quelques classiques */

    public void carre() {
        for (int i=0;i<4;i++) {
            avancer(100);
            droite(90);
        }
    }

    public void poly(int n, int a) {
        for (int j=0;j<a;j++) {
            avancer(n);
            droite(360/a);
        }
    }

    public void spiral(int n, int k, int a) {
        for (int i = 0; i < k; i++) {
            couleur(color +1);
            avancer(n);
            droite(360/a);
            n = n+1;
        }
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDir() {
        return dir;
    }

    public boolean isCrayon() {
        return crayon;
    }

    public static double getRatioDegRad() {
        return ratioDegRad;
    }
}
