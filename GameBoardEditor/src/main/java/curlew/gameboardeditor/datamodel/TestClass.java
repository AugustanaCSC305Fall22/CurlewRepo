package curlew.gameboardeditor.datamodel;

import java.io.File;
import java.io.IOException;

public class TestClass {

	public static void main(String[] args) throws IOException {
//		TerrainMap ourMap = new TerrainMap(6,5,Tile2DGeometry.TileShape.SQUARE);
//		ourMap = GameBoardIO.loadMap(new File("ACE.TMap"));
////		ourMap.deleteColumn(3);
////		ourMap.build(0, 0);
////		LandformsGenerator test = new GateToHellGenerator(ourMap);
////		test.build(1, 5, 3);
////		test.build(0,0,1);
////		RandomMapGenerator g = new RandomMapGenerator(ourMap);
////		g.createMap();
//		ObjExporter map = new ObjExporter(ourMap);
//		map.export(new File("test.obj"));
//		printMap(ourMap);
		
	}

	public static void printMap(TerrainMap map) {
		for (int i = 0; i < map.getRows(); i++) {
			System.out.println();

			for (int j = 0; j < map.getColumns(); j++) {
				System.out.print(Math.round(map.getHeight(i, j)) + " ");
				
			}
			
		}

	}
} 