

package curlew.gameboardeditor.ui;


import java.io.IOException;

import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
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
	    private Label SelectedLabel;
	    
	    @FXML
	    private Button backButton;
	    @FXML
	    private TerrainMap terrain;
	    
	    @FXML
	    private Button AddBlockButton;

	    @FXML
	    private Button AddMountianButton;

	    @FXML
	    private Button AddValleyButton;

	    @FXML
	    private Button DigButton;
	    
	    @FXML
	    private Canvas twoDCanvas;
//      I just left these lines of code in case of my code is not correct.	
//	    int length1 = (int) lengthSlider.getValue();
//	    int width1 = (int) widthSlider.getValue();
	    
//	    terrain = new terrainMap(length1, width1);
	    
	    
//	    private int length = (int) lengthSlider.getValue();
//	    private int width = (int) widthSlider.getValue();
//	    
//	    @FXML
//	    private terrainMap terrain = new terrainMap(length, width);

//	    
	int width = SizeController.getWidth();
	int length = SizeController.getLength();
	
	@FXML
	private void makeMap() {
		terrain = new TerrainMap(width, length);
	}
	
	@FXML
	private void initialize() {
	    GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
	    gc.setStroke(Color.BLUE);
	    gc.strokeRect(10, 50, 100, 80);
	}
	
	@FXML
	void getTextDigButton(ActionEvent event) {
		String text = DigButton.getText();
		lastClickedFeature(text);
	}
	@FXML
	void getTextAddBlockButton(ActionEvent event) {
		String text = AddBlockButton.getText();
		lastClickedFeature(text);
	}
	@FXML
	void getTextAddMountainButton(ActionEvent event) {
		String text = AddMountianButton.getText();
		lastClickedFeature(text);
	}
	@FXML
	void getTextAddValleyButton(ActionEvent event) {
		String text = AddValleyButton.getText();
		lastClickedFeature(text);
	}
	    
	@FXML 
	void lastClickedFeature(String str) {
		SelectedLabel.setText(str);
	}

    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("primary");
    }

    @FXML
    void saveAsButtonHandler(ActionEvent event) throws IOException {
    	App.fileSaver();
    }
    
    
    @FXML
    void sliderHandler (ActionEvent event) throws IOException {
    	int length = (int) lengthSlider.getValue();
    	int width = (int) widthSlider.getValue();
    	terrain = new TerrainMap(length, width);
    }
    
}
