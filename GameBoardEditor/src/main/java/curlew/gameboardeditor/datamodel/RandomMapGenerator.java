package curlew.gameboardeditor.datamodel;

import java.util.ArrayList;
import java.util.Random;



public class RandomMapGenerator {
	
	
	private Random rand = new Random();

	
	TerrainMap map;
	ArrayList<Landforms> list = new ArrayList<Landforms>();
	

	
	public RandomMapGenerator(TerrainMap map) {
		this.map=map;
		landformListMaker();
	}
	
	
	private void landformListMaker() {
		list.add(new Mountains(map, rand.nextInt(map.getRows()), rand.nextInt(map.getColumns())));
		list.add(new Valley(map, rand.nextInt(map.getRows()), rand.nextInt(map.getColumns())));
		list.add(new Trench(map, rand.nextInt(map.getRows()), rand.nextInt(map.getColumns())));
		list.add(new Volcano(map, rand.nextInt(map.getRows()), rand.nextInt(map.getColumns())));
		list.add(new GateToHell(map, rand.nextInt(map.getRows()), rand.nextInt(map.getColumns())));

	}
	
	public void createMap() {
		for(int i = 0 ; i <= map.getRows()*10/100 ; i++) {
			list.get(rand.nextInt(list.size())).build(rand.nextInt(5)+1);
		}
	}
	

	
	

}
