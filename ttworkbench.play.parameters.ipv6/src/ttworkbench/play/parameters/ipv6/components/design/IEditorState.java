package ttworkbench.play.parameters.ipv6.components.design;

public interface IEditorState extends IBasicControlState {
	
	boolean isset( EditorStateFlag theEditorStateFlag);
	
	void setFlag( EditorStateFlag theEditorStateFlag);
	
	void unsetFlag( EditorStateFlag theEditorStateFlag);
	
}