/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 *
 * @author Lisek
 */
public class SuperHero extends Hero {
    
    public SuperHero(String name, int id, int locationX, int locationY, Intersection familyTown) {
        super(name, id, locationX, locationY, familyTown);
        this.character.setFill(Color.OLIVEDRAB);
    }
    
    public void run() {
        super.run();
        Group superHero = new Group();
        superHero.getChildren().add(this.character);
        SuperNewHeroSimulator.characters.getChildren().add(superHero);
    }
}
