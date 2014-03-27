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
import com.testingtech.muttcn.kernel.Value;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public abstract class VerifyingAdapter<C extends Control,P extends Expression> implements IVerifyingControl<C,P> {
	
	private class VerifyListener implements Listener {
		
		@Override
		public void handleEvent( final Event theOriginalEvent) {
			
			if ( !isVerificationEnabled()) {
				theOriginalEvent.doit = true;
				return;
			}
				
			
			verificationResults.clear();

			String modifiedText = getModifiedTextByEvent( theOriginalEvent);

      VerificationEvent<String> verificationEvent = new VerificationEvent<String>( theOriginalEvent.type, modifiedText, verificationResults);
			
      try {
				beforeVerification( verificationEvent);
				if ( verificationEvent.skipVerification)
					return;

				List<IVerifier<String>> verifiers = verifierMap.get( theOriginalEvent.type);
				for ( IVerifier<String> verifier : verifiers) {
					VerificationResult<String> verificationResult = verifier.verify( modifiedText, verificationEvent.verifierParams);
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

		private void beforeVerification( VerificationEvent<String> theEvent) {
			for ( IVerificationListener listener : listeners) {
				listener.beforeVerification( theEvent);
			}
    }
		
		private void afterVerificationStep( VerificationEvent<String> theEvent) {
			for ( IVerificationListener<String> listener : listeners) {
				listener.afterVerificationStep( theEvent);
			}
		}
		
		private void afterVerification( VerificationEvent<String> theEvent) {
			for ( IVerificationListener listener : listeners) {
				listener.afterVerification( theEvent);
			}
		}
		
		
	}

	private ReentrantLock lock = new ReentrantLock();
	private final C control;
	private final IParameter<P> parameter;
	private final Map<Integer, List<IVerifier<String>>> verifierMap = new HashMap<Integer, List<IVerifier<String>>>();
	private final Set<IVerificationListener<String>> listeners = new HashSet<IVerificationListener<String>>();
	private final List<VerificationResult<String>> verificationResults = new ArrayList<VerificationResult<String>>();
	private boolean verificationEnabled = true;
	
	public VerifyingAdapter( final IParameter<P> theParameter, final Composite theParent, final int theStyle, final IVerifier<String> ... theVerifiers) {
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
	public void addVerifierToEvent( final IVerifier<String> theVerifier, final int theEventType) {
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

	protected abstract String getModifiedTextByEvent( final Event theEvent);
	
	protected abstract C createControl( final Composite theParent, final int theStyle);
	

	@Override
	public List<VerificationResult<String>> getVerificationResults() {
		return verificationResults;
	}
	
	@Override
	public void addListener( final IVerificationListener<String> theListener) {
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
