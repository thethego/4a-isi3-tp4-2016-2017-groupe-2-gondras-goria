package controler;

import model.Model;
import model.Turtle;

/**
 * Created by theo on 12/04/17.
 */
public class Controller {
    Model model;

    public Controller(Model model){
        this.model = model;
    }


    public void handleAction(String c,String inputValue){
        if (c.equals("Avancer")) {
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
            this.addTurtle();
            // actions des boutons du bas
        else if (c.equals("Proc1"))
            this.square();
        else if (c.equals("Proc2"))
            this.poly();
        else if (c.equals("Proc3"))
            this.spiral();
        else if (c.equals("Effacer"))
            this.reset();
        else if (c.equals("Quitter"))
            System.exit(0);
    }

    public void moveForward(int inputValue){
        model.forward(inputValue);
    }

    public void right(int inputValue){
        model.right(inputValue);
    }

    public void left(int inputValue){
        model.left(inputValue);
    }

    public void pencilUp(){
        model.pencilUp();
    }

    public void pencilDown(){
        model.pencilDown();
    }

    public void addTurtle(){
        model.addTurtle();
    }

    public void square(){
        model.square();
    }

    public void poly(){
        model.poly(60,8);
    }

    public void spiral(){
        model.spiral(50,40,6);
    }

    public void reset(){
        model.reset();
    }

    public void setColor(int color){
        model.setColor(color);
    }

    public void changeTurtle (int X, int Y){
        model.setCurrentTurtle(X,Y);
    }

    public void changeTurtle(Turtle turtle){
        model.setCurrentTurtle(turtle);
    }
}
