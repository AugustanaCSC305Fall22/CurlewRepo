

package curlew.gameboardeditor.ui;


import java.awt.Desktop;
import java.awt.Point;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;

import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.TestClass;
import curlew.gameboardeditor.generators.GateToHellLandformGenerator;
import curlew.gameboardeditor.generators.LandformGenerator;
import curlew.gameboardeditor.generators.MountainLandformGenerator;
import curlew.gameboardeditor.generators.TrenchLandformGenerator;
import curlew.gameboardeditor.generators.ValleyLandformGenerator;
import curlew.gameboardeditor.generators.VolcanoLandformGenerator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
	    private MenuItem saveAsButton;

	    @FXML
	    private MenuItem saveButton;
	    
	    @FXML
	    private Button backButton;
	    
	    @FXML
	    private ComboBox<LandformGenerator> featureComboBox;
	    
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
	    
	    private boolean creatingSelectionRect;
	    
	    private ObservableList<LandformGenerator> featureList;
	    
		private TwoDMapEditor mapEditor;
		
		private int legendSelectedHeight;

		private boolean moveClicked;
		
		private boolean isSavedRecent;
		
		@FXML
		private BorderPane mainPane;

		private ContextMenu rightClickMenu;

		private ContextMenu squareSelectMenu;


		/**
		 * Initializes the canvas, builds the map and makes the 2DEditor. 
		 * It also has listeners to call the select and unselect methods for the canvas
		 */
	    @FXML
	    private void initialize() {
	    	twoDCanvas.setHeight(400); //delete this and set Height in scene builder directly.
	    	twoDCanvas.setWidth(400);
	    	isSavedRecent = false;
	    	
	    	rightClickMenu = new ContextMenu();
	    	squareSelectMenu = new ContextMenu();
	    	
	    	
	    	TerrainMap map = App.getMap();
	    	mapEditor = new TwoDMapEditor(twoDCanvas);
	    	
	    	featureList = FXCollections.observableArrayList(new MountainLandformGenerator(), new VolcanoLandformGenerator(), new ValleyLandformGenerator(), new TrenchLandformGenerator(), new GateToHellLandformGenerator());

	    	GraphicsContext gcLegend = tileElevationLegendCanvas.getGraphicsContext2D();
	    	
	    	drawLegendOnCanvas(gcLegend);
	    	//Nested for loops to outline the canvas based on the desired size from the user

	    	
	    	tileElevationLegendCanvas.setOnMouseClicked(event -> {
	    		Point p = new Point(Math.round((int) event.getX()), Math.round((int) event.getY()));
	    		setLegendSelectedHeight(p.y);
	    		if (p.x >= 0 && p.x < 100 && p.y >= 0 && p.y <= 400) {
	    			mapEditor.raiseTile(legendSelectedHeight);
	    			isSavedRecent = false;
	    		}
	    		
	    	});
	    	
	    	twoDCanvas.setOnMousePressed(event -> {
				if(!creatingSelectionRect&&!moveClicked&&mapEditor.isValidDragEvt(event)) {
					mapEditor.setOrigin(event);
				}
	    	});
	    	
	    	twoDCanvas.setOnMouseDragged(event -> {
	    		if(mapEditor.isValidDragEvt(event)) {
	    			creatingSelectionRect =mapEditor.drawSelectionRect(event);
	    		}else {
	    			if(!creatingSelectionRect) {
	    				mapEditor.setOriginToNull();
	    			}
	    		}
	    	});
	    	
	    	
	    	twoDCanvas.setOnMouseClicked(event -> {

	    		if (event.getButton() == MouseButton.SECONDARY && mapEditor.isValidSelectEvt(event)) {
	    			if(creatingSelectionRect) {
	    				showSquareSelectionMenu(event);
	    			}else {
	    				showRightClickMenu(event);
	    				squareSelectMenu.hide();
	    				mapEditor.draw();
	    			}
	    			
	    		} else {
	    			if(creatingSelectionRect) {
	    				showSquareSelectionMenu(event);
	    			}else if(moveClicked) {
	    				if(mapEditor.isValidSelectEvt(event)) {
	    					mapEditor.squareMove(event);
		    				moveClicked = false;
	    				}
	    			}
	    			else {
	    				mapEditor.canvasClicked(event);
	    				squareSelectMenu.hide();
	    			}
	    			rightClickMenu.hide();
	    		}
	    		
	    	});
	    	
	    	
	    	
	    	featureComboBox.setItems(featureList);
	    
	    	ToggleGroup toggleGroup = new ToggleGroup();
	    	raiseTileButton.setToggleGroup(toggleGroup);
	    	lowerTileButton.setToggleGroup(toggleGroup);	
	    	
	    	Stage stage = App.getStage();
	    	stage.setOnCloseRequest(e -> {
				try {
					windowExit();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
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
		LandformGenerator feature = featureComboBox.getValue();
		mapEditor.drawLandforms(feature, getScale());
		isSavedRecent = false;
	}
	
    @FXML
    private void clickedBack(ActionEvent event) throws IOException {
    	if (!isSavedRecent) {
    		ButtonType saveAndBack = new ButtonType("Save and Back", ButtonBar.ButtonData.OK_DONE);
    		ButtonType discard = new ButtonType("Discard", ButtonBar.ButtonData.CANCEL_CLOSE);
    		
    		Alert saveConfirmation = new Alert(AlertType.CONFIRMATION, "abcd", saveAndBack, discard);
    		saveConfirmation.setTitle("CONFIRMATION");
    		saveConfirmation.setHeaderText("You have unsaved changes");
    		saveConfirmation.setContentText("Do you save before exiting?");
    		if (saveConfirmation.showAndWait().get() == saveAndBack) {
    			saveHandler();
    		} 
    	}
    	App.setRoot("mainMenu");
    }

    /**
     * When clicked, will save the file by calling the App saveAs function
     * @param event
     * @throws IOException
     */
    @FXML
    void saveAsHandler(ActionEvent event) throws IOException {
    	App.saveAsProjectFile();
    	isSavedRecent = true;
    }
    
    /**
     * When clicked, will save the work and update the existing file and
     * @param event
     * @throws IOException 
     */
    @FXML
    void saveHandler() throws IOException {
    	App.saveProjectFile();
    	isSavedRecent = true;
    }
    

    @FXML
    void exportHandler(ActionEvent event) throws IOException {
    	App.exportFile();
    }
    
    
    @FXML
    void saveAsTemplateHandler(ActionEvent event) throws IOException {
    	App.saveAsTemplate();
    }
    
    /** 
     * This method handles the dig button, for all selected tiles will lower its elevation if possible
     */
    @FXML
    private void lowerTileButtonHandler() {
    	mapEditor.lowerTile();
    	isSavedRecent = false;
    }
    /**
     * this handles the buildButton and elevates the selected tiles if possible
     */
    @FXML
    private void raiseTileButtonHandler() {
    	mapEditor.raiseTile();
    	isSavedRecent = false;
    }
    
    /**
     * This meathod will iterate through the selected tiles set and unselect them all
     */
    @FXML
    void unselectAll() {
    	mapEditor.unSelectAllPoints();
    }
    
	
    
    
    @FXML
    private void helpMenuFeature() {
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
    private void helpMenuScale() {
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
    private void helpMenuInfo() {
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
    	isSavedRecent = false;
    }
    
    @FXML
    private void deleteRow() {
    	mapEditor.deleteRow();
    	isSavedRecent = false;
    }
    
    @FXML
    private void addColumn() {
    	mapEditor.addColumn();
    	isSavedRecent = false;
    }
    
    @FXML
    private void deleteColumn() {
    	mapEditor.deleteColumn();
    	isSavedRecent = false;
    }
    
    @FXML
    private void selectTilesOfSameHeight() {
    	mapEditor.selectSameHeight();
    }
    
    
    private void showRightClickMenu(MouseEvent event) {
    	rightClickMenu.getItems().clear();
    	MenuItem addRowItem = new MenuItem("Add Row");
    	MenuItem delRowItem = new MenuItem("Delete Row");
    	MenuItem addColItem = new MenuItem("Add Column");
    	MenuItem delColItem = new MenuItem("Delete Column");
    	MenuItem sameHeightSelectItem = new MenuItem("Select Same Height");
    	MenuItem closeItem = new MenuItem("Close");
    	MenuItem pasteItem = new MenuItem("Paste");
    	
    	rightClickMenu.getItems().addAll(pasteItem, addRowItem, delRowItem, addColItem, delColItem, sameHeightSelectItem, closeItem);
    	
    	if(!mapEditor.copied()) {
    		pasteItem.setDisable(true);
    	}
    	

    	addRowItem.setOnAction(eve->{mapEditor.addRow(event); isSavedRecent = false;});
    	delRowItem.setOnAction(eve ->{mapEditor.deleteRow(event); isSavedRecent = false;});
    	addColItem.setOnAction(eve->{mapEditor.addColumn(event); isSavedRecent = false;});
    	delColItem.setOnAction(eve->{mapEditor.deleteColumn(event); isSavedRecent = false;});
    	closeItem.setOnAction(eve->{rightClickMenu.hide();});

    	sameHeightSelectItem.setOnAction(eve->{mapEditor.selectSameHeight(event);});
    	pasteItem.setOnAction(eve->{mapEditor.paste(event); isSavedRecent = false;});
    	
    	rightClickMenu.show(twoDCanvas, event.getScreenX(), event.getScreenY());
    }
    
    private void showSquareSelectionMenu(MouseEvent event) {
    	creatingSelectionRect = false;
    	
    	squareSelectMenu.getItems().clear();
    	MenuItem selectAllItem = new MenuItem("Select All");
    	MenuItem copyItem = new MenuItem("Copy");
    	MenuItem moveItem = new MenuItem("Move");
    	MenuItem clearItem = new MenuItem("Clear");
    	MenuItem closeItem = new MenuItem("Close");
    	squareSelectMenu.getItems().addAll(selectAllItem, copyItem, moveItem, clearItem,closeItem);
    	
    	closeItem.setOnAction(eve->{squareSelectMenu.hide(); mapEditor.draw();});
    	selectAllItem.setOnAction(eve->{mapEditor.squareSelect();});
    	copyItem.setOnAction(eve->{mapEditor.squareCopy();});

    	clearItem.setOnAction(eve->{mapEditor.squareClear(); isSavedRecent = false;});
    	moveItem.setOnAction(eve->{moveClicked =true;mapEditor.drawMoveSquare(); isSavedRecent = false;});

    	
    	squareSelectMenu.show(twoDCanvas, event.getScreenX(), event.getScreenY());
		
	}
    
    @FXML
    private void openTutorialVideo() throws URISyntaxException, IOException {
    	final URI uri = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
    	if(Desktop.isDesktopSupported()) {
    		Desktop.getDesktop().browse(uri);
    	}
    }
    
    @FXML
    private void undo() {
    	mapEditor.undo();
    	isSavedRecent = false;
    }
    
    @FXML
    private void redo() {
    	mapEditor.redo();
    	isSavedRecent = false;
    }
    
    
    private void windowExit() throws IOException {
    	if (!isSavedRecent) {
    		ButtonType saveAndExit = new ButtonType("Save and Exit", ButtonBar.ButtonData.OK_DONE);
    		ButtonType discard = new ButtonType("Discard", ButtonBar.ButtonData.CANCEL_CLOSE);
    		
    		Alert saveConfirmation = new Alert(AlertType.CONFIRMATION, "abcd", saveAndExit, discard);
    		saveConfirmation.setTitle("CONFIRMATION");
    		saveConfirmation.setHeaderText("You have unsaved changes");
    		saveConfirmation.setContentText("Do you save before exiting?");
    		if (saveConfirmation.showAndWait().get() == saveAndExit) {
    			saveHandler();
    			Platform.exit();
    		} else {
    			Platform.exit();
    		}
    	}
    	Platform.exit();
    	System.out.println(isSavedRecent);
    }
    
    
}