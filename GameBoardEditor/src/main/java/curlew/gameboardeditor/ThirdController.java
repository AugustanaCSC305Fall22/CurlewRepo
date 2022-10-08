

package curlew.gameboardeditor;

import java.io.IOException;

    

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

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
	    private MenuItem DigFeatureButton;


	    
	    @FXML
	    private Button backButton;
	    
	    private terrainMap terrain;
	
//	    int length1 = (int) lengthSlider.getValue();
//	    int width1 = (int) widthSlider.getValue();
	    
//	    terrain = new terrainMap(length1, width1);
	    
    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("primary");
    }

    @FXML
    void saveAsButtonHandler(ActionEvent event) throws IOException {
    	App.fileSaver();
    }
    
}
