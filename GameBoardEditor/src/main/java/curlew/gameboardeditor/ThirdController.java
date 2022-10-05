

package curlew.gameboardeditor;

import java.io.IOException;

    

	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;

	public class ThirdController {

	    @FXML
	    private MenuItem TitleLabel;

	    @FXML
	    private Button saveAsButton;
	    
	    @FXML
	    private Slider widthSlider;
	    
	    @FXML
	    private Slider lengthSlider;

	    @FXML
	    void saveAsFile(ActionEvent event) {

	    }
	    @FXML
	    private Button backButton;

	

    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("primary");
    }

}
