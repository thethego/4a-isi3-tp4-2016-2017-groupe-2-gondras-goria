package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by theo on 12/04/17.
 */
public class Turtle{

    private final static int DIR_VARIATION = 40;

    private CopyOnWriteArrayList<Segment> segments;
    private int x, y;
    private double dir;
    private boolean visible;
    private int color;
    private double speed;

    public Turtle() {
        this(0,0);
    }

    public Turtle(int newX, int newY) {
        segments = new CopyOnWriteArrayList<>();
        reset();
        x = newX;
        y = newY;
    }

    public void setColor(int n) {
        color = n;
    }

    public int getColor() {
        return color;
    }

    public void reset() {
        Random rand = new Random();
        x = 0;
        y = 0;
        dir = rand.nextInt(360)+1;
        speed = rand.nextInt(9)+1;
        visible = false;
        segments.clear();
    }

    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public void setDir(double newDir){
        newDir %= 360;
        if(newDir < 0) newDir = 360 + newDir;
        dir = newDir;
    }

    public void forwardRec(double dist){
        int width = Model.getWidth();
        int height = Model.getHeight();
        Vector v = new Vector(dist, dir);
        if(isVisible()) {
            int realX = (int)v.getXWithoutDimension(x);
            int realY = (int)v.getYWithoutDimension(y);

            //Toroidal environment, when we arrive on a side, we go on the other side
            int endX = realX;
            int endY = realY;
            int newX = realX;
            int newY = realY;
            if (realX < 0) {
                endX = 0;
                newX = width;
                endY = (int) Math.round(y + ((endX - x) / Math.cos(Vector.ratioDegRad * dir)) * Math.sin(Vector.ratioDegRad * dir));
                newY = endY;
            } else if (realX > width) {
                endX = width;
                newX = 0;
                endY = (int) Math.round(y + ((endX - x) / Math.cos(Vector.ratioDegRad * dir)) * Math.sin(Vector.ratioDegRad * dir));
                newY = endY;
            }
            if (realY < 0) {
                endY = 0;
                newY = height;
                endX = (int) Math.round(x + ((endY - y) / Math.sin(Vector.ratioDegRad * dir)) * Math.cos(Vector.ratioDegRad * dir));
                newX = endX;
            } else if (realY > height) {
                endY = height;
                newY = 0;
                endX = (int) Math.round(x + ((endY - y) / Math.sin(Vector.ratioDegRad * dir)) * Math.cos(Vector.ratioDegRad * dir));
                newX = endX;
            }

            Segment seg = new Segment();

            seg.getPtStart().setX(x);
            seg.getPtStart().setY(y);
            seg.getPtEnd().setX(endX);
            seg.getPtEnd().setY(endY);
            seg.setColor(color);

            segments.add(seg);
            if(segments.size() > 15){
                segments.remove(0);
            }
            setPosition(newX, newY);
            if (endX != realX || endY != realY) {
                dist = (int) Math.round(Math.sqrt(Math.pow(realX - endX, 2) + Math.pow(realY - endY, 2)));
                forward(dist);
            }
        } else {
            setPosition((int)Math.round(v.getX(x)),(int)Math.round(v.getY(y)));
        }
    }

    public void forward(double dist) {
        this.speed = dist;
        forwardRec(dist);

    }

    public void right(int ang) {
        setDir((dir + ang) % 360);
    }

    public void left(int ang) {
        setDir((dir - ang) % 360);
    }

    public void setVisible() {
        visible = true;
    }

    public void setInvisible() {
        visible = false;
        segments.clear();
    }

    public void color(int n) {
        color = n % 12;
    }

    public void couleurSuivante() {
        color(color +1);
    }

    /** some classical */

    public void square() {
        for (int i=0;i<4;i++) {
            forward(100);
            right(90);
        }
    }

    public void poly(int n, int a) {
        for (int j=0;j<a;j++) {
            forward(n);
            right(360/a);
        }
    }

    public void spiral(int n, int k, int a) {
        for (int i = 0; i < k; i++) {
            color(color +1);
            forward(n);
            right(360/a);
            n = n+1;
        }
    }

    public CopyOnWriteArrayList<Segment> getSegments() {
        return segments;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDir() {
        return dir;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isVisible() {
        return visible;
    }

    public double getRandomDir(){
        Random rand = new Random();
        double newDir = getDir() + rand.nextInt(2*DIR_VARIATION) - DIR_VARIATION;
        if(newDir < 0) newDir += 360;
        return newDir;
    }

    public void setRandomDir(){
        setDir(getRandomDir());
    }
}
