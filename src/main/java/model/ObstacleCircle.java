package model;

/**
 * Created by menros on 16/05/17.
 */
public class ObstacleCircle implements Obstacle {
    private Point center;
    private int diameter;

    public ObstacleCircle(Point center, int diameter) {
        this.center = center;
        this.diameter = diameter;
    }

    public Point getCenter() {
        return center;
    }

    public int getDiameter() {
        return diameter;
    }

    @Override
    public Boolean isInObstacle(Point point) {
        int distCentre = (int) Math.sqrt(Math.pow(point.getX() - center.getX(),2)+Math.pow(point.getY() - center.getY(),2));
        return distCentre <= diameter;
    }
}
