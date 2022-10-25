package curlew.gameboardeditor.datamodel;

public class TestClass {

	public static void main(String[] args) {
		TerrainMap ourMap = new TerrainMap(10,10);
		Landforms hell = new GateToHell(ourMap, 5,5);
		Landforms hell2 = new GateToHell(ourMap, 5,5);
//		hell.delete();
//		hell.scale(4);
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
