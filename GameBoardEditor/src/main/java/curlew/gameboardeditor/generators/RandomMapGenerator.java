package curlew.gameboardeditor.generators;

import java.util.ArrayList;
import java.util.Random;

import curlew.gameboardeditor.datamodel.TerrainMap;

/**
 * 
 * @author Team Curlew 
 * This class is a Random Map Generator which draws a blank map, then adds random landforms which changes elevations within the map
 * 
 */
public class RandomMapGenerator {
	
	private Random rand = new Random();
	private TerrainMap map;
	private ArrayList<LandformGenerator> list = new ArrayList<LandformGenerator>();
	
	public RandomMapGenerator(TerrainMap map) {
		this.map=map;
		landformListMaker();
	}
	
	private void landformListMaker() {
		list.add(new MountainLandformGenerator());
		list.add(new ValleyLandformGenerator());
		list.add(new TrenchLandformGenerator());
		list.add(new VolcanoLandformGenerator());
		list.add(new GateToHellLandformGenerator());

	}
	
	public void createMap() {
		for(int i = 0 ; i <= map.getRows()*20/100 ; i++) {
			list.get(rand.nextInt(list.size())).build(map, rand.nextInt(map.getRows()), rand.nextInt(map.getColumns()),rand.nextInt(5)+1);
		}
	}
	

	
	

}
