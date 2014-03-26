package ttworkbench.play.parameters.ipv6.editors.verification;

import java.util.List;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;

public class VerificationResult<T> {
	
	/**
	 * Verifier which has produced this result.
	 */
	public final IVerifier<T> verifier;
	
	/**
	 * input to verify
	 */
	public final T input;
	
	/**
	 * verification succeeded?
	 */
	public final boolean verified;
	
	/**
	 * List of messages
	 */
	public final List<MessageRecord> messages;
	
	
	public VerificationResult( final IVerifier<T> theVerifier, final T theInput, final boolean isVerified, final List<MessageRecord> theMessages) {
		super();
		this.verifier = theVerifier;
		this.input = theInput;
		this.verified = isVerified;
		this.messages = theMessages;
	}

}
