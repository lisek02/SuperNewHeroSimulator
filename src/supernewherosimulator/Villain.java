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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Lisek
 */
public class Villain extends Hero {

    public Villain(String name, int id, int locationX, int locationY, Intersection familyTown) {
        super(name, id, locationX, locationY, familyTown);
        this.character.setFill(Color.GREEN);
        this.bound = 8;
    }

    public void run() {
        super.run();
        //Platform.runLater(this);
        if(this.moveToClosestTown()) {
            this.attack();
        }
        //if((this.getCurrentPosition().getPowerSource().getPotential() == 0) && (this.getCurrentPosition().getPopulation() == 0)) {
        
        //}
        
    }

    public void showCharacterDetails() {
        Label characterDetails = new Label();
        int posX = (int) SuperNewHeroSimulator.scene.getWidth() - 150;
        int posY = 390;
 
        characterDetails.setLayoutX(posX + SuperNewHeroSimulator.shiftX);
        characterDetails.setLayoutY(posY + SuperNewHeroSimulator.shiftY);
        characterDetails.setWrapText(true);
        characterDetails.setText(this.getText());

        //Planet familyTown = (Planet) this.getFamilyTown();
        characterDetails.setText("Name: " + this.getName() +
                      //"\nFamilyTown: " + familyTown.getName() +
                      "\nHP: " + this.HP +
                      "\nintelligence: " + this.intelligence +
                      "\nstrength: " + this.strength +
                      "\nspeed: " + this.speed +
                      "\nendurance: " + this.endurance +
                      "\nenergy: " + this.enegry +
                      "\nskill: " + this.skillId
                      );

        this.character.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                SuperNewHeroSimulator.characterLabels.getChildren().clear();
                SuperNewHeroSimulator.characterLabels.getChildren().add(characterDetails);
            }
        });
    }
    
    public String getText() {
        return super.getText();
    }
    
    public void setCharacterRectangle() {
        this.character = new Rectangle(this.getFamilyTown().getX() + this.bound/2, this.getFamilyTown().getY() + this.bound/2, 16, 16);
        this.character.setFill(Color.GREEN);
    }
    
    public void attack() {

            double currentPositionPotential = this.getCurrentPosition().getPowerSource().getPotential();
            Villain tmp = this;
            //while(currentPositionPotential > 0) {
            Timer absorbing = new Timer();
            TimerTask absorbingTask = new TimerTask() {
                @Override
                public void run() {
                    tmp.absorbPotential();
                    tmp.eatPeople();

                    if((tmp.getCurrentPosition().getPowerSource().getPotential() == 0) && (tmp.getCurrentPosition().getPopulation() <= 0)) {
                        absorbing.cancel();
                        //absorbingTask.cancel();
                        //Platform.runLater(this);
                        //tmp.character.setFill(Color.PINK);
                        //tmp.moveBetween(tmp.getCurrentPosition(), SuperNewHeroSimulator.planet[2]);
                    }
                }      
            };
            absorbing.scheduleAtFixedRate(absorbingTask, 0, 2000);
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
