package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;

/**
 * 
 * @author Team Curlew
 * This class is responsible for creating valleys.
 *
 */
public class ValleyLandformGenerator extends LandformGenerator {
	
	@Override
	public void build(TerrainMap map, int row, int column, int scale) {

		MountainLandformGenerator mountain = new MountainLandformGenerator();
		mountain.build(map, row,column-3, scale);
		mountain.build(map, row,column+3,scale);
		if(scale==0) {
			for(int i =row-2;i<=row+2;i++) {
				map.setHeightAt(i, column, map.getInitialDepth());
			}
		}
		else {
			for(int i =row-2;i<=row+2;i++) {
				map.setHeightAt(i, column, map.getInitialDepth()/2);
			}
		}
	}

	@Override
	public String getName() {
		return "Valley";
	}
}
