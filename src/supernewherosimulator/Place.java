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
public class Place {
    
    private int x;
    private int y;

    public Place(int locationX, int locationY) {
        this.x = locationX;
        this.y = locationY;
    }
    
    
    
    /**
     *
     */
    public void create() {
        
    }
    
    /**
     *
     */
    public void destroy() {
        
    }

    /**
     * @return the locationX
     */
    public int getX() {
        return x;
    }

    /**
     * @param locationX the locationX to set
     */
    public void setLocationX(int locationX) {
        this.x = locationX;
    }

    /**
     * @return the locationY
     */
    public int getY() {
        return y;
    }

    /**
     * @param locationY the locationY to set
     */
    public void setLocationY(int locationY) {
        this.y = locationY;
    }
    
    /**
     * print coordinates of intersection (x,y)
     */
    public void print() {
        System.out.printf("(%d,%d)", this.x, this.y);
    }
    
}
