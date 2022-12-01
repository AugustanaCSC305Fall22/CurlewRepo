package curlew.gameboardeditor.datamodel;

import java.util.LinkedList;


public class UndoRedoHandler {
	
	private LinkedList<UndoRedoAble> undoStack;
	private LinkedList<UndoRedoAble> redoStack;
	private UndoRedoAble map;
	
	public UndoRedoHandler(UndoRedoAble map) {
		undoStack = new LinkedList<>();
		redoStack = new LinkedList<>();
		this.map = map;
		undoStack.push(map.getClone());
	}
	
	public void saveState() {
		undoStack.push(map.getClone());
		redoStack.clear();
	}
	
	public void undo() {
		if(undoStack.size()<=1) {
			System.out.println("Stack Empty");
			return;
		}else {
			redoStack.push(undoStack.pop().getClone());
			map.setState(undoStack.peek().getClone());
		}
		
	}
	
	public void redo() {
		if(redoStack.isEmpty()) {
			return;
		}else {
			UndoRedoAble state =redoStack.pop();
			undoStack.push(state.getClone());
			map.setState(state.getClone());
		}
		
	}

	public void removeState() {
		undoStack.pop();
		
	}

}
