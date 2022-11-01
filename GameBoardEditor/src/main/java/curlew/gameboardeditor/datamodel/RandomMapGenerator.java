package curlew.gameboardeditor.datamodel;

import java.util.ArrayList;
import java.util.Random;



public class RandomMapGenerator {
	
	
	private Random rand = new Random();

	
	TerrainMap map;
	ArrayList<LandformsGenerator> list = new ArrayList<LandformsGenerator>();
	

	
	public RandomMapGenerator(TerrainMap map) {
		this.map=map;
		landformListMaker();
	}
	
	
	private void landformListMaker() {
		list.add(new MountainGenerator(map));
		list.add(new ValleyGenerator(map));
		list.add(new TrenchGenerator(map));
		list.add(new VolcanoGenerator(map));
		list.add(new GateToHellGenerator(map));

	}
	
	public void createMap() {
		for(int i = 0 ; i <= map.getRows()*10/100 ; i++) {
			list.get(rand.nextInt(list.size())).build( rand.nextInt(map.getRows()), rand.nextInt(map.getColumns()),rand.nextInt(5)+1);
		}
	}
	

	
	

}
