

package curlew.gameboardeditor.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import curlew.gameboardeditor.datamodel.GameBoardIO;
import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.Tile2DGeometry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * 
 * @author Team Curlew
 * This class is a controller for PreviewMap fxml
 *
 */
public class PreviewMapController {    

    private static final int BOX_LENGTH = 30;

    @FXML
    private Label mapNameLabel;

    @FXML
    private Canvas previewCanvas;

    @FXML
    private Label titleLabel;
    
    @FXML
    private Canvas templateCanvas;
    
    @FXML
    private ChoiceBox<Tile2DGeometry.TileShape> shapeChoiceBox;
    
    private TwoDMapEditor mapPreview;
    private int selectedAreaIndex;
    private ContextMenu rightClickMenu;
    File[] templateFiles;
    
    private static final ArrayList<String> ORIGINAL_FILES= new ArrayList<String>(Arrays.asList( 
    		"ACE.TMap","cliff.TMap","colussium.TMap","elevated corner.TMap" ,"many walls.TMap", 
    		"Maze.TMap" ,"mountains.TMap", "statue in a pit.TMap","volcano hell.TMap")); 
        
    //create new file that represents the templates folder 
    File templatesFolder = new File("templates/");


    @FXML
    private void initialize() throws JsonSyntaxException, JsonIOException, IOException {
    	//Initializes the canvas, builds the map and makes the 2DEditor
    	
    	rightClickMenu = new ContextMenu();
    	makeFileSelectionMenu();
    	templateCanvas.setHeight((BOX_LENGTH+2)*templateFiles.length);
    	
    	templateCanvas.setOnMouseClicked(evt -> {
    		int y = (int) evt.getY();
        	selectedAreaIndex = y/(BOX_LENGTH+2);
    		
        	if (evt.getButton() == MouseButton.SECONDARY) {
    			createRightClickMenu(evt);
    		}else {
    			try {
    				onMouseClicked(evt);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			rightClickMenu.hide();
    		}
		} );
    	
    	shapeChoiceBox.getItems().addAll(Tile2DGeometry.TileShape.SQUARE ,Tile2DGeometry.TileShape.HEXAGON);
		shapeChoiceBox.setValue(Tile2DGeometry.TileShape.SQUARE);
    	
    	App.setMap(GameBoardIO.loadMap(templateFiles[selectedAreaIndex]));
    	App.getMap().setTileShape(Tile2DGeometry.TileShape.SQUARE);
    	mapNameLabel.setText(templateFiles[selectedAreaIndex].getName().replace(".TMap", ""));
    	
    	mapPreview = new TwoDMapEditor(previewCanvas);
    	
    }
    
	@FXML
    void clickNext(ActionEvent event) throws IOException {
    	App.setRoot("mapEditor");
    }
	@FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }
    
	//Updates the preview according to the file selected
    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
    	mapNameLabel.setText(templateFiles[selectedAreaIndex].getName().replace(".TMap", ""));
    	TerrainMap map = GameBoardIO.loadMap(templateFiles[selectedAreaIndex]);
    	App.setMap(map);
    	mapPreview.setShape(shapeChoiceBox.getValue());
    	mapPreview.updateScale();
    	mapPreview.draw();
    	
    }
    
    // Creates a right click menu
    private void createRightClickMenu(MouseEvent event) {
    	rightClickMenu.getItems().clear();
    	
    	MenuItem selectItem = new MenuItem("Select");
    	MenuItem deleteItem = new MenuItem("Delete");

    	rightClickMenu.getItems().addAll(selectItem, deleteItem);
    	selectItem.setOnAction(eve->{try {
			onMouseClicked(event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
    	deleteItem.setOnAction(eve ->{deleteSelectdFile(event);});
    	if(ORIGINAL_FILES.contains( templateFiles[selectedAreaIndex].getName())) {
    		deleteItem.setDisable(true);
    	}
    	rightClickMenu.show(templateCanvas, event.getScreenX(), event.getScreenY());
    }
    
    // Creates a file selection menu and draws it on the template canvas
    private void makeFileSelectionMenu() {
    	templateFiles = templatesFolder.listFiles();

    	GraphicsContext gc = templateCanvas.getGraphicsContext2D();
    	gc.clearRect(0, 0,templateCanvas.getWidth() , templateCanvas.getHeight());
    	int width = (int) templateCanvas.getWidth();
    	int boxWidth = width - 2;
    	gc.setStroke(Color.BLACK);
    	for (int i = 0; i < templateFiles.length; i++) {
    		gc.strokeRect(2, i * (BOX_LENGTH+2), boxWidth, BOX_LENGTH);
    		gc.strokeText(templateFiles[i].getName().replaceAll(".TMap", ""), (boxWidth/2) -40 , ((BOX_LENGTH + 2) * i) + BOX_LENGTH/2);
    		
    	}
    }

    //Deletes the selected file
    private void deleteSelectdFile(MouseEvent event) {
    	File file = this.templateFiles[selectedAreaIndex];
		
    	ButtonType delete = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
		ButtonType dontDelete = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
		
		Alert saveConfirmation = new Alert(AlertType.CONFIRMATION, "abcd", delete, dontDelete);
		saveConfirmation.setTitle("CONFIRMATION");
		saveConfirmation.setHeaderText("Confirmation Delete");
		saveConfirmation.setContentText("Do you want to delete "+file.getName() + "?");
		if (saveConfirmation.showAndWait().get() == delete) {
			file.delete();
	    	makeFileSelectionMenu();
	    	try {
				App.setMap(GameBoardIO.loadMap(templateFiles[0]));
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	mapPreview.updateScale();
	    	mapPreview.draw();
		} 
    }    
}
