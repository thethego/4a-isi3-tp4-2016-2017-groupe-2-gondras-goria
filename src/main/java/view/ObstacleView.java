package view;

import model.Obstacle;

import java.awt.*;

/**
 * Created by menros on 03/05/17.
 */
public class ObstacleView {
    private Obstacle obstacle;

    public ObstacleView(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public void drawObstacle(Graphics graph) {
        if (graph==null)
            return;

        graph.setColor(decodeColor(this.obstacle.getColor()));
        graph.drawRect(
                this.obstacle.getPoint().getX(),
                this.obstacle.getPoint().getY(),
                this.obstacle.getWidth(),
                this.obstacle.getHeight()
        );
        graph.fillRect(
                this.obstacle.getPoint().getX(),
                this.obstacle.getPoint().getY(),
                this.obstacle.getWidth(),
                this.obstacle.getHeight()
        );
    }

    protected Color decodeColor(int c) {
        switch(c) {
            case 0: return(Color.black);
            case 1: return(Color.blue);
            case 2: return(Color.cyan);
            case 3: return(Color.darkGray);
            case 4: return(Color.red);
            case 5: return(Color.green);
            case 6: return(Color.lightGray);
            case 7: return(Color.magenta);
            case 8: return(Color.orange);
            case 9: return(Color.gray);
            case 10: return(Color.pink);
            case 11: return(Color.yellow);
            default : return(Color.black);
        }
    }
}
