/*******************************************************************************
 * Copyright (c)  2014 Johannes Dahlke, Thomas Büttner, Alexander Dümont, Fares Mokrani
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
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
 * Contributors: Johannes Dahlke, Thomas Büttner, Alexander Dümont, Fares Mokrani
 ******************************************************************************/
package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.testingtech.muttcn.kernel.Expression;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public abstract class VerifyingAdapter<C extends Control, P extends Expression, T> implements IVerifyingControl<C,P,T> {
	
	private class VerifyListener implements Listener {
		
		@Override
		public void handleEvent( final Event theOriginalEvent) {
			
			if ( !isVerificationEnabled()) {
				theOriginalEvent.doit = true;
				return;
			}
				
			
			verificationResults.clear();

			T modifiedText = getModifiedTextByEvent( theOriginalEvent);

      VerificationEvent<T> verificationEvent = new VerificationEvent<T>( theOriginalEvent.type, modifiedText, verificationResults);
			
      try {
				beforeVerification( verificationEvent);
				if ( verificationEvent.skipVerification)
					return;

				List<IVerifier<T>> verifiers = verifierMap.get( theOriginalEvent.type);
				for ( IVerifier<T> verifier : verifiers) {
					VerificationResult<T> verificationResult = verifier.verify( modifiedText, verificationEvent.verifierParams);
					verificationResults.add( verificationResult);

					afterVerificationStep( verificationEvent);
					if ( verificationEvent.skipVerification)
						return;
				}

				afterVerification( verificationEvent);
			} finally {
				theOriginalEvent.doit = verificationEvent.doit;
			}
		}

		private void beforeVerification( VerificationEvent<T> theEvent) {
			for ( IVerificationListener<T> listener : listeners) {
				listener.beforeVerification( theEvent);
			}
    }
		
		private void afterVerificationStep( VerificationEvent<T> theEvent) {
			for ( IVerificationListener<T> listener : listeners) {
				listener.afterVerificationStep( theEvent);
			}
		}
		
		private void afterVerification( VerificationEvent<T> theEvent) {
			for ( IVerificationListener<T> listener : listeners) {
				listener.afterVerification( theEvent);
			}
		}
		
		
	}

	private ReentrantLock lock = new ReentrantLock();
	private final C control;
	private final IParameter<P> parameter;
	private final Map<Integer, List<IVerifier<T>>> verifierMap = new HashMap<Integer, List<IVerifier<T>>>();
	private final Set<IVerificationListener<T>> listeners = new HashSet<IVerificationListener<T>>();
	private final List<VerificationResult<T>> verificationResults = new ArrayList<VerificationResult<T>>();
	private boolean verificationEnabled = true;
	
	public VerifyingAdapter( final IParameter<P> theParameter, final Composite theParent, final int theStyle, final IVerifier<T> ... theVerifiers) {
		super();
		this.control = createControl( theParent, theStyle);
		this.parameter = theParameter;
		this.verifierMap.put( SWT.Verify, new ArrayList( Arrays.asList( theVerifiers)));
		updateListener();
	}
	
	public boolean isVerificationEnabled() {
		return verificationEnabled ;
	}

	@Override
	public void addVerifierToEvent( final IVerifier<T> theVerifier, final int theEventType) {
		if ( !verifierMap.containsKey( theEventType))
			verifierMap.put( theEventType, new ArrayList( Arrays.asList( theVerifier)));
		else
			verifierMap.get( theEventType).add( theVerifier);
		updateListener();
	}

	private void updateListener() {
		Listener[] listeners;
		eventTypeLoop: for ( Integer eventType : verifierMap.keySet()) {
			listeners = control.getListeners( eventType);
			for (int i = 0; i < listeners.length; i++) {
				if ( listeners[i] instanceof VerifyingAdapter.VerifyListener)
					continue eventTypeLoop;
			}
			control.addListener( eventType, getVerifyListener());
		}
	}
	
	private Listener getVerifyListener() {
		return new VerifyListener();
	}

	protected abstract T getModifiedTextByEvent( final Event theEvent);
	
	protected abstract C createControl( final Composite theParent, final int theStyle);
	

	@Override
	public List<VerificationResult<T>> getVerificationResults() {
		return verificationResults;
	}
	
	@Override
	public void addListener( final IVerificationListener<T> theListener) {
		listeners.add( theListener);
	}
	
	@Override
	public C getControl() {
		return control;
	}

	@Override
	public IParameter<P> getParameter() {
		return parameter;
	}
	
	public void enableVerification() {
		verificationEnabled = true;
	}

	public void disableVerification() {
		verificationEnabled = false;
	}
	
	@Override
	public final void forceText(String theText) {
		boolean verificationWasEnabled = verificationEnabled;
		disableVerification();
		setText( theText);
		if ( verificationWasEnabled)
			enableVerification();
	}
	
}
