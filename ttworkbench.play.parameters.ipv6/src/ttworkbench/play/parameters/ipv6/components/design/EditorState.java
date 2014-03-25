package ttworkbench.play.parameters.ipv6.components.design;

import java.util.EnumSet;


public class EditorState extends BasicControlState implements IEditorState {
	
	private EnumSet<EditorStateFlag> editorStateFlags = EnumSet.noneOf( EditorStateFlag.class);
	
	
	public void setFlag( final EditorStateFlag theEditorStateFlag) {
		this.editorStateFlags.add( theEditorStateFlag);
	}
	
	public void unsetFlag( final EditorStateFlag theEditorStateFlag) {
		this.editorStateFlags.remove( theEditorStateFlag);
	}
	
	public void flag( final EditorStateFlag theEditorStateFlag, final boolean isSet) {
		if ( isSet)
			this.editorStateFlags.add( theEditorStateFlag);
		else
			this.editorStateFlags.remove( theEditorStateFlag);	
	}
	
	public boolean isset( final EditorStateFlag theEditorStateFlag) {
		return editorStateFlags.contains( theEditorStateFlag);
	}
}