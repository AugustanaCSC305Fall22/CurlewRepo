package curlew.gameboardeditor.datamodel;

public class TestClass {

	public static void main(String[] args) {
		TerrainMap ourMap = new TerrainMap(10,10);
		LandformsGenerator test = new GateToHellGenerator(ourMap);
		test.build(1, 5, 3);
//		test.build(0,0,1);
//		RandomMapGenerator g = new RandomMapGenerator(ourMap);
//		g.createMap();
		printMap(ourMap);
	}

	public static void printMap(TerrainMap ourMap) {
		for (int i = 0; i < ourMap.getRows(); i++) {
			System.out.println();

			for (int j = 0; j < ourMap.getColumns(); j++) {
				System.out.print(Math.round(ourMap.getHeight(i, j)) + " ");

			}
		}

	}
} 