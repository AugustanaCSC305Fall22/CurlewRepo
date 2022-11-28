package curlew.gameboardeditor.ui;

import javafx.event.ActionEvent; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;

import curlew.gameboardeditor.datamodel.LandformsGenerator;
import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.TestClass;

public class twoDMapEditor {
	
	
	
	private TerrainMap map;
	private Canvas canvas;
	private HashSet<Point> pointSet;
	private double length;
	

	/**
	 * Constructs twoDMapEditor by combining the terrainMap and the twoDMapEditor
	 * Then it sets the data fields from both objects to this object to be used in the MapEditorController
	 * @param map
	 * @param twoDCanvas
	 */
	public twoDMapEditor(TerrainMap map, Canvas twoDCanvas) {
		this.map = map;
		canvas = twoDCanvas;
		pointSet= new HashSet<>();
		setTileLength();
		draw();
	}
	
	public void setTileLength() {
		length=  (canvas.getHeight()/(Math.max(map.getColumns(), map.getRows())));
	}
	
	public void draw() {

		GraphicsContext gc = canvas.getGraphicsContext2D();
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (int i = 0; i < map.getRows(); i++) {
			for (int j = 0; j < map.getColumns(); j++) {
				//Sets the default colors for the outline of the canvas
				gc.setStroke(Color.BLACK);
		    	gc.strokeRect(j * length, i * length,length, length);
		    	int height = (int) Math.round(map.getHeight(i, j));
				gc.setFill(Color.rgb(250-20*(height),250-20*(height) ,250-20*(height)));	
				gc.fillRect(j * length, i * length,length, length);

			}
    	}
		
		for(Point point:pointSet) {
			gc.setStroke(Color.AQUA);
			gc.strokeRect(point.x * length, point.y*length, length, length);
		}
	}
	
	public void canvasClicked(Point point) {
		
		if (point.x >= 0 && point.x<map.getColumns() && point.y>=0 && point.y < map.getRows()) {
		
			if(pointSet.contains(point)) {
				pointSet.remove(point);
			}
			else {
				pointSet.add(point);
			}
			draw();
		}
	}
	
	public void drawLandforms(LandformsGenerator landform, int scale) {
		if(pointSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else if(pointSet.size()!=1) {
    		pointSet.clear();
    		draw();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
    	}
		else if (landform == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText("You must select the feature to be added");
			alert.show();
		} else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			landform.build(p.y, p.x, scale);
			pointSet.clear();
			draw();
		}
	}
	
	public void raiseTile() {
		for(Point point:pointSet) {
			try {
				map.build(point.y, point.x);
			}catch (IllegalArgumentException e) {}
		}
		draw();
	}
	
	public void raiseTile(int allivation) {
		for(Point point:pointSet) {
			try {
				map.build(point.y, point.x,allivation);
			}catch (IllegalArgumentException e) {}
		}
		draw();
	}
	public void lowerTile() {
		for(Point point:pointSet) {
			try {
				map.lowerTile(point.y, point.x);
			}catch (IllegalArgumentException e) {}
		}
		draw();
	}
	

	public double getLength() {
		// TODO Auto-generated method stub
		return length;
	}

	public void unSelectAllPoints() {
		// TODO Auto-generated method stub
		pointSet.clear();
		draw();
		
	}
	
	public void addRow() {
		if(pointSet.size()!=1) {
			pointSet.clear();
    		draw();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			map.addRow(p.y);
			pointSet.clear();
			setTileLength();
			draw();
		}
	}
	
	public void deleteRow() {
		if(pointSet.size()!=1) {
			pointSet.clear();
    		draw();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			map.deleteRow(p.y);
			pointSet.clear();
			setTileLength();
			draw();
		}
	}
	
	public void addColumn() {
		if(pointSet.size()!=1) {
			pointSet.clear();
    		draw();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			map.addColumn(p.x);
			pointSet.clear();
			setTileLength();
			draw();
		}
	}
	
	public void deleteColumn() {
		if(pointSet.size()!=1) {
			pointSet.clear();
    		draw();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			map.deleteColumn(p.x);
			pointSet.clear();
			setTileLength();
			draw();
		}
	}
	
	public void selectSameHeight() {
		if(pointSet.size()!=1) {
			pointSet.clear();
    		draw();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			double height = map.getHeight(p.y, p.x);
			for(int i=0;i<map.getRows();i++) {
				for(int j=0;j<map.getColumns();j++) {
					if(map.getHeight(i, j)==height) {
						pointSet.add(new Point(j,i));
					}
				}
			}
			draw();
		}
	}
}




