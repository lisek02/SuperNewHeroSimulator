/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

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
