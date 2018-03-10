// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ROVU_Class_Diagram.Rovu_system;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3d;

import ROVU_Class_Diagram.Rovu_system.Map;
import ROVU_Class_Diagram.Rovu_system.Observer;
import ROVU_Class_Diagram.Rovu_system.Robot;
import ROVU_Class_Diagram.Rovu_system.Task;

/************************************************************/
/**
 * 
 */
public class CentralStation extends Subject {

	static private final CentralStation INSTANCE = new CentralStation();
	public Map map;
	public Task task;
	
	private CentralStation() {
		task = new Task(Request.sendCoordinatesWithIntervalAndWallFollowing);
	}
	
	public static CentralStation getInstance() {
		return INSTANCE;
	}
	
	public void init(int mapSize) {
		map = new Map(mapSize);
	}
	
	public void start() {
		System.out.println("Central station started");
		pushTask(task, observers); //Let the bots send their coordinates by their own counter interval for now
	}
	
	private void pushTask(Task toPush, List<Observer> ObserversToReceive) {
		for(Observer observer: ObserversToReceive) {
			observer.update(toPush);
		}
	}
	
	public void saveImage(BufferedImage image, String imageName) {
		try {
			File outputFile = new File("images/" + imageName + ".png");
			ImageIO.write(image, "png", outputFile);
		}catch(IOException e) {
			System.err.println("Problem saving " + imageName + ": " + e.getMessage());
		}
	}
	
	
	public void report(int coordinate1, int coordinate2, Observer observer, RobotDirection direction, BufferedImage image) {
		if(map.getGridPointStatus(coordinate1, coordinate2) != GridPointStatus.COVERED) {
			map.setGridPointStatus(GridPointStatus.COVERED, coordinate1,coordinate2);
			String imageName = coordinate1 + "_" + coordinate2;
			saveImage(image, imageName);
		}
				
		
		switch(direction) {
		case NORTH:
			if(coordinate1 != 0 && map.getGridPointStatus(coordinate1 - 1, coordinate2) == GridPointStatus.COVERED) {
				observer.update(new Task(Request.turnLeft));
			}
			break;
		case WEST:
			if(coordinate2 != 19 && map.getGridPointStatus(coordinate1, coordinate2 + 1) == GridPointStatus.COVERED) {
				observer.update(new Task(Request.turnLeft));
			}
			break;
		case SOUTH:
			if(coordinate1 != 19 && map.getGridPointStatus(coordinate1 + 1, coordinate2) == GridPointStatus.COVERED) {
				observer.update(new Task(Request.turnLeft));
			}
			break;
		case EAST:
			if(coordinate2 != 0 && map.getGridPointStatus(coordinate1, coordinate2 - 1) == GridPointStatus.COVERED) {
				observer.update(new Task(Request.turnLeft));
			}
			break;
		default:
			break;
			
		}
		if(map.getCoveredPercentage() > 70.0) {
			pushTask(new Task(Request.goHome), observers);
		}
	}
};