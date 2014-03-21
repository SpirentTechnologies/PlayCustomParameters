package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

public abstract class VerifyingAdapter<T extends Widget> implements IVerifyingWidget<T> {
	
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

	private final T widget;
	private final Map<Integer, List<IVerificator<String>>> verificatorMap = new HashMap<Integer, List<IVerificator<String>>>();
	private IVerificationListener<String> listener;
	private final List<VerificationResult<String>> verificationResults = new ArrayList<VerificationResult<String>>();
	
	public VerifyingAdapter( final Composite theParent, final int theStyle, final IVerificator<String> ... theVerificators) {
		super();
		this.widget = createWidget( theParent, theStyle);
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
			listeners = widget.getListeners( eventType);
			for (int i = 0; i < listeners.length; i++) {
				if ( listeners[i] instanceof VerifyingAdapter.VerifyListener)
					continue eventTypeLoop;
			}
			widget.addListener( eventType, getVerifyListener());
		}
	}
	
	private Listener getVerifyListener() {
		return new VerifyListener();
	}

	protected abstract String getModifiedTextByEvent( final Event theEvent);
	
	protected abstract T createWidget( final Composite theParent, final int theStyle);
	

	@Override
	public List<VerificationResult<String>> getVerificationResults() {
		return verificationResults;
	}
	
	@Override
	public void setListener( final IVerificationListener<String> theListener) {
		this.listener = theListener;
	}
	
	@Override
	public final T getEncapsulatedWidget() {
		return widget;
	}

}
