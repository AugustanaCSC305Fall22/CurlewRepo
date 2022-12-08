package curlew.gameboardeditor.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.Tile2DGeometry;
import curlew.gameboardeditor.generators.MazeMapGenerator;
import curlew.gameboardeditor.generators.RandomMapGenerator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

public class SizeController implements Initializable {
	@FXML
	private Slider columnSlider;
	
	@FXML
	private Slider rowSlider;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Button backButton;
	
	@FXML
    private ChoiceBox<Tile2DGeometry.TileShape> shapeChoiceBox;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(MainMenuController.genMaze) {
			rowSlider.setMin(20);
			columnSlider.setMin(20);
		}
		shapeChoiceBox.getItems().addAll(Tile2DGeometry.TileShape.SQUARE ,Tile2DGeometry.TileShape.HEXAGON);
		shapeChoiceBox.setValue(Tile2DGeometry.TileShape.SQUARE);
	}
	

	
	@FXML
	void clickedNext() throws IOException {
		App.setMap(new TerrainMap((int)rowSlider.getValue(),(int)columnSlider.getValue(), shapeChoiceBox.getValue()));

		if(MainMenuController.genRandom) {
			genRandom();	
		} else if(MainMenuController.genMaze) {
			genMaze();
		}
		App.setRoot("mapEditor");
	}
	
	@FXML
	void clickedBack() throws IOException {
		App.setRoot("mainMenu");
	}
	
	void genRandom() {
		RandomMapGenerator rg= new RandomMapGenerator(App.getMap());
		rg.createMap();
	}
	
	void genMaze() {
		MazeMapGenerator mg = new MazeMapGenerator(App.getMap());
		mg.genrateMaze();
	}
	

}

