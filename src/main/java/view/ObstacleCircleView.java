package view;

import model.ObstacleCircle;

import java.awt.*;

/**
 * Created by menros on 22/05/17.
 */
public class ObstacleCircleView implements ObstacleView {
    private ObstacleCircle obstacle;

    public ObstacleCircleView(ObstacleCircle obstacle) {
        this.obstacle = obstacle;
    }


    @Override
    public void drawObstacle(Graphics graph) {
        if (graph==null)
            return;

        graph.setColor(Color.lightGray);

        graph.fillOval(
                this.obstacle.getCenter().getX()-this.obstacle.getDiameter(),
                this.obstacle.getCenter().getY()-this.obstacle.getDiameter(),
                this.obstacle.getDiameter()*2,
                this.obstacle.getDiameter()*2
        );
    }
}
