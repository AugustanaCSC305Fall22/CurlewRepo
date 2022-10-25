package curlew.gameboardeditor.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import curlew.gameboardeditor.datamodel.TerrainMap;

public class JSONExporter {
	
	public static void writeJSONFile(File file, TerrainMap mapAddy) {
    	// writing the object into a text file
    	try {
    		String absolute = file.getAbsolutePath();
    		FileOutputStream output = new FileOutputStream(file);
    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("CreateJSONFile"));
    		JSONObject jsonObject = new JSONObject();
    		
    		for (int row = 0; row < mapAddy.getRow() ; row++) {
    			for (int col = 0; col < row ; col++ ) {    				
    				jsonObject.put("index" , mapAddy.getValue(row, col));
    				
    			}
    		}
     		output.close();
    	}
    	
    	catch(IOException e){
    		System.err.println("Error saving to file");
    	}

	}

}
