package curlew.gameboardeditor.datamodel;

public class ValleyGenerator extends LandformsGenerator {
	
	MountainGenerator mountain;
	
	public ValleyGenerator(TerrainMap map) {
		super(map);
		mountain = new MountainGenerator(map);
	}

	@Override
	public void build(int row, int column, int scale) {
		
		TerrainMap map = super.getMapOnBuild();
		
		mountain.build(row,column-3, scale);
		mountain.build(row,column+3,scale);
		if(scale==0) {
			for(int i =row-2;i<=row+2;i++) {
				map.build(i, column, map.getInitialDepth());
			}
		}
		else {
			for(int i =row-2;i<=row+2;i++) {
				map.build(i, column, map.getInitialDepth()/2);
			}
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Valley";
	}
}
