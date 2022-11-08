package curlew.gameboardeditor.datamodel;

public class GateToHellGenerator extends LandformsGenerator {

	public GateToHellGenerator(TerrainMap map) {
		super(map);		
	}

	@Override
	public void build(int row, int column, int scale) {
		// TODO Auto-generated method stub
		TerrainMap map = super.getMapOnBuild();
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Gate To Hell";
	}

}