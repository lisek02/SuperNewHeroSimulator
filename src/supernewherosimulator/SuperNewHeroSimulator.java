/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import static java.lang.Math.sqrt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Lisek
 */
public class SuperNewHeroSimulator extends Application {
    public static int numOfInters = 19, numOfTowns = 11, numofIntersections = numOfInters - numOfTowns;
    int i;
    public static Intersection[] inter = new Intersection[numOfInters];
    public static Planet[] planet = new Planet[numOfTowns];
    public static ArrayList<Villain> villains = new ArrayList<>();
    
    public static Group root;    
    public static Group characters;
    public static Group paths;
    public static Group detailsLabels;
    public static Group characterLabels;
    public static Group roads;
    public static Group releaseButton;
    public static Group planetsLabels;
    public static Group superHeroLabels;
    public static Scene scene;
    
    public static int shiftX = -40;
    public static int shiftY = -10;
    
    public static int maxNumOfCivil = 1000;
    //minimal and maximal number of civilians in a town
    int minPopul = (int) (maxNumOfCivil * 0.3);
    int maxPopul = maxNumOfCivil;
    
    public static ArrayList<Integer> powerSourceTypes;
       
    @Override
    public void start(Stage primaryStage) {
      
        //StackPane root = new StackPane();
        root = new Group();
        root.setId("root");
       
        scene = new Scene(root, 1200, 730);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
        primaryStage.setTitle("Super New Hero Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //showing control labels
        Group detailsLabels = new Group();
        root.getChildren().add(detailsLabels);
        
        Label planetLabel = new Label("Planet details:");
        planetLabel.setLayoutX(scene.getWidth() - 150 + shiftX);
        planetLabel.setLayoutY(20 + shiftY);

        Label civilianLabel = new Label("Civilian details:");
        civilianLabel.setLayoutX(scene.getWidth() - 150 + shiftX);
        civilianLabel.setLayoutY(220 + shiftY);
        
        Label villainLabel = new Label("Cylon details:");
        villainLabel.setLayoutX(scene.getWidth() - 150 + shiftX);
        villainLabel.setLayoutY(370 + shiftY);
      
        Label SuperHeroLabel = new Label("Colonial Fleet Hero details:");
        SuperHeroLabel.setLayoutX(scene.getWidth() - 150 + shiftX);
        SuperHeroLabel.setLayoutY(550 + shiftY);
        
        detailsLabels.getChildren().addAll(planetLabel, civilianLabel, villainLabel, SuperHeroLabel);        

        //generating towns and intersections
        generatePlanet(planet);
        generateIntersection(inter);

        //drawing towns and intersections
        Group planets = new Group();
        Node[] planetNode = new Node[numOfInters];

        planetsLabels = new Group();
        planets.getChildren().add(planetsLabels);
        
        releaseButton = new Group();
        root.getChildren().add(releaseButton);
        
        roads = new Group();
        root.getChildren().add(roads);
        
        for(i=0; i<numOfInters; i++) {
            inter[i].setInterRectangle(i, numOfTowns);
            planetNode[i] = inter[i].getInterRectangle();
            planets.getChildren().add(planetNode[i]);
            
            planetNode[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    Intersection thisInter = null;
                    Rectangle rec = (Rectangle)event.getTarget();
                    for(i=0; i<numOfInters; i++) {
                        if((inter[i].getX() == rec.getX()+2) && (inter[i].getY() == rec.getY()+2)) {
                            thisInter = inter[i];
                        }
                    }
                    //planetsLabels.getChildren().clear();
                    releaseButton.getChildren().clear();
                    //planetsLabels.getChildren().add(thisInter.showIntersectionDetails());
                    thisInter.showIntersectionDetails();
                    thisInter.print();           
                }            
            });        
        }
             
        root.getChildren().add(planets);
     
        //generating a character group
        characters = new Group();
        root.getChildren().add(characters);
        
        characterLabels = new Group();
        characters.getChildren().add(characterLabels);
        
        superHeroLabels = new Group();
        characters.getChildren().add(superHeroLabels);
        
        //seting up a path
        paths = new Group();
        root.getLocalToSceneTransform();
        //paths.setAutoSizeChildren(true);
        root.getChildren().add(paths);
        paths.getLocalToSceneTransform();
        

        //characters label group


        
        int releaseTheCivil = 2000;
        int releaseTheVillain = 2000;
        
        Timer timerCivil = new Timer();
        timerCivil.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
                       int townToReleaseFrom = randInt(0, numOfTowns-1);
                       //System.out.println(townToReleaseFrom);
                       Civilian cywil = new Civilian("cywil" + randInt(1, 1000), i, inter[townToReleaseFrom].getX(), inter[townToReleaseFrom].getY(), inter[townToReleaseFrom]);  
                       Thread thread = new Thread(cywil); 
                       Platform.runLater(thread);
                        //thread.start();
            }       
        }, 0, releaseTheCivil);
        
        Timer timerVillain = new Timer();
        timerVillain = new Timer();
        timerVillain.scheduleAtFixedRate(new TimerTask() {
            
            @Override   
            public void run() {
                        Place placeToSpawn = new Place(0,0);
                        placeToSpawn.randomEdge();
                        Villain vill = new Villain("villain" + randInt(1, 1000), randInt(1, 1000), placeToSpawn.getX(), placeToSpawn.getY(), new Intersection(placeToSpawn.getX(), placeToSpawn.getY()));
                        Thread threadV = new Thread(vill);
                        threadV.setName("Villain");
                        Platform.runLater(threadV);
                        //threadV.start();
            }
        }, 0, releaseTheVillain);
        
        for(Intersection intersection : inter) {
            ArrayList<Intersection> interA = intersection.getNeighboursList();
            for(Intersection neigh : interA) {
                intersection.drawRoad(neigh);
            }
        }
        //showLabel(30, 30, planet[1]);


        
        
        //TESTY
//        ArrayList<Intersection> testowa_tablica = new ArrayList<>();
//        testowa_tablica.add(inter[2]);
//        testowa_tablica.add(inter[5]);
//        testowa_tablica.add(inter[6]);
//        testowa_tablica.add(inter[14]);
//        testowa_tablica.add(inter[17]);
//        System.out.println("testowe skrzyżowanie");
//        find_lowest_f(testowa_tablica, inter[12]).printIntersection();
        
//        System.out.println("szukanie drogi:");
//        ArrayList<Intersection> pathArray = new ArrayList<>();
//        pathArray = findPath(inter[1], inter[11]);
//        for(i=0; i < pathArray.size(); i++) {
//            pathArray.get(i).printIntersection();
//        }
//        
//        System.out.println("heuristic:");
//        System.out.println(heuristic_cost_estimate(inter[3], inter[10]));
        
        //creating civilians
//        for(i=0; i<10; i++) {
//            Civilian cywil = new Civilian("cywil", i, inter[i].getIntersectionX(), inter[i].getIntersectionY(), inter[i]);       
//            Thread thread = new Thread(cywil);
//            thread.run();
//        }


               
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
//    public static void checkCollision(Node character) {
//        boolean collision = false;
//        for(Intersection inter : SuperNewHeroSimulator.inter) {
//            //Shape intersect = Shape.intersect(character, inter.getInterRectangle());
//            if(character.getBoundsInParent().intersects(inter.getInterRectangle().getBoundsInParent())) {
//                collision = true;
//            }
//        }
//        if(collision) {
//            character.Color.YELLOW);
//        }
//    }
//    
    public static void showLabel(int x, int y, Object object) {
        Task dynamicTimeTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                while (true) {
                    if(object.getClass() == Planet.class) {
                        Planet toUpdate = (Planet) object;
                        updateMessage("Name: " + toUpdate.getName() + "\n" +
                                    "Capital: " + toUpdate.isCapital() + "\n" +
                                    "Coordinates: (" + toUpdate.getX() + " " + toUpdate.getY() + ")\n" +                                
                                    "Population: " + toUpdate.getPopulation() + "\n" +
                                    "Power source: " + toUpdate.getPowerSource().getPotential() + "\n" +
                                    "Occupied: " + toUpdate.isOccupied());
                    } else if (object.getClass() == Villain.class) {
                        Villain toUpdate = (Villain) object;
                        updateMessage("Name: " + toUpdate.getName() +
                                        "\nId: " + toUpdate.toString() +
                                        //"\nFamilyTown: " + familyTown.getName() +
                                        "\nHP: " + toUpdate.HP +
                                        "\nintelligence: " + toUpdate.intelligence +
                                        "\nstrength: " + toUpdate.strength +
                                        "\nspeed: " + toUpdate.speed +
                                        "\nendurance: " + toUpdate.endurance +
                                        "\nenergy: " + toUpdate.enegry +
                                        "\nskill: " + toUpdate.skillId);
                    } else if (object.getClass() == SuperHero.class) {
                        SuperHero toUpdate = (SuperHero) object;
                        updateMessage("Name: " + toUpdate.getName() +
                                        "\nId: " + toUpdate.toString() +
                                        //"\nFamilyTown: " + familyTown.getName() +
                                        "\nHP: " + toUpdate.HP +
                                        "\nintelligence: " + toUpdate.intelligence +
                                        "\nstrength: " + toUpdate.strength +
                                        "\nspeed: " + toUpdate.speed +
                                        "\nendurance: " + toUpdate.endurance +
                                        "\nenergy: " + toUpdate.enegry +
                                        "\nskill: " + toUpdate.skillId);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
                return null;
            }
        };
        Label dynamicTimeDisplayLabel2 = new Label();
        dynamicTimeDisplayLabel2.textProperty().bind(dynamicTimeTask.messageProperty());
        dynamicTimeDisplayLabel2.setLayoutX(x + shiftX);
        dynamicTimeDisplayLabel2.setLayoutY(y + shiftY);
        if(object.getClass() == Planet.class) {
            planetsLabels.getChildren().clear();
            planetsLabels.getChildren().add(dynamicTimeDisplayLabel2);
        } else if(object.getClass() == Villain.class) {
            characterLabels.getChildren().clear();
            characterLabels.getChildren().add(dynamicTimeDisplayLabel2);
        } else if (object.getClass() == SuperHero.class) {
            superHeroLabels.getChildren().clear();
            superHeroLabels.getChildren().add(dynamicTimeDisplayLabel2); 
        }
        Thread t2 = new Thread(dynamicTimeTask);
        t2.setName("Tesk Time Updater");
        t2.setDaemon(true);
        t2.start();   
    }
    
    private void generatePlanet(Planet[] planet) {    

//    public enum planetNames {
//        Aquarion, Aerilol, Canceron, Gemenon, Caprica, Leonis, Libran, Picon, Sagittaron, Tauron, Virgon
//    }
        generateTypes(6, numOfTowns);
        
        planet[0] = new Planet(500,50,new Semaphore(1, false),"Aquarion", randInt(minPopul, maxPopul));
        planet[1] = new Planet(275,150,new Semaphore(1, false),"Leonis", randInt(minPopul, maxPopul));
        planet[2] = new Planet(725,150,new Semaphore(1, false),"Picon", randInt(minPopul, maxPopul));
        planet[3] = new Planet(50,250,new Semaphore(1, false),"Gemenon", randInt(minPopul, maxPopul));
        planet[4] = new Planet(950,250,new Semaphore(1, false),"Virgon", randInt(minPopul, maxPopul));
        planet[5] = new Planet(500,350,new Semaphore(1, false),"Caprica", randInt(minPopul, maxPopul));
        planet[5].setCapital(true);
        planet[6] = new Planet(50,450,new Semaphore(1, false),"Aerilon", randInt(minPopul, maxPopul));
        planet[7] = new Planet(950,450,new Semaphore(1, false),"Libran", randInt(minPopul, maxPopul));
        planet[8] = new Planet(275,550,new Semaphore(1, false),"Canceron", randInt(minPopul, maxPopul));
        planet[9] = new Planet(725,550,new Semaphore(1, false),"Tauron", randInt(minPopul, maxPopul));
        planet[10] = new Planet(500,650,new Semaphore(1, false),"Sagittarion", randInt(minPopul, maxPopul));
    }
    
    private void generateIntersection(Intersection[] inter) {
        /**
         * intersections declaration:
         * 0-10 city intersection
         * 11-18 normal intersection
         */  

        for(i=0; i<numOfTowns; i++) {
            inter[i] = planet[i];
        }        
        inter[11] = new Intersection(500, 150);
        inter[12] = new Intersection(275, 250);
        inter[13] = new Intersection(500, 250);
        inter[14] = new Intersection(725, 250);
        inter[15] = new Intersection(275, 450);
        inter[16] = new Intersection(500, 450);
        inter[17] = new Intersection(725, 450);
        inter[18] = new Intersection(500, 550);
        
        //neighbours
        inter[0].addNeighbours(inter[11]);
        inter[1].addNeighbours(inter[11], inter[12]);
        inter[2].addNeighbours(inter[11], inter[14]);
        inter[3].addNeighbours(inter[12]);
        inter[4].addNeighbours(inter[13]);
        inter[5].addNeighbours(inter[13], inter[16]);
        inter[6].addNeighbours(inter[16]);
        inter[7].addNeighbours(inter[17]);
        inter[8].addNeighbours(inter[15], inter[18]);
        inter[9].addNeighbours(inter[17], inter[18]);
        inter[10].addNeighbours(inter[18]);
        inter[11].addNeighbours(inter[0], inter[1], inter[2], inter[13]);
        inter[12].addNeighbours(inter[1], inter[3], inter[13], inter[15]);
        inter[13].addNeighbours(inter[5], inter[11], inter[12], inter[14]);
        inter[14].addNeighbours(inter[2], inter[4], inter[13], inter[17]);
        inter[15].addNeighbours(inter[6], inter[8], inter[12], inter[16]);
        inter[16].addNeighbours(inter[5], inter[15], inter[17], inter[18]);
        inter[17].addNeighbours(inter[7], inter[9], inter[14], inter[16]);
        inter[18].addNeighbours(inter[8], inter[9], inter[10], inter[16]);    
    }
    
    public void generateTypes(int uniq, int all) {
        powerSourceTypes = new ArrayList<>();
        for(i=0; i<uniq; i++) {
            powerSourceTypes.add(i);
        }
        for(i=uniq; i<all; i++) {
            powerSourceTypes.add(randInt(0, uniq - 1));
        }
        Collections.shuffle(powerSourceTypes);
        System.out.println(powerSourceTypes);
    }
     
    public static ArrayList<Intersection> findPath(Intersection start, Intersection end) {
        ArrayList<Intersection> closedset = new ArrayList<>();  //set of noder already evaluated
        ArrayList<Intersection> openset = new ArrayList<>();    // set of tentative nodes to be evaluated
        openset.add(start);     //initially containing start node
        //ArrayList<Intersection> came_from = new ArrayList<>();    // set of tentative nodes to be evaluated
        HashMap<Intersection, Intersection> came_from = new HashMap<>();
        
        HashMap<Intersection, Double> g_score = new HashMap<>();
        HashMap<Intersection, Double> f_score = new HashMap<>();
        
        g_score.put(start, 0.0);    //cost form start along best known path
        //Estimated total cost from start to goal through y.
        f_score.put(start, g_score.get(start) + heuristic_cost_estimate(start, end));

        while(!openset.isEmpty()) {
            Intersection current = find_lowest_f(openset, end);
            if (current == end) {
                //closedset.add(end);
                break;
            }
            else {
                openset.remove(current);
                closedset.add(current);
                ArrayList<Intersection> interNeighbours = new ArrayList<>();
                interNeighbours = current.getNeighboursList();
                for(int i=0; i<interNeighbours.size(); i++) {
                    Intersection neighbour = interNeighbours.get(i);
                    if(closedset.contains(neighbour)) { continue; }
                    double tentative_g_score = g_score.getOrDefault(current, 0.0) + heuristic_cost_estimate(current, neighbour);    ///heuristic cost estimate(?)

                    if((!openset.contains(neighbour)) || (tentative_g_score < g_score.getOrDefault(neighbour, 0.0))) {
                        came_from.put(neighbour, current);
                        //came_from.replace(neighbour, current);
                        g_score.replace(neighbour, tentative_g_score);
                        double cost = heuristic_cost_estimate(neighbour, end) + g_score.getOrDefault(neighbour, 0.0);
                        f_score.replace(neighbour, cost);
                        if(!openset.contains(neighbour)) {
                            openset.add(neighbour);
                        }
                    }
                }
            }
        }
        //return closedset;
        return reconstructPath(came_from, end, start);
    }
    
    /**
     * finds intersection with lowest heuristic cost to travel to the end
     * @param openset set of intersections to find in
     * @param end final intersection
     * @return 
     */
    public static Intersection find_lowest_f(ArrayList<Intersection> openset, Intersection end) {
        double lowest_cost = heuristic_cost_estimate(openset.get(0), end);
        Intersection lowest = openset.get(0);
        
        for(int i=0; i<openset.size(); i++) {
            double possible_lowest = heuristic_cost_estimate(openset.get(i), end);
            if (possible_lowest  < lowest_cost) {
                lowest = openset.get(i);
                lowest_cost = possible_lowest;
            }
        }
        
        return lowest;
    }
    
    /**
     * calculate heuristic distance between starting and ending intersection (straight-line distance)
     * @param start
     * @param end
     * @return heuristic distance between starting and ending intersection
     */
    private static double heuristic_cost_estimate(Intersection start, Intersection end) {
        double distance = sqrt(Math.pow((start.getX() - end.getX()),2.0) + Math.pow((start.getY() - end.getY()),2.0));    
        return distance;
    }
    
    /**
     * reconstruct path between two towns based on map of navigated intersections
     * @param came_from array of navigated intersections
     * @param current final intersection
     * @param start first intersection
     * @return path between two towns
     */
    private static ArrayList<Intersection> reconstructPath(HashMap came_from, Intersection current, Intersection start) {
        ArrayList<Intersection> total_path = new ArrayList<>();
        
        total_path.add(current);
        while((came_from.containsKey(current)) && !(total_path.contains(came_from.get(current)))) {
            current = (Intersection) came_from.get(current);
            total_path.add(current);
        }
        //total_path.add(start);
        Collections.reverse(total_path);
        return total_path;
    }
    
    /**
     * Random value
     * @param min minimum value
     * @param max maximum value
     * @return random integer in a range <min;max>
     */
    public static int randInt(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
