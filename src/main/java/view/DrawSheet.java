package view;

import model.Turtle;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by theo on 12/04/17.
 */
public class DrawSheet extends JPanel implements Observer {
    private ArrayList<TurtleView> turtleViews;
    private TurtleView currentTurtleView;

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

    public TurtleView getCurrentTurtleView() {
        return currentTurtleView;
    }

    public void setCurrentTurtleView(TurtleView currentTurtleView) {
        this.currentTurtleView = currentTurtleView;
    }

    public void update(Observable o, Object arg) {

    }
}
