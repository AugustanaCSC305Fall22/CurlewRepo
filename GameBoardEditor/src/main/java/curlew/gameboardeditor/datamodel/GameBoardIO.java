package curlew.gameboardeditor.datamodel;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @author Team Curlew
 * This class is responsible for saving and uploading Terrain Map into obj or TMAP format
 *
 */
public class GameBoardIO {
	/**
	 * Saves the map in TMAP format
	 * @param map the map to save
	 * @param outputFile The file to save the map in
	 * @throws IOException
	 */
	public static void saveMap(TerrainMap map, File outputFile) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(outputFile);
		gson.toJson(map, writer);
		writer.close();
	}
	
	/**
	 * Returns the Terrain Map from the input file
	 * @param inputFile The file to read the Terrain Map from 
	 * @return the Terrain Map
	 * @throws JsonSyntaxException
	 * @throws JsonIOException
	 * @throws IOException
	 */
	public static TerrainMap loadMap(File inputFile) throws JsonSyntaxException, JsonIOException, IOException  {
		Gson gson = new Gson();
		FileReader reader = new FileReader(inputFile);
		TerrainMap map = gson.fromJson(reader, TerrainMap.class);
		reader.close();
		return map;		
	}
	
	/**
	 * Exports the map to obj format
	 * @param map The map to export
	 * @param file File name
	 * @throws IOException
	 */
	public static void exportMap(TerrainMap map, File file) throws IOException {
		ObjExporter objMap = new ObjExporter(map);
		objMap.export(file);
	}

		
	

}