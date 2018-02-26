package main.java.softdesign;

import simbad.gui.*;
import simbad.sim.*;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * Start working on this file
 *
 * You can use as many source files and packages
 * as you like, as long as everything is below
 * main.java.softdesign
 *
 * Some resources:
 *
 * - https://github.com/jimmikaelkael/simbad/tree/master/src
 * - https://www.ibm.com/developerworks/java/library/j-robots/
 * - https://github.com/VU-SoftwareDesign/SimbadMultiRobot
 * - https://github.com/VU-SoftwareDesign/SimbadHelloWorld
 *
 */
public class Main {

    public static void main(String[] args) {
        // request antialising so that diagonal lines are not "stairy"
        System.setProperty("j3d.implicitAntialiasing", "true");
        
        // creation of the environment containing all obstacles and robots
        Environment environment = new Environment();
        
        // create two instances of the same example robot
        Robot robot1 = new Robot(new Vector3d(4, 0, 4), "Robot 1");
        Robot robot2 = new Robot(new Vector3d(-4, 0, -4), "Robot 2");
        Robot robot3 = new Robot(new Vector3d(4, 0, -4), "Robot 3");
        Robot robot4 = new Robot(new Vector3d(-4, 0, 4), "Robot 4");
        
      // add the four robots to the environment
        environment.add(robot1);
        environment.add(robot2);
        environment.add(robot3);
        environment.add(robot4);
        
        Hashtable<Vector3d, String> worldMap = environment.getCoordinatesInArea();
        
        // here we create an instance of the whole Simbad simulator and we assign the newly created environment 
        Simbad frame = new Simbad(environment, false);
        frame.update(frame.getGraphics());
        
//        todo:
//        	-add a while loop to continiously get pictures from robots and terminate if every coordinate in worldmap is marked
    }

} 