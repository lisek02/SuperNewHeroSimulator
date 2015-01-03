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
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
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
    private Intersection familyTown;
    private Intersection currentPosition;
    
    Human(String name, int id, int locationX, int locationY, Intersection familyTown) {
        this.setName(name);
        this.setId(id);
        this.setLocationX(locationX);
        this.setLocationY(locationY);
        this.familyTown = familyTown;
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

    public void moveBetween(Place start, Place end, double delay, double time) {
        Path characterPath = new Path();
        
        int rectangleBound = 5;
        
        Rectangle human = new Rectangle(start.getX() - rectangleBound, start.getY() - rectangleBound, 10, 10);
        human.setFill(Color.web("blue"));
        Node character = human;
        //character.setVisible(false);
        
        characterPath.getElements().add(new MoveTo(start.getX(), start.getY()));
        characterPath.getElements().add(new LineTo(end.getX(), end.getY()));
        
        SuperNewHeroSimulator.paths.getChildren().add(character);
        SuperNewHeroSimulator.paths.getChildren().add(characterPath);
        
        final PathTransition characterTransition = new PathTransition();
        
        characterTransition.setDuration(Duration.seconds(time));
        characterTransition.setPath(characterPath);
        characterTransition.setNode(character);
        characterTransition.setDelay(Duration.seconds(delay));
        characterTransition.play(); 
        characterTransition.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                SuperNewHeroSimulator.paths.getChildren().remove(character);

            }
        });
    }
    
    public void run() {
        Planet homeTown = (Planet)this.getFamilyTown();
        Planet toGo;
        double delay = 0.0;
        
        //Intersection currentIntersection = new Intersection(this.familyTown.getX(), this.familyTown.getY(), new Semaphore(1, false));
        Intersection currentIntersection = this.familyTown;
        Intersection endIntersection;
        Place startPosition, endPosition;
        
        if(homeTown.getPopulation() != 0) {
            homeTown.decreasePopulation();
            
            int toGoId;
            do {
                toGoId = randInt(0, SuperNewHeroSimulator.numOfTowns - 1);
                toGo = (Planet) SuperNewHeroSimulator.inter[toGoId];
            } while (toGo == homeTown);

            ArrayList<Intersection> path = new ArrayList<>();
            path = SuperNewHeroSimulator.findPath(homeTown, toGo);
            
            Place currentHumanPosition = (Place) currentIntersection;

            path.remove(0);
            for (Intersection path1 : path) {
                path1.print();
                
                endIntersection = path1;
                
                startPosition = calculateStartPosition(currentIntersection, endIntersection);
                endPosition = calculateEndPosition(currentIntersection, endIntersection);
                
                try {
                    currentIntersection.sem.acquire();
                } catch(InterruptedException e) {
                    System.out.println("exception");
                }
//                System.out.println("id: " + this.getId());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
//                }
                this.moveBetween(currentHumanPosition, startPosition, delay, 5.5);
                delay += 5.5;
                
                currentIntersection.sem.release();
                
                this.moveBetween(startPosition, endPosition, delay, 3.0);
                delay += 3.0;
                
                currentIntersection = path1;
                startPosition = endPosition;
                currentHumanPosition = endPosition;
            }            
        } else System.out.println("Brak mieszkańców");
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
}