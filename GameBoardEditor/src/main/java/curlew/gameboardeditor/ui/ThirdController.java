

package curlew.gameboardeditor.ui;


import java.io.IOException;

import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

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
	    private Button AddBlockButton;

	    @FXML
	    private ComboBox<String> featureComboBox;
	    
	    @FXML
	    private Button addFeatureButton;

	    @FXML
	    private Button DigButton;
	   
	    @FXML
	    private HBox hBoxCanvas;

	    @FXML
	    private Canvas twoDCanvas;
	    
	    private twoDMapEditor mapEditor;
	    
	    @FXML
	    private ToggleButton addBlockButton;

	    @FXML
	    private ToggleButton digBlockButton;
	    
	    @FXML
	    private ToggleButton toggleButton3;
	    
	    @FXML
	    private void initialize() {
	    	//Initializes the canvas, builds the map and makes the 2DEditor
	    	twoDCanvas.setHeight(400);
	    	twoDCanvas.setWidth(400);
	    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
	    	mapEditor = new twoDMapEditor(makeMap(), twoDCanvas);
	    	
	    	//Nested for loops to outline the canvas based on the desired size from the user
	    	for (int i = 1; i <= mapEditor.numRows+1; i++) {
				for (int j = 1; j <= mapEditor.numCols+1; j++) {
					//Sets the default colors for the outline of the canvas
					gc.setStroke(Color.BLACK);
					gc.setFill(Color.WHITE);
					gc.fillRect(i * mapEditor.boxLengthSize, j * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);
					//strokeRect(X coord, Y coord, length, width)
					//Will build boxes top to bottem, then left to right
			    	gc.strokeRect(i * mapEditor.boxLengthSize, j * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);	
					gc.fillRect(i * mapEditor.boxLengthSize, j * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);
				}
	    	}
	    	
	    	
	    	featureComboBox.setValue("Mountain");
	    	featureComboBox.setItems(featureList);
	    
	    	ToggleGroup toggleGroup = new ToggleGroup();
	    	addBlockButton.setToggleGroup(toggleGroup);
	    	digBlockButton.setToggleGroup(toggleGroup);


	    
	    	
	    }
	    
	  
	    
	    @FXML
		private TerrainMap makeMap() {
	    	TerrainMap terrain = new TerrainMap(SizeController.width, SizeController.length);
			return terrain;
		}
	    
	    public Canvas getTwoDCanvas() {
	    	return twoDCanvas;
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
//		Temporarily commented out need to test earlier feature
//		int selectedRowIndex = 0; //need access method from Donny's 2d canvas
//		int selectedColIndex = 0; //need access method from Donny's 2d canvas
//		
//		if (selectedRowIndex = null || selectedColIndex = null) {
//			//Warning
//		} else {
//			if (featureComboBox.getId() == null) {
//				//Warning
//			} else {
//				if (featureComboBox.getId() == "Mountain") {
//					Mountains mtn = new Mountains(terrain, selectedRowIndex, selectedColIndex);
//				} else if (featureComboBox.getId() == "Trench") {
//					Trench trench = new Trench(terrain, selectedRowIndex, selectedColIndex);
//				} else if (featureComboBox.getId() == "Valley") {
//					Valley valley = new Valley(terrain, selectedRowIndex, selectedColIndex);
//				} else if (featureComboBox.getId() == "Volcano") {
//					Volcano volcano = new Volcano(terrain, selectedRowIndex, selectedColIndex);
//				} else if (featureComboBox.getId() == "Gate to Hell") {
//					GateToHell hell = new GateToHell(terrain, selectedRowIndex, selectedColIndex);
//				}
//			}
//		}
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
