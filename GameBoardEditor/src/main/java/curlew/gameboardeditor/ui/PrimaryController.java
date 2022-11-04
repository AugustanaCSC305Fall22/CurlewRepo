package curlew.gameboardeditor.ui;

import java.io.File;
import java.io.IOException;

import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController {

	@FXML
	private Button GenRandomButton;

	@FXML
	private Button loadFileButton;

	@FXML
	private Button scratchButton;

	@FXML
	private Button templateButton;
	
	protected static boolean genRandom;
	
//	@FXML
//	public TerrainMap terrain;
	
	

//	@FXML
//	void genRandom(ActionEvent event) throws IOException {
//		//Will do other stuff here too
//		terrain = new TerrainMap(8, 8);
//		switchToSizeScreen(event);
//	}

	@FXML
	public void loadExistingFile() throws IOException{
		App.loadExistingFile();
		
		
	}

	@FXML
	private void switchToSecondary() throws IOException {
		App.setRoot("secondary");
	}

	@FXML
	void switchToThird(ActionEvent event) throws IOException {
		App.setRoot("third");
	}

	@FXML
	void switchToSizeScreen(ActionEvent event) throws IOException {
		genRandom =event.getSource().equals(GenRandomButton);
		
		App.setRoot("sizeScreen");
	}
}


