/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.ArrayList;
import java.util.Arrays;
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
        this.moveToClosestTown();
        //decrease potential
        //choose new city
        //go to new city (meanwhile check collision
        //decrease potential
        //...
    }
    
    public void moveToClosestTown() {
        Intersection start = SuperNewHeroSimulator.find_lowest_f(new ArrayList<Intersection>(Arrays.asList(SuperNewHeroSimulator.inter)), this.getFamilyTown());
        System.out.println("Villain: " + start.getX() + " " + start.getY());
        
        Node character = this.character;
        SuperNewHeroSimulator.paths.getChildren().add(character);
        
        TranslateTransition transTransition = new TranslateTransition(Duration.millis(2000), character);
        transTransition.setByX(start.getX() - this.getFamilyTown().getX());
        transTransition.setByY(start.getY() - this.getFamilyTown().getY());
        transTransition.play();
//        transTransition.setOnFinished(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                Villain villain = (Villain) event.getSource();
//                villain.setFamilyTown(start);
//            }
//        });
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
        
    }
    
    /**
     *
     */
    public void increaseSkill() {
        
    }
    
}
