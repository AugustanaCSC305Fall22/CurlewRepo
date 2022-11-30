package curlew.gameboardeditor.datamodel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


public class GameBoardIO {

	public static void saveMap(TerrainMap map, File outputFile) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(outputFile);
		gson.toJson(map, writer);
		writer.close();
	}
	
	public static TerrainMap loadMap(File inputFile) throws JsonSyntaxException, JsonIOException, IOException  {
		Gson gson = new Gson();
		FileReader reader = new FileReader(inputFile);
		System.out.println(inputFile);
		TerrainMap map = gson.fromJson(reader, TerrainMap.class);
		reader.close();
		return map;		
	}

	public static void exportMap(TerrainMap map, File file) throws IOException {
		ObjExporter objMap = new ObjExporter(map);
		objMap.export(file);
	}

//	public static void saveMapAsTemplate(TerrainMap map) {
//		File file = new File("templates:\\map\\map. TMap");
//		
//	}		
	

}