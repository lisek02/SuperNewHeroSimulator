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
    
    private String name;
    private int familyTown;
    
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
     * @return the familyTown
     */
    public int getFamilyTown() {
        return familyTown;
    }

    /**
     * @param familyTown the familyTown to set
     */
    public void setFamilyTown(int familyTown) {
        this.familyTown = familyTown;
    }
    
}
