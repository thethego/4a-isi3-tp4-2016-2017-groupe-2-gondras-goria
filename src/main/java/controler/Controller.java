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
        else if (c.equals("setColor")) {
            try {
                this.setColor(Integer.parseInt(inputValue));
            } catch (NumberFormatException ex) {
                System.err.println("ce n'est pas un nombre : " + inputValue);
            }
        } else if (c.equals("changeTurtle")) {
            try {
                String[] splitted = inputValue.split(",");
                if(splitted.length != 2){
                    System.err.println("ce n'est pas des coordonnés : " + inputValue);
                } else {
                    this.changeTurtle(Integer.parseInt(splitted[0]),Integer.parseInt(splitted[1]));
                }
            } catch (NumberFormatException ex) {
                System.err.println("ce n'est pas un nombre : " + inputValue);
            }
        } else if (c.equals("mouseMoved")) {
            try {
                String[] splitted = inputValue.split(",");
                if(splitted.length != 2){
                    System.err.println("ce n'est pas des coordonnés : " + inputValue);
                } else {
                    this.mouseMoved(Integer.parseInt(splitted[0]),Integer.parseInt(splitted[1]));
                }
            } catch (NumberFormatException ex) {
                System.err.println("ce n'est pas un nombre : " + inputValue);
            }
        } else if (c.equals("disableObjective"))
            disableObjective();
        else if (c.equals("Lever toutes"))
            model.setAllInvisible();
        else if (c.equals("Baisser toutes"))
            model.setAllVisible();
        else if (c.equals("Quitter"))
            System.exit(0);
    }

    private void moveForward(int inputValue){
        model.forward(inputValue);
    }

    private void right(int inputValue){
        model.right(inputValue);
    }

    private void left(int inputValue){
        model.left(inputValue);
    }

    private void pencilUp(){
        model.pencilUp();
    }

    private void pencilDown(){
        model.pencilDown();
    }

    private void addTurtle(){
        model.addTurtle();
    }

    private void square(){
        model.square();
    }

    private void poly(){
        model.poly(60,8);
    }

    private void spiral(){
        model.spiral(50,40,6);
    }

    private void reset(){
        model.reset();
    }

    private void setColor(int color){
        model.setColor(color);
    }

    private void changeTurtle (int X, int Y){
        model.setCurrentTurtle(X,Y);
    }

    private void mouseMoved (int X, int Y) {
        model.mouseMoved(X,Y);
    }

    private void disableObjective(){
        model.disableObjective();
    }

    private void changeTurtle(Turtle turtle){
        model.setCurrentTurtle(turtle);
    }
}
