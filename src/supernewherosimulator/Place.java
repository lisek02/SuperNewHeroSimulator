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
    
    public void randomEdge() {
        int edge = SuperNewHeroSimulator.randInt(1,4);
        int randY = SuperNewHeroSimulator.randInt(0, (int) SuperNewHeroSimulator.scene.getHeight());
        int randX = SuperNewHeroSimulator.randInt(0, (int) SuperNewHeroSimulator.scene.getWidth());
        switch(edge) {
            case 1: this.x = 0;
                    this.y = randY;
                    break;
            case 2: this.x = (int) SuperNewHeroSimulator.scene.getWidth();
                    this.y = randY;
                    break;
            case 3: this.x = randX;
                    this.y = 0;
                    break;
            case 4: this.x = randX;
                    this.y = (int) SuperNewHeroSimulator.scene.getHeight();
                    break;
        }
                
         
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
