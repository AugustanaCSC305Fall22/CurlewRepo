package curlew.gameboardeditor.datamodel;
/**
 * 
 * @author Team Curlew
 * This interface makes it possible for every object that implements the interface to be able to undo and redo.  
 *
 */
public interface UndoRedoAble {
	
	/**
	 * Returns the clone of the object
	 * @return The clone of the object
	 */
	public abstract UndoRedoAble getClone();
	
	/**
	 * Sets the object to the state provided
	 * @param state The state to set the object to
	 */
	public abstract void setState(UndoRedoAble state);
	

}
