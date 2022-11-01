package curlew.gameboardeditor.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import curlew.gameboardeditor.datamodel.TerrainMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	
//	@FXML
//	public static int onLengthSliderChanged() {
//		length = (int) lengthSlider.getValue();
//		return length;
//	}
//	
//	@FXML
//	public static int onWidthSliderChanged() {
//		width = (int) widthSlider.getValue();
//		return width;
//	}

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
		App.setRoot("third");
	}
	
}

