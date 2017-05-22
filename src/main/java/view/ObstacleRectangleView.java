package view;

import model.Obstacle;
import model.ObstacleRectangle;

import java.awt.*;

/**
 * Created by menros on 03/05/17.
 */
public class ObstacleRectangleView implements ObstacleView {
    private ObstacleRectangle obstacle;

    public ObstacleRectangleView(ObstacleRectangle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(ObstacleRectangle obstacle) {
        this.obstacle = obstacle;
    }

    public void drawObstacle(Graphics graph) {
        if (graph==null)
            return;

        graph.setColor(Color.lightGray);

        graph.fillRect(
                this.obstacle.getPoint().getX(),
                this.obstacle.getPoint().getY(),
                this.obstacle.getWidth(),
                this.obstacle.getHeight()
        );
    }
}
