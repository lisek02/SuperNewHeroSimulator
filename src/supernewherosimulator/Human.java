/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import static supernewherosimulator.SuperNewHeroSimulator.paths;
import static supernewherosimulator.SuperNewHeroSimulator.randInt;
/**
 *
 * @author Lisek
 */
public abstract class Human implements Runnable {
    
    private String name;
    private int id;
    private int locationX;
    private int locationY;
    private int bound = 5;
    protected Intersection familyTown;
    protected Intersection currentPosition;
    protected Rectangle character;
    
    public Human(String name, int id, int locationX, int locationY, Intersection familyTown) {
        this.setName(name);
        this.setId(id);
        this.setLocationX(locationX);
        this.setLocationY(locationY);
        this.familyTown = familyTown;
        Platform.runLater(this);
        this.setCharacterRectangle();
    }
    
    public Place calculateStartPosition(Intersection currentIntersection, Intersection endIntersection) {
        int bound = currentIntersection.getBound()/2;
        
        int begX = currentIntersection.getX();
        int begY = currentIntersection.getY();
        int endX = endIntersection.getX();
        int endY = endIntersection.getY();
        int resX,resY;
        
        if(begX == endX) {
            if(begY - endY > 0) {                                                               //up
                resX = begX + 2*bound;
                resY = begY;
            } else {                                                                            //down
                resX = begX;
                resY = begY + 2*bound;
            }
        } else {
            if((begX - endX > 0)) {                                                             //left
                resX = begX;
                resY = begY;
            } else {                                                                            //right
                resX = begX + 2*bound;
                resY = begY + 2*bound;
            }
        }    
        
        return(new Place(resX,resY));
    }
  
    public Place calculateEndPosition(Intersection currentIntersection, Intersection endIntersection) {
        int bound = currentIntersection.getBound()/2;
        
        int begX = currentIntersection.getX();
        int begY = currentIntersection.getY();
        int endX = endIntersection.getX();
        int endY = endIntersection.getY();
        int resX,resY;
        
        if(begX == endX) {
            if(begY - endY > 0) {                                                               //up
                resX = endX + 2*bound;
                resY = endY + 2*bound;
            } else {                                                                            //down
                resX = endX;
                resY = endY ;
            }
        } else {
            if((begX - endX > 0)) {                                                             //left
                resX = endX + 2*bound;
                resY = begY;
            } else {                                                                            //right
                resX = endX;
                resY = endY + 2*bound;
            }
        }    
        
        return(new Place(resX,resY));
    }
    
    public void moveBetween(Intersection currentIntersection, Intersection finalIntersection) {
        //Initialization
        
        //Planet homeTown = (Planet)this.getFamilyTown();
        //Planet toGo;
        
//        int toGoId;
//        do {
//            toGoId = randInt(0, SuperNewHeroSimulator.numOfTowns - 1);
//            toGo = (Planet) SuperNewHeroSimulator.inter[toGoId];
//        } while (toGo == homeTown);

//        Intersection currentIntersection = this.familyTown; 
        
        
        Place startPosition, endPosition;
        
        //drawing a character
//        int rectangleBound = 5;        
//        Rectangle human = new Rectangle(this.getLocationX() - rectangleBound, this.getLocationY() - rectangleBound, 10, 10);
//        human.setFill(Color.web("blue"));
        
        Node character = this.character;
        SuperNewHeroSimulator.paths.getChildren().add(character);
             
        //calculating a path
        ArrayList<Intersection> path = new ArrayList<>();
        path = SuperNewHeroSimulator.findPath(currentIntersection, finalIntersection);

        Place currentHumanPosition = (Place) currentIntersection;
        path.remove(0);

        SequentialTransition seqTransition = new SequentialTransition(character);

        final Duration sec15 = Duration.millis(3000);
        final Duration sec30 = Duration.millis(1500);          

       //adding path sections to sequential trasition
        for(Intersection path1 : path) {             

            startPosition = calculateStartPosition(currentIntersection, path1);
            endPosition = calculateEndPosition(currentIntersection, path1); 

            TranslateTransition chTrans1 = new TranslateTransition(sec15);
            chTrans1.setByX(startPosition.getX() - currentHumanPosition.getX());
            chTrans1.setByY(startPosition.getY() - currentHumanPosition.getY());

            TranslateTransition chTrans2 = new TranslateTransition(sec30);
            chTrans2.setByX(endPosition.getX() - startPosition.getX());
            chTrans2.setByY(endPosition.getY() - startPosition.getY());

            seqTransition.getChildren().add(chTrans1);
            seqTransition.getChildren().add(chTrans2);

            currentIntersection = path1;
            currentHumanPosition = endPosition;
        }
        //if position == intersection, check if free to go, wait or go

        seqTransition.setInterpolator(Interpolator.LINEAR);
        System.out.println("Cue points:" + seqTransition.getCuePoints());
        seqTransition.play();
    }          
        
    public Label getCharacterInfo() {
        Label details = new Label();
        details.setWrapText(true);
        details.setText("name:" + this.name);
        return details;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the locationX
     */
    public int getLocationX() {
        return locationX;
    }

    /**
     * @param locationX the locationX to set
     */
    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public void increaseX(int change) {
        this.locationX += change;
    }
    
    /**
     * @return the locationY
     */
    public int getLocationY() {
        return locationY;
    }

    /**
     * @param locationY the locationY to set
     */
    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
    
    /**
     * @return the familyTown
     */
    public Intersection getFamilyTown() {
        return familyTown;
    }

    /**
     * @param familyTown the familyTown to set
     */
    public void setFamilyTown(Intersection familyTown) {
        this.familyTown = familyTown;
    }
    
    public Rectangle drawHuman() {
        Rectangle human = new Rectangle(this.locationX, this.locationY, 10, 10);
        human.setFill(Color.web("blue"));
        return human;
    }
    
    public void setCharacterRectangle() {
        this.character = new Rectangle(this.familyTown.getX() - this.bound, this.familyTown.getY() - this.bound, 10, 10);
        this.character.setFill(Color.BLUE);
    }
}