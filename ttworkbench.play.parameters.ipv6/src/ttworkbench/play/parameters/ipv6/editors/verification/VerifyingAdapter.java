package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public abstract class VerifyingAdapter<C extends Control,P> implements IVerifyingControl<C,P> {
	
	private class VerifyListener implements Listener {
		
		@Override
		public void handleEvent( final Event theOriginalEvent) {
			verificationResults.clear();

			String modifiedText = getModifiedTextByEvent( theOriginalEvent);

      VerificationEvent<String> verificationEvent = new VerificationEvent<String>( theOriginalEvent.type, modifiedText, verificationResults);
			
      try {
				beforeVerification( verificationEvent);
				if ( verificationEvent.skipVerification)
					return;

				List<IVerificator<String>> verificators = verificatorMap.get( theOriginalEvent.type);
				for ( IVerificator<String> verificator : verificators) {
					VerificationResult<String> verificationResult = verificator.verify( modifiedText, verificationEvent.verificatorParams);
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
			if ( listener != null)
				listener.beforeVerification( theEvent);
    }
		
		private void afterVerificationStep( VerificationEvent<String> theEvent) {
			if ( listener != null)
				listener.afterVerificationStep( theEvent);
		}
		
		private void afterVerification( VerificationEvent<String> theEvent) {
			if ( listener != null)
				listener.afterVerification( theEvent);
		}
		
		
	}

	
	private final C control;
	private final IParameter<P> parameter;
	private final Map<Integer, List<IVerificator<String>>> verificatorMap = new HashMap<Integer, List<IVerificator<String>>>();
	private IVerificationListener<String> listener;
	private final List<VerificationResult<String>> verificationResults = new ArrayList<VerificationResult<String>>();
	
	public VerifyingAdapter( final IParameter<P> theParameter, final Composite theParent, final int theStyle, final IVerificator<String> ... theVerificators) {
		super();
		this.control = createControl( theParent, theStyle);
		this.parameter = theParameter;
		this.verificatorMap.put( SWT.Verify, new ArrayList( Arrays.asList( theVerificators)));
		updateListener();
	}
	
	@Override
	public void addVerificatorToEvent( final IVerificator<String> theVerificator, final int theEventType) {
		if ( !verificatorMap.containsKey( theEventType))
			verificatorMap.put( theEventType, new ArrayList( Arrays.asList( theVerificator)));
		else
			verificatorMap.get( theEventType).add( theVerificator);
		updateListener();
	}

	private void updateListener() {
		Listener[] listeners;
		eventTypeLoop: for ( Integer eventType : verificatorMap.keySet()) {
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
	public void setListener( final IVerificationListener<String> theListener) {
		this.listener = theListener;
	}
	
	@Override
	public C getControl() {
		return control;
	}

	@Override
	public IParameter<P> getParameter() {
		return parameter;
	}

}
