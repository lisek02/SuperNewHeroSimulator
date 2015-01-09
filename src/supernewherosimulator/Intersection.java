/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.shape.Rectangle;
import static supernewherosimulator.SuperNewHeroSimulator.numOfInters;
import static supernewherosimulator.SuperNewHeroSimulator.root;

/**
 *
 * @author Lisek
 */
public class Intersection extends Place {
    private ArrayList<Intersection> neighbours = new ArrayList<Intersection>();
    private int bound = 20;
    protected boolean occupied;
    protected Rectangle interRectangle;
    
    public Semaphore sem;
    
    public Intersection(int x, int y) {
        super(x, y);
        this.sem = new Semaphore(1, true);
        this.occupied = false;
    }
    
    public void showIntersectionDetails() {
        Label details = new Label();
        details.setWrapText(true);
        details.setText("(" + this.getX() + " " + this.getY() + ")\n" +
                        "permits: " + this.sem.availablePermits());
        //return details;
    }
    
    public void drawRoad(Intersection end) {
        int difX = end.getX() - this.getX();
        int difY = end.getY() - this.getY();
        if(difX > 0) {
            Rectangle road = new Rectangle(this.getX(), this.getY(), difX, 20);
            road.setStroke(Color.BLACK);
            road.setFill(Color.WHITE);
            road.setStrokeWidth(2);
            SuperNewHeroSimulator.roads.getChildren().add(road); 
        }
       if(difY > 0) {
            Rectangle road = new Rectangle(this.getX(), this.getY(), 20, difY);
            road.setStroke(Color.BLACK);
            road.setFill(Color.WHITE);
            road.setStrokeWidth(2);
            SuperNewHeroSimulator.roads.getChildren().add(road); 
        }
    }
    
    /**
     * set length of intersecttion's side
     * @param bound 
     */
    public void setBound(int bound) {
        this.bound = bound;
    }
    
    /**
     * @return length of intersection's side
     */
    public int getBound() {
        return this.bound;
    }
    
    /**
     * Draws a town (black) or an intersection (red)
     * @param i object number
     * @param numOfTowns number of towns
     * @return town
     */
    public Rectangle drawIntersection(int i, int numOfTowns) {
        String color;
        if(i<numOfTowns) color = "Black";
        else color = "Red";
        Rectangle town = new Rectangle(this.getX(), this.getY(), this.bound, this.bound);
        town.setFill(Color.web(color));
        return town;              
    }
    
    /**
     * creates array list of neighbour intersections
     * @param intersection - single neighbour
     */
    public void addNeighbours(Intersection... intersections) {
        for (Intersection intersection : intersections) {
            this.neighbours.add(intersection);
        }
    }
    
    public void printNeighbours() {
        for(int i=0; i<this.neighbours.size(); i++) {
            this.neighbours.get(i).print();
        }
    }
    
    public ArrayList<Intersection> getNeighboursList() {
        return this.neighbours;
    }
    
    public void generateIntersections(Intersection[] inter) {
        
    }
    /**
     *
     */
    public void raiseSemaphore() {
        
    }
    
    /**
     *
     */
    public void lowerSemaphore() {
        
    }

    /**
     * @return the interRectangle
     */
    public Rectangle getInterRectangle() {
        return interRectangle;
    }

    /**
     * 
     */
    public void setInterRectangle(int i, int numOfTowns) {
        String color;
        if(i<numOfTowns) color = "Black";
        else color = "Red";
        this.interRectangle = new Rectangle(this.getX()-2, this.getY()-2, this.bound+4, this.bound+4);
        this.interRectangle.setFill(Color.web(color));        
    }
    
    /**
     * @return the occupied
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * @param occupied the occupied to set
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
}
