package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;
/**
 * 
 * @author Team Curlew
 * This class generates a gate to hell on a Map
 *
 */
public class GateToHellLandformGenerator extends LandformGenerator {

	@Override
	public void build(TerrainMap map, int row, int column, int scale) {
		if (scale != 0) {
			for (int i = row; i < row + 2; i++) {
				for (int j = column; j < column + 2; j++) {
					map.setHeightAt(i, j, 0);
				}
			}
		} else {
			for (int i = row; i < row + 2; i++) {
				for (int j = column; j < column + 2; j++) {
					map.setHeightAt(i, j, 2);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Gate To Hell";
	}

}