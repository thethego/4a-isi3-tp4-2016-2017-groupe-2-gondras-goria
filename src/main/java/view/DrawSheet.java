package view;

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

/**
 * Created by theo on 12/04/17.
 */
public class DrawSheet extends JPanel implements Observer {
    private ArrayList<TurtleView> turtleViews;

    public DrawSheet() {
        turtleViews = new ArrayList<TurtleView>();
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color c = g.getColor();

        Dimension dim = getSize();
        g.setColor(Color.white);
        g.fillRect(0,0,dim.width, dim.height);
        g.setColor(c);

        showTurtles(g);
    }

    public void showTurtles(Graphics g) {
        for(Iterator it = turtleViews.iterator();it.hasNext();) {
            TurtleView t = (TurtleView) it.next();
            t.drawTurtle(g);
        }
    }

    public void findTurtle(int X, int Y){

    }

    public void update(Observable o, Object arg){
        if(arg instanceof Turtle){
            TurtleView tView = new TurtleView((Turtle)arg);
            turtleViews.add(tView);
        }
        this.repaint();
    }
}
