package model;

import java.util.ArrayList;

/**
 * Created by hagoterio on 02/05/17.
 */
public class Vector {
    public static final double ratioDegRad = 0.0174533; // ratio radians/degres

    int dist;
    int angle;

    //To handle toroidal environment
    int width;
    int height;

    public Vector(int dist, int angle, int[] dimension) {
        this.dist = dist;
        this.angle = angle;
        this.width = dimension[0];
        this.height = dimension[1];
    }

    public Vector(int x1, int y1, int x2, int y2, int[] dimension){
        this.width = dimension[0];
        this.height = dimension[1];

        ArrayList<int[]> vectors = new ArrayList<>();
        int [] values;
        int minDist = Integer.MAX_VALUE;
        int currentAngle = 0;

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


        for(int[] value : vectors){
            if(value[0] < minDist){
                minDist = value[0];
                currentAngle = value[1];
            }
        }

        this.dist = minDist;
        this.angle = currentAngle;
    }

    public int getDist() {
        return dist;
    }

    public int getAngle() {
        return angle;
    }

    public int getX(int x){
        return (int) Math.round(x+dist*Math.cos(ratioDegRad*angle));
    }

    public int getY(int y){
        return (int) Math.round(y+dist*Math.sin(ratioDegRad*angle));
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void addAngle(int value){
        angle += value;
    }

    public int getDist(int x1, int y1, int x2, int y2){
        return (int) Math.round(Math.sqrt(Math.pow(x1 - x2,2)+Math.pow(y1 - y2,2)));
    }

    public int getAngle(int x1, int y1, int x2, int y2){
        return getAll(x1,y1,x2,y2)[1];
    }

    public int[] getAll(int x1, int y1, int x2, int y2){
        int[] ret = new int[2];
        ret[0] = getDist(x1, y1, x2, y2);
        if(ret[0] > 0){
            ret[1] = (int) Math.round(Math.toDegrees(Math.acos((float) (x2-x1)/ret[0])));
            if(y2 < y1){
                ret[1] *= -1;
            }
        } else {
            ret[1] = 0;
        }
        return ret;
    }
}
