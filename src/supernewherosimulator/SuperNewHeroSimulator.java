/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supernewherosimulator;

import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
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
import javafx.scene.Node;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Lisek
 */
public class SuperNewHeroSimulator extends Application {
    
    private Timeline timeline;
    private AnimationTimer timer;
    private Integer frame = 0;

    public static int numOfInters = 19, numOfTowns = 11, numofIntersections = numOfInters - numOfTowns;
    int i;
    public static Intersection[] inter = new Intersection[numOfInters];
    
    public static Group characters;
    public static Group paths;
    
    @Override
    public void start(Stage primaryStage) {
      
        StackPane root = new StackPane();
        root.setId("root");
       
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        
        primaryStage.setTitle("Super New Hero Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

        //generating intersections
        generateIntersection(inter);

        //creating a road out of intersections
        Road droga = new Road();
        for(i=0; i<numOfInters; i++) {
            droga.addIntersection(inter[i]);   
        }
        //droga.getRoad(); 
        
        //drawing towns and intersections
        Group towns = new Group();
        for(i=0; i<numOfInters; i++) {
            towns.getChildren().add(inter[i].drawIntersection(i, numOfTowns)); 
        }
        root.getChildren().add(towns);
        //towns.setId("towns");
        //towns.setOpacity(0.5);
        
        //creating a character
        for(i=0; i<10; i++) {
            
        }
        //Hero stillNoob = new Hero(23, 10, 10, 2);
//        Thread hero = new Thread(stillNoob);
        //hero.start();
        
        //for(i=0; i<numOfInters; i++) inter[i].printIntersection();
        
        
        //drawing a character
        characters = new Group();
        //Node character = stillNoob.drawHuman();
        //characters.getChildren().add(character);
        root.getChildren().add(characters);
        
        //seting up a path
        paths = new Group();
        paths.setAutoSizeChildren(true);
        root.getChildren().add(paths);
        
//        final Path heroPath = new Path();
//        heroPath.getElements().add(new MoveTo(stillNoob.getLocationX(), stillNoob.getLocationY()));
//        for(i=0; i<numOfInters; i++) {
//            heroPath.getElements().add(new LineTo(inter[i].getIntersectionX(), inter[i].getIntersectionY()));
//        }

        //heroPath.getElements().add(new CubicCurveTo(100,100, 250,300,200,123));
        
//        paths.getChildren().add(heroPath);
//        paths.getChildren().add(character);
//        final PathTransition heroPathTransition = new PathTransition();
        
        
//        heroPathTransition.setDuration(Duration.seconds(15.0));
//        //heroPathTransition.setDelay(Duration.seconds(0.5));
//        heroPathTransition.setPath(heroPath);
//        heroPathTransition.setNode(character);
//        heroPathTransition.setCycleCount(Timeline.INDEFINITE);
//        heroPathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
//        heroPathTransition.setAutoReverse(true);
//        heroPathTransition.getInterpolator();
//        heroPathTransition.play();
        
        //animation
        /*
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.setAutoReverse(true);
        
        
        
        final Text timerText = new Text(10, 10, frame.toString());
        towns.getChildren().add(timerText);     //Poprawić
        timerText.toFront();
        int animationStartX = 5;
        int animationStartY = 5;
        
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timerText.setText(frame.toString());
                frame++;
                character.setTranslateX(now * 0.00005);
            }
        };
          
        timeline.play();
        timer.start();*/
        
        
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
        
        for(i=0; i<10; i++) {
            Civilian cywil = new Civilian("cywil", i, inter[i].getIntersectionX(), inter[i].getIntersectionY(), inter[i]);       
            Thread thread = new Thread(cywil);
            thread.run();
        }


               
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void generateIntersection(Intersection[] inter) {
        /**
         * intersections declaration:
         * 0-10 city intersection
         * 11-18 normal intersection
         */  

        for(i=0; i<numOfInters; i++) {
            inter[i] = new Intersection();
        }
        inter[0].setIntersection(500, 50);
        inter[1].setIntersection(275, 150);
        inter[2].setIntersection(725, 150);
        inter[3].setIntersection(50, 250);
        inter[4].setIntersection(950, 250);
        inter[5].setIntersection(500, 350);
        inter[6].setIntersection(50, 450);
        inter[7].setIntersection(950, 450);
        inter[8].setIntersection(275, 550);
        inter[9].setIntersection(725, 550);
        inter[10].setIntersection(500, 650);
        inter[11].setIntersection(500, 150);
        inter[12].setIntersection(275, 250);
        inter[13].setIntersection(500, 250);
        inter[14].setIntersection(725, 250);
        inter[15].setIntersection(275, 450);
        inter[16].setIntersection(500, 450);
        inter[17].setIntersection(725, 450);
        inter[18].setIntersection(500, 550);
        
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
    private static Intersection find_lowest_f(ArrayList<Intersection> openset, Intersection end) {
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
        double distance = sqrt(Math.pow((start.getIntersectionX() - end.getIntersectionX()),2.0) + Math.pow((start.getIntersectionY() - end.getIntersectionY()),2.0));    
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
