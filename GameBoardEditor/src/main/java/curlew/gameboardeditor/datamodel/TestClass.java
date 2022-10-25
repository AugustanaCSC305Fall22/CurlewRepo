package curlew.gameboardeditor.datamodel;

public class TestClass {

	public static void main(String[] args) {
		TerrainMap ourMap = new TerrainMap(10,10);
//		printMap(ourMap);
		
//		Mountains moutain = new Mountains(ourMap,6,6);
//		moutain.scale(4);
//		moutain.delete();
//		printMap(ourMap);
		Valley valley = new Valley(ourMap,5,5);
//		valley.delete();
		valley.scale(5);
		printMap(ourMap);
		

	}
	
	private static void printMap(TerrainMap ourMap) {
		for(int i=0;i<ourMap.getRows();i++) {
			System.out.println();

			for(int j=0;j<ourMap.getColumns(); j++) {
				System.out.print(Math.round(ourMap.getHeight(i, j)) +" ");

		}
	}

}
}
