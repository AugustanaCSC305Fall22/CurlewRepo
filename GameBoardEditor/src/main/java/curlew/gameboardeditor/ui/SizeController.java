package curlew.gameboardeditor.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import curlew.gameboardeditor.datamodel.MazeGenerator;
import curlew.gameboardeditor.datamodel.RandomMapGenerator;
import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class SizeController implements Initializable {
	@FXML
	private Slider widthSlider;
	
	@FXML
	private Slider lengthSlider;
	
	@FXML
	private Button doneButton;
	
	@FXML
	private Button backButton;
	
	public static int width;

	public static int length;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		length = 10;
		width = 10;
		// TODO Auto-generated method stub
		widthSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				width = (int) widthSlider.getValue();
			}
		
		});
		lengthSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				length = (int) lengthSlider.getValue();
			}
		});
	}
	
	@FXML
	public int getWidth() {
		width = (int) widthSlider.getValue();
    	return width;
	}
	
	@FXML
    public int getLength() {
		length = (int) lengthSlider.getValue();
    	return length;
    }
	
	@FXML
	void clickedDone() throws IOException {
		App.setMap(new TerrainMap(width,length));
		if(MainMenuController.genRandom) {
			if (getLength() >= 20 && getWidth() >= 20) {
				genRandom();
//				RandomMapGenerator rg= new RandomMapGenerator(App.getMap());
//				rg.createMap();
//				App.setRoot("mapEditor");
			} else {
				new Alert(AlertType.WARNING, "A random map must be greater than or equal to 20 by 20.").showAndWait();
				App.setRoot("sizeScreen");
			}
//			sizeCapMazeRan();
//			RandomMapGenerator rg= new RandomMapGenerator(App.getMap());
//			rg.createMap();
		} else if(MainMenuController.genMaze) {
			if (getLength() >= 20 && getWidth() >= 20) {
				genMaze();
//				MazeGenerator mg = new MazeGenerator(App.getMap());
//				mg.genrateMaze();
//				App.setRoot("mapEditor");
			} else {
				new Alert(AlertType.WARNING, "A maze map must be greater than or equal to 20 by 20.").showAndWait();
				App.setRoot("sizeScreen");
			}
//			sizeCapMazeRan();
//			MazeGenerator mg = new MazeGenerator(App.getMap());
//			mg.genrateMaze();
		} else {
			App.setRoot("mapEditor");
		}
//		App.setRoot("mapEditor");
	}
	
	@FXML
	void clickedBack() throws IOException {
		App.setRoot("mainMenu");
	}
	
	void genRandom() throws IOException {
		RandomMapGenerator rg= new RandomMapGenerator(App.getMap());
		rg.createMap();
		App.setRoot("mapEditor");
	}
	
	void genMaze() throws IOException {
		MazeGenerator mg = new MazeGenerator(App.getMap());
		mg.genrateMaze();
		App.setRoot("mapEditor");
	}
	
}

