package curlew.gameboardeditor.generators;

import curlew.gameboardeditor.datamodel.TerrainMap;

public class MountainLandformGenerator extends LandformGenerator {

	@Override
	public void build(TerrainMap ourMap, int row,int column, int scale) {
		double maxHeight = ourMap.getInitialDepth() + ourMap.getDepthChange() *scale;
		for(int i=row-2; i<=row+2;i++) {
			for(int j =column-2; j<=column +2;j++) {
				if(i==row && j == column) {
					ourMap.setHeightAt(i, j, maxHeight);
				}
				else if((i<=row+1&&i>=row-1)&&(j<=column+1&&j>=column-1)) {
					double height = Math.max(ourMap.getInitialDepth(), 2*maxHeight/3);
					ourMap.setHeightAt(i, j,height);
				}
				else {
					double height = Math.max(ourMap.getInitialDepth(), maxHeight/3);
					ourMap.setHeightAt(i, j, height);
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Mountain";
	}

}
	
	
	


