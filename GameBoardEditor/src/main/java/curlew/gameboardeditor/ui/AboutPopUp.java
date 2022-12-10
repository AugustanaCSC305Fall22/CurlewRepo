package curlew.gameboardeditor.ui;

import java.io.IOException; 

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * 
 * @author Team Curlew
 *This class is a controller for the about pop.
 */
public class AboutPopUp {
	
	/**
	 * Switches Main Menu.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void clickedOkayButton(ActionEvent event) throws IOException {
		App.setRoot("mainMenu");
	}
}
