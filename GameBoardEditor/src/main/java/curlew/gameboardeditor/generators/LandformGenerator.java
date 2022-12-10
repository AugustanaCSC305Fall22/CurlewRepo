package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;

/**
 * 
 * @author Team Curlew
 * This abstract class is responsible for making landforms on a given terrain map.
 *
 */
public abstract class LandformGenerator {
	
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
