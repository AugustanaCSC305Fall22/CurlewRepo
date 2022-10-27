package curlew.gameboardeditor.datamodel;

public class Valley extends Landforms {
	
	Mountains leftMountain;
	Mountains rightMountain;
	
	public Valley(TerrainMap map, int row, int column) {
		super(map,row, column);
	}

	@Override
	protected void build(int row, int column, int scale) {
		
		TerrainMap map = super.getMapOnBuild();
		leftMountain= new Mountains(map,row,column-3);
		rightMountain = new Mountains(map,row,column+3);
		if(scale==0) {
			for(int i =row-2;i<=row+2;i++) {
				try {
				map.build(i, column, map.getInitialDepth());
				}
				catch(IndexOutOfBoundsException e) {}
			}
		}
		else {
			for(int i =row-2;i<=row+2;i++) {
				try {
				map.build(i, column, map.getInitialDepth()/2);
				} catch(IndexOutOfBoundsException e) {}
			}
		}
		leftMountain.scale(scale);
		rightMountain.scale(scale);
	}
	
	

}