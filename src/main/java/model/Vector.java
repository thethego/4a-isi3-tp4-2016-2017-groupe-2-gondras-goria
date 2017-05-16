package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hagoterio on 02/05/17.
 */
public class Vector {
    public static final double ratioDegRad = 0.0174533; // ratio radians/degres

    private double dist;
    private double angle;

    //To handle toroidal environment
    private int width;
    private int height;

    public Vector(double dist, double angle, int[] dimension) {
        this.dist = dist;
        this.angle = angle;
        this.width = dimension[0];
        this.height = dimension[1];
    }

    public Vector(int x1, int y1, int x2, int y2, int[] dimension){
        this.width = dimension[0];
        this.height = dimension[1];

        ArrayList<double[]> vectors = new ArrayList<>();
        double[] values;
        double minDist = Double.MAX_VALUE;
        double currentAngle = 0;

        //for the toroidal environment
        //we search the best way to go to another point
        values = getAll(x1,y1,x2,y2);
        vectors.add(values);
        values = getAll(x1,y1,x2-width,y2-height);
        vectors.add(values);
        values = getAll(x1,y1,x2,y2-height);
        vectors.add(values);
        values = getAll(x1,y1,x2+width,y2-height);
        vectors.add(values);
        values = getAll(x1,y1,x2+width,y2);
        vectors.add(values);
        values = getAll(x1,y1,x2+width,y2+height);
        vectors.add(values);
        values = getAll(x1,y1,x2,y2+height);
        vectors.add(values);
        values = getAll(x1,y1,x2-width,y2+height);
        vectors.add(values);
        values = getAll(x1,y1,x2-width,y2);
        vectors.add(values);
        for(double[] value : vectors){
            if(value[0] < minDist){
                minDist = value[0];
                currentAngle = value[1];
            }
        }

        this.dist = minDist;
        this.angle = currentAngle;
    }

    public double getDist() {
        return dist;
    }

    public double getAngle() {
        return angle;
    }

    public int getX(int x){
        int newX = (int) (Math.round(x+dist*Math.cos(ratioDegRad*angle)))%width;
        if(newX < 0){
            newX = width - newX;
        }
        return newX;

    }

    public int getY(int y){
        int newY = (int) (Math.round(y+dist*Math.sin(ratioDegRad*angle)))%height;
        if(newY < 0){
            newY = height - newY;
        }
        return newY;
    }

    public int getXWithoutDimension(int x){
        return (int) (Math.round(x+dist*Math.cos(ratioDegRad*angle)));
    }

    public int getYWithoutDimension(int y){
        return (int) (Math.round(y+dist*Math.sin(ratioDegRad*angle)));
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void addAngle(int newAngle){
        angle = (angle + newAngle) % 360;
    }

    public void inverseAngle(){
        addAngle(180);
    }

    public double getDist(int x1, int y1, int x2, int y2){
        return Math.round(Math.sqrt(Math.pow(x1 - x2,2)+Math.pow(y1 - y2,2)));
    }

    public double getAngle(int x1, int y1, int x2, int y2){
        return getAll(x1,y1,x2,y2)[1];
    }

    public double[] getAll(int x1, int y1, int x2, int y2){
        double[] ret = new double[2];
        ret[0] = getDist(x1, y1, x2, y2);
        if(ret[0] > 0){
            ret[1] = Math.round(Math.toDegrees(Math.acos((float) (x2-x1)/ret[0])));
            if(y2 < y1){
                ret[1] *= -1;
            }
        } else {
            ret[1] = 0;
        }
        return ret;
    }

    public static Vector getRandomVector(int[] dimension){
        Random rand = new Random();
        int dist = rand.nextInt(100) + 1;
        int angle = rand.nextInt(360);
        return  new Vector(dist,angle,dimension);
    }

    public static Vector getRandomVector(int[] dimension, Turtle turtle){
        int angle = turtle.getRandomDir();
        return  new Vector(10,angle,dimension);
    }
}
