package curlew.gameboardeditor.datamodel;

public class Volcano extends Mountains{

	public Volcano(TerrainMap map, int row, int column) {
		super(map, row, column);
	}
	
	
	protected void build(int row, int column, int scale) {
		super.build(row, column, scale);
		TerrainMap map = super.getMapOnBuild();
		if (scale == 0) {
			map.build(row, column, map.getInitialDepth());
		} else {
			map.build(row, column, 1);
		}
		}

}
