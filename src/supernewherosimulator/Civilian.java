/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import static supernewherosimulator.SuperNewHeroSimulator.randInt;

/**
 *
 * @author Lisek
 */
public class Civilian extends Human {

    public Civilian(String name, int id, int locationX, int locationY, Intersection familyTown) {
        super(name, id, locationX, locationY, familyTown);
    }
    
    public void run() {
        super.run();
        //Initialization
        Planet homeTown = (Planet)this.getFamilyTown();
        Planet toGo;
        
        //calculate destination town
        if(homeTown.getPopulation() != 0) {
            homeTown.decreasePopulation();
            
            int toGoId;
            do {
                toGoId = randInt(0, SuperNewHeroSimulator.numOfTowns - 1);
                toGo = (Planet) SuperNewHeroSimulator.inter[toGoId];
            } while (toGo == homeTown);
            
            Intersection currentIntersection = this.getFamilyTown();
            Intersection endIntersection = toGo;

            //move between towns
            this.moveBetween(currentIntersection, endIntersection);

            //collision detection
            this.checkCollision(character, SuperNewHeroSimulator.inter);
        }
    }

    public Label getCharacterInfo() {
        Label details = new Label();
        details.setWrapText(true);
        details.setText("name: " + this.getName() + "\n" +
                        "family town: " + ((Planet)this.getFamilyTown()).getName()
                        );
        return details;
    }    
    
    /**
     *
     * @randomize Town to visit
     */
    public int randomizeTown() {
        return 1;
        
    }
    
    /**
     * @visit a Town
     */
    public void visitTown() {
        
    }
    
    /**
     *  @stop walk
     */
    public void stop() {
        
    }
    
    /**
     *  @resume walk
     */
    public void resume() {
    
    }

    
}
