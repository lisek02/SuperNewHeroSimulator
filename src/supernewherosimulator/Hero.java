/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Lisek
 */
public class Hero extends Human {    
    private int HP;
    private int intelligence;
    private int strength;
    private int speed;
    private int endurance;
    private int enegry;
    private int skill;

    public Hero(String name, int id, int locationX, int locationY, Intersection familyTown) {
        super(name, id, locationX, locationY, familyTown);
    }
    
    public void setCharacterRectangle() {
        this.character = new Rectangle(this.getFamilyTown().getX(), this.getFamilyTown().getY(), 20, 20);
        this.character.setFill(Color.GREEN);
    }
    
    public void run() {
        //super.run();
    }
    
    /**
     * @fight with other hero
     */
    public void fight() {
        
    }
    
    /**
     *  @attack other hero
     */
    public void attack() {
        
    }
    
    /**
     *  @destroy the Hero
     */
    public void die() {
        
    }
    
    /**
     *  @kill a Hero
     */
    public void kill() {
        
    }

    /**
     * @return the HP
     */
    public int getHP() {
        return HP;
    }

    /**
     * @param HP the HP to set
     */
    public void setHP(int HP) {
        this.HP = HP;
    }

    /**
     * @return the intelligence
     */
    public int getIntelligence() {
        return intelligence;
    }

    /**
     * @param intelligence the intelligence to set
     */
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    /**
     * @return the strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @param strength the strength to set
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the endurance
     */
    public int getEndurance() {
        return endurance;
    }

    /**
     * @param endurance the endurance to set
     */
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    /**
     * @return the enegry
     */
    public int getEnegry() {
        return enegry;
    }

    /**
     * @param enegry the enegry to set
     */
    public void setEnegry(int enegry) {
        this.enegry = enegry;
    }

    /**
     * @return the skill
     */
    public int getSkill() {
        return skill;
    }

    /**
     * @param skill the skill to set
     */
    public void setSkill(int skill) {
        this.skill = skill;
    }
    
}
