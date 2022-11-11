

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

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
	    private Button DigButton;

	    @FXML
	    private Canvas twoDCanvas;
	    
	    @FXML
	    private ToggleButton addBlockButton;

	    @FXML
	    private ToggleButton digBlockButton;
	    
	    @FXML
	    private Slider scaleSlider;
	    
	    @FXML
	    private MenuItem scaleHelp;
	    
	    @FXML 
	    private MenuItem featureHelp;
	    
	    ObservableList<LandformsGenerator> featureList;
	    
		private HashSet<Point> pointSet= new HashSet<>();
		
		private twoDMapEditor mapEditor;


		/**
		 * Initializes the canvas, builds the map and makes the 2DEditor. 
		 * It also has listeners to call the select and unselect methods for the canvas
		 */
	    @FXML
	    private void initialize() {
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
				
					if(pointSet.contains(p)) {
						unselectPoint(p);
						pointSet.remove(p);
						neighbourCheck(p);
					}
					else {
						selectBox(p);
						pointSet.add(p);
					}
				}
	    	});
	    	
	    	featureComboBox.setItems(featureList);
	    
	    	ToggleGroup toggleGroup = new ToggleGroup();
	    	addBlockButton.setToggleGroup(toggleGroup);
	    	digBlockButton.setToggleGroup(toggleGroup);	    	
	    }
    
	    /**
	     * 
	     * @param point
	     * takes the point from the mouse and unselects the box if the box is selected
	     */
	    private void unselectPoint(Point point) {
//	    	pointSet.remove(point);
	    	int selectedColIndex = point.x;
			int selectedRowIndex = point.y;
		    GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
			gc.setStroke(Color.BLACK);
			gc.strokeRect(selectedColIndex * mapEditor.boxLengthSize, selectedRowIndex * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);	
//			neighbourCheck(point);
	    	
	    }
		
		private void selectBox(Point point) {
//			pointSet.add(point);
			int selectedColIndex = point.x;
			int selectedRowIndex = point.y;
			GraphicsContext  gc = twoDCanvas.getGraphicsContext2D();
			gc.setStroke(Color.AQUA);
			gc.strokeRect(selectedColIndex * mapEditor.boxLengthSize, selectedRowIndex * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);	
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
		if(pointSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else if(pointSet.size()!=1) {
    		new Alert(AlertType.WARNING, "Select only one box!").show();
    	}
		else if (feature == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText("You must select the feature to be added");
			alert.show();
		} else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			int selectedColIndex = p.x;
			int selectedRowIndex = p.y;
			feature.build(selectedRowIndex, selectedColIndex, getScale());
			TestClass.printMap(App.getMap());
			fillMap();
			drawOutLine();
			pointSet.remove(p);
		}
	}
	
    @FXML
    private void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }

    @FXML

    private void saveAsButtonHandler(ActionEvent event) throws IOException {

    	App.saveProjectFile();
    }
    /** 
     * This method handles the dig button, for all selected tiles will lower its elevation if possible
     */
    @FXML
    private void digButtonHandler() {
    	if(pointSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else {
    		Iterator<Point> it = pointSet.iterator();
    		while (it.hasNext()) {
    			Point p=it.next();
    			int selectedColIndex = p.x;
    			int selectedRowIndex = p.y;
		    	try {
			    	App.getMap().dig(selectedRowIndex, selectedColIndex);
			    	updateTile(p);
		    	}catch (IllegalArgumentException e) {
		    		new Alert(AlertType.WARNING, "You can not go down further!").show();
		    		break;
		    	}
    		}
    	}
    }
    /**
     * this handles the buildButton and elevates the selected tiles if possible
     */
    @FXML
    private void buildButtonHandler() {
    	if(pointSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}
    	else {
    		Iterator<Point> it = pointSet.iterator();
    		while (it.hasNext()) {
    			Point p=it.next();
    			int selectedColIndex = p.x;
    			int selectedRowIndex = p.y;
		    	try {
			    	App.getMap().build(selectedRowIndex, selectedColIndex);
			    	updateTile(p);
		    	}catch (IllegalArgumentException e) {
		    		new Alert(AlertType.WARNING, "You can not build further!").show();
		    		break;
		    	}
    		}
    	}
    }
    
    /**
     * This meathod will iterate through the selected tiles set and unselect them all
     */
    @FXML
    void unselectAll() {
    	Iterator<Point> it = pointSet.iterator();
		while (it.hasNext()){
			Point p=it.next();
			unselectPoint(p);
		}
		pointSet.clear();
    }
    
	/**
	 * 
	 * @param p, Point is passed in and checks the neighboring tiles of the tiles the point is in
	 */
    private void neighbourCheck(Point p) {
    	Point[] nArray= {new Point(p.x,p.y+1),new Point(p.x,p.y-1),new Point(p.x+1,p.y),new Point(p.x-1,p.y)};
    	for(Point point:nArray) {
    		if(pointSet.contains(point)) {
    			selectBox(point);
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
    private void updateTile(Point p) {
    	int selectedColIndex = p.x;
		int selectedRowIndex = p.y;
    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
    	int height = (int) Math.round(App.getMap().getHeight(selectedRowIndex, selectedColIndex));
		gc.setFill(Color.rgb(250-20*(height),250-20*(height) ,250-20*(height)));	
		gc.fillRect((selectedColIndex) * mapEditor.boxLengthSize, (selectedRowIndex) * mapEditor.boxWidthSize, mapEditor.boxLengthSize, mapEditor.boxWidthSize);
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
    			"For mountains, scale 5 gives the highest mountain with center of mountain being maximum height\n\n" +
    			"");
    	scaleInfo.setTitle("Scale Information");
    	scaleInfo.setHeaderText("Information:");
    	scaleInfo.show();
    }
    
}