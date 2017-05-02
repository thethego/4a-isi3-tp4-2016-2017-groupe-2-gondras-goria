package model;

/**
 * Created by hagoterio on 02/05/17.
 */
public class Vector {
    public static final double ratioDegRad = 0.0174533; // ratio radians/degres

    int dist;
    int angle;

    public Vector(int dist, int angle) {
        this.dist = dist;
        this.angle = angle;
    }

    public Vector(int x1, int y1, int x2, int y2){
        dist = (int) Math.round(Math.sqrt(Math.pow(x1 - x2,2)+Math.pow(y1 - y2,2)));
        if(dist > 0){
            angle = (int) Math.round(Math.toDegrees(Math.acos((float) (x2-x1)/dist)));
            if(y2 > y1){
                angle *= -1;
            }
        } else {
            angle = 0;
        }
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
        return (int) Math.round(y+dist*Math.sin(Vector.ratioDegRad*angle));
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public void inverseAngle(){
        if(angle>0){
            angle -= 180;
        } else {
            angle += 180;
        }
    }
}
