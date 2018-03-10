// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ROVU_Class_Diagram.Rovu_system;

import java.awt.Color;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import ROVU_Class_Diagram.Rovu_system.Robot;
import simbad.sim.Arch;
import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;


/************************************************************/
/**
 * 
 */
public class Environment extends EnvironmentDescription {
	
	private final Vector3d[] STARTING_COORDINATES = {new Vector3d(9.5, 0, -9.5), new Vector3d(-9.5, 0, -9.5), new Vector3d(-9.5, 0, 9.5), new Vector3d(9.5, 0, 9.5)};
	private final Vector3d[] BOX_COORDINATES = {new Vector3d(0, 0, 8.5), new Vector3d(0, 0, -8.5), new Vector3d(-9, 0, 0), new Vector3d(9, 0, 0)};
	private final Vector3f BOX_SIZE = new Vector3f(2, 1, 3);
	private final Vector3d[] ARCH_COORDINATES = {new Vector3d(-7.6, 0, 0), new Vector3d(-7.6, 0, 1), new Vector3d(7.6, 0, 0), new Vector3d(7.6, 0, 1)};
	private final double STARTING_ROTATION[] = {Math.PI, Math.PI / 2 * 3, 0.0, Math.PI / 2}; 
	private final int MAPSIZE = 20;
	
	private ObstacleFactory fac;
	public CentralStation station;
	public Robot robot[];
	
	
	public Environment(int arches, int boxes, int robots) {
		robot = new Robot[robots];
		fac = new ObstacleFactory();
		this.station = CentralStation.getInstance();
		this.station.init(MAPSIZE);
		//this.station = new CentralStation(MAPSIZE);
		
		this.light1IsOn = true;
		this.light2IsOn = true;
        
        // enable the physics engine in order to have better physics effects on the objects
		this.setUsePhysics(true);
        // show the axes so that we know where things are
		this.showAxis(true);
        //It seems that 40 x 30 is not possible as stated in the project assumptions. Also giving a higher number than 20 makes the frame not 100% visible for some reason.
		this.setWorldSize(MAPSIZE);
		
		//Small change here, we decided to always have outer walls. Only number boxes and arches can be choosen.
        Wall w1 = fac.getWall(new Vector3d(-10, 0, 0), 20, 2, this);
        w1.setColor(new Color3f(Color.BLUE));
        w1.rotate90(1);
        this.add(w1);
        
        Wall w2 = fac.getWall(new Vector3d(10, 0, 0), 20, 2, this);
        w2.setColor(new Color3f(Color.GREEN));
        w2.rotate90(1);
        this.add(w2);
        
        Wall w3 = fac.getWall(new Vector3d(0, 0, 10), 20, 2, this);
        w3.setColor(new Color3f(Color.RED));
        this.add(w3);
        
        Wall w4 = fac.getWall(new Vector3d(0, 0, -10), 20, 2, this);
        w4.setColor(new Color3f(Color.YELLOW));
        this.add(w4);
        
        for(int i = 0; i < boxes; i++) {
        	add(fac.getBox(BOX_COORDINATES[i], BOX_SIZE, this));
        }
        
        //Arches will be added later if possible
//        for(int i = 0; i < arches * 2; i++) {
//        	add(fac.getArch(ARCH_COORDINATES[i], this));
//        }
        
        for (int i = 0; i < robot.length; i++) {
			robot[i] = new Robot(STARTING_COORDINATES[i], "" + i);
			robot[i].setDirection(i);
			this.add(robot[i]);
			robot[i].setStation(station);
			System.out.println("Robot coordinates: " + STARTING_COORDINATES[i] + " Facing: " + robot[i].getDirection());
		}  
	}
	
	public void rotateForStartingPosition() {
		for (int i = 0; i < robot.length; i++) {
			robot[i].rotateY(STARTING_ROTATION[i]);
		} 
	}
};
