package curlew.gameboardeditor.datamodel;

public class Mountains extends Landforms {

	public Mountains(TerrainMap map, int row, int column) {
		super(map, row, column);
	}

	@Override
	public void build(int scale) {
		TerrainMap ourMap = super.getMapOnBuild();
		int row =getRow();
		int column = getColumn();
		double maxHeight = ourMap.getInitialDepth() + ourMap.getDepthChange() *scale;
		for(int i=row-2; i<=row+2;i++) {
			for(int j =column-2; j<=column +2;j++) {
				if(i==row && j == column) {
					ourMap.build(i, j, maxHeight);
				}
				else if((i<=row+1&&i>=row-1)&&(j<=column+1&&j>=column-1)) {
					double height = Math.max(ourMap.getInitialDepth(), 2*maxHeight/3);
					ourMap.build(i, j,height);
				}
				else {
					double height = Math.max(ourMap.getInitialDepth(), maxHeight/3);
					ourMap.build(i, j, height);
				}
			}
		}
	}
}
	
	
	


