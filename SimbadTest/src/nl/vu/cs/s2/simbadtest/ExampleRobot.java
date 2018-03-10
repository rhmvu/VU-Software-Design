package nl.vu.cs.s2.simbadtest;


import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;

public class ExampleRobot extends Agent {

	private String currentMode;
	private RangeSensorBelt sonars;

    public ExampleRobot(Vector3d position, String name) {
        super(position, name);
        
        sonars = RobotFactory.addSonarBeltSensor(this, 8);
        
        // Add bumpers
        RobotFactory.addBumperBeltSensor(this, 12);
        // Add sonars
        RobotFactory.addSonarBeltSensor(this, 4);        
    }

    /** This method is called by the simulator engine on reset. */
    public void initBehavior() {
        System.out.println("I exist and my name is " + this.name);
    }

    /** This method is call cyclically (20 times per second) by the simulator engine. */
    public void performBehavior() {
    	
    	if (getCounter()%100==0){
            // print each sonars measurement
            for (int i=0;i< sonars.getNumSensors();i++) {
                double range = sonars.getMeasurement(i); 
                double angle = sonars.getSensorAngle(i);
                boolean hit = sonars.hasHit(i);
                System.out.println("Sonar at angle "+ angle + " with number " + i + 
                " measured range ="+range+ " has hit something:"+hit+""); 
            }
            System.out.println("\n\n");
        }
    	
    	// perform the following actions every 7 virtual seconds
    	if(this.getCounter() % 7 == 0) {
    		if(sonars.getMeasurement(5) > 0.6 && sonars.getMeasurement(6) > 1 && sonars.getMeasurement(7) > 1) {
    			this.setTranslationalVelocity(0.0);
    			this.rotateY(Math.PI / 2 * 3);
    		}
    		else if(sonars.getMeasurement(0) < 0.4) {
    			this.setTranslationalVelocity(0.0);
    			this.rotateY(Math.PI/2);
    		}else {
    			this.setTranslationalVelocity(0.5);
    		}
    	}
    	
    }
}