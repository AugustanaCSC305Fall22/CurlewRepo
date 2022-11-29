

package curlew.gameboardeditor.ui;


import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

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
import javafx.scene.input.MouseButton;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

	public class MapEditorController {
		
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
	    private Canvas tileElevationLegendCanvas;
	    
	    @FXML
	    private Canvas twoDCanvas;
	    
	    @FXML
	    private ToggleButton raiseTileButton;

	    @FXML
	    private ToggleButton lowerTileButton;
	    
	    @FXML
	    private Slider scaleSlider;
	    
	    @FXML
	    private MenuItem scaleHelp;
	    
	    @FXML 
	    private MenuItem featureHelp;
	    
	    ObservableList<LandformsGenerator> featureList;
	    
		
		
		private twoDMapEditor mapEditor;
		
		private int legendSelectedHeight;
		
		


		/**
		 * Initializes the canvas, builds the map and makes the 2DEditor. 
		 * It also has listeners to call the select and unselect methods for the canvas
		 */
	    @FXML
	    private void initialize() {
	    	twoDCanvas.setHeight(400);
	    	twoDCanvas.setWidth(400);
	    	
	    	TerrainMap map = App.getMap();
	    	mapEditor = new twoDMapEditor(map, twoDCanvas);
	    	
	    	featureList = FXCollections.observableArrayList(new MountainGenerator(map), new VolcanoGenerator(map), new ValleyGenerator(map), new TrenchGenerator(map), new GateToHellGenerator(map));

	    	GraphicsContext gcLegend = tileElevationLegendCanvas.getGraphicsContext2D();
	    	
	    	drawLegendOnCanvas(gcLegend);
	    	//Nested for loops to outline the canvas based on the desired size from the user

	    	
	    	tileElevationLegendCanvas.setOnMouseClicked(event -> {
	    		Point p = new Point(Math.round((int) event.getX()), Math.round((int) event.getY()));
	    		setLegendSelectedHeight(p.y);
	    		if (p.x >= 0 && p.x < 100 && p.y >= 0 && p.y <= 400) {
	    			mapEditor.raiseTile(legendSelectedHeight);
	    		}
	    		
	    	});
	    	
	    	twoDCanvas.setOnMouseClicked(event -> {
	    		//find the closest x box corrdinates, then find the closest y
	    		if (event.getButton() == MouseButton.SECONDARY) {
	    			ContextMenu context = new ContextMenu();
	    	    	MenuItem item1 = new MenuItem("Undo");
	    	    	MenuItem item2 = new MenuItem("Redo");
	    	    	MenuItem item3 = new MenuItem("Add Row");
	    	    	MenuItem item4 = new MenuItem("DeleteRow");
	    	    	MenuItem item5 = new MenuItem("Add Column");
	    	    	MenuItem item6 = new MenuItem("Delete Column");
	    	    	MenuItem item7 = new MenuItem("Select Same Height");
//	    	    	item3.setOnAction(event -> );
	    	    	context.getItems().addAll(item1, item2, item3, item4, item5, item6, item7);
	    	    	context.show(twoDCanvas, event.getScreenX(), event.getScreenY());
	    		} else {
	    			double tileLength = mapEditor.getLength();
					Point p = new Point((int) (event.getX()/tileLength),(int)(event.getY()/tileLength));
					mapEditor.canvasClicked(p);
	    		}
	    		
	    		
//	    		double tileLength = mapEditor.getLength();
//				Point p = new Point((int) (event.getX()/tileLength),(int)(event.getY()/tileLength));
//				mapEditor.canvasClicked(p);
	    	});
	    	
	    	
	    	
	    	featureComboBox.setItems(featureList);
	    
	    	ToggleGroup toggleGroup = new ToggleGroup();
	    	raiseTileButton.setToggleGroup(toggleGroup);
	    	lowerTileButton.setToggleGroup(toggleGroup);	    	
	    }
	    
	    
	    private void drawLegendOnCanvas(GraphicsContext gcLegend) {
	    	gcLegend.setStroke(Color.BLACK);
	    	for (int i = 0; i < 7; i++) {
	    		gcLegend.strokeRect(50 , (i * 50) + (i * 6) + 1 , 50, 50);
	    		System.out.println((i * 50) + (i * 6) + 1);
	    		gcLegend.setFill(Color.rgb(250-(40*i), 250-(40*i) ,250-(40*i)));
	    		gcLegend.fillRect(50 , (i * 50) + (i * 6) + 1, 50, 50);
	    		
	    		gcLegend.strokeRect(0 , (i * 50) + (i * 6) + 1 , 50, 50);
	    		if (i == 0) {
	    			gcLegend.strokeText("Level" + 0, 10, 28);
	    		} else {
	    			gcLegend.strokeText("Level" + i, 10, 28 + (i*56));
	    		}
	    		
	    	}
	    }
	    
	    private void setLegendSelectedHeight(int y){
	    	if (y > 1 && y <= 57) {
	    		legendSelectedHeight = 0;
	    	} else if (y > 64 && y < 113) {
	    		legendSelectedHeight = 2;
	    	} else if (y > 113 && y < 169) {
	    		legendSelectedHeight = 4;
	    	} else if (y > 169 && y < 225) {
	    		legendSelectedHeight = 6;
	    	} else if (y > 225 && y < 281) {
	    		legendSelectedHeight = 8;
	    	} else if (y > 281 && y < 337) {
	    		legendSelectedHeight = 10;
	    	} else if (y > 337 && y < 400){
	    		legendSelectedHeight = 12;
	    	}
	    }
	    
	   
		
	/**
	 * gets the scale size for the features being added
	 * @return Int the scale of the feature being added to the map
	 */
	@FXML
	int getScale() {
		int scale = (int) scaleSlider.getValue();
		return scale;
	}
	/**
	 * 
	 * @param event, ActionEvent of addFeatureButton clicked
	 * This method first makes sure there is a tile selected and there is only one box selected at a time
	 * Then it will added the selected feature to the tile
	 */
	@FXML
	private void addFeatures(ActionEvent event) {
		LandformsGenerator feature = featureComboBox.getValue();
		mapEditor.drawLandforms(feature, getScale());
	}
	
    @FXML
    private void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }

    /**
     * When clicked, will save the file by calling the App saveAs function
     * @param event
     * @throws IOException
     */
    @FXML
    void saveAsHandler(ActionEvent event) throws IOException {
    	App.saveProjectFile();
    }
    /** 
     * This method handles the dig button, for all selected tiles will lower its elevation if possible
     */
    @FXML
    private void lowerTileButtonHandler() {
    	mapEditor.lowerTile();
    }
    /**
     * this handles the buildButton and elevates the selected tiles if possible
     */
    @FXML
    private void raiseTileButtonHandler() {
    	mapEditor.raiseTile();
    }
    
    /**
     * This meathod will iterate through the selected tiles set and unselect them all
     */
    @FXML
    void unselectAll() {
    	mapEditor.unSelectAllPoints();
    }
    
	

    
    @FXML
    private void featureHelpClicked() {
    	Alert featureInfo = new Alert(AlertType.INFORMATION, "Multiple features are provided in our program.\n\n" + 
    			"Mountain is 5 by 5 feature and the selected box will be the middle of the mountain.\n\n"
    			+ "Volcano is 5 by 5 feature and the selected box will be the middle of the volcano.\n\n"
    			+ "Gate To Hell is a 2 by 2 feature and the selected box will be the top-left of the Gate.\n\n"
    			+ "Trench is a diagnal feature that goes bottom right. Selected box will be the top left of the trench.\n\n"
    			+ "Valley is 5-box vertical line with 2 moutains on left and right. Selected box will be the middle of the valley.");
    	featureInfo.setTitle("Feature Information");
    	featureInfo.setHeaderText("Information:");
    	featureInfo.show();
    }
    
    @FXML
    private void scaleHelpClicked() {
    	Alert scaleInfo = new Alert(AlertType.INFORMATION, "Scale slider is provided in our program\n\n" +
    			"Scale slider determines the scale of the feature from 1 to 5.\n\n" +
    			"Higher the scale is, higher the mountains and volcanoes are.\n\n" +
    			"Higher the scale is, longer the trenches are.\n\n" + 
    			"Higher the scale is, higher the mountains are next to the valley.\n\n" +
    			"Scale won't affect the Gate To Hell. It will remain as 2 by 2 pit.");
    	scaleInfo.setTitle("Scale Information");
    	scaleInfo.setHeaderText("Information:");
    	scaleInfo.show();
    }
    
    @FXML
    private void infoHelpClicked() {
    	Alert pageInfo = new Alert(AlertType.INFORMATION, "Welcome to the Terrain Editor.\n\n" 
    			+ "You can select multiple boxes at the same time and edit.\n" 
    			+ "With Tile Elevation Legend, you can modify the height of the selected tile immediately.\n" 
    			+ "Click Unselect Tiles to unselect all the selected tiles.\n" 
    			+ "Click features to see different features that can be added on the map\n" 
    			+ "You can only select one tile to add the feature.\n"
    			+ "Use the scale slider to modify the size/elevation of selected features\n"
    			+ "Click on feature info under help menu to find out more about features\n"
    			+ "Click Add/Delete Row/Column item under Edit menu to add/delete row/column on the selected tile.\n"
    			+ "3D view will let you view your project in 3D model.\n");
    	pageInfo.setTitle("Map Editor Information");
    	pageInfo.setHeaderText("Information:");
    	pageInfo.show();
    }
    
    @FXML
    private void threeDView() throws Exception {
    	ThreeDController threeDController = new ThreeDController();
		Stage stage = new Stage();
    	threeDController.start(stage);
    	
    }
    
    @FXML
    private void addRow() {
    	mapEditor.addRow();
    }
    
    @FXML
    private void deleteRow() {
    	mapEditor.deleteRow();
    }
    
    @FXML
    private void addColumn() {
    	mapEditor.addColumn();
    }
    
    @FXML
    private void deleteColumn() {
    	mapEditor.deleteColumn();
    }
    
    @FXML
    private void selectTilesOfSameHeight() {
    	mapEditor.selectSameHeight();
    }
}