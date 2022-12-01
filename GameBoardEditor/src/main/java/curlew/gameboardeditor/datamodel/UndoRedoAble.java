package curlew.gameboardeditor.datamodel;

public interface UndoRedoAble {
	
	public abstract UndoRedoAble getClone();
	
	public abstract void setState(UndoRedoAble state);
	

}
