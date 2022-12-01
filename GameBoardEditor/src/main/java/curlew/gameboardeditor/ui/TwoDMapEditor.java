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
import curlew.gameboardeditor.datamodel.UndoRedoHandler;
import curlew.gameboardeditor.generators.LandformGenerator;

public class TwoDMapEditor {
	

	private Canvas canvas;
	private HashSet<Point> pointSet;
	private double length;
	private Point origin;
	private Point end;
	private double[][] copyArray;
	private UndoRedoHandler undoRedoHandler;
	
	/**
	 * Constructs TwoDMapEditor by combining the terrainMap and the twoDMapEditor
	 * Then it sets the data fields from both objects to this object to be used in the MapEditorController
	 * @param map
	 * @param twoDCanvas
	 */
	public TwoDMapEditor(Canvas twoDCanvas) {
		undoRedoHandler = new UndoRedoHandler(App.getMap());
		canvas = twoDCanvas;
		pointSet= new HashSet<>();
		setTileLength();
		draw();
	}
	
	public void setTileLength() {
		length=  (canvas.getHeight()/(Math.max(App.getMap().getColumns(), App.getMap().getRows())));
	}
	
	public void draw() {

		GraphicsContext gc = canvas.getGraphicsContext2D();
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (int i = 0; i < App.getMap().getRows(); i++) {
			for (int j = 0; j < App.getMap().getColumns(); j++) {
				//Sets the default colors for the outline of the canvas
				gc.setStroke(Color.BLACK);
		    	gc.strokeRect(j * length, i * length,length, length);
		    	int height = (int) Math.round(App.getMap().getHeight(i, j));
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
		if (point.x >= 0 && point.x<App.getMap().getColumns() && point.y>=0 && point.y < App.getMap().getRows()) {
		
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
			landform.build(App.getMap(), p.y, p.x, scale);
			pointSet.clear();
			draw();
			undoRedoHandler.saveState();
		}
	}
	
	public void raiseTile() {
		for(Point point:pointSet) {
			try {
				App.getMap().increaseHeightAt(point.y, point.x);
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	
	public void raiseTile(int allivation) {
		for(Point point:pointSet) {
			try {
				App.getMap().setHeightAt(point.y, point.x,allivation);
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	public void lowerTile() {
		for(Point point:pointSet) {
			try {
				App.getMap().decreaseHeightAt(point.y, point.x);
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
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
		App.getMap().addRow(p.y);
		pointSet.clear();
		setTileLength();
		draw();
		undoRedoHandler.saveState();
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
		App.getMap().deleteRow(p.y);
		pointSet.clear();
		setTileLength();
		draw();
		undoRedoHandler.saveState();
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
		App.getMap().addColumn(p.x);
		pointSet.clear();
		setTileLength();
		draw();
		undoRedoHandler.saveState();
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
		App.getMap().deleteColumn(p.x);
		pointSet.clear();
		setTileLength();
		draw();
		undoRedoHandler.saveState();
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
		double height = App.getMap().getHeight(p.y, p.x);
		for(int i=0;i<App.getMap().getRows();i++) {
			for(int j=0;j<App.getMap().getColumns();j++) {
				if(App.getMap().getHeight(i, j)==height) {
					pointSet.add(new Point(j,i));
				}
			}
		}
		draw();
	}

	public void setOrigin(MouseEvent event) {
		origin =convertEventToPoint(event);
	}

	private void drawSelectionRect() {
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
		end=convertEventToPoint(event);
		drawSelectionRect();
	}
	
	private Point convertEventToPoint(MouseEvent event) {
		return new Point((int) (event.getX()/length),(int)(event.getY()/length));
	}

	public boolean isValid(MouseEvent event) {
		Point point = convertEventToPoint(event);
		return (point.x >= 0 && point.x<=App.getMap().getColumns() && point.y>=0 && point.y <= App.getMap().getRows());
	}

	public void squareSelect() {
		
		int startX = (int) Math.min(end.getX(), origin.getX());
		int startY = (int) Math.min(end.getY(), origin.getY());
		int endX = (int) Math.max(end.getX(),origin.getX());
		int endY = (int) Math.max(end.getY(), origin.getY());
		for(int x = startX; x<endX; x++) {
			for(int y= startY; y<endY; y++) {
				pointSet.add(new Point(x,y));
			}
		}
		draw();
	}

	public void squareCopy() {
		int startX = (int) Math.min(end.getX(), origin.getX());
		int startY = (int) Math.min(end.getY(), origin.getY());
		int endX = (int) Math.max(end.getX(),origin.getX());
		int endY = (int) Math.max(end.getY(), origin.getY());
		copyArray =new double[endY-startY][endX-startX];
		for(int x = startX; x<endX; x++) {
			for(int y= startY; y<endY; y++) {
				copyArray[y-startY][x-startX]= App.getMap().getHeight(y, x);
			}
		}
		draw();
	}
	
	public boolean copied() {
		return copyArray!=null;
	}

	public void paste(MouseEvent event) {
		Point point = convertEventToPoint(event);
		for(int i=0;i<copyArray.length;i++) {
			for(int j=0; j<copyArray[0].length;j++) {
				try {
					App.getMap().setHeightAt(point.y+i, point.x+j, copyArray[i][j]);
				}catch(IndexOutOfBoundsException e) {};
			}
		}
		draw();
		undoRedoHandler.saveState();
		
	}

	public void squareClear() {
		int startX = (int) Math.min(end.getX(), origin.getX());
		int startY = (int) Math.min(end.getY(), origin.getY());
		int endX = (int) Math.max(end.getX(),origin.getX());
		int endY = (int) Math.max(end.getY(), origin.getY());
		for(int x = startX; x<endX; x++) {
			for(int y= startY; y<endY; y++) {
				App.getMap().setHeightAt(y, x, App.getMap().getInitialDepth());
			}
		}
		draw();
		undoRedoHandler.saveState();
		
	}

	public void squareMove(MouseEvent event) {
		double[][] temp=new double[0][0];
		if(copied()) {
			temp = copyArray.clone();
		}
		squareCopy();
		squareClear();
		undoRedoHandler.removeState();
		paste(event);
		undoRedoHandler.removeState();
		undoRedoHandler.saveState();
		
		if(temp.length!=0) {
			copyArray =temp;
		}
		
	}
	
	public void undo() {
		undoRedoHandler.undo();
		this.unSelectAllPoints();
		this.setTileLength();
		draw();
	}
	
	public void redo() {
		undoRedoHandler.redo();
		this.unSelectAllPoints();
		this.setTileLength();
		draw();
	}
}




