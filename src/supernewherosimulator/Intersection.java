/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import static supernewherosimulator.SuperNewHeroSimulator.numOfInters;

/**
 *
 * @author Lisek
 */
public class Intersection extends Place {
    private int x;
    private int y;
    private ArrayList<Intersection> neighbours = new ArrayList<Intersection>();
    
    /**
     * set coordinates for intersection
     * @param x - X coordinate
     * @param y - Y coordinate
     */
    public void setIntersection(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return X coordinate of intersection
     */
    public int getIntersectionX() {
        return(this.x);
    }

    /**
    * @return Y coordinate of intersection
    */
    public int getIntersectionY() {
        return(this.y);
    }
    /**
     * print coordinates of intersection (x,y)
     */
    public void printIntersection() {
        System.out.printf("(%d,%d)", this.x, this.y);
    }
    
    /**
     * Draws a town (black) or an intersection (red)
     * @param i object number
     * @param numOfTowns number of towns
     * @return town
     */
    public Circle drawIntersection(int i, int numOfTowns) {
        String color;
        if(i<numOfTowns) color = "Black";
        else color = "Red";
        Circle town = new Circle(this.getIntersectionX(), this.getIntersectionY(), 10, Color.web(color));
        return town;              
    }
    
    /**
     * creates array list of neighbour intersections
     * @param intersection - single neighbour
     */
    public void addNeighbours(Intersection... intersections) {
        for(int i=0; i<intersections.length; i++) {
            Intersection intersection = intersections[i];
            this.neighbours.add(intersection);
        }
    }
    
    public void printNeighbours() {
        for(int i=0; i<this.neighbours.size(); i++) {
            this.neighbours.get(i).printIntersection();
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
    
}
