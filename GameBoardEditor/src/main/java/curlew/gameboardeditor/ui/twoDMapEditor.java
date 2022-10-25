package curlew.gameboardeditor.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import curlew.gameboardeditor.datamodel.TerrainMap;

public class twoDMapEditor {
	Canvas twoDCanvas = ThirdController.getTwoDCanvas();
	GraphicsContext gc = twoDCanvas.getGraphicsContext2D();
	//This will come from the new size fxml 
	//double height = SizeController.getHeight();
	//double width = SizeController.getWidth();
	int heightCells = (int) 400 / height;
	int widthCells = (int) 400 / width;
	int totalCells = heightCells * widthCells;
	
	for (int i = 0; i <= heightCells; i++) {
		for (int j = 0; j <= widthCells; j++) {
			gc.setStroke(Color.BLACK);
	    	gc.strokeRect(i * heightCells, j * widthCells, j * heightCells, i * heightCells);
		}
		
	}
	
	
}
