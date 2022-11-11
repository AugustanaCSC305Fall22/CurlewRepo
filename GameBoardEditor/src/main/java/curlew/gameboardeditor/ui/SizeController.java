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
	public static int width;

	
	

	@FXML
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
			if (getLength() >= 20 || getWidth() >= 20) {
				RandomMapGenerator rg= new RandomMapGenerator(App.getMap());
				rg.createMap();
				App.setRoot("mapEditor");
			} else {
				new Alert(AlertType.WARNING, "Width and Length must be equal to or greater than 20.").showAndWait();
				App.setRoot("sizeScreen");
			}
		}else if(MainMenuController.genMaze) {
			if (getLength() >= 20 || getWidth() >= 20) {
				MazeGenerator mg = new MazeGenerator(App.getMap());
				mg.genrateMaze();
				App.setRoot("mapEditor");
			} else {
				new Alert(AlertType.WARNING, "Width and Length must be equal to or greater than 20.").showAndWait();
				App.setRoot("sizeScreen");
			}
		}
//		App.setRoot("mapEditor");
	}
	
//	void sizeCapMazeRandom() throws IOException {
//		if (!MainMenuController.genMaze || !MainMenuController.genRandom) {
//			if (getLength() < 20 || getWidth() < 20) {
//				new Alert(AlertType.WARNING, "Width and Length must be equal to or greater than 20.").showAndWait();
//				App.setRoot("sizeScreen");
//			} else {
//				App.setRoot("mapEditor");
//			}
//		}
//	}
	
}

