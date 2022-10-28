package curlew.gameboardeditor.datamodel;

public class TestClass {

	public static void main(String[] args) {
		TerrainMap ourMap = new TerrainMap(10,10);
		RandomMapGenerator gMap = new RandomMapGenerator(ourMap);
		gMap.createMap();
		//Landforms some = new Trench(ourMap, 9, 5);
		//Landforms we = new Mountains(ourMap,5,5);
		//we.build(3);
		printMap(ourMap);
	}

	private static void printMap(TerrainMap ourMap) {
		for (int i = 0; i < ourMap.getRows(); i++) {
			System.out.println();

			for (int j = 0; j < ourMap.getColumns(); j++) {
				System.out.print(Math.round(ourMap.getHeight(i, j)) + " ");

			}
		}

	}
} 