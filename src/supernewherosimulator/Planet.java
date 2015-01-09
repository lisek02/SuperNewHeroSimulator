/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Lisek
 */
public class Planet extends Intersection {
    
    private String name;
    private int population;
    private PowerSource powerSource;
    private boolean capital;

    public Planet(int x, int y, Semaphore sem, String name, int population) {
        super(x, y);
        this.name = name;
        this.population = population;
        this.powerSource = new PowerSource();
        this.capital = false;
    }
    
    public void showIntersectionDetails() {
        Planet thisPlanet = this;
        int posX = (int) SuperNewHeroSimulator.scene.getWidth() - 150;
        int posY = 40;
        
        SuperNewHeroSimulator.showLabel(posX, posY, this);

        //releasing super hero from capital        
        Intersection target = this;
        Button releaseSH = new Button("Launch Viper!");
        releaseSH.setLayoutX(posX + SuperNewHeroSimulator.shiftX);
        releaseSH.setLayoutY(155);
        SuperNewHeroSimulator.releaseButton.getChildren().add(releaseSH);
        releaseSH.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Planet capital = SuperNewHeroSimulator.planet[5];
                SuperHero sHero = new SuperHero("Cpt. Adama", SuperNewHeroSimulator.randInt(1, 100), capital.getX(), capital.getY(), capital, target);
                Thread threadS = new Thread(sHero);
                Platform.runLater(threadS);
            }
        });            
        super.showIntersectionDetails();
        //return details;
    }
    
    public void releaseSuperHero() {
        System.out.println("SuperHero!");
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
        int ratio = 100;
        if(this.population <= ratio && this.population > 0) {
            this.population = 0;
        }
        else if(this.population > 0) {
            this.population -= ratio;
        }

    }    

    /**
     * @return the powerSource
     */
    public PowerSource getPowerSource() {
        return powerSource;
    }

    /**
     * @param powerSource the powerSource to set
     */
    public void setPowerSource(PowerSource powerSource) {
        this.powerSource = powerSource;
    }    

    public void setInterRectangle(int i, int numOfTowns) {
        super.setInterRectangle(i, numOfTowns);
        if(this.isCapital()) {
            this.getInterRectangle().setFill(Color.PINK);
        }
    }    
    
    /**
     * @return the capital
     */
    public boolean isCapital() {
        return capital;
    }

    /**
     * @param capital the capital to set
     */
    public void setCapital(boolean capital) {
        this.capital = capital;
    }
}
