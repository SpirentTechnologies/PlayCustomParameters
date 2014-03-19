package ttworkbench.play.parameters.ipv6.validators;

import java.math.BigInteger;

public abstract class FibValidator extends ContextualValidator {
	
	public static class ParameterKey {
		static final int PX_N = 0;
		static final int PX_FIB_NUMBER = 1;
		static final int PX_FIB_SUCC_NUMBER = 2;
	}

	
	final static BigInteger[] fibonacciSequence = new BigInteger[256];
	static {
		fibonacciSequence[0] = new BigInteger( "0");
		fibonacciSequence[1] = new BigInteger( "1");
		for ( int i = 2; i < 256; i++) {
			fibonacciSequence[i] = fibonacciSequence[i-2].add( fibonacciSequence[i-1]);
		}
	}

	public FibValidator( String theTitle) {
		super( theTitle, "");
	}
	

	public FibValidator with(IValidatorContext theContext) {
		setContext( theContext);
		return this;
	}
	
	
	protected BigInteger getFibonacciNumber( BigInteger theValue) {
		if ( theValue.intValue() > 255)
			return new BigInteger( "0");
		return fibonacciSequence[ theValue.intValue()];
	}

	protected boolean isFibonacciNumber( BigInteger theValue) {
		for (int i = 0; i < fibonacciSequence.length; i++) {
			if ( fibonacciSequence[i].compareTo( theValue) == 0)
				return true;
		} 
		return false;
	}

	protected BigInteger nextFibonacciNumber( BigInteger theValue) {
		for (int i = 0; i < fibonacciSequence.length -1; i++) {
			if ( fibonacciSequence[i].compareTo( theValue) > 0)
				return fibonacciSequence[i];
		} 
		return new BigInteger( "0");
	}

}
