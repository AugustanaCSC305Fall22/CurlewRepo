package curlew.gameboardeditor.ui;

import java.io.IOException;

import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class SizeController {
	@FXML
	private static Slider widthSlider;
	
	@FXML
	private static Slider lengthSlider;
	
	@FXML
	private Button doneButton;
	
	@FXML
	private TerrainMap terrain;
	
	public int width;
	public int length;
	
	
//	@FXML
//    int widthSliderHandler (ActionEvent event) throws IOException {
//    	int width = (int) widthSlider.getValue();
//    	return width;
//    }

//	@FXML
//	public static int getWidth() {
//		int width = (int) widthSlider.getValue();
//    	return width;
//	}
	
//	@FXML
//    int lengthSliderHandler (ActionEvent event) throws IOException {
//		int length = (int) widthSlider.getValue();
//    	return length;
//    }
	
//	@FXML
//    public static int getLength() {
//		int length = (int) widthSlider.getValue();
//    	return length;
//    }
	
	@FXML
	public static int onLengthSliderChanged() {
		int length = (int) lengthSlider.getValue();
		return length;
	}
	
	@FXML
	public static int onWidthSliderChanged() {
		int width = (int) widthSlider.getValue();
		return width;
	}
	
	@FXML
	void clickedDone() throws IOException {
		App.setRoot("third");
	}
}

