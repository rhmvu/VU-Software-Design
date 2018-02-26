package main.java.softdesign;

import java.text.DecimalFormat;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.jogamp.nativewindow.util.Dimension;
import com.sun.prism.Graphics;
import com.sun.prism.paint.Color;

import javafx.scene.Camera;
import simbad.demo.ImagerDemo;
import simbad.sim.Agent;
import simbad.sim.CameraSensor;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;
import simbad.sim.SensorMatrix;

public class Robot extends Agent {

	private String currentMode;
	private RangeSensorBelt sonars, bumpers;
	private CameraSensor camera;
	private double elapsed;
	//SensorMatrix luminanceMatrix = this.camera.createCompatibleSensorMatrix();
    
    public Robot(Vector3d position, String name) {
        super(position, name);        
       bumpers = RobotFactory.addBumperBeltSensor(this, 12);
       sonars = RobotFactory.addSonarBeltSensor(this,12); 
       camera = RobotFactory.addCameraSensor(this);
    }

    /** This method is called by the simulator engine on reset. */
    public void initBehavior() {
        System.out.println("I exist and my name is " + this.name);
        //this.elapsed = getLifeTime();
    }
    
    private Vector3d getCurrentCoordinate() {
    	Point3d temp = new Point3d();
    	this.getCoords(temp);
    	return new Vector3d(temp.x, temp.z, temp.y); 
    }
    
    private void roundCoordinates(Vector3d coordinate) {
    	//round the coordinates to nearest 0.05
    	coordinate.x = Math.round(coordinate.x * 20.0) / 20.0;
    	coordinate.y = Math.round(coordinate.y * 20.0) / 20.0;
    	coordinate.z = 0; //excluding robot's height to keep the mapping 2d.
    }
    
    private void moveDeterministic() {
    	if (sonars.oneHasHit()) 
		{
            // reads the three front quadrants
            double left = sonars.getFrontLeftQuadrantMeasurement();
            double right = sonars.getFrontRightQuadrantMeasurement();
            double front = sonars.getFrontQuadrantMeasurement();
            // if obstacle near
            if ((front  < 0.7)||(left  < 0.7)||(right  < 0.7)) { 
            
                if (left < right) {
                    setRotationalVelocity(-1);
                }
                else {
                    setRotationalVelocity(1);
                }
                setTranslationalVelocity(0);
            } 
            else
            {
                setRotationalVelocity(0);
                setTranslationalVelocity(0.5);
            }
		}
    }
    
    private void moveRandom() {
    	if(this.collisionDetected()) {
    		this.currentMode = "avoidObstacle";
    	} else {
    		this.currentMode = "goAround";
    	}
        
    	if(this.currentMode == "goAround") {
    		// the robot's speed is always 0.5 m/s
            this.setTranslationalVelocity(0.5);            
            setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));
        } else {
        	// don't move
        	this.setTranslationalVelocity(0);
        	// rotate only until obstacle is not there
        	setRotationalVelocity(Math.PI / 2);
        }
    }
    
    public boolean timeIsRight() {
    	// do an activity in every 10th virtual second
    	return this.getCounter() % 10 ==0;
    }

    /** This method is call cyclically (20 times per second) by the simulator engine. */
    public void performBehavior() {
    	
//    	if (getLifeTime() - this.elapsed > 1.0D)
//        {
//          this.elapsed = getLifeTime();
//          this.camera.copyVisionImage(this.luminanceMatrix);
//        }
    	
    	// move random in every 10th virtual second
    	 if(timeIsRight()) {
    		 moveRandom();
    		 
    		 Vector3d currentCoordinate = getCurrentCoordinate();
    		 roundCoordinates(currentCoordinate);
    		 System.out.println(currentCoordinate.toString());
    		 
    	 } else {
    		 moveDeterministic();
    	 }
    }
}