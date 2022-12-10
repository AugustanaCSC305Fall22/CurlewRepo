package curlew.gameboardeditor.datamodel;

import java.util.LinkedList;
/**
 * This class is responsible for undo or redo an action 
 * in any class that implements the UndoRedoAble interface.
 * 
 * @author Dale Skrien (ported to JavaFX by Forrest Stonedahl)
 * @version 1.0 August 2005 (updated to JavaFX Oct. 2018)
 * @version 2.0 December 2022 updated by team Curlew to make it more extensible 
 */

public class UndoRedoHandler {
	
	private LinkedList<UndoRedoAble> undoStack;
	private LinkedList<UndoRedoAble> redoStack;
	private UndoRedoAble data;
	
	/**
	 * Constructs a UndoRedoHandler
	 */
	public UndoRedoHandler(UndoRedoAble map) {
		undoStack = new LinkedList<>();
		redoStack = new LinkedList<>();
		this.data = map;
		undoStack.push(map.getClone());
	}
	
	/**
	 * Saves a clone of the data in the undoStack
	 */
	public void saveState() {
		undoStack.push(data.getClone());
		redoStack.clear();
	}
	
	/**
	 * Pushes the current state in the redo stack and Undo the last change in the data
	 */
	public void undo() {
		if(undoStack.size()<=1) {
			return;
		}else {
			redoStack.push(undoStack.pop().getClone());
			data.setState(undoStack.peek().getClone());
		}
		
	}
	
	/**
	 * Undos the undo
	 */
	public void redo() {
		if(redoStack.isEmpty()) {
			return;
		}else {
			UndoRedoAble state =redoStack.pop();
			undoStack.push(state.getClone());
			data.setState(state.getClone());
		}
		
	}

	/**
	 * Removes the top state in undo stack
	 */
	public void removeState() {
		undoStack.pop();
		
	}

}
