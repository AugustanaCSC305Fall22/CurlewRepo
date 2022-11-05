package curlew.gameboardeditor.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import curlew.gameboardeditor.datamodel.GameBoardIO;
import curlew.gameboardeditor.datamodel.TerrainMap;


/**
 * JavaFX App
 */
public class App extends Application {
	
    private static Scene scene;
    private static Stage stage;
    private static File selectedFile;
    private static Path filePath;
    
    private static Scene mainScene;
    
    private static TerrainMap map;
    
    
    @Override
    public void start(Stage stage) throws IOException {
    	
    	scene = new Scene(loadFXML("aboutPopUp"),640, 480);
    	Stage popUp = new Stage();
    	popUp.setTitle("Welcome to the Terrain Map Editor");
        popUp.setScene(scene);
        popUp.showAndWait();
//        scene.setOnKeyPressed( e -> keyPressed(e));
        
        
        mainScene = new Scene(loadFXML("mainMenu"), 640, 480);
        stage.setScene(mainScene);
        stage.show();
    }
    
//    private void keyPressed(KeyEvent e) throws IOException {
//    	KeyCode key = e.getCode();
//    	if (key == KeyCode.ALL_CANDIDATES) {
//    		App.setRoot("mainMenu");
//    	}
//    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    

    public static void loadExistingFile() {
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("3D TerrainMap File", "*.TMap"));
    	File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
        	selectedFile = file;
        	
        	try {
				map = GameBoardIO.loadMap(selectedFile);
				App.setRoot("previewMap");
        	} catch (IOException e) {
				e.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setContentText("There was an error loading " + selectedFile);
				alert.show();
			}
        	
        }
    }
    
    public static void saveProjectFile() throws IOException {
    	
       // TerrainMap mapAddy = map; //calling terrain map object to get info about box to be saved in to a text file later
        
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("3D TerrainMap File", "*.TMap"));
    	fileChooser.setTitle("Save As");
    	fileChooser.setInitialFileName("Untitled.TMap");
    	File file = fileChooser.showSaveDialog(stage);
    	if (file != null) {
    		GameBoardIO.saveMap(map, file);
    	} 
	
    }
    	
	public static TerrainMap getMap() {
		return map;
	}
	
	public static void setMap(TerrainMap newMap) {
		map = newMap;
	} 
    
    private File getSelectedFile() {
    	return selectedFile;
    }
    
    private Path getFilePath() {
    	return filePath;
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}