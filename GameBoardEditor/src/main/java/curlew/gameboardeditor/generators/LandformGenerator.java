package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;

public abstract class LandformGenerator {
	
	public abstract void build(TerrainMap map, int row,int column, int scale);
	
	public String toString() {
		return getName();
	};

	public abstract String getName();
}
