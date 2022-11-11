

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
	    
		private HashSet<Point> pointSet= new HashSet<>();
		
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
	    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
	    	TerrainMap map = App.getMap();
	    	mapEditor = new twoDMapEditor(map, twoDCanvas);
	    	
	    	featureList = FXCollections.observableArrayList(new MountainGenerator(map), new VolcanoGenerator(map), new ValleyGenerator(map), new TrenchGenerator(map), new GateToHellGenerator(map));

	    	GraphicsContext gcLegend = tileElevationLegendCanvas.getGraphicsContext2D();
	    	
	    	drawLegendOnCanvas(gcLegend);
	    	//Nested for loops to outline the canvas based on the desired size from the user
	    	fillMap();
	    	drawOutLine();
	    	
	    	tileElevationLegendCanvas.setOnMouseClicked(event -> {
	    		Point p = new Point(Math.round((int) event.getX()), Math.round((int) event.getY()));
	    		if (p.x >= 0 && p.x < 100 && p.y >= 0 && p.y <= 400) {
	    			if(pointSet.isEmpty()) {
	    	    		new Alert(AlertType.WARNING, "Select a box first!").show();
	    	    	} else {
	    	    		Iterator<Point> it = pointSet.iterator();
	    	    		setLegendSelectedHeight(p.y);
	    	    		System.out.println(legendSelectedHeight);
	    	    		while (it.hasNext()) {
	    	    			Point point = it.next();
	    					int selectedColIndex = point.x;
	    					int selectedRowIndex = point.y;
	    					map.build(selectedRowIndex, selectedColIndex, legendSelectedHeight);
	    					fillMap();
	    	    		}
	    	    		unselectAll();
	    	    		drawOutLine();
	    	    		
	    	    	}
	    		}
	    		
	    	});
	    	
	    	twoDCanvas.setOnMouseClicked(event -> {
	    		//find the closest x box corrdinates, then find the closest y
	    		int tileLength = mapEditor.tileLengthSize;
				int tileWidth = mapEditor.tileWidthSize;
				Point p = new Point(Math.round((int)event.getX()/(tileLength)), Math.round((int)event.getY()/(tileWidth)));
				
				if (p.x >= 0 && p.x<App.getMap().getColumns() && p.y>=0 && p.y < App.getMap().getRows()) {
				
					if(pointSet.contains(p)) {
						unselectPoint(p);
						pointSet.remove(p);
						neighbourCheck(p);
					}
					else {
						selectTile(p);
						pointSet.add(p);
					}
				}
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
	     * @param point
	     * takes the point from the mouse and unselects the box if the box is selected
	     */
	    private void unselectPoint(Point point) {
//	    	pointSet.remove(point);
	    	int selectedColIndex = point.x;
			int selectedRowIndex = point.y;
		    GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
			gc.setStroke(Color.BLACK);
			gc.strokeRect(selectedColIndex * mapEditor.tileLengthSize, selectedRowIndex * mapEditor.tileWidthSize, mapEditor.tileLengthSize, mapEditor.tileWidthSize);	
//			neighbourCheck(point);
	    	
	    }
	    /**
	     * @param point
	     * takes the point from the mouse and selects the box if the box is unselected
	     */
		private void selectTile(Point point) {
//			pointSet.add(point);
			int selectedColIndex = point.x;
			int selectedRowIndex = point.y;
			GraphicsContext  gc = twoDCanvas.getGraphicsContext2D();
			gc.setStroke(Color.AQUA);
			gc.strokeRect(selectedColIndex * mapEditor.tileLengthSize, selectedRowIndex * mapEditor.tileWidthSize, mapEditor.tileLengthSize, mapEditor.tileWidthSize);	
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
    	if(pointSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else {
    		Iterator<Point> it = pointSet.iterator();
    		while (it.hasNext()) {
    			Point p=it.next();
    			int selectedColIndex = p.x;
    			int selectedRowIndex = p.y;
		    	try {
			    	App.getMap().lowerTile(selectedRowIndex, selectedColIndex);
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
    private void raiseTileButtonHandler() {
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
    			selectTile(point);
    		}
    	}
    }




	private void drawOutLine() {
    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
    	for (int i = 0; i < mapEditor.numCols; i++) {
			for (int j = 0; j < mapEditor.numRows; j++) {
				//Sets the default colors for the outline of the canvas
				gc.setStroke(Color.BLACK);
		    	gc.strokeRect(i * mapEditor.tileLengthSize, j * mapEditor.tileWidthSize, mapEditor.tileLengthSize, mapEditor.tileWidthSize);	

			}
    	}
    }
    private void fillMap() {
    	
    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
    	for (int i = 0; i < mapEditor.numRows; i++) {
			for (int j = 0; j < mapEditor.numCols; j++) {
				int height = (int) Math.round(App.getMap().getHeight(i, j));
				gc.setFill(Color.rgb(250-20*(height),250-20*(height) ,250-20*(height)));	
				gc.fillRect((j) * mapEditor.tileLengthSize, (i) * mapEditor.tileWidthSize, mapEditor.tileLengthSize, mapEditor.tileWidthSize);
			}
    	}
    }
    private void updateTile(Point p) {
    	int selectedColIndex = p.x;
		int selectedRowIndex = p.y;
    	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
    	int height = (int) Math.round(App.getMap().getHeight(selectedRowIndex, selectedColIndex));
		gc.setFill(Color.rgb(250-20*(height),250-20*(height) ,250-20*(height)));	
		gc.fillRect((selectedColIndex) * mapEditor.tileLengthSize, (selectedRowIndex) * mapEditor.tileWidthSize, mapEditor.tileLengthSize, mapEditor.tileWidthSize);
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