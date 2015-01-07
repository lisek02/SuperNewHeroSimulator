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
    
    public Label showIntersectionDetails() {
        int posX = (int) SuperNewHeroSimulator.scene.getWidth() - 150;
        int posY = 40;
        Label details = new Label();
        details.setLayoutX(posX + SuperNewHeroSimulator.shiftX);
        details.setLayoutY(posY + SuperNewHeroSimulator.shiftY);
        details.setWrapText(true);
        details.setText(
                        "Name: " + this.getName() + "\n" +
                        "Capital: " + this.isCapital() + "\n" +
                        "Coordinates: (" + this.getX() + " " + this.getY() + ")\n" +                                
                        "Population: " + this.getPopulation() + "\n" +
                        "Power source: " + this.getPowerSource().getPotential() + "\n" +
                        "Occupied: " + this.isOccupied()
                        );
        
        Intersection target = this;
        Button releaseSH = new Button("Launch Viper!");
        releaseSH.setLayoutX(posX + SuperNewHeroSimulator.shiftX);
        releaseSH.setLayoutY(details.getLayoutY() + 120);
        SuperNewHeroSimulator.releaseButton.getChildren().add(releaseSH);
        releaseSH.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Planet capital = SuperNewHeroSimulator.planet[5];
                SuperHero sHero = new SuperHero("Cpt. Adama", SuperNewHeroSimulator.randInt(1, 100), capital.getX(), capital.getY(), capital);
                Thread threadS = new Thread(sHero);
                Platform.runLater(threadS);
                capital.releaseSuperHero();
                sHero.moveBetween(capital, target);
            }
        });            
        super.showIntersectionDetails();
        return details;
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
