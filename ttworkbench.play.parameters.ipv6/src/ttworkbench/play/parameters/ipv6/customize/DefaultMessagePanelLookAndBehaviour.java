package ttworkbench.play.parameters.ipv6.customize;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class DefaultMessagePanelLookAndBehaviour implements IMessagePanelLookAndBehaviour {

	private int flashDurationInSeconds = 4;
	private int flashDurationOfSuccessMessagesInSeconds = 2;
	
	private Set<Listener> changedListeners = new HashSet<Listener>();
	
	private boolean flashTaggedSuccessMessages = true;
	
	private boolean beep = false;		
	
	@Override
	public void enableBeep() {
		this.beep = true;
	}
	
	@Override
	public boolean isBeepEnabled() {
		return beep;
	}
	
	@Override
	public void disableBeep() {
		this.beep = false;	
	}
	

	@Override
	public void setFlashDuration( int theFlashDurationInSeconds) {
	  this.flashDurationInSeconds = theFlashDurationInSeconds;
	}
	
	@Override
	public int getFlashDuration() {
		return flashDurationInSeconds;
	}
	
	@Override
	public void setFlashDurationOfSuccessMessages(int theFlashDurationInSeconds) {
		flashDurationOfSuccessMessagesInSeconds = theFlashDurationInSeconds;
	}

	@Override
	public int getFlashDurationOfSuccessMessages() {
		return flashDurationOfSuccessMessagesInSeconds;
	}
	
	@Override
	public void setChangedListener(Listener theChangedListener) {
		changedListeners.add( theChangedListener);
	}
	
	@Override
	public void doOnChange() {
		for (Listener changedListener : changedListeners) {
			synchronized (changedListener) {
				changedListener.handleEvent( new Event());
			}
		}
	}
	
	@Override
	public void enableFlashingOfTaggedSuccessMessages() {
		flashTaggedSuccessMessages = true;
	}
	
	@Override
	public void disableFlashingOfTaggedSuccessMessages() {
		flashTaggedSuccessMessages = false;
	}
	
	@Override
  public boolean isFlashingOfTaggedSuccessMessagesEnabled() {
  	return flashTaggedSuccessMessages;
  }


	
	
}
