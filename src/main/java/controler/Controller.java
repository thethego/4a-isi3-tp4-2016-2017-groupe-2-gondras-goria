package controler;

import model.Model;
import model.Turtle;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by theo on 12/04/17.
 */
public class Controller {
    Model model;

    public Controller(Model model){
        this.model = model;
    }

    public Controller() {
        this(new Model());
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
        else if (c.equals("Ajouter"))
            this.addTurtle(width/2, height/2);
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
        model.avancer(inputValue);
    }

    public void right(int inputValue){
        model.droite(inputValue);
    }

    public void left(int inputValue){
        model.gauche(inputValue);
    }

    public void pencilUp(){
        model.leverCrayon();
    }

    public void pencilDown(){
        model.baisserCrayon();
    }

    public void addTurtle(double newX, double newY){
        model.addTurtle(newX,newY);
    }

    public void square(){
        model.carre();
    }

    public void poly(){
        model.poly(60,8);
    }

    public void spiral(){
        model.spiral(50,40,6);
    }

    public void reset(double newX, double newY){
        model.reset(newX, newY);
    }

    public void setColor(int color){
        model.setColor(color);
    }

    public void changeTurtle (int X, int Y){
        model.setCurrentTurtle(X,Y);
    }
}
