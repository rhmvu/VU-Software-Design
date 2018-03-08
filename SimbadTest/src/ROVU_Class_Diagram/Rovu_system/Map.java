// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package ROVU_Class_Diagram.Rovu_system;

import ROVU_Class_Diagram.Rovu_system.GridPoint;

import ROVU_Class_Diagram.Rovu_system.GridPointStatus;
import javax.vecmath.Vector3d;


public class Map {
	
	public final GridPoint[][] grid;
	private double coveredPercentage;
	
	public Map(int mapSize) {
		double startingPosX = -10.0;
		double startingPosZ = 10.0;
		
		grid = new GridPoint[mapSize][mapSize];
		
		for(int x = 0; x < mapSize; x++) {
			for(int z = 0; z < mapSize; z++) {
				grid[x][z] = new GridPoint(new Vector3d(startingPosX + GridPoint.RANGE + x, 0, startingPosZ - GridPoint.RANGE - z), GridPointStatus.UNKNOWN);
				
			}
		}
	}

	public void setGridPointStatus(GridPointStatus status, int x, int z) {
		grid[x][z].setStatus(status);
		calculateCoveredPercentage();
	}

	public Double getCoveredPercentage() {
		return coveredPercentage;
	}

	/**
	 * 
	 */
	private void calculateCoveredPercentage() {
		double numberOfGridPoints = grid.length * grid.length;
		double coveredGridPoints = 0;
		double objectGridPoints = 0;
		
		for(int x = 0; x < grid.length; x++) {
			for(int z = 0; z < grid.length; z++) {
				switch (grid[x][z].gridpointstatus) {
					case COVERED:
						coveredGridPoints++;
					case OBSTACLE:
						objectGridPoints++;		
					default: 
						
				}
			}
		}
		coveredPercentage = (objectGridPoints + coveredGridPoints) / numberOfGridPoints * 100;
	}
};
