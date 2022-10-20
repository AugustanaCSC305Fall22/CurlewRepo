package curlew.gameboardeditor;

public class TestClass {

	public static void main(String[] args) {
		TerrainMap ourMap = new TerrainMap(10,10);
//		printMap(ourMap);
		
		Mountains moutain = new Mountains(ourMap,1,1);
		printMap(ourMap);
		

	}
	
	private static void printMap(TerrainMap ourMap) {
		for(int i=0;i<ourMap.getRow();i++) {
			System.out.println();
			for(int j=0;j<ourMap.getColoumn(); j++) {
				System.out.print(ourMap.getHeight(i, j) +" ");
			}
		}
	}

}
