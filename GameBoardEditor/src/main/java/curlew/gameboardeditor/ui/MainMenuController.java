package curlew.gameboardeditor.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {

	@FXML
	private Button GenRandomButton;
	
	@FXML
	private Button mazeButton;
	
	protected static boolean genRandom;
	protected static boolean genMaze;
	
	@FXML
    private void initialize() {
		App.setSelectedFileToNull();
	}

	@FXML
	public void loadExistingFile() throws IOException{
		App.loadExistingFile();
	}
	
	@FXML
	void switchToPreviewScreen(ActionEvent event) throws IOException {
		App.setRoot("previewMap");
	}

	@FXML
	void switchToSizeScreen(ActionEvent event) throws IOException {
		genRandom =event.getSource().equals(GenRandomButton);
		genMaze= event.getSource().equals(mazeButton);
		App.setRoot("sizeScreen");
	}
}


