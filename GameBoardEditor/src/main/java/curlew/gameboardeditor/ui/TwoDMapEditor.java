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
import curlew.gameboardeditor.datamodel.Tile2DGeometry;
import curlew.gameboardeditor.datamodel.UndoRedoHandler;
import curlew.gameboardeditor.generators.LandformGenerator;

public class TwoDMapEditor {
	

	private Canvas canvas;
	private HashSet<Tile2DGeometry> selectedShapeSet;
	private double scale;
	private Tile2DGeometry origin;
	private Tile2DGeometry end;
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
		selectedShapeSet= new HashSet<>();
		updateScale();
		draw();
	}
	
	public void updateScale() {
		if(App.getMap().getTileShape()== Tile2DGeometry.TileShape.SQUARE) {
			scale=  (canvas.getHeight()/(Math.max(App.getMap().getColumns(), App.getMap().getRows())));
    	}else {
    		scale=  (canvas.getHeight()/(Math.max(App.getMap().getColumns(), App.getMap().getRows())+1));
    	}
	}
	
	public void draw() {
		TerrainMap map = App.getMap();
		GraphicsContext gc = canvas.getGraphicsContext2D();
    	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (int row = 0; row < map.getRows(); row++) {
			for (int col = 0; col < map.getColumns(); col++) {
				Tile2DGeometry shape = map.getShapeAt(row, col);
				gc.setStroke(Color.BLACK);
				
				double[] xCoord =shape.getPolygonXCoords(scale);
				double[] yCoord = shape.getPolygonYCoords(scale);
				gc.strokePolygon(xCoord, yCoord, xCoord.length);
				
		    	int height = (int) Math.round(App.getMap().getHeight(row, col));
				gc.setFill(Color.rgb(250-20*(height),250-20*(height) ,250-20*(height)));	
				gc.fillPolygon(xCoord, yCoord, xCoord.length);

			}
    	}
		
		for(Tile2DGeometry shape:selectedShapeSet) {
			gc.setStroke(Color.AQUA);
			double[] xCoord =shape.getPolygonXCoords(scale);
			double[] yCoord = shape.getPolygonYCoords(scale);
			gc.strokePolygon(xCoord, yCoord, xCoord.length);
		}
		
		
	}
	
	public void canvasClicked(MouseEvent event) {
		
		Tile2DGeometry shape =getShapeFromEvent(event);
		if (shape.getCol() >= 0 && shape.getCol()<App.getMap().getColumns() && shape.getRow()>=0 && shape.getRow() < App.getMap().getRows()) {
		
			if(selectedShapeSet.contains(shape)) {
				selectedShapeSet.remove(shape);
			}
			else {
				selectedShapeSet.add(shape);
			}
			draw();
		}
	}
	
	public void drawLandforms(LandformGenerator landform, int scale) {
		if(selectedShapeSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else if(selectedShapeSet.size()!=1) {
    		unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
    	}
		else if (landform == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText("You must select the feature to be added");
			alert.show();
		} else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			landform.build(App.getMap(), shape.getRow(), shape.getCol(), scale);
			selectedShapeSet.clear();
			draw();
			undoRedoHandler.saveState();
		}
	}
	
	public void raiseTile() {
		for(Tile2DGeometry shape:selectedShapeSet) {
			try {
				App.getMap().increaseHeightAt(shape.getRow(), shape.getCol());
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	
	public void raiseTile(int elevation) {
		for(Tile2DGeometry shape:selectedShapeSet) {
			try {
				App.getMap().setHeightAt(shape.getRow(), shape.getCol(),elevation);
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	public void lowerTile() {
		for(Tile2DGeometry shape:selectedShapeSet) {
			try {
				App.getMap().decreaseHeightAt(shape.getRow(), shape.getCol());
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	

	

	public void unSelectAllPoints() {
		selectedShapeSet.clear();
		draw();
		
	}
	
	public void addRow() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			addRow(shape);
		}
	}
	
	public void addRow(MouseEvent event) {
		Tile2DGeometry shape= getShapeFromEvent(event);
		addRow(shape);
	}
	
	private void addRow(Tile2DGeometry shape) {
		App.getMap().addRow(shape.getRow());
		selectedShapeSet.clear();
		updateScale();
		draw();
		undoRedoHandler.saveState();
	}
	
	public void deleteRow() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			deleteRow(shape);
		}
	}
	
	public void deleteRow(MouseEvent event) {
		deleteRow(getShapeFromEvent(event));
	}
	
	private void deleteRow(Tile2DGeometry shape) {
		App.getMap().deleteRow(shape.getRow());
		selectedShapeSet.clear();
		updateScale();
		draw();
		undoRedoHandler.saveState();
	}
	
	public void addColumn() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			addColumn(shape);
		}
	}
	
	public void addColumn(MouseEvent event) {
		addColumn(getShapeFromEvent(event));
	}
	
	private void addColumn(Tile2DGeometry shape) {
		App.getMap().addColumn(shape.getCol());
		selectedShapeSet.clear();
		updateScale();
		draw();
		undoRedoHandler.saveState();
	}
	
	public void deleteColumn() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			deleteColumn(shape);
		}
	}
	
	public void deleteColumn(MouseEvent event) {
		deleteColumn(getShapeFromEvent(event));
	}
	
	private void deleteColumn(Tile2DGeometry shape) {
		App.getMap().deleteColumn(shape.getCol());
		selectedShapeSet.clear();
		updateScale();
		draw();
		undoRedoHandler.saveState();
	}
	
	
	public void selectSameHeight() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllPoints();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			selectSameHeight(shape);
		}
	}
	
	public void selectSameHeight(MouseEvent event) {
		selectSameHeight(getShapeFromEvent(event));
	}
	
	private void selectSameHeight(Tile2DGeometry shape) {
		TerrainMap map =App.getMap();
		double height = map.getHeight(shape.getRow(), shape.getCol());
		for(int row=0;row<map.getRows();row++) {
			for(int col=0;col<map.getColumns();col++) {
				if(App.getMap().getHeight(row, col)==height) {
					selectedShapeSet.add(map.getShapeAt(row, col));
				}
			}
		}
		draw();
	}

	public void setOrigin(MouseEvent event) {
		origin =getShapeFromEvent(event);
	}
	
	public void setOriginToNull() {
		origin = null;
	}

	private void drawRect(Color color, boolean fill) {
		TerrainMap map = App.getMap();
		draw();
		int startRow = (int) Math.min(end.getRow(), origin.getRow());
		int startCol = (int) Math.min(end.getCol(), origin.getCol());
		int endRow = (int) Math.max(end.getRow(), origin.getRow());
		int endCol = (int) Math.max(end.getCol(), origin.getCol());
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		for (int row = startRow; row < endRow; row++) {
			for (int col = startCol; col < endCol; col++) {
				Tile2DGeometry shape = map.getShapeAt(row, col);
				double[] xCoord =shape.getPolygonXCoords(scale);
				double[] yCoord = shape.getPolygonYCoords(scale);
				if(fill) {
					gc.setFill(color);
					gc.fillPolygon(xCoord, yCoord, xCoord.length);
				}else {
					gc.setStroke(color);
					gc.strokePolygon(xCoord, yCoord, xCoord.length);
				}
			}
		}
	}

	public boolean drawSelectionRect(MouseEvent event) {
		TerrainMap map = App.getMap();
		if(origin!=null) {
			end=this.getShapeFromEvent(event);
			if(end.getCol() ==origin.getCol()) {
				if(end.getCol()>=map.getColumns()) {
					end=map.getShapeAt(end.getRow(), end.getCol()-1);
				}else {
					end= map.getShapeAt(end.getRow(), end.getCol()+1);
				}
				
			}if(end.getRow() == origin.getRow()) {
				if(end.getRow()>=map.getRows()) {
					end=map.getShapeAt(end.getRow()-1, end.getCol());
				}else {
					end=map.getShapeAt(end.getRow()+1, end.getCol());;
				}
			}
			drawRect(Color.rgb(137, 207, 240, 0.5), true);
			return true;
		}else {
			return false;
		}
	}
	
	public void drawMoveSquare() {
		drawRect(Color.RED,false);
	}
	
	private Tile2DGeometry getShapeFromEvent(MouseEvent event) {
		return App.getMap().getTileGeometryContaining(event.getX(),event.getY() , scale);

	}

	public boolean isValidDragEvt(MouseEvent event) {
		Tile2DGeometry shape = getShapeFromEvent(event);
		return (shape.getCol() >=0 && shape.getCol()<=App.getMap().getColumns() && shape.getRow()>=0 && shape.getRow() <= App.getMap().getRows());
	}
	
	public boolean isValidSelectEvt(MouseEvent event) {
		Tile2DGeometry shape = getShapeFromEvent(event);
		return (shape.getCol() >=0 && shape.getCol()<App.getMap().getColumns() && shape.getRow()>=0 && shape.getRow() < App.getMap().getRows());
	}

	public void squareSelect() {
		
		int startRow = (int) Math.min(end.getRow(), origin.getRow());
		int startCol = (int) Math.min(end.getCol(), origin.getCol());
		int endRow = (int) Math.max(end.getRow(), origin.getRow());
		int endCol = (int) Math.max(end.getCol(), origin.getCol());
		for(int row = startRow; row< endRow; row++) {
			for(int col= startCol; col<endCol; col++) {
				selectedShapeSet.add(App.getMap().getShapeAt(row, col));
			}
		}
		draw();
	}

	public void squareCopy() {
		int startRow = (int) Math.min(end.getRow(), origin.getRow());
		int startCol = (int) Math.min(end.getCol(), origin.getCol());
		int endRow = (int) Math.max(end.getRow(), origin.getRow());
		int endCol = (int) Math.max(end.getCol(), origin.getCol());
		copyArray =new double[endRow-startRow][endCol-startCol];
		for(int row = startRow; row< endRow; row++) {
			for(int col= startCol; col<endCol; col++) {
				copyArray[row-startRow][col-startCol]= App.getMap().getHeight(row, col);
			}
		}
		draw();
	}
	
	public boolean copied() {
		return copyArray!=null;
	}

	public void paste(MouseEvent event) {
		Tile2DGeometry shape = getShapeFromEvent(event);
		for(int i=0;i<copyArray.length;i++) {
			for(int j=0; j<copyArray[0].length;j++) {
				try {
					App.getMap().setHeightAt(shape.getRow()+i, shape.getCol()+j, copyArray[i][j]);
				}catch(IndexOutOfBoundsException e) {};
			}
		}
		draw();
		undoRedoHandler.saveState();
		
	}

	public void squareClear() {
		int startRow = (int) Math.min(end.getRow(), origin.getRow());
		int startCol = (int) Math.min(end.getCol(), origin.getCol());
		int endRow = (int) Math.max(end.getRow(), origin.getRow());
		int endCol = (int) Math.max(end.getCol(), origin.getCol());
		for(int row = startRow; row<endRow; row++) {
			for(int col= startCol; col<endCol; col++) {
				App.getMap().setHeightAt(row, col, App.getMap().getInitialDepth());
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
		this.updateScale();
		draw();
	}
	
	public void redo() {
		undoRedoHandler.redo();
		this.unSelectAllPoints();
		this.updateScale();
		draw();
	}
	
	public void setShape(Tile2DGeometry.TileShape shape) {
		App.getMap().setTileShape(shape);
		updateScale();
		draw();
		undoRedoHandler.saveState();
	}
	
}




