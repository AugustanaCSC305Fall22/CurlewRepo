package curlew.gameboardeditor.datamodel;

public class TestClass {

	public static void main(String[] args) {
		TerrainMap ourMap = new TerrainMap(10,10);
<<<<<<< HEAD
//		printMap(ourMap);
		
		//Mountains mountain = new Mountains(ourMap,0,5);
		//mountain.scale(1);
//		moutain.scale(4);
//		moutain.delete();
//		printMap(ourMap);

		
	//	Volcano volcano = new Volcano(ourMap, 5, 5);
	//	volcano.scale(3);
//		volcano.delete();
		
		
		Valley valley = new Valley(ourMap, 0,5);
		//valley.scale(2);
		valley.delete();
=======
		Landforms hell = new GateToHell(ourMap, 5,5);
		Landforms hell2 = new GateToHell(ourMap, 5,5);
//		hell.delete();
//		hell.scale(4);
>>>>>>> branch 'main' of https://github.com/AugustanaCSC305Fall22/CurlewRepo.git
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
