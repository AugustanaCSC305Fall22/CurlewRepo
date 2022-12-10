package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;

public abstract class LandformGenerator {
	/**
	 * This abstract class allows for different types of landforms which all have the parameters row, column, and scale. 
	 * The landforms do not kmpw what they are, they just know their elevations
	 * @param map - The map to draw on
	 * @param row - The row the feature is centered
	 * @param column - The column the feature is centered
	 * @param scale - The scale of the feature
	 */
	
	public abstract void build(TerrainMap map, int row,int column, int scale);
	
	public String toString() {
		return getName();
	};

	public abstract String getName();
}
