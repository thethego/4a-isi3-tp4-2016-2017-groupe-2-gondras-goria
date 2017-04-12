package controler;

import model.Turtle;

import java.util.ArrayList;

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
        currentTurtle.poly(60,8);;
    }

    public void spiral(){
        currentTurtle.spiral(50,40,6);
    }
}
