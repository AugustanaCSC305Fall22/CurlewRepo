

package curlew.gameboardeditor;

import java.io.IOException;

    

	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.MenuItem;

	public class ThirdController {

	    @FXML
	    private MenuItem TitleLabel;

	    @FXML
	    private Button saveAsButton;

	    @FXML
	    void saveAsFile(ActionEvent event) {

	    }

	

    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("primary");
    }

}
