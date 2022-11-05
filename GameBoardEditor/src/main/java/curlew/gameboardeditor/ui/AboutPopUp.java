package curlew.gameboardeditor.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutPopUp {

	
	@FXML
	private Button okayButton;
	
	@FXML
	void clickedOkayButton(ActionEvent event) throws IOException {
		App.setRoot("mainMenu");
	}
	
}
