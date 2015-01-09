/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Lisek
 */
public class SuperHero extends Hero {
    
    private Intersection target;
    
    public SuperHero(String name, int id, int locationX, int locationY, Intersection familyTown, Intersection target) {
        super(name, id, locationX, locationY, familyTown);
        this.bound = 10;
        this.target = target;
    }

    public void showCharacterDetails() {
        //Label characterDetails = new Label();
        int posX = (int) SuperNewHeroSimulator.scene.getWidth() - 150;
        int posY = 570;
        
        SuperNewHeroSimulator.showLabel(posX, posY, this);
    }
    
    public void run() {
        Planet capital = SuperNewHeroSimulator.planet[5];
        
        super.run();
        Group superHero = new Group();
        superHero.getChildren().add(this.character);
        SuperNewHeroSimulator.characters.getChildren().add(superHero);
        capital.releaseSuperHero();
        this.moveBetween(capital, this.target);
        this.checkCollisionWithVillain(SuperNewHeroSimulator.villains);
    }
    
    public void checkCollisionWithVillain(ArrayList<Villain> second) {
        SuperHero thisSH = this;
        ObservableBooleanValue colliding;
        for(Hero hero : second) {
            colliding = Bindings.createBooleanBinding(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return thisSH.getCharacterRectangle().getBoundsInParent().intersects(hero.getCharacterRectangle().getBoundsInParent());
                }
            }, thisSH.getCharacterRectangle().boundsInParentProperty(), hero.getCharacterRectangle().boundsInParentProperty());

            colliding.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        thisSH.attack(hero);
                    } else {
                        //
                    }
                }
            });
        }
        //return colliding.get();
    }   
 
    public void setCharacterRectangle() {
        this.character = new Rectangle(this.getFamilyTown().getX(), this.getFamilyTown().getY(), 20, 20);
        this.character.setFill(Color.OLIVEDRAB);
    }
}
