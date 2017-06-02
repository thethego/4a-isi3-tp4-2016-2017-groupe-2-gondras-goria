package view;

import model.Segment;
import model.Turtle;
import model.Vector;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by theo on 12/04/17.
 */
public class TurtleView{
    protected static final int rp=10, rb=5; // Taille de la pointe et de la base de la fleche
    private Turtle turtle;

    public TurtleView(Turtle turtle) {
        this.turtle = turtle;
    }

    public Turtle getTurtle() {
        return turtle;
    }

    public void setTurtle(Turtle turtle) {
        this.turtle = turtle;
    }

    public void drawTurtle (Graphics graph) {
        if (graph==null)
            return;

        // Dessine les segments
        CopyOnWriteArrayList<Segment> segments = (CopyOnWriteArrayList<Segment>) turtle.getSegments().clone();
        for(Iterator it = segments.iterator(); it.hasNext();) {
            Segment seg = (Segment) it.next();
            drawSegment(graph, seg);
        }

        //Calcule les 3 coins du triangle a partir de
        // la position de la tortue p
        Point p = new Point(turtle.getX(),turtle.getY());
        Polygon arrow = new Polygon();

        //Calcule des deux bases
        //Angle de la right
        double theta= Vector.ratioDegRad*(-turtle.getDir());
        //Demi angle au sommet du triangle
        double alpha=Math.atan( (float)rb / (float)rp );
        //Rayon de la fleche
        double r=Math.sqrt( rp*rp + rb*rb );
        //Sens de la fleche

        //Pointe
        Point p2=new Point((int) Math.round(p.x+r*Math.cos(theta)),
                (int) Math.round(p.y-r*Math.sin(theta)));
        arrow.addPoint(p2.x,p2.y);
        arrow.addPoint((int) Math.round( p2.x-r*Math.cos(theta + alpha) ),
                (int) Math.round( p2.y+r*Math.sin(theta + alpha) ));

        //Base2
        arrow.addPoint((int) Math.round( p2.x-r*Math.cos(theta - alpha) ),
                (int) Math.round( p2.y+r*Math.sin(theta - alpha) ));

        arrow.addPoint(p2.x,p2.y);
        graph.setColor(decodeColor(turtle.getColor()));
        graph.fillPolygon(arrow);
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

    public void drawSegment(Graphics graph, Segment seg) {
        if (graph==null)
            return;

        graph.setColor(decodeColor(seg.getColor()));
        graph.drawLine(seg.getPtStart().getX(), seg.getPtStart().getY(), seg.getPtEnd().getX(), seg.getPtEnd().getY());
    }

    public int getX(){
        return turtle.getX();
    }

    public int getY(){
        return turtle.getY();
    }
}
