package curlew.gameboardeditor.ui;

import java.io.File;
import java.io.IOException;

import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainMenuController {

	@FXML
	private Button GenRandomButton;

	@FXML
	private Button loadFileButton;

	@FXML
	private Button scratchButton;

	@FXML
	private Button templateButton;
	
	@FXML
	private Button mazeButton;
	
	protected static boolean genRandom;
	protected static boolean genMaze;
	


	@FXML
	public void loadExistingFile() throws IOException{
		App.loadExistingFile();
		
		
	}

	@FXML
	private void switchToSecondary() throws IOException {
		App.setRoot("previewMap");
	}

	@FXML
	void switchToThird(ActionEvent event) throws IOException {
		App.setRoot("previewMap");
	}

	@FXML
	void switchToSizeScreen(ActionEvent event) throws IOException {
		genRandom =event.getSource().equals(GenRandomButton);
		genMaze= event.getSource().equals(mazeButton);
		App.setRoot("sizeScreen");
	}
}


