package view;

import model.Obstacle;
import model.Turtle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by theo on 12/04/17.
 */
public class DrawSheet extends JPanel implements Observer {
    private ArrayList<TurtleView> turtleViews;
    private ArrayList<ObstacleView> obstacleViews;

    public DrawSheet() {
        turtleViews = new ArrayList<TurtleView>();
        obstacleViews = new ArrayList<>();
    }

    public ArrayList<TurtleView> getTurtleViews() {
        return turtleViews;
    }

    public void setTurtleViews(ArrayList<TurtleView> turtleViews) {
        this.turtleViews = turtleViews;
    }

    public void addTurtle(Turtle turtle){
        TurtleView turtleView = new TurtleView(turtle);
        this.turtleViews.add(turtleView);
    }

    public void addObstacle(Obstacle obstacle){
        ObstacleView obstacleView = new ObstacleView(obstacle);
        obstacleViews.add(obstacleView);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color c = g.getColor();

        Dimension dim = getSize();
        g.setColor(Color.white);
        g.fillRect(0,0,dim.width, dim.height);
        g.setColor(c);

        showObstacle(g);
        showTurtles(g);
    }

    public void showTurtles(Graphics g) {
        for(Iterator it = this.turtleViews.iterator();it.hasNext();) {
            TurtleView t = (TurtleView) it.next();
            t.drawTurtle(g);
        }
    }

    public void showObstacle(Graphics g) {
        for(Iterator it = this.obstacleViews.iterator();it.hasNext();) {
            ObstacleView o = (ObstacleView) it.next();
            o.drawObstacle(g);
        }
    }

    public void update(Observable o, Object arg){
        if(arg instanceof Turtle){
            this.addTurtle((Turtle)arg);
        }
        if(arg instanceof Obstacle){
            this.addObstacle((Obstacle)arg);
        }
        if(arg instanceof ArrayList){
            for(Iterator it = ((ArrayList)arg).iterator();it.hasNext();) {
                Object next = it.next();
                if(next instanceof Obstacle){
                    this.addObstacle((Obstacle)next);
                }
            }
        }
        if(arg instanceof CopyOnWriteArrayList){
            for(Iterator it = ((CopyOnWriteArrayList)arg).iterator();it.hasNext();) {
                Object next = it.next();
                if(next instanceof Turtle){
                    this.addTurtle((Turtle)next);
                }
            }
        }
        this.repaint();
    }
}
