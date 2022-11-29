package curlew.gameboardeditor.ui;

import javafx.event.ActionEvent; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.TestClass;
import curlew.gameboardeditor.generators.LandformGenerator;

public class TwoDMapEditor {
	
	private TerrainMap map;
	private Canvas canvas;
	private HashSet<Point> pointSet;
	private double length;
	private Point origin;

	
	/**
	 * Constructs TwoDMapEditor by combining the terrainMap and the twoDMapEditor
	 * Then it sets the data fields from both objects to this object to be used in the MapEditorController
	 * @param map
	 * @param twoDCanvas
	 */
	public TwoDMapEditor(TerrainMap map, Canvas twoDCanvas) {
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
	
	public void canvasClicked(MouseEvent event) {
		
		Point point =convertEventToPoint(event);
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
	
	public void drawLandforms(LandformGenerator landform, int scale) {
		if(pointSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else if(pointSet.size()!=1) {
    		unSelectAllPoints();
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
			landform.build(map, p.y, p.x, scale);
			pointSet.clear();
			draw();
		}
	}
	
	public void raiseTile() {
		for(Point point:pointSet) {
			try {
				map.increaseHeightAt(point.y, point.x);
			}catch (IllegalArgumentException e) {}
		}
		draw();
	}
	
	public void raiseTile(int allivation) {
		for(Point point:pointSet) {
			try {
				map.setHeightAt(point.y, point.x,allivation);
			}catch (IllegalArgumentException e) {}
		}
		draw();
	}
	public void lowerTile() {
		for(Point point:pointSet) {
			try {
				map.decreaseHeightAt(point.y, point.x);
			}catch (IllegalArgumentException e) {}
		}
		draw();
	}
	

	public double getLength() {
		// TODO Auto-generated method stub
		return length;
	}

	public void unSelectAllPoints() {
		pointSet.clear();
		draw();
		
	}
	
	public void addRow() {
		if(pointSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			addRow(p);
		}
	}
	
	public void addRow(MouseEvent event) {
		Point p= convertEventToPoint(event);
		addRow(p);
	}
	
	private void addRow(Point p) {
		map.addRow(p.y);
		pointSet.clear();
		setTileLength();
		draw();
	}
	
	public void deleteRow() {
		if(pointSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			deleteRow(p);
		}
	}
	
	public void deleteRow(MouseEvent event) {
		deleteRow(convertEventToPoint(event));
	}
	
	private void deleteRow(Point p) {
		map.deleteRow(p.y);
		pointSet.clear();
		setTileLength();
		draw();
	}
	
	public void addColumn() {
		if(pointSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			addColumn(p);
		}
	}
	
	public void addColumn(MouseEvent event) {
		addColumn(convertEventToPoint(event));
	}
	
	private void addColumn(Point p) {
		map.addColumn(p.x);
		pointSet.clear();
		setTileLength();
		draw();
	}
	
	public void deleteColumn() {
		if(pointSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			deleteColumn(p);
		}
	}
	
	public void deleteColumn(MouseEvent event) {
		deleteColumn(convertEventToPoint(event));
	}
	
	private void deleteColumn(Point p) {
		map.deleteColumn(p.x);
		pointSet.clear();
		setTileLength();
		draw();
	}
	
	
	public void selectSameHeight() {
		if(pointSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Point> it = pointSet.iterator();
			Point p= it.next();
			selectSameHeight(p);
		}
	}
	
	public void selectSameHeight(MouseEvent event) {
		selectSameHeight(convertEventToPoint(event));
	}
	
	private void selectSameHeight(Point p) {
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

	public void setOrigin(MouseEvent event) {
		origin =convertEventToPoint(event);
	}

	private void drawSelectionRect(Point end) {
		draw();
		double x = Math.min(end.getX(), origin.getX());
		double y = Math.min(end.getY(), origin.getY());
		double width = Math.abs(end.getX()-origin.getX());
		double height = Math.abs(end.getY() - origin.getY());
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLUE);
		gc.strokeRect(x*length, y*length, width*length, height*length);
		
	}

	public void drawSelectionRect(MouseEvent event) {
		Point end =convertEventToPoint(event);
		drawSelectionRect(end);
	}
	
	private Point convertEventToPoint(MouseEvent event) {
		return new Point((int) (event.getX()/length),(int)(event.getY()/length));
	}
}




