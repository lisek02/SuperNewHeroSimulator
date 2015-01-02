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
public class Villain extends Hero {

    public Villain(String name, int id, int locationX, int locationY, Intersection familyTown) {
        super(name, id, locationX, locationY, familyTown);
    }


    
    /**
     *
     * @return
     */
    public int chooseCity() {
        return 1;
    }
    
    /**
     *
     */
    public void attackCity() {
        
    }
    
    /**
     *
     */
    public void absorbPotential() {
        
    }
    
    /**
     *
     */
    public void increaseSkill() {
        
    }
    
}
