package curlew.gameboardeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

/**
 * JavaFX App
 */
public class App extends Application {
	

    private static Scene scene;
    private static Stage stage;
    private static File selectedFile;
    private static Path filePath;
    
    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void loadExistingFile() {
    	
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Object Files", "*.obj"));
    	File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
        	selectedFile = file;
        	//selectedFile = fileChooser.showSaveDialog(stage);
            //filePath = (Path) file.toPath();
        }
    }
    public static void fileSaver() throws IOException{
    	
        terrainMap mapAddy = new terrainMap(8,8); //calling terrain map object to get info about box to be saved in to a text file later
        mapAddy.getBox(3, 5); //test cases for now methods must be added to terrain map later to get final connection
        
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Object Files", "*.obj"));
    	fileChooser.setTitle("Save As");
    	fileChooser.setInitialFileName("DefaultFileName");
    	File file = fileChooser.showSaveDialog(stage);
    	if (file != null) {
    	// writing the object into a text file
    	try {
    		String absolute = file.getAbsolutePath();
    		FileOutputStream output = new FileOutputStream(file);
    		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("boxAddy.dat"));
    		
    		for (int row = 0; row < mapAddy.getlength() ; row++) {
    			oos.writeObject(row);
    			for (int col = 0; col < row ; col++ ) {
    				oos.writeObject(col);
    			}
    		}
     		output.close();
    	}
    	
    	catch(IOException e){
    		System.err.println("Error saving to file");
    	}
    	}
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