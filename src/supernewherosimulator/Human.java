/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
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
    
    Human(String name, int id, int locationX, int locationY, Intersection familyTown) {
        this.setName(name);
        this.setId(id);
        this.setLocationX(locationX);
        this.setLocationY(locationY);
        this.familyTown = familyTown;
    }
    
    public void animate(ArrayList<Intersection> intersectionPath) {
        Node character = this.drawHuman();    
        Path characterPath = new Path();
        
        characterPath.getElements().add(new MoveTo(this.getLocationX(), this.getLocationY()));
        
        for(int i=0; i<intersectionPath.size(); i++) {
            Intersection currentInter = intersectionPath.get(i);
            characterPath.getElements().add(new LineTo(currentInter.getIntersectionX(), currentInter.getIntersectionY()));
        }

        SuperNewHeroSimulator.paths.getChildren().add(character);
        SuperNewHeroSimulator.paths.getChildren().add(characterPath);
        
        final PathTransition characterTransition = new PathTransition();
        
        characterTransition.setDuration(Duration.seconds(5.0));
        //characterTransition.setDelay(Duration.seconds(0.5));
        characterTransition.setPath(characterPath);
        characterTransition.setNode(character);
        characterTransition.setCycleCount(1);
        characterTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        characterTransition.setAutoReverse(false);
        characterTransition.getInterpolator();
        characterTransition.play();
    }
    
    public void run() {
        Intersection homeTown = this.getFamilyTown();
        Intersection toGo;
        int toGoId;
        do {
            toGoId = randInt(0, SuperNewHeroSimulator.numOfTowns - 1);
            toGo = SuperNewHeroSimulator.inter[toGoId];
        } while (toGo == homeTown);
        
//        this.locationX = SuperNewHeroSimulator.inter[homeTown].getIntersectionX();
//        this.locationY = SuperNewHeroSimulator.inter[homeTown].getIntersectionY();
        ArrayList<Intersection> path = new ArrayList<>();
        path = SuperNewHeroSimulator.findPath(homeTown, toGo);
        System.out.println("home town:");
        homeTown.printIntersection();
        System.out.println("to go:");
        toGo.printIntersection();
        for (Intersection path1 : path) {
            path1.printIntersection();
        }
        System.err.println("----");
        this.animate(path);
        System.out.println("Jestę wątkię");
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
