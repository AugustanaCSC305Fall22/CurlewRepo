

package curlew.gameboardeditor.ui;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;

import curlew.gameboardeditor.datamodel.GateToHellGenerator;
import curlew.gameboardeditor.datamodel.LandformsGenerator;
import curlew.gameboardeditor.datamodel.MountainGenerator;
import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.TrenchGenerator;
import curlew.gameboardeditor.datamodel.ValleyGenerator;
import curlew.gameboardeditor.datamodel.VolcanoGenerator;
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
		
		ObservableList<LandformsGenerator> featureList;
		private TerrainMap map;

	    @FXML
	    private MenuItem TitleLabel;

	    @FXML
	    private Button saveAsButton;
	    
	    @FXML
	    private MenuItem DigFeatureButton;

	    
	    @FXML
	    private Button backButton;
	    
	    @FXML
	    private Button AddBlockButton;

	    @FXML
	    private ComboBox<LandformsGenerator> featureComboBox;
	    
	    @FXML
	    private Button addFeatureButton;

	    @FXML
	    private Button DigButton;

	    @FXML
	    private Canvas twoDCanvas;
	    
	    private twoDMapEditor mapEditor;
	    
	    @FXML
	    private ToggleButton addBlockButton;

	    @FXML
	    private ToggleButton digBlockButton;
	    
	    
	    @FXML
	    private void initialize() {
	    	//Initializes the canvas, builds the map and makes the 2DEditor
	    	twoDCanvas.setHeight(400);
	    	twoDCanvas.setWidth(400);
	    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
	    	map = new TerrainMap(SizeController.width, SizeController.length);
	    	mapEditor = new twoDMapEditor(map, twoDCanvas);
	    	
	    	featureList = FXCollections.observableArrayList(new MountainGenerator(map), new VolcanoGenerator(map), new ValleyGenerator(map), new TrenchGenerator(map), new GateToHellGenerator(map));
	    	
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
	    	
	    	twoDCanvas.setOnMouseClicked(event -> {
	    		//find the closest x box corrdinates, then find the closest y
				
				Point p = new Point((int) event.getX(), (int) event.getY());
				int boxLength = mapEditor.boxLengthSize;
				int boxWidth = mapEditor.boxWidthSize;
				if (p.x > 0 && p.y < 400) {
					int numRows = Math.round(p.x/(boxLength));
					int numCols = Math.round(p.y/(boxWidth));
					selectBox(numRows, numCols);
				}
	    	});
	    	
	    	
//	    	featureComboBox.setValue("Mountain");
	    	featureComboBox.setItems(featureList);
	    
	    	ToggleGroup toggleGroup = new ToggleGroup();
	    	addBlockButton.setToggleGroup(toggleGroup);
	    	digBlockButton.setToggleGroup(toggleGroup);


	    
	    	
	    }
	    
//		private TerrainMap makeMap() {
//	    	TerrainMap terrain = new TerrainMap(SizeController.width, SizeController.length);
//			return terrain;
//		}
		
//	    @FXML
//		private void canvasMouseHandler(MouseEvent mouse) {
//			//find the closest x box corrdinates, then find the closest y
//			int numRows;
//			int numCols;
//			Point p = new Point(mouse.getX(), mouse.getY());
//			int boxLength = mapEditor.boxLengthSize;
//			int boxWidth = mapEditor.boxWidthSize;
//			if (p.x >= 0 && p.y <= 400) {
//				int rowsToSub = Math.round(p.x/(boxLength));
//				if ((p.x / boxLength) - rowsToSub >= .5) {
//					numRows = rowsToSub + 1;
//				} else {
//					numRows = rowsToSub - 1;
//				}
//				int colsToSub = Math.round(p.y/(boxWidth));
//				if ((p.x / boxWidth) - colsToSub >= .5) {
//					numCols = colsToSub + 1;
//				} else {
//					numCols = colsToSub - 1;
//				}
//				selectBox(numRows, numCols);
//				
//			}
//			
//		}
		
		private void selectBox(int numRows, int numCols) {
			GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
			gc.setStroke(Color.AQUA);
			gc.strokeRect(numRows * mapEditor.boxLengthSize, numCols * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);	
			gc.fillRect(numRows * mapEditor.boxLengthSize, numCols * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);

			
			

		}
		
	    public Canvas getTwoDCanvas() {
	    	return twoDCanvas;
	    }
	
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
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("primary");
    }

    @FXML
    void saveAsButtonHandler(ActionEvent event) throws IOException {
    	App.fileSaver();
    }
    
    
    
}
