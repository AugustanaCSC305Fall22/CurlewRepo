package curlew.gameboardeditor.ui;

import java.io.IOException; 
import java.net.URL;
import java.util.ResourceBundle;

import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.Tile2DGeometry;
import curlew.gameboardeditor.generators.MazeMapGenerator;
import curlew.gameboardeditor.generators.RandomMapGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

/**
 * 
 * @author Team Curlew
 * This class is a controller for SizesScreen fxml
 *
 */
public class SizeController implements Initializable {
	@FXML
	private Slider columnSlider;
	
	@FXML
	private Slider rowSlider;
	
	@FXML
    private ChoiceBox<Tile2DGeometry.TileShape> shapeChoiceBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(MainMenuController.mazeSelected()) {
			rowSlider.setMin(20);
			columnSlider.setMin(20);
		}
		shapeChoiceBox.getItems().addAll(Tile2DGeometry.TileShape.SQUARE ,Tile2DGeometry.TileShape.HEXAGON);
		shapeChoiceBox.setValue(Tile2DGeometry.TileShape.SQUARE);
	}
	
	@FXML
	private void clickedNext() throws IOException {
		App.setMap(new TerrainMap((int)rowSlider.getValue(),(int)columnSlider.getValue(), shapeChoiceBox.getValue()));

		if(MainMenuController.radomSelected()) {
			genRandom();	
		} else if(MainMenuController.mazeSelected()) {
			genMaze();
		}
		App.setRoot("mapEditor");
	}
	
	@FXML
	private void clickedBack() throws IOException {
		App.setRoot("mainMenu");
	}
	
	private void genRandom() {
		RandomMapGenerator rg= new RandomMapGenerator(App.getMap());
		rg.createMap();
	}
	
	private void genMaze() {
		MazeMapGenerator mg = new MazeMapGenerator(App.getMap());
		mg.genrateMaze();
	}
	
}

