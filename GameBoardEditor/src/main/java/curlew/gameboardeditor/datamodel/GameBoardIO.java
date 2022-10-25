package curlew.gameboardeditor.datamodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameBoardIO {

	public static String toJSON(TerrainMap map) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(map);
	}
	
	public static TerrainMap fromJSON(String jsonText) {
		Gson gson = new Gson();
		return gson.fromJson(jsonText, TerrainMap.class);		
	}		
}