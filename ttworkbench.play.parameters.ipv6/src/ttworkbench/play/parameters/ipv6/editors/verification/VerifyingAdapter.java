package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

public abstract class VerifyingAdapter<T extends Widget> implements IVerifyingWidget<T> {

	private final T widget;
	private final IVerificator<String>[] verificators;
	private IVerificationListener<String> listener;
	private final List<VerificationResult<String>> verificationResults = new ArrayList<VerificationResult<String>>();
	
	public VerifyingAdapter( final Composite theParent, final int theStyle, final IVerificator<String> ... theVerificators) {
		super();
		this.widget = createWidget( theParent, theStyle);
		this.verificators = theVerificators;
		if ( verificators.length > 0)
			widget.addListener( SWT.Verify, getVerifyListener());
	}

	private Listener getVerifyListener() {
		return new Listener() {
			@Override
			public void handleEvent( final Event theOriginalEvent) {
				verificationResults.clear();

				String modifiedText = getModifiedTextByEvent( theOriginalEvent);

        VerificationEvent<String> verificationEvent = new VerificationEvent<String>( modifiedText, verificationResults);
				
        try {
					beforeVerification( verificationEvent);
					if ( verificationEvent.skipVerification)
						return;

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
			
		};
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
