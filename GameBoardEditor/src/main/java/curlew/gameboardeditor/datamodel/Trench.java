package curlew.gameboardeditor.datamodel;

public class Trench extends Landforms{

	private static double TRENCH_HEIGHT =0.5;
	public Trench(TerrainMap map, int row, int column) {
		super(map, row, column);
		
	}

	@Override
	protected void build(int row, int column, int scale) {
		// TODO Auto-generated method stub
		TerrainMap map = super.getMapOnBuild();
		for(int i =row;i<row+2;i++) {
			for (int j=column; j<column+2;j++) {
				map.build(i, j, map.getInitialDepth());
			}
		}
		buildTrench(map,row+1,column+1,5+1,0);
		if(scale!=0) {
			for(int i =row;i<row+2;i++) {
				for (int j=column; j<column+2;j++) {
					map.build(i, j, TRENCH_HEIGHT);
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
			map.build(row, column+1, newDepth );
			map.build(row+1, column+1, newDepth );
			map.build(row+1, column, newDepth );
			buildTrench(map, row+1, column+1, numLeft-1, scale);
		}
	}

}
