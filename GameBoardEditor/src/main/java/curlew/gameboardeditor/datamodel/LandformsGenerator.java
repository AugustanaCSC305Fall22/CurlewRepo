package curlew.gameboardeditor.datamodel;

public abstract class LandformsGenerator {
	
	private TerrainMap mapOnBuild;
	
	public LandformsGenerator(TerrainMap map) {
		mapOnBuild = map;
//		build(row, column, defaultScale);
	}
	
	

	public TerrainMap getMapOnBuild() {
		return mapOnBuild;
	}


	
	public abstract void build(int row,int column, int scale);
	
	@Override
	public abstract String toString();
	

}
