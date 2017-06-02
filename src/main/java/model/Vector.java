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

    public Vector(double dist, double angle) {
        this.dist = dist;
        this.angle = angle;
    }

    public Vector(double x1, double y1, double x2, double y2){

        ArrayList<double[]> vectors = new ArrayList<>();
        double[] values;
        double minDist = Double.MAX_VALUE;
        double currentAngle = 0;

        int width = Model.getWidth();
        int height = Model.getHeight();
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
        this.setAngle(currentAngle);
    }

    public double getDist() {
        return dist;
    }

    public double getAngle() {
        return angle;
    }

    public double getX(double x){
        int width = Model.getWidth();
        double newX = getXWithoutDimension(x)%width;
        if(newX < 0){
            newX += width;
        }
        return newX;

    }

    public double getY(double y){
        int height = Model.getHeight();
        double newY = getYWithoutDimension(y)%height;
        if(newY < 0){
            newY += height;
        }
        return newY;
    }

    public double getXWithoutDimension(double x){
        return x+dist*Math.cos(ratioDegRad*angle);
    }

    public double getYWithoutDimension(double y){
        return y+dist*Math.sin(ratioDegRad*angle);
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public void setAngle(double angle) {
        angle %= 360;
        if(angle < 0) angle = 360 + angle;
        this.angle = angle;
    }

    public void addAngle(double newAngle){
        setAngle((angle + newAngle));
    }

    public void inverseAngle(){
        addAngle(180);
    }

    public double getDist(double x1, double y1, double x2, double y2){
        return Math.sqrt(Math.pow(x1 - x2,2)+Math.pow(y1 - y2,2));
    }

    public double getAngle(double x1, double y1, double x2, double y2){
        return getAll(x1,y1,x2,y2)[1];
    }

    public double[] getAll(double x1, double y1, double x2, double y2){
        double[] ret = new double[2];
        ret[0] = getDist(x1, y1, x2, y2);
        if(ret[0] > 0){
            ret[1] = Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        } else {
            ret[1] = 0;
        }
        return ret;
    }

    public void normalize(){
        setDist(1);
    }
}
