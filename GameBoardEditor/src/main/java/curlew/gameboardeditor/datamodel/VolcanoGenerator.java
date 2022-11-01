package curlew.gameboardeditor.datamodel;

public class VolcanoGenerator extends MountainGenerator{

	public VolcanoGenerator(TerrainMap map) {
		super(map);
	}
	
	
	public void build(int row, int column, int scale) {
		super.build(row, column, scale);
		TerrainMap map = super.getMapOnBuild();
	
		if (scale == 0) {
			map.build(row, column, map.getInitialDepth());
		} else {
			map.build(row, column, 1);
		}
		}

}
