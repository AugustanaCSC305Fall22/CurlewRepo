package curlew.gameboardeditor;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateJSONFile {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject JSONobj = new JSONObject();
//		JSONobj.put("name", "Farah");
//		JSONobj.put("location", "RockIsland");
		
		// should take from row and create an object
		
		
		JSONArray list = new JSONArray();
//		list.put("JSP");
//		list.put("Python");
//		list.put("Java");
		
		// should create a list of columns
		// and add it to the object of rows
		
		

		
//		JSONobj.put("courses", list);
		
		try(FileWriter file = new FileWriter(" ")){
			file.write(JSONobj.toString());
			file.flush();
		} catch(IOException e){
			
		}
		
		System.out.println(JSONobj);
	}

}
