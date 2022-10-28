package curlew.gameboardeditor.datamodel;

public class GateToHell extends Landforms {

	private static boolean isMade = false;

	public GateToHell(TerrainMap map, int row, int column) {
		super(map, row, column);
		// TODO Auto-generated constructor stub
		if(isMade) {
			throw new IllegalArgumentException("only one allowed");
		}else {
			isMade= true;
		}
	}

	@Override
	public void build(int scale) {
		// TODO Auto-generated method stub
		TerrainMap map = super.getMapOnBuild();
		int row = getRow();
		int column = getColumn();
		if (scale != 0) {
			for (int i = row; i < row + 2; i++) {
				for (int j = column; j < column + 2; j++) {
					map.build(i, j, 0);
				}
			}
		} else {
			for (int i = row; i < row + 2; i++) {
				for (int j = column; j < column + 2; j++) {
					map.build(i, j, 2);
				}
			}

		}
	}

}