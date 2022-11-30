package curlew.gameboardeditor.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import curlew.gameboardeditor.datamodel.GameBoardIO;
import curlew.gameboardeditor.datamodel.TerrainMap;


/**
 * JavaFX App
 */
public class App extends Application {
	
    private static Scene scene;
    private static Stage stage;
    private static File selectedFile;
    private static TerrainMap map;
    
    @Override
    public void start(Stage stage) throws IOException {
   
    	scene = new Scene(loadFXML("aboutPopUp"),640, 550);    	
        stage.setScene(scene);		
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    /**
     * loadExistingFile
     * Loads an existing .TMap file created by the user earlier and opens it for editing. Thefile itslef will be edited
     */
    public static void loadExistingFile() {
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("3D TerrainMap File", "*.TMap"));
    	File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
        	selectedFile = file;
        	
        	try {
				map = GameBoardIO.loadMap(selectedFile);
				App.setRoot("mapEditor");
        	} catch (IOException e) {
				e.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setContentText("There was an error loading " + selectedFile);
				alert.show();
			}
        	
        }
    }
    /**
     * void saveProjectFile
     * @throws IOException
     * Stores the current map as a TMap file and stores it in the users desired folder on the computer as well as the data field selected file.
     */
    public static void saveAsProjectFile() throws IOException {        
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("3D TerrainMap File", "*.TMap"));
    	fileChooser.setTitle("Save As");
    	fileChooser.setInitialFileName("Untitled.TMap");
    	File file = fileChooser.showSaveDialog(stage);
    	if (file != null) {
    		selectedFile = file;
    		GameBoardIO.saveMap(map, file);
    	} 
	
    }
    
    /**
     * updates the saved file
     * @throws IOException
     */
	public static void saveProjectFile() throws IOException {
    	if (selectedFile != null) {
    		GameBoardIO.saveMap(map, selectedFile);
    	}else {
    		saveAsProjectFile();
    	}
	}
	
    /**
   	 * getMap()
   	 * @return the current map
   	 */
	public static TerrainMap getMap() {
		return map;
	}
	/**
	 * setMap()
	 * @param newMap
	 * sets the map data field to the new incoming map
	 */
	public static void setMap(TerrainMap newMap) {
		map = newMap;
	} 
    
    /**
     * Auto generated from Javafx
     * @param fxml
     * @return Parent
     * @throws IOException
     * loads the fxml file initially, and gets called in the setRoot() method to change the scene
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


    
 

}