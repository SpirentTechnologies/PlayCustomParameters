package ttworkbench.play.parameters.ipv6;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class ParameterEditorMapper {
	
	private static ConcurrentHashMap<String, String> PARAMETER_TYPE_EDITOR_CLASS_MAP = new ConcurrentHashMap<String, String>();
	static {
		PARAMETER_TYPE_EDITOR_CLASS_MAP.put( "^(UInt\\d{0,2}|Int\\d{0,2})$", "ttworkbench.play.parameters.ipv6.editors.IntegerEditor");
		PARAMETER_TYPE_EDITOR_CLASS_MAP.put( "^(MacLayer|PC_MAC_UCA_HS01)$", "ttworkbench.play.parameters.ipv6.editors.MacAddressEditor");
	}
	
	
	private static ParameterEditorMapper INSTANCE = null;
	private static String BLOCKER = "BLOCKER";

	public static ParameterEditorMapper getInstance() {
		if(INSTANCE==null) {
			synchronized (BLOCKER) {
				if(INSTANCE==null) {
					INSTANCE = new ParameterEditorMapper();
				}
			}
		}
		return INSTANCE;
	}
	
	
	private HashMap<String, Class<? extends IParameterEditor<?>>> cachedEditorClasses = new HashMap<String, Class<? extends IParameterEditor<?>>>();
	
	public ParameterEditorMapper() {
		
	}

	public IParameterEditor<?> getEditor(IParameter<?> theParameter) {
		IParameterEditor<?> editor = null;

		for(Entry<String, String> entryParameterEditor : PARAMETER_TYPE_EDITOR_CLASS_MAP.entrySet()) {
			if(theParameter.getType().matches(entryParameterEditor.getKey())) {
				Class<? extends IParameterEditor<?>> editorType = getEditorTypeByClassPath(entryParameterEditor.getValue());
				if(editorType!=null) {
					editor = getEditorInstanceByType(editorType);
					break;
				}
			}
		}

		if(editor==null) {
			editor = new DefaultEditor();
		}
		return editor;
	}

	private IParameterEditor<?> getEditorInstanceByType(Class<? extends IParameterEditor<?>> theEditorType) {
		IParameterEditor<?> editor = null;
		try {
			editor = theEditorType.newInstance();
		} catch (InstantiationException e) {
			logError( "Could not create an instance of type \""+theEditorType+"\": "+e.getMessage());
		} catch (IllegalAccessException e) {
			logError( "Could not create an instance of type \""+theEditorType+"\": "+e.getMessage());
		}
		return editor;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends IParameterEditor<?>> getEditorTypeByClassPath(String classPath) {
		Class<? extends IParameterEditor<?>> editorType = cachedEditorClasses.get( classPath);
		if(editorType==null) {
			synchronized (this) {

				try {
					Class<?> rawType = getClass().getClassLoader().loadClass( classPath);
					if(IParameterEditor.class.isAssignableFrom( rawType)) {
						
						boolean validConstructor = false;
						for(Constructor<?> constructor : rawType.getConstructors()) {
							if(constructor.getParameterTypes().length==0) {
								validConstructor = true;
							}
						}
						
						if(validConstructor) {
							editorType = (Class<? extends IParameterEditor<?>>) rawType;
							cachedEditorClasses.put( classPath, editorType);
						}
						else {
							logError( "Class \""+rawType+"\" has no valid constructor without parameters.");
						}
					}
					else {
						logError( "Class \""+rawType+"\" is not extending \""+IParameterEditor.class+"\".");
					}
				}
				catch(Exception e) {
					logError( "Class \""+classPath+"\" could not be found.");
				}
			}
		}
		return editorType;
	}

	private void logError(String msg) {
		// TODO logger
		System.err.println( msg);
	}

}
