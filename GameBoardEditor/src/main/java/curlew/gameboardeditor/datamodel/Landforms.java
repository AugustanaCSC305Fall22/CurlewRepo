package curlew.gameboardeditor.datamodel;

public abstract class Landforms {
	private static int defaultScale =3;
	private int row;
	private int column;
	private TerrainMap mapOnBuild;
	
	public Landforms(TerrainMap map,int row, int column) {
		mapOnBuild = map;
		this.row= row;
		this.column = column;
//		build(row, column, defaultScale);
	}
	
	public int getRow() {
		return row;
	}


	public int getColumn() {
		return column;
	}


	public TerrainMap getMapOnBuild() {
		return mapOnBuild;
	}


	public void scale(int scaleSize) {
		build(scaleSize);
	}
	
	public void delete() {
		scale(0);
	}
	
	
	public abstract void build(int scale);
	

}
