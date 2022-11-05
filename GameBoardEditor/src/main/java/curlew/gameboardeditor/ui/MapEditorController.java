

package curlew.gameboardeditor.ui;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;

import curlew.gameboardeditor.datamodel.GateToHellGenerator;
import curlew.gameboardeditor.datamodel.LandformsGenerator;
import curlew.gameboardeditor.datamodel.MountainGenerator;
import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.TestClass;
import curlew.gameboardeditor.datamodel.TrenchGenerator;
import curlew.gameboardeditor.datamodel.ValleyGenerator;
import curlew.gameboardeditor.datamodel.VolcanoGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

	public class MapEditorController {
		
		ObservableList<LandformsGenerator> featureList;
		private int selectedColIndex=-1;
		private int selectedRowIndex=-1;
		
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
	    private Slider scaleSlider;
	    

	    @FXML
	    private void initialize() {
	    	//Initializes the canvas, builds the map and makes the 2DEditor
	    	twoDCanvas.setHeight(400);
	    	twoDCanvas.setWidth(400);
	    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
	    	TerrainMap map = App.getMap();
	    	mapEditor = new twoDMapEditor(map, twoDCanvas);
	    	
	    	featureList = FXCollections.observableArrayList(new MountainGenerator(map), new VolcanoGenerator(map), new ValleyGenerator(map), new TrenchGenerator(map), new GateToHellGenerator(map));

	    	
	    	//Nested for loops to outline the canvas based on the desired size from the user
	    	fillMap();
	    	drawOutLine();
	    	
	    	twoDCanvas.setOnMouseClicked(event -> {
	    		//find the closest x box corrdinates, then find the closest y
	    		int boxLength = mapEditor.boxLengthSize;
				int boxWidth = mapEditor.boxWidthSize;
				Point p = new Point(Math.round((int)event.getX()/(boxLength)), Math.round((int)event.getY()/(boxWidth)));
				
				if (p.x >= 0 && p.x<App.getMap().getColumns() && p.y>=0 && p.y < App.getMap().getRows()) {
				
					unselectPrevious();
					selectedColIndex = p.x;
					selectedRowIndex = p.y;
					selectBox();
				}
	    	});
	    	
	    	featureComboBox.setItems(featureList);
	    
	    	ToggleGroup toggleGroup = new ToggleGroup();
	    	addBlockButton.setToggleGroup(toggleGroup);
	    	digBlockButton.setToggleGroup(toggleGroup);


	    
	    	
	    }
	  
	    


	    
	    private void unselectPrevious() {
	    	if(selectedColIndex!=-1) {
		    	GraphicsContext  gc = twoDCanvas.getGraphicsContext2D();
				gc.setStroke(Color.BLACK);
				gc.strokeRect(selectedColIndex * mapEditor.boxLengthSize, selectedRowIndex * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);	

	    	}
	    }
		
		private void selectBox() {
			GraphicsContext  gc = twoDCanvas.getGraphicsContext2D();
			gc.setStroke(Color.AQUA);
			gc.strokeRect(selectedColIndex * mapEditor.boxLengthSize, selectedRowIndex * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);	
		}
		
	    public Canvas getTwoDCanvas() {
	    	return twoDCanvas;
	    }
	
	@FXML
	int getScale() {
		int scale = (int) scaleSlider.getValue();
		return scale;
	}
	
	@FXML
	void addFeatures(ActionEvent event) {
		LandformsGenerator feature = featureComboBox.getValue();
		if(selectedColIndex==-1) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}
		else if (feature == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText("You must select the feature to be added");
			alert.show();
		} else {
			feature.build(selectedRowIndex, selectedColIndex, getScale());
			TestClass.printMap(App.getMap());
			fillMap();
			drawOutLine();
		}
	}
	
    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }

    @FXML
    void saveAsButtonHandler(ActionEvent event) throws IOException {
    	App.saveProjectFile();
    }
    
    @FXML
    void digButtonHandler() {
    	if(selectedColIndex==-1) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else {
	    	try {
	    	App.getMap().dig(selectedRowIndex, selectedColIndex);
	    	
	    	}catch (IllegalArgumentException e) {
	    		new Alert(AlertType.WARNING, "You can not go down further!").show();
	    		updateTile();
	    	}
    	}
    }
    
    @FXML
    void buildButtonHandler() {
    	if(selectedColIndex==-1) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else {
	    	try {
	    	App.getMap().build(selectedRowIndex, selectedColIndex);
	    	updateTile();
	    	}catch (IllegalArgumentException e) {
	    		new Alert(AlertType.WARNING, "You can not build further!").show();
	    	}
    	}
    }
    private void drawOutLine() {
    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
    	for (int i = 0; i < mapEditor.numCols; i++) {
			for (int j = 0; j < mapEditor.numRows; j++) {
				//Sets the default colors for the outline of the canvas
				gc.setStroke(Color.BLACK);
		    	gc.strokeRect(i * mapEditor.boxLengthSize, j * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);	

			}
    	}
    }
    private void fillMap() {
    	
    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
    	for (int i = 0; i < mapEditor.numRows; i++) {
			for (int j = 0; j < mapEditor.numCols; j++) {
				int height = (int) Math.round(App.getMap().getHeight(i, j));
				gc.setFill(Color.rgb(250-20*(height),250-20*(height) ,250-20*(height)));	
				gc.fillRect((j) * mapEditor.boxLengthSize, (i) * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);
			}
    	}
    }
    private void updateTile() {
    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
    	int height = (int) Math.round(App.getMap().getHeight(selectedRowIndex, selectedColIndex));
		gc.setFill(Color.rgb(250-20*(height),250-20*(height) ,250-20*(height)));	
		gc.fillRect((selectedColIndex) * mapEditor.boxLengthSize, (selectedRowIndex) * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);
    }
    
}