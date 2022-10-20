package curlew.gameboardeditor;

public abstract class Landforms {
	private static int defaultScale =3;
	private int row;
	private int column;
	private TerrainMap mapOnBuild;
	
	public Landforms(TerrainMap map,int row, int column) {
		mapOnBuild = map;
		this.row= row;
		this.column = column;
		build(row, column, defaultScale);
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
		build(row,column, scaleSize);
	}
	
	public void delete() {
		scale(0);
	}
	
	
	protected abstract void build(int row, int column, int scale);
	

}
