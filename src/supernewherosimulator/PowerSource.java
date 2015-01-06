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
public class PowerSource {
    
    private int id;
    private int type;
    private int maxPotential;
    private double potential;
    private int skill;
    
    public PowerSource() {
        this.id = SuperNewHeroSimulator.randInt(0, 100);
        this.type = SuperNewHeroSimulator.powerSourceTypes.get(0);
        this.maxPotential = 100;
        SuperNewHeroSimulator.powerSourceTypes.remove(0);
        this.potential = SuperNewHeroSimulator.randInt((int) 0.3*maxPotential, maxPotential);
        //this.skill = skill;
    }
    
    /**
     *
     */
    public void increasePotential() {
        if(this.getPotential() < this.maxPotential) {
            this.setPotential(this.getPotential() + 1);
        }
    }
    
    /**
     *
     */
    public void decreasePotential() {
        if(this.getPotential() > 0) {
            this.setPotential(this.getPotential() - 1);
        }
        
    }

    /**
     * @return the potential
     */
    public double getPotential() {
        return potential;
    }

    /**
     * @param potential the potential to set
     */
    public void setPotential(double potential) {
        this.potential = potential;
    }
    
}
