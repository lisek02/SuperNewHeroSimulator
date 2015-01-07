/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Lisek
 */
public class Villain extends Hero {

    public Villain(String name, int id, int locationX, int locationY, Intersection familyTown) {
        super(name, id, locationX, locationY, familyTown);
        this.character.setFill(Color.GREEN);
    }

    public void run() {
        super.run();
        if(this.moveToClosestTown()) {
            double currentPositionPotential = this.getCurrentPosition().getPowerSource().getPotential();
            Villain tmp = this;
            //while(currentPositionPotential > 0) {
            Timer absorbing = new Timer();
            TimerTask absorbingTask = new TimerTask() {
                @Override
                public void run() {
                    tmp.absorbPotential();
                    tmp.eatPeople();
                }      
            };
            absorbing.scheduleAtFixedRate(absorbingTask, 0, 2000);
            if((currentPositionPotential == 0) && (this.getCurrentPosition().getPopulation() <= 0)) {
                absorbing.cancel();
                absorbingTask.cancel();
                this.character.setFill(Color.PINK);
            }
        }
        //}
//        if(currentPositionPotential == 0) {
//            this.moveToClosestTown();
//        }
//        

        
        //choose new city
        //go to new city (meanwhile check collision
        //decrease potential
        //...
    }
    
    public boolean moveToClosestTown() {
        Intersection start = SuperNewHeroSimulator.find_lowest_f(new ArrayList<Intersection>(Arrays.asList(SuperNewHeroSimulator.inter)), this.getFamilyTown());
        if(!start.isOccupied()) {        
            Villain tmp = this;
            Node character = this.character;
            SuperNewHeroSimulator.paths.getChildren().add(character);

            TranslateTransition transTransition = new TranslateTransition(Duration.millis(2000), character);
            transTransition.setByX(start.getX() - this.getFamilyTown().getX());
            transTransition.setByY(start.getY() - this.getFamilyTown().getY());
            transTransition.play();
            //System.out.println("Node property: " + transTransition.nodeProperty().getValue());
            //transTransition.nodeProperty();
            transTransition.onFinishedProperty();
            this.setCurrentPosition((Planet)start);
            transTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    tmp.getCurrentPosition().setOccupied(true);
    //                tmp.setCurrentPosition((Planet)start);
    //                System.out.println("Family town: " + tmp.getCurrentPosition());
                }
            });
            return true;
        }
        else return false;
    }
    /**
     *
     * @return
     */
    public int chooseCity() {
        return 1;
    }
    
    /**
     *
     */
    public void attackCity() {
        
    }
    
    /**
     *
     */
    public void absorbPotential() {
        this.getCurrentPosition().getPowerSource().decreasePotential();
    }
    
    /**
     *
     */
    public void increaseSkill() {
        
    }
    
    public void eatPeople() {
        this.getCurrentPosition().decreasePopulation();
    }
    
}
