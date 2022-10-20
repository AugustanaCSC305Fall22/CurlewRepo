package curlew.gameboardeditor;

public abstract class Landforms {
	private static int defaultScale =3;
	private int row;
	private int column;
	
	public Landforms(int row, int column) {
		this.row= row;
		this.column = column;
		build(row,column, defaultScale);
	}
	
	public void scale(int scaleSize) {
		build(row,column, scaleSize);
	}
	
	public void delete() {
		scale(0);
	}
	
	protected abstract void build(int row, int column, int scale);
	

}
