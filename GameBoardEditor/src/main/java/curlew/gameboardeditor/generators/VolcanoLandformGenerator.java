package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;

/**
 * 
 * @author Team Curlew
 * This class is responsible for making Volcanos.
 *
 */
public class VolcanoLandformGenerator extends MountainLandformGenerator{

	@Override
	public void build(TerrainMap map, int row, int column, int scale) {
		super.build(map, row, column, scale);
		if (scale == 0) {
			map.setHeightAt(row, column, map.getInitialDepth());
		} else {
			map.setHeightAt(row, column, 1);
		}
	}
	
	@Override
	public String getName() {
		return "Volcano";
	}
}
