package ttworkbench.play.parameters.ipv6.customize;

public interface IValidatingEditorLookAndBehaviour extends IEditorLookAndBehaviour {
	
	IEditorLookAndBehaviour getEditorLookAndBehaviour();
	
	IMessageViewLookAndBehaviour getMessagePanelLookAndBehaviour();

	/** 
	 * @return Milliseconds the validation process after a input should be delayed.
	 */
	long getStartValidationDelay();
	
	/** 
	 * @return Milliseconds the corresponding validation hint to a running validation process should be delayed. 
	 * This value is related as well as {@link #getStartValidationDelay()} to the moment of input modification.
	 */
	long getShowValidationInProgressMessageDelay();
	
	
}
