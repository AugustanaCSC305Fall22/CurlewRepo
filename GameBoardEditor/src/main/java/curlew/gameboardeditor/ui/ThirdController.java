

package curlew.gameboardeditor.ui;


import java.io.IOException;

import curlew.gameboardeditor.datamodel.GateToHell;
import curlew.gameboardeditor.datamodel.Mountains;
import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.Trench;
import curlew.gameboardeditor.datamodel.Valley;
import curlew.gameboardeditor.datamodel.Volcano;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

	public class ThirdController {
		
		ObservableList<String> featureList = FXCollections.observableArrayList("Mountain", "Valley", "Volcano", "Trench", "Gate to Hell");

	    @FXML
	    private MenuItem TitleLabel;

	    @FXML
	    private Button saveAsButton;
	    
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
	    private ComboBox<String> featureComboBox;
	    
	    @FXML
	    private Button addFeatureButton;

	    @FXML
	    private Button DigButton;
	    
	    @FXML
	    private static Canvas twoDCanvas;
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
	@FXML
	int width = SizeController.getWidth();
	int length = SizeController.getLength();

	@FXML
	private void makeMap() {
		terrain = new TerrainMap(width, length);
	}
	
//	@FXML
//	private void initialize() {
//	    GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
//	    gc.setStroke(Color.BLUE);
//	    gc.strokeRect(10, 50, 100, 80);
//	}
	

	    @FXML
	    private void initialize() {
	    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
	    	gc.setStroke(Color.BLUE);
	    	gc.strokeRect(10, 50, 100, 80);
	    	
	    	featureComboBox.setValue("Mountain");
	    	featureComboBox.setItems(featureList);
	    }
	    public static Canvas getTwoDCanvas() {
	    	return twoDCanvas;
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
//	@FXML
//	void getTextAddMountainButton(ActionEvent event) {
//		String text = AddMountianButton.getText();
//		lastClickedFeature(text);
//	}
//	@FXML
//	void getTextAddValleyButton(ActionEvent event) {
//		String text = AddValleyButton.getText();
//		lastClickedFeature(text);
//	}
	
	@FXML
	void addFeature(ActionEvent event) {
		int selectedRowIndex = 0; //need access method from Donny's 2d canvas
		int selectedColIndex = 0; //need access method from Donny's 2d canvas
		
		if (selectedRowIndex = null || selectedColIndex = null) {
			//Warning
		} else {
			if (featureComboBox.getId() == null) {
				//Warning
			} else {
				if (featureComboBox.getId() == "Mountain") {
					Mountains mtn = new Mountains(terrain, selectedRowIndex, selectedColIndex);
				} else if (featureComboBox.getId() == "Trench") {
					Trench trench = new Trench(terrain, selectedRowIndex, selectedColIndex);
				} else if (featureComboBox.getId() == "Valley") {
					Valley valley = new Valley(terrain, selectedRowIndex, selectedColIndex);
				} else if (featureComboBox.getId() == "Volcano") {
					Volcano volcano = new Volcano(terrain, selectedRowIndex, selectedColIndex);
				} else if (featureComboBox.getId() == "Gate to Hell") {
					GateToHell hell = new GateToHell(terrain, selectedRowIndex, selectedColIndex);
				}
			}
		}
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
    
    
    
}
