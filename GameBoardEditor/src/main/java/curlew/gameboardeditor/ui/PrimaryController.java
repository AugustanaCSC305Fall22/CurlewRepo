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
	
	@FXML
	public TerrainMap terrain;

	@FXML
	void genRandom(ActionEvent event) throws IOException {
		//Will do other stuff here too
		terrain = new TerrainMap(8, 8);
		switchToThird(event);
	}

	@FXML
	public void loadExistingFile() {
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
	void switchToSizeController(ActionEvent event) throws IOException {
		App.setRoot("sizeScreen");
	}
}


