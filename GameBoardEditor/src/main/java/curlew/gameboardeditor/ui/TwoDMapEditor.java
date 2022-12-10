package curlew.gameboardeditor.ui;

   
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


import java.util.HashSet;
import java.util.Iterator;

import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.Tile2DGeometry;
import curlew.gameboardeditor.datamodel.UndoRedoHandler;
import curlew.gameboardeditor.generators.LandformGenerator;

/**
 * 
 * @author Team Curlew
 * This class is responsible for editing the map and show it in the UI 
 *
 */
public class TwoDMapEditor {
	

	private Canvas canvas;
	private HashSet<Tile2DGeometry> selectedShapeSet;
	private double scale;
	private Tile2DGeometry originOfSelectionArea;
	private Tile2DGeometry endOfSelectionArea;
	private double[][] copyArray;
	private UndoRedoHandler undoRedoHandler;
	
	/**
	 * Constructs TwoDMapEditor by combining the terrainMap and the twoDMapEditor
	 * Then it sets the data fields from both objects to this object to be used in the MapEditorController
	 * @param twoDCanvas
	 */
	public TwoDMapEditor(Canvas twoDCanvas) {
		undoRedoHandler = new UndoRedoHandler(App.getMap());
		canvas = twoDCanvas;
		selectedShapeSet= new HashSet<>();
		updateScale();
		draw();
	}

	/**
	 * Updates the scale when size or the shape of the map changes
	 */
	public void updateScale() {
		if(App.getMap().getTileShape()== Tile2DGeometry.TileShape.SQUARE) {
			scale=  (canvas.getHeight()/(Math.max(App.getMap().getColumns(), App.getMap().getRows())));
    	}else {
    		scale=  (canvas.getHeight()/(Math.max(App.getMap().getColumns(), App.getMap().getRows())+1));
    	}

	}
	
	/**
	 * Draws the map on the canvas
	 */
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
	
	/**
	 * Selects the tile if it is not selected and unselects the tile if it's selected.
	 * @param event the mouse event
	 */
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
	
	/**
	 * Draws the landfor according to the scale
	 * @param landform the landform to be drawn
	 * @param scale the scale of the landform
	 */
	public void drawLandforms(LandformGenerator landform, int scale) {
		if(selectedShapeSet.isEmpty()) {
    		new Alert(AlertType.WARNING, "Select a box first!").show();
    	}else if(selectedShapeSet.size()!=1) {
    		unSelectAllTiles();
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
	
	/**
	 * Raises the height of all the selected tiles
	 */
	public void raiseTile() {
		for(Tile2DGeometry shape:selectedShapeSet) {
			try {
				App.getMap().increaseHeightAt(shape.getRow(), shape.getCol());
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	
	/**
	 * Raises the height of all the selected tile to the elevation provided
	 * @param elevation The elevation needed
	 */
	public void raiseTile(int elevation) {
		for(Tile2DGeometry shape:selectedShapeSet) {
			try {
				App.getMap().setHeightAt(shape.getRow(), shape.getCol(),elevation);
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	
	/**
	 * Lowers the height of all the selected tiles
	 */
	public void lowerTile() {
		for(Tile2DGeometry shape:selectedShapeSet) {
			try {
				App.getMap().decreaseHeightAt(shape.getRow(), shape.getCol());
			}catch (IllegalArgumentException e) {}
		}
		draw();
		undoRedoHandler.saveState();
	}
	
	/**
	 * Unselects all the points
	 */
	public void unSelectAllTiles() {
		selectedShapeSet.clear();
		draw();
	}
	
	/**
	 * Add row to the selected tile
	 */
	public void addRow() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllTiles();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			addRow(shape);
		}
	}
	
	/**
	 * Add row where the mouse was clicked
	 * @param event Mouse event
	 */
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
	
	/**
	 * Delete row of the selected tile
	 */
	public void deleteRow() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllTiles();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			deleteRow(shape);
		}
	}
	
	/**
	 * Delete row on the tile that was clicked
	 * @param event Mouse event
	 */
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
	
	/**
	 * Add column after the column of the selected tile
	 */
	public void addColumn() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllTiles();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			addColumn(shape);
		}
	}
	
	/**
	 * Adds column after the column of the tile that was clicked 
	 * @param event Mouse Event
	 */
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
	
	/**
	 * Deletes the column of the selected tile
	 */
	public void deleteColumn() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllTiles();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			deleteColumn(shape);
		}
	}
	
	/**
	 * Deletes the column of the tile mouse was clicked at
	 * @param event Mouse Event
	 */
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
	
	/**
	 * Selects all the tiles having the same height as the selected tile
	 */
	public void selectSameHeight() {
		if(selectedShapeSet.size()!=1) {
			unSelectAllTiles();
    		new Alert(AlertType.WARNING, "Select only one box!").show();
		}else {
			Iterator<Tile2DGeometry> it = selectedShapeSet.iterator();
			Tile2DGeometry shape= it.next();
			selectSameHeight(shape);
		}
	}
	
	/**
	 * Selects all the tile having the same height as the tile mouse was clicked on
	 * @param event Mouse Event
	 */
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
	
	/**
	 * Sets the origin of selected area.
	 * @param event Mouse Event
	 */
	public void setOriginOfSelectionArea(MouseEvent event) {
		originOfSelectionArea =getShapeFromEvent(event);
	}
	
	/**
	 * Sets the origin of selected area to null
	 */
	public void setOriginOfSelectedAreaToNull() {
		originOfSelectionArea = null;
	}

	private void drawArea(Color color, boolean fill) {
		TerrainMap map = App.getMap();
		draw();
		int startRow = (int) Math.min(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int startCol = (int) Math.min(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
		int endRow = (int) Math.max(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int endCol = (int) Math.max(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
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

	/**
	 * Draws the selection area
	 * @param event MouseEvent
	 * @return True if the area was drawn else false
	 */
	public boolean drawSelectionArea(MouseEvent event) {
		TerrainMap map = App.getMap();
		if(originOfSelectionArea!=null) {
			endOfSelectionArea=this.getShapeFromEvent(event);
			if(endOfSelectionArea.getCol() ==originOfSelectionArea.getCol()) {
				if(endOfSelectionArea.getCol()>=map.getColumns()) {
					endOfSelectionArea=map.getShapeAt(endOfSelectionArea.getRow(), endOfSelectionArea.getCol()-1);
				}else {
					endOfSelectionArea= map.getShapeAt(endOfSelectionArea.getRow(), endOfSelectionArea.getCol()+1);
				}
				
			}if(endOfSelectionArea.getRow() == originOfSelectionArea.getRow()) {
				if(endOfSelectionArea.getRow()>=map.getRows()) {
					endOfSelectionArea=map.getShapeAt(endOfSelectionArea.getRow()-1, endOfSelectionArea.getCol());
				}else {
					endOfSelectionArea=map.getShapeAt(endOfSelectionArea.getRow()+1, endOfSelectionArea.getCol());;
				}
			}
			drawArea(Color.rgb(137, 207, 240, 0.5), true);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Draws the move area
	 */
	public void drawMoveArea() {
		drawArea(Color.RED,false);
	}
	
	private Tile2DGeometry getShapeFromEvent(MouseEvent event) {
		return App.getMap().getTileGeometryContaining(event.getX(),event.getY() , scale);

	}

	/**
	 * Checks whether a drag event is valid or not
	 * @param event Mouse Event
	 * @return True if its a valid drag event else false
	 */
	public boolean isValidDragEvt(MouseEvent event) {
		Tile2DGeometry shape = getShapeFromEvent(event);
		return (shape.getCol() >=0 && shape.getCol()<=App.getMap().getColumns() && shape.getRow()>=0 && shape.getRow() <= App.getMap().getRows());
	}
	
	/**
	 * Checks whether a selection event is valid or not 
	 * @param event Mouse Event
	 * @return True if its a valid selection event else false
	 */
	public boolean isValidSelectEvt(MouseEvent event) {
		Tile2DGeometry shape = getShapeFromEvent(event);
		return (shape.getCol() >=0 && shape.getCol()<App.getMap().getColumns() && shape.getRow()>=0 && shape.getRow() < App.getMap().getRows());
	}

	/**
	 * Selects all the tiles in the selection area
	 */
	public void squareSelect() {
		
		int startRow = (int) Math.min(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int startCol = (int) Math.min(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
		int endRow = (int) Math.max(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int endCol = (int) Math.max(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
		for(int row = startRow; row< endRow; row++) {
			for(int col= startCol; col<endCol; col++) {
				selectedShapeSet.add(App.getMap().getShapeAt(row, col));
			}
		}
		draw();
	}
	
	/**
	 * Copies all the heights of tiles in the selection area
	 */
	public void squareCopy() {
		int startRow = (int) Math.min(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int startCol = (int) Math.min(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
		int endRow = (int) Math.max(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int endCol = (int) Math.max(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
		copyArray =new double[endRow-startRow][endCol-startCol];
		for(int row = startRow; row< endRow; row++) {
			for(int col= startCol; col<endCol; col++) {
				copyArray[row-startRow][col-startCol]= App.getMap().getHeight(row, col);
			}
		}
		draw();
	}
	
	/**
	 * Returns whether something is copied in the clip board or not
	 * @return true if copied else false
	 */
	public boolean copied() {
		return copyArray!=null;
	}
	
	/**
	 * Starts changing the height of tiles according to the copied array.
	 * @param event Mouse Event
	 */
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
	
	/**
	 * Resets the height of the tiles in the selected area to InitialDepth
	 */
	public void squareClear() {
		int startRow = (int) Math.min(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int startCol = (int) Math.min(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
		int endRow = (int) Math.max(endOfSelectionArea.getRow(), originOfSelectionArea.getRow());
		int endCol = (int) Math.max(endOfSelectionArea.getCol(), originOfSelectionArea.getCol());
		for(int row = startRow; row<endRow; row++) {
			for(int col= startCol; col<endCol; col++) {
				App.getMap().setHeightAt(row, col, App.getMap().getInitialDepth());
			}
		}
		draw();
		undoRedoHandler.saveState();
		
	}
	
	/**
	 * Moves the selected area to the tile which was clicked
	 * @param event Mouse Event
	 */
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
	
	/**
	 * Undos the last change in the data model
	 */
	public void undo() {
		undoRedoHandler.undo();
		this.unSelectAllTiles();
		this.updateScale();
		draw();
	}
	
	/**
	 * Redos the last undo
	 */
	public void redo() {
		undoRedoHandler.redo();
		this.unSelectAllTiles();
		this.updateScale();
		draw();
	}
	
	/**
	 * Sets the shape of the TerrainMap
	 * @param shape The shape needed to set the TerraimMap to.
	 */
	public void setShape(Tile2DGeometry.TileShape shape) {
		App.getMap().setTileShape(shape);
		updateScale();
		this.selectedShapeSet.clear();
		draw();
		undoRedoHandler.saveState();
	}
	
}




