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
