package curlew.gameboardeditor.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * 
 * @author Team Curlew
 * This class is a controller for MainMenu fxml
 */
public class MainMenuController {

	@FXML
	private Button GenRandomButton;
	
	@FXML
	private Button mazeButton;
	
	private static boolean randomSelected;
	private static boolean mazeSelected;
	
	@FXML
    private void initialize() {
		App.setSelectedFileToNull();
	}

	@FXML
	private void loadExistingFile() throws IOException{
		App.loadExistingFile();
	}
	
	@FXML
	private void switchToPreviewScreen(ActionEvent event) throws IOException {
		App.setRoot("previewMap");
	}

	@FXML
	private void switchToSizeScreen(ActionEvent event) throws IOException {
		randomSelected =event.getSource().equals(GenRandomButton);
		mazeSelected= event.getSource().equals(mazeButton);
		App.setRoot("sizeScreen");
	}
	
	/**
	 * Returns whether maze idea button was pressed
	 * @return whether maze idea button was pressed
	 */
	public static boolean mazeSelected() {
		return mazeSelected;
	}
	
	/**
	 * Returns whether generate random button was pressed
	 * @return whether generate random button was pressed
	 */
	public static boolean radomSelected() {
		return randomSelected;
	}
}


