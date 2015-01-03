/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.concurrent.Semaphore;
import javafx.scene.control.Label;

/**
 *
 * @author Lisek
 */
public class Planet extends Intersection {
    
    private String name;
    private int population;
    private int powerSourcesTypes[] = new int[6];

    public Planet(int x, int y, Semaphore sem, String name, int population) {
        super(x, y, sem);
        this.name = name;
        this.population = population;
    }
    
    public Label showIntersectionDetails() {
        Label details = new Label();
        details.setWrapText(true);
        details.setText("(" + this.getX() + " " + this.getY() + ")\n" +
                        "Name: " + this.getName() + "\n" +
                        "Population: " + this.getPopulation() + "\n"
                        );
        super.showIntersectionDetails();
        return details;
    }
    /**
     *
     */
    public void addPopulation() {
        
    }
    
    /**
     *
     */
    public void substrackPopulation() {
        
    }
    
    /**
     *
     */
    public void sendSuperHero() {
        
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
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(int population) {
        this.population = population;
    }
    
    /**
     * decrease planet population by 1
     */
    public void decreasePopulation() {
        this.population -= 1;
    }    
    
}
