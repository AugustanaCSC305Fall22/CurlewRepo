

package curlew.gameboardeditor.ui;

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
    
    private TwoDMapEditor mapPreview;
    

    private static final int BOX_LENGTH=35;
    
    private int selectedAreaIndex;
    File[] templateFiles;
    
    //create new file that represents the templates folder 
    File templatesFolder = new File("templates/");
    
    
    @FXML
    private void initialize() throws JsonSyntaxException, JsonIOException, IOException {
    	//Initializes the canvas, builds the map and makes the 2DEditor
    	
    	
    	templateFiles = templatesFolder.listFiles();
    	templateCanvas.setHeight((BOX_LENGTH+2)*templateFiles.length);
    	GraphicsContext gc = templateCanvas.getGraphicsContext2D();
    	
    	int width = (int) templateCanvas.getWidth();
    	System.out.println(BOX_LENGTH);
    	int boxWidth = width - 2;
    	gc.setStroke(Color.BLACK);
    	for (int i = 0; i < templateFiles.length; i++) {
    		gc.strokeRect(2, i * (BOX_LENGTH+2), boxWidth, BOX_LENGTH);
    		gc.strokeText(templateFiles[i].getName().replaceAll(".TMap", ""), (boxWidth/2) -40 , ((BOX_LENGTH + 2) * i) + BOX_LENGTH/2);
    		
    	}
    	templateCanvas.setOnMouseClicked(evt -> {
			try {
				onMouseClicked(evt);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
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
    	int y = (int) event.getY();
    	selectedAreaIndex = y/(BOX_LENGTH+2);
    	mapNameLabel.setText(templateFiles[selectedAreaIndex].getName().replace(".TMap", ""));
    	TerrainMap map = GameBoardIO.loadMap(templateFiles[selectedAreaIndex]);
    	App.setMap(map);
    	mapPreview.setTileLength();
    	mapPreview.draw();
    	
    }

    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }
    
    

}
