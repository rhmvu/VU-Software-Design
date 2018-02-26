package main.java.softdesign;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

public class Environment extends EnvironmentDescription {
	int worldSize;
	public Environment() {
		
		// turn on the lights
        this.light1IsOn = true;
        this.light2IsOn = true;
        this.worldSize = 20;
        // enable the physics engine in order to have better physics effects on the objects
        this.setUsePhysics(true);
        
        // show the axes so that we know where things are
        this.showAxis(true);
        
        this.setWorldSize(worldSize);
        
        Wall w1 = new Wall(new Vector3d(-5, 0, 0), 10, 2, this); //x, z, y form, length and height. Nothing for thickness
        w1.setColor(new Color3f(Color.BLUE));
        w1.rotate90(1);
        add(w1);
        
        Wall w2 = new Wall(new Vector3d(5, 0, 0), 10, 2, this);
        w2.setColor(new Color3f(Color.RED));
        w2.rotate90(1);
        add(w2);
        
        Wall w3 = new Wall(new Vector3d(0, 0, 5), 10, 2, this);
        w3.setColor(new Color3f(Color.GREEN));
        add(w3);
        
        Wall w4 = new Wall(new Vector3d(0, 0, -5), 10, 2, this);
        w4.setColor(new Color3f(Color.YELLOW));
        add(w4);

        Box box1 = new Box(new Vector3d(-3, 0, -3), new Vector3f(1, 1, 1), this);
        box1.setColor(new Color3f(Color.BLACK));
        add(box1);
        
        Box box2 = new Box(new Vector3d(-3, 0, 3), new Vector3f(1, 1, 1), this);
        box2.setColor(new Color3f(Color.ORANGE));
        add(box2);
        
    }
	
	public Hashtable<Vector3d, String> getCoordinatesInArea(){
		Hashtable<Vector3d, String> result = new Hashtable<>();
		for(double i=-worldSize; i<worldSize; i+=0.05) {
			for(double j=-worldSize; j<worldSize; j+=0.05) {
				result.put(new Vector3d(i, 0, j), "unknown");
			}
		}
		return result;
	}
}
