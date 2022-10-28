package curlew.gameboardeditor.datamodel;

public class Volcano extends Mountains{

	public Volcano(TerrainMap map, int row, int column) {
		super(map, row, column);
	}
	
	
	public void build(int scale) {
		super.build(scale);
		TerrainMap map = super.getMapOnBuild();
		int row = getRow();
		int column = getColumn();
		if (scale == 0) {
			map.build(row, column, map.getInitialDepth());
		} else {
			map.build(row, column, 1);
		}
		}

}
