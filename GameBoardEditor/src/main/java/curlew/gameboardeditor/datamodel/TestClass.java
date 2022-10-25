package curlew.gameboardeditor.datamodel;

public class TestClass {

	public static void main(String[] args) {
		TerrainMap ourMap = new TerrainMap(10,10);
//		printMap(ourMap);
		
//		Mountains moutain = new Mountains(ourMap,6,6);
//		moutain.scale(4);
//		moutain.delete();
//		printMap(ourMap);
		
		Volcano volcano = new Volcano(ourMap, 0, 0);
		volcano.scale(3);
		volcano.delete();
		printMap(ourMap);
		
		
		
		

	}
	
	private static void printMap(TerrainMap ourMap) {
		for(int i=0;i<ourMap.getRows();i++) {
			System.out.println();
			for(int j=0;j<ourMap.getColumns(); j++) {
				System.out.print(ourMap.getHeight(i, j) +" ");
			}
		}
	}

}
