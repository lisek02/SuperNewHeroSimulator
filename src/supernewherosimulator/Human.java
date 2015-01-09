/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import static supernewherosimulator.SuperNewHeroSimulator.paths;
import static supernewherosimulator.SuperNewHeroSimulator.randInt;
/**
 *
 * @author Lisek
 */
public abstract class Human implements Runnable {
    
    private String name;
    private int id;
    private int locationX;
    private int locationY;
    protected int bound = 5;
    private Intersection familyTown;
    private Planet currentPosition;
    protected Rectangle character;
    
    public Human(String name, int id, int locationX, int locationY, Intersection familyTown) {
        this.setName(name);
        this.setId(id);
        this.setLocationX(locationX);
        this.setLocationY(locationY);
        this.familyTown = familyTown;
        this.setCharacterRectangle();
        //Platform.runLater(this);
    }
    
    public void run() {
        this.showCharacterDetails();
    }
    
    public Place calculateStartPosition(Intersection currentIntersection, Intersection endIntersection) {
        int bound = currentIntersection.getBound()/2;
        
        int begX = currentIntersection.getX();
        int begY = currentIntersection.getY();
        int endX = endIntersection.getX();
        int endY = endIntersection.getY();
        int resX,resY;
        
        if(begX == endX) {
            if(begY - endY > 0) {                                                               //up
                resX = begX + 2*bound;
                resY = begY;
            } else {                                                                            //down
                resX = begX;
                resY = begY + 2*bound;
            }
        } else {
            if((begX - endX > 0)) {                                                             //left
                resX = begX;
                resY = begY;
            } else {                                                                            //right
                resX = begX + 2*bound;
                resY = begY + 2*bound;
            }
        }    
        
        return(new Place(resX,resY));
    }
  
    public Place calculateEndPosition(Intersection currentIntersection, Intersection endIntersection) {
        int bound = currentIntersection.getBound()/2;
        
        int begX = currentIntersection.getX();
        int begY = currentIntersection.getY();
        int endX = endIntersection.getX();
        int endY = endIntersection.getY();
        int resX,resY;
        
        if(begX == endX) {
            if(begY - endY > 0) {                                                               //up
                resX = endX + 2*bound;
                resY = endY + 2*bound;
            } else {                                                                            //down
                resX = endX;
                resY = endY ;
            }
        } else {
            if((begX - endX > 0)) {                                                             //left
                resX = endX + 2*bound;
                resY = begY;
            } else {                                                                            //right
                resX = endX;
                resY = endY + 2*bound;
            }
        }    
        
        return(new Place(resX,resY));
    }
    
    public void moveBetween(Intersection currentIntersection, Intersection finalIntersection) {
        Place startPosition, endPosition;

        Node character = this.character;
        //Platform.runLater(this);
        SuperNewHeroSimulator.paths.getChildren().add(character);
             
        //calculating a path
        ArrayList<Intersection> path = new ArrayList<>();
        path = SuperNewHeroSimulator.findPath(currentIntersection, finalIntersection);

        Place currentHumanPosition = (Place) currentIntersection;
        path.remove(0);

        SequentialTransition seqTransition = new SequentialTransition(character);

        final Duration sec15 = Duration.millis(500);
        final Duration sec30 = Duration.millis(1500);          

       //adding path sections to sequential trasition
        for(Intersection path1 : path) {             
            if(this.getClass() == SuperHero.class) {
                startPosition = new Place(currentIntersection.getX(), currentIntersection.getY());
                endPosition = new Place(path1.getX(), path1.getY());
            } else {
                startPosition = calculateStartPosition(currentIntersection, path1);
                endPosition = calculateEndPosition(currentIntersection, path1);      
            }
            
            if(!(this.getClass() == SuperHero.class)) {
                TranslateTransition chTrans1 = new TranslateTransition(sec15);
                chTrans1.setByX(startPosition.getX() - currentHumanPosition.getX());
                chTrans1.setByY(startPosition.getY() - currentHumanPosition.getY());
                seqTransition.getChildren().add(chTrans1);
                chTrans1.setAutoReverse(true);
            }

            TranslateTransition chTrans2 = new TranslateTransition(sec30);
            chTrans2.setByX(endPosition.getX() - startPosition.getX());
            chTrans2.setByY(endPosition.getY() - startPosition.getY());
            seqTransition.getChildren().add(chTrans2);
            chTrans2.setAutoReverse(true);

            currentIntersection = path1;
            currentHumanPosition = endPosition;
        }
        //if position == intersection, check if free to go, wait or go

        seqTransition.setInterpolator(Interpolator.LINEAR);
        seqTransition.play();
//        if(this.getCurrentPosition().sem.availablePermits() == 0) {
//            seqTransition.pause();
//        }
//        else {
//            seqTransition.play();
//        }
    }     
    
    public void checkCollision(Node first, Intersection[] second) {
        ObservableBooleanValue colliding;
        for(Intersection inter : second) {
            colliding = Bindings.createBooleanBinding(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    return first.getBoundsInParent().intersects(inter.getInterRectangle().getBoundsInParent());
                }
            }, first.boundsInParentProperty(), inter.getInterRectangle().boundsInParentProperty());

            colliding.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(newValue) {
                        inter.getInterRectangle().setFill(Color.YELLOW);
//                        try {
//                            inter.sem.acquire();
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    } else {
                        inter.getInterRectangle().setFill(Color.RED);
//                        inter.sem.release();
                    }
                }
            });
        }
        //return colliding.get();
    }
    
    public void showCharacterDetails() {
        Label characterDetails = new Label();
        int posX = (int) SuperNewHeroSimulator.scene.getWidth() - 150;
        int posY = 240;
 
        characterDetails.setLayoutX(posX + SuperNewHeroSimulator.shiftX);
        characterDetails.setLayoutY(posY + SuperNewHeroSimulator.shiftY);
        characterDetails.setWrapText(true);
        characterDetails.setText(this.getText());
        
        this.character.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                SuperNewHeroSimulator.characterLabels.getChildren().clear();
                SuperNewHeroSimulator.characterLabels.getChildren().add(characterDetails);
            }
        });
    }
    
    private String getText() {
        String text = "Name: " + this.name + "\nFamilyTown: ";
        return text;
    }
    
    public void displayCharacterInfo() {
        Label characterInfo = this.getCharacterInfo();
         
        character.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Stage stage = new Stage();
                stage.setHeight(200);
                stage.setWidth(300);
                stage.setTitle("Character details");

                Group detailsRoot = new Group();
                Scene scene = new Scene(detailsRoot, 300, 200);
                stage.setScene(scene);

                detailsRoot.getChildren().add(characterInfo);

//                Button stop = new Button();
//                stop.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    public void handle(MouseEvent buttonEvent) {
//                        //something
//                    }
//                });

//                detailsRoot.getChildren().add(stop);

                stage.show();
//                SuperNewHeroSimulator.characterLabels.getChildren().clear();
//                SuperNewHeroSimulator.characterLabels.getChildren().add(characterInfo);

            }
        });
       }
    
    public Label getCharacterInfo() {
        Label details = new Label();
        details.setWrapText(true);
        details.setText("name:" + this.name + "\n" //+
                        //"family town: " + ()
        
                        );
        return details;
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the locationX
     */
    public int getLocationX() {
        return locationX;
    }

    /**
     * @param locationX the locationX to set
     */
    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public void increaseX(int change) {
        this.locationX += change;
    }
    
    /**
     * @return the locationY
     */
    public int getLocationY() {
        return locationY;
    }

    /**
     * @param locationY the locationY to set
     */
    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
    
    /**
     * @return the familyTown
     */
    public Intersection getFamilyTown() {
        return familyTown;
    }

    /**
     * @param familyTown the familyTown to set
     */
    public void setFamilyTown(Intersection familyTown) {
        this.familyTown = familyTown;
    }
    
    public Rectangle drawHuman() {
        Rectangle human = new Rectangle(this.locationX, this.locationY, 10, 10);
        human.setFill(Color.web("blue"));
        return human;
    }
    
    public void setCharacterRectangle() {
        this.character = new Rectangle(this.getFamilyTown().getX() - this.bound, this.getFamilyTown().getY() - this.bound, 10, 10);
        this.character.setFill(Color.BLUE);
    }
    
    public Rectangle getCharacterRectangle() {
        return this.character;
    }
    
    /**
     * @return the currentPosition
     */
    public Planet getCurrentPosition() {
        return currentPosition;
    }

    /**
     * @param currentPosition the currentPosition to set
     */
    public void setCurrentPosition(Planet currentPosition) {
        this.currentPosition = currentPosition;
    }
}