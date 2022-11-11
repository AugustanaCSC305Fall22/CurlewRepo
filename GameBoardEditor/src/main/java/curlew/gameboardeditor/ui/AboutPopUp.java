package curlew.gameboardeditor.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AboutPopUp {

	@FXML
	private Button okayButton;
	
	@FXML
	void clickedOkayButton(ActionEvent event) throws IOException {
		App.setRoot("mainMenu");
	}
}
