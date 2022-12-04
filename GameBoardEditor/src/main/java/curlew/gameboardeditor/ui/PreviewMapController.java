

package curlew.gameboardeditor.ui;

import java.awt.Point;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import curlew.gameboardeditor.datamodel.GameBoardIO;
import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class PreviewMapController {    

    @FXML
    private Button backButton;

    @FXML
    private Label mapNameLabel;

    @FXML
    private ScrollBar mapScrollBar;

    @FXML
    private Button nextButton;

    @FXML
    private Canvas previewCanvas;

    @FXML
    private Label titleLabel;
    
    @FXML
    private Canvas templateCanvas;
    
    private TwoDMapEditor mapPreview;
    
    private final int numTemplates = 8;

    private int boxLength;
	private boolean moveClicked;

    
    private int selectedAreaIndex;
    File[] templateFiles;
    
    
    //create new file that represents the templates folder 
    File templatesFolder = new File("templates/");
    
    
    @FXML
    private void initialize() throws JsonSyntaxException, JsonIOException, IOException {
    	//Initializes the canvas, builds the map and makes the 2DEditor
    	
    	
    	templateFiles = templatesFolder.listFiles();
    	
    	GraphicsContext gc = templateCanvas.getGraphicsContext2D();
    	
    	int width = (int) templateCanvas.getWidth();
    	int length = (int) templateCanvas.getHeight();
    	boxLength = (length / numTemplates)-2;
    	int boxWidth = width - 2;
    	gc.setStroke(Color.BLACK);
    	for (int i = 0; i < numTemplates; i++) {
    		gc.strokeRect(2, i * (boxLength+2), boxWidth, boxLength);
    		gc.strokeText(templateFiles[i].getName().replaceAll(".TMap", ""), (boxWidth/2) -40 , ((boxLength + 2) * i) + boxLength/2);
    		
    	}
    	templateCanvas.setOnMouseClicked(evt -> {
    		int y = (int) evt.getY();
        	selectedAreaIndex = y/(boxLength+2);
    		
        	if (evt.getButton() == MouseButton.SECONDARY) {
    			createRightClickMenu(evt);
    		}else {
    			try {
    				onMouseClicked(evt);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
		} );
    	
    	
    	
    	App.setMap(GameBoardIO.loadMap(templateFiles[selectedAreaIndex]));
    	mapNameLabel.setText(templateFiles[selectedAreaIndex].getName().replace(".TMap", ""));
    	
    	mapPreview = new TwoDMapEditor(previewCanvas);
    	
    }
    
	@FXML
    void clickNext(ActionEvent event) throws IOException {
//    	App.setMap(GameBoardIO.loadMap(templateFiles[selectedAreaIndex]));
    	App.setRoot("mapEditor");
    }
    
    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
    	
    	mapNameLabel.setText(templateFiles[selectedAreaIndex].getName().replace(".TMap", ""));
    	TerrainMap map = GameBoardIO.loadMap(templateFiles[selectedAreaIndex]);
    	App.setMap(map);
    	mapPreview.draw();
    	
    }
    
    private void createRightClickMenu(MouseEvent event) {
    	ContextMenu context = new ContextMenu();

    	MenuItem selectItem = new MenuItem("Select");
    	MenuItem deleteItem = new MenuItem("Delete");

    	
    	context.getItems().addAll(selectItem, deleteItem);
    	

    	
    	selectItem.setOnAction(eve->{try {
			onMouseClicked(event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
    	deleteItem.setOnAction(eve ->{deleteSelectdFile(event);});
    	
    	
    	context.show(templateCanvas, event.getScreenX(), event.getScreenY());
    }
    


    private void deleteSelectdFile(MouseEvent event) {
    	
		
	}

	@FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }
    
    

}
