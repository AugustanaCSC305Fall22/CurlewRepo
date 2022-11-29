

package curlew.gameboardeditor.ui;

import java.io.File;
import java.io.IOException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import curlew.gameboardeditor.datamodel.GameBoardIO;
import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    private final int numTemplates = 8;

    private int boxLength;
    
    private int selectedAreaIndex;
    
    private final String[] fileArray =  {"cliff", "ACE", "statue in a pit", "colussium", "moutains", "volcano hell", "elevated corner", "many walls"};
    
    @FXML
    private void initialize() {
    	//Initializes the canvas, builds the map and makes the 2DEditor
    	
    	GraphicsContext gc = templateCanvas.getGraphicsContext2D();
    	
    	int width = (int) templateCanvas.getWidth();
    	int length = (int) templateCanvas.getHeight();
    	boxLength = (length / numTemplates)-2;
    	int boxWidth = width - 2;
    	gc.setStroke(Color.BLACK);
    	for (int i = 0; i < numTemplates; i++) {
    		gc.strokeRect(2, i * (boxLength+2), boxWidth, boxLength);
    		gc.strokeText(fileArray[i], (boxWidth/2) -40 , ((boxLength + 2) * i) + boxLength/2);
    		
    	}
    	templateCanvas.setOnMouseClicked(evt -> {
			try {
				onMouseClicked(evt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
    }
    @FXML
    void clickNext(ActionEvent event) throws IOException {
    	App.setMap(GameBoardIO.loadMap(new File(fileArray[selectedAreaIndex] + ".TMap")));
    	App.setRoot("mapEditor");
    }
    
    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
    	int y = (int) event.getY();
    	selectedAreaIndex = y/(boxLength+2);
    	mapNameLabel.setText(fileArray[selectedAreaIndex]);
    	TerrainMap map = GameBoardIO.loadMap(new File(fileArray[selectedAreaIndex] + ".TMap"));
    	TwoDMapEditor mapPreview = new TwoDMapEditor(map,previewCanvas);
    }

    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }
    
    

}
