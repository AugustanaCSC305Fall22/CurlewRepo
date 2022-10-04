package curlew.gameboardeditor;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

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
	void genRandom(ActionEvent event) throws IOException {
		//Will do other stuff here too
		
		switchToThird(event);
	}

	@FXML
	void loadExistingFile(ActionEvent event) {
		FileChooser file = new FileChooser();
		
	}

	@FXML
	private void switchToSecondary() throws IOException {
		App.setRoot("secondary");
	}

	@FXML
	void switchToThird(ActionEvent event) throws IOException {
		App.setRoot("third");
	}

}


