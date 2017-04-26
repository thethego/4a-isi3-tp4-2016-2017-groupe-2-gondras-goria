package controler;

import model.Turtle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by theo on 12/04/17.
 */
public class Controller {
    private Turtle currentTurtle;
    private ArrayList<Turtle> turtles;

    public Controller(Turtle currentTurtle) {
        this.currentTurtle = currentTurtle;
        this.turtles = new ArrayList<Turtle>();
        this.turtles.add(currentTurtle);
    }

    public void handleAction(String c,String inputValue,double width,double height){
        if (c.equals("Avancer")) {
            System.out.println("command avancer");
            try {
                this.moveForward(Integer.parseInt(inputValue));
            } catch (NumberFormatException ex){
                System.err.println("ce n'est pas un nombre : " + inputValue);
            }

        }
        else if (c.equals("Droite")) {
            try {
                this.right(Integer.parseInt(inputValue));
            } catch (NumberFormatException ex){
                System.err.println("ce n'est pas un nombre : " + inputValue);
            }
        }
        else if (c.equals("Gauche")) {
            try {
                this.left(Integer.parseInt(inputValue));
            } catch (NumberFormatException ex){
                System.err.println("ce n'est pas un nombre : " + inputValue);
            }
        }
        else if (c.equals("Lever"))
            this.pencilUp();
        else if (c.equals("Baisser"))
            this.pencilDown();
            // actions des boutons du bas
        else if (c.equals("Proc1"))
            this.square();
        else if (c.equals("Proc2"))
            this.poly();
        else if (c.equals("Proc3"))
            this.spiral();
        else if (c.equals("Effacer"))
            this.reset(width/2, height/2);
        else if (c.equals("Quitter"))
            System.exit(0);
    }

    public void moveForward(int inputValue){
        currentTurtle.avancer(inputValue);
    }

    public void right(int inputValue){
        currentTurtle.droite(inputValue);
    }

    public void left(int inputValue){
        currentTurtle.gauche(inputValue);
    }

    public void pencilUp(){
        currentTurtle.leverCrayon();
    }

    public void pencilDown(){
        currentTurtle.baisserCrayon();
    }

    public void square(){
        currentTurtle.carre();
    }

    public void poly(){
        currentTurtle.poly(60,8);
    }

    public void spiral(){
        currentTurtle.spiral(50,40,6);
    }

    public void reset(double newX, double newY){
        for (Iterator it = turtles.iterator(); it.hasNext();) {
            Turtle t = (Turtle) it.next();
            t.reset();
        }
        currentTurtle.setPosition((int)newX,(int)newY);
    }
}
