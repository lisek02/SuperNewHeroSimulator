/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.ArrayList;

/**
 *
 * @author Lisek
 */
public class Road {
    
    private ArrayList<Intersection> roadSectionsList = new ArrayList<Intersection>();
    /*
    private ArrayList<Intersection> closedSet = new ArrayList<Intersection>();
    private ArrayList<Intersection> openSet = new ArrayList<Intersection>();
    private ArrayList<Intersection> came_from = new ArrayList<Intersection>();
    */
    
    /**
     * @add section to a road
     * @param section - defines a section
     */
    public void addIntersection(Intersection i) {
        roadSectionsList.add(i);
    }
    
    /**
     * prints intersections of an object.
     */
    public void printIntersection() {
        this.roadSectionsList.toString();
    }
    
    /**
     * returns all intersections of a road
     */
    public void getRoad() {
        for(int i=0; i<roadSectionsList.size(); i++) {
            roadSectionsList.get(i).printIntersection();
        }
    }
}
