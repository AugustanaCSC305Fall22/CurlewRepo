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
	
	/**
	 * Builds the landform in the given map.
	 * @param map The terrain map on which the landform is to be made
	 * @param row The top left row of the location of where it should be made
	 * @param column The top left column of the location of where it should be made
	 * @param scale The scale of the landform
	 */
	public abstract void build(TerrainMap map, int row,int column, int scale);
	
	@Override
	public String toString() {
		return getName();
	};
	
	/**
	 * Returns the name of the landform
	 * @return The name of the landforms
	 */
	public abstract String getName();
}
