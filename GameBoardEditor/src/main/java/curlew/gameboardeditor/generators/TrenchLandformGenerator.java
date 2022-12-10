package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;

/**
 * 
 * @author Team Curlew
 * This class is responsible for making trenches.
 *
 */
public class TrenchLandformGenerator extends LandformGenerator{

	private final static double TRENCH_HEIGHT = 1.0;

	@Override
	public void build(TerrainMap map, int row, int column, int scale) {
		for(int i =row;i<row+2;i++) {
			for (int j=column; j<column+2;j++) {
					map.setHeightAt(i, j, map.getInitialDepth());
			}
		}
		buildTrench(map,row+1,column+1,5+1,0);
		if(scale!=0) {
			for(int i =row;i<row+2;i++) {
				for (int j=column; j<column+2;j++) {
					map.setHeightAt(i, j, TRENCH_HEIGHT);
				}
			}
			buildTrench(map,row+1,column+1,scale+1,scale);
		}
	}
	
	private void buildTrench(TerrainMap map,int row, int column, int numLeft, int scale) {
		if(numLeft!=0) {
			double newDepth =0;
			if(scale==0) {
				newDepth = map.getInitialDepth();
			}else {
				newDepth = TRENCH_HEIGHT;
			}
			map.setHeightAt(row, column+1, newDepth );
			map.setHeightAt(row+1, column+1, newDepth );
			map.setHeightAt(row+1, column, newDepth );
			buildTrench(map, row+1, column+1, numLeft-1, scale);
		}
	}

	@Override
	public String getName() {
		return "Trench";
	}

}
