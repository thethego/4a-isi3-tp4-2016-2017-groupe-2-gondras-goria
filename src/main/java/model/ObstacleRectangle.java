package model;


/**
 * Created by menros on 03/05/17.
 */
public class ObstacleRectangle implements Obstacle {
    private Point point;
    private int height, width;
    private int color;

    public ObstacleRectangle(Point point, int height, int width) {
        this.point = point;
        this.height = height;
        this.width = width;
    }

    public Point getPoint() {
        return point;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getColor() {
        return color;
    }

    @Override
    public Boolean isInObstacle(Point point){

        if(this.point.getX() <= point.getX() && (this.point.getX() + this.width) >= point.getX()){
            if(this.point.getY() <= point.getY() && (this.point.getY() + this.height) >= point.getY()){
                return true;
            }
        }
        return false;
    }
}
