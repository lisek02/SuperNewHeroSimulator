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
            
            Intersection currentIntersection = this.familyTown;
            Intersection endIntersection = toGo;

            //move between towns
            this.moveBetween(currentIntersection, endIntersection);

            //collision detection
            //this.checkCollision(Node first, Node second);
            
            ObservableBooleanValue colliding;
            for(Intersection inter : SuperNewHeroSimulator.inter) {        
                colliding = Bindings.createBooleanBinding(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        return character.getBoundsInParent().intersects(inter.getInterRectangle().getBoundsInParent());
                    }
                }, character.boundsInParentProperty(), inter.getInterRectangle().boundsInParentProperty());

                colliding.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if(newValue) {
                            //try {
                                //seqTransition.stop();
                                //inter.sem.acquire();
                                //seqTransition.play();
                                inter.getInterRectangle().setFill(Color.YELLOW);
                            //} catch (InterruptedException ex) {
                               // Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                            //}
                        } else {
                            //inter.sem.release();
                            //seqTransition.play();
                            inter.getInterRectangle().setFill(Color.RED);
                        }
                    }
                });  
            }    
        } else System.out.println("Brak mieszkańców");
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
