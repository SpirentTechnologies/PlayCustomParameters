/*******************************************************************************
 * Copyright (c)  .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * You may not use this file except in compliance with the License.
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * This project came to life under the cooperation of the Authors (cited below) and the Testing Technologies GmbH company in the frame of a University Project proposed by the FU-Berlin.
 * 
 * The software is basically a plug-in for the company's eclipse-based framework TTWorkbench. The plug-in offers a new user-friendly view that enables easy configuration of parameters meant to test IPv6 environments.
 * 
 * 
 * Contributors:
 *     
 ******************************************************************************/
package ttworkbench.play.parameters.ipv6.editors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.parameters.ipv6.components.design.ComponentState;
import ttworkbench.play.parameters.ipv6.components.design.ControlState;
import ttworkbench.play.parameters.ipv6.components.design.ControlStateFlag;
import ttworkbench.play.parameters.ipv6.components.design.EditorState;
import ttworkbench.play.parameters.ipv6.components.design.EditorStateFlag;
import ttworkbench.play.parameters.ipv6.components.design.IEditorState;
import ttworkbench.play.parameters.ipv6.components.messaging.controls.IMessageContainer;
import ttworkbench.play.parameters.ipv6.customize.IEditorLookAndBehaviour;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public abstract class AbstractEditor<T> implements IParameterEditor<T> {

  private EditorState editorState = new EditorState();
	
	private String title;
	private String description;
	private IParameter<T> parameter;
	private IConfiguration configuration;
	private Set<T> values = new TreeSet<T>();
	private Composite control;
	
	private IEditorLookAndBehaviour lookAndBehaviour;
	
	private Map<String,String> attributes = new HashMap<String, String>();

	
	public AbstractEditor( final String theTitle, final String theDescription) {
		super();
		this.title = theTitle;
		this.description = theDescription;
		setLookAndBehaviour( getDefaultLookAndBehaviour());
		editorState.setComponentState( ComponentState.CONSTRUCTED);
	}
	
	protected Set<T> getValues() {
		return values;
	}
	
	@Override
	public void setEnabled( boolean theEnabledState) {
	  editorState.flag( ControlStateFlag.ENABLED, theEnabledState);	
	  applySettings();
	}
	
	public void applySettings() {
		if ( editorState.isset( ControlState.CREATED)) {
			
			// Composite.isVisible() behaves strange:
			//  when the parent object is currently not shown, it'll return false.
			//  this is why flag-checking here returns wrong answers.
			
		  // if ( control.isVisible() != visible)
		  	control.setVisible( editorState.isset( ControlStateFlag.VISIBLE));

		  if ( control.isEnabled() != editorState.isset( ControlStateFlag.ENABLED))
		  	control.setEnabled( editorState.isset( ControlStateFlag.ENABLED));
		  
	  }
	}

	@Override
	public boolean isEnabled() {
	  // Enabled in terms of "is not intended disabled".
	  // Thereby ignore that the enable state of the wrapped control in relation to this may be sometime inconsistent. 
		return hasControl() && editorState.isset( ControlStateFlag.ENABLED);
	}

	@Override
	public void setVisible( boolean theVisibleState) {
		editorState.flag( ControlStateFlag.VISIBLE, theVisibleState);
	  applySettings();
	}


	@Override
	public boolean isVisible() {
		// Visible in terms of "is not intended hide".
	  // Thereby ignore that the visible state of the wrapped control in relation to this may be sometime inconsistent. 
		return hasControl() && editorState.isset( ControlStateFlag.VISIBLE);
				//&& control.isVisible();
	}

	
	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setAttribute(String theName, String theValue) {
		attributes.put( theName, theValue);
		
		// handle advanced attribute  
		if ( theName.equalsIgnoreCase( "advanced")) {
			editorState.flag( EditorStateFlag.ADVANCED_MODE, !theValue.equalsIgnoreCase( "false"));
		}
	}
	
	protected String getAttribute( String theName) {
		return attributes.get( theName);
	}

	@Override
	public void parametersChanged(List<IParameter<?>> theParameters) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateParameter() {
		editorState.setFlag( EditorStateFlag.UPDATE_PARAMETER);
		reloadParameter();
		editorState.unsetFlag( EditorStateFlag.UPDATE_PARAMETER);
	}
	
  /**
   * reloadParameter is called after the parameter value has changed 
   * to ensure the current value displayed in the editor is the actual parameter value.  
   */
	public abstract void reloadParameter();
	
	public void setAdvancedMode() {
		editorState.setFlag( EditorStateFlag.ADVANCED_MODE);
	}
	
  public void setNormalMode() {
  	editorState.unsetFlag( EditorStateFlag.ADVANCED_MODE);	
	}
  
  public boolean isAdvancedMode() {
  	return editorState.isset( EditorStateFlag.ADVANCED_MODE);	
	}
  
	@Override
	public void setParameter(IParameter<T> theParameter) {
		if(theParameter == null) {
			throw new IllegalArgumentException( "Parameter provided is null.");
		}
		this.parameter = theParameter;
	}
	
	@Override
	public IParameter<T> getParameter() {
		return parameter;
	}
	
	@Override
	public void setConfiguration(IConfiguration theConfiguration) {
	  this.configuration = theConfiguration;
	}
	
	protected IConfiguration getConfiguration() {
		return configuration;
	}

	protected void loadProvidedValues() {
		if ( configuration != null) {
			Set<IParameterValueProvider> competentValueProviders = configuration.getValueProviders( parameter);
			for (IParameterValueProvider competentValueProvider : competentValueProviders) {
				values.addAll( competentValueProvider.getAvailableValues( parameter));
			}
		}
	}
	
	
	@Override
	public void updateConfig() {
		editorState.setFlag( EditorStateFlag.UPDATE_CONFIG);
    loadProvidedValues();
		editorState.unsetFlag( EditorStateFlag.UPDATE_CONFIG);
	}
	
	
	public void updateControl() {
		editorState.setFlag( EditorStateFlag.UPDATE_CONTROL);
		lookAndBehaviour.doOnChange();
		editorState.unsetFlag( EditorStateFlag.UPDATE_CONTROL);
	}
	
	protected void setLookAndBehaviour(IEditorLookAndBehaviour theLookAndBehaviour) {
		this.lookAndBehaviour = theLookAndBehaviour;
	}
	
	public IEditorLookAndBehaviour getLookAndBehaviour() {
		return lookAndBehaviour;
	}
	
	protected abstract IEditorLookAndBehaviour getDefaultLookAndBehaviour();
	
	
	@Override
	public final Composite createControl( Composite theParent) {
		editorState.setControlState( ControlState.CREATING);
		control = new Composite( theParent, SWT.None);
	  control.addDisposeListener( new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent theArg0) {
				editorState.setControlState( ControlState.DISPOSED);
			}
		});
		
	  designControl( control); 
	  applySettings();
	  
	  editorState.setControlState( ControlState.CREATED);
	  return control;
	}
	
	protected abstract void designControl( final Composite theControl);
	

	public Composite getControl() {
		return control;
	}
	
	public boolean hasControl() {
		return editorState.isset( ControlState.CREATED);
	}
	
	public IEditorState getState() {
		return editorState;
	}
	
	private boolean setFocusToSomeChild( final Composite theControl) {
		for ( Control ctrl : theControl.getChildren()) {
			ctrl.setFocus();
			if ( ctrl.isFocusControl() && !(ctrl instanceof IMessageContainer))
				return true;
			if ( ctrl instanceof Composite)
				if ( setFocusToSomeChild( (Composite) ctrl))
				  return true;
		}	
		return false;
	}
	
	/**
	 * For performance and specificity reasons overriding this method is recommended.
	 */
	public void setFocus() {
		if ( hasControl()) {	
			control.setFocus();
			if ( !control.isFocusControl())
				setFocusToSomeChild( control);
		}
	}
	
	
	@Override
	public String toString() {
		return "\""+getParameter().getId()+"\"@"+hashCode();
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		editorState.setComponentState( ComponentState.DESTROYING);
		super.finalize();
		editorState.setComponentState( ComponentState.DESTROYED);
	}
	
}


