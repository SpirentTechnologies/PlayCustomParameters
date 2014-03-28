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
package ttworkbench.play.parameters.ipv6.editors.floatingpoint;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.xalan.xsltc.compiler.Pattern;

import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
import ttworkbench.play.parameters.ipv6.editors.verification.IVerifier;
import ttworkbench.play.parameters.ipv6.editors.verification.VerificationResult;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class FloatTypeVerifier implements IVerifier<String> {
	
	private final Locale locale;
	
	public FloatTypeVerifier( final Locale theLocale) {
	  super();
		this.locale = theLocale;	
	}
	
	public FloatTypeVerifier() {
		this(  Locale.getDefault());
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	private final String escapeRegex( final String theRegex) {
		String result = "";
		for (int i = 0; i < theRegex.length(); i++) {
			result += escapeRegex( theRegex.charAt( 0));
		}
		return result;
	}
	
	private final String escapeRegex( final Character theRegexCharacter) {
		final Character[] escapes = {'.','+','\\','?','*','(',')','{','}','[',']'};
		for (int i = 0; i < escapes.length; i++) {
			if ( theRegexCharacter.equals( escapes[i]))
					return "\\" + theRegexCharacter;
		}
		return "" + theRegexCharacter;
	}

	private boolean isValueFloatPreTest( String theValue) {
		final String floatRegex = "(-|\\+)?[0-9]{1,3}(%s?[0-9]{3})*(%s[0-9]+)?(%s(-|\\+)?[0-9]+)?";
		try {
			DecimalFormatSymbols dfs = new DecimalFormatSymbols( locale);
			final String groupingSeparator = escapeRegex( dfs.getGroupingSeparator());
			final String decimalSeparator = escapeRegex( dfs.getDecimalSeparator());
			final String exponentSeparator = escapeRegex( dfs.getExponentSeparator());
			
			String localizedFloatRegex = String.format( floatRegex, groupingSeparator, decimalSeparator, exponentSeparator);
			return theValue.matches( localizedFloatRegex);
		} catch (Exception e) {
			return false;
		}
	}

  private String convertLocaleNumberToBigDecimalExpression( String theValue) {
  	NumberFormat numberFormat = NumberFormat.getInstance(locale);
  	 if (numberFormat instanceof DecimalFormat) {
  	     ((DecimalFormat) numberFormat).setDecimalSeparatorAlwaysShown(true);
  	     ((DecimalFormat) numberFormat).setParseBigDecimal( true);
  	 }
  	try {
  	  // replace unparsable plus operator
  		theValue = theValue.replaceAll( "^\\+", "");				
			return numberFormat.parse( theValue).toString();
	  } catch (ParseException e) {
	  	e.printStackTrace();
			return theValue;
		}
  }

  private boolean isValueFloatPostTest( String theAdjustInput) {
  	try {
  		new BigDecimal( theAdjustInput);
  		return true;
  	} catch (Exception e) {
  		return false;
  	}
  } 
 
	@Override
	public VerificationResult<String> verify( String theInput, Object... theParams) {
		boolean verified = isValueFloatPreTest( theInput);
		String adjustInput = verified ? convertLocaleNumberToBigDecimalExpression( theInput) : theInput;
		verified = verified && isValueFloatPostTest( adjustInput);
		
		String asYetTestetLocalesString = "";
		for (int i = 0; i < theParams.length; i++) {
			asYetTestetLocalesString += ((Locale) theParams[i]).getDisplayLanguage() + ", ";
		}
		asYetTestetLocalesString += locale.getDisplayLanguage();
		
		MessageRecord inputRejectedWarning = new MessageRecord( "invalid_input_warning", String.format( "Floating point value expected. \"%s\" has not a valid format.", theInput), ErrorKind.warning); 
		MessageRecord inputAcceptedSuccess = new MessageRecord( "invalid_input_warning", String.format( "Input of \"%s\" accepted.", theInput), ErrorKind.success); 	
		MessageRecord localeHint = new MessageRecord( "persistant_local_info", String.format( "Current locale is \"%s\".", locale.getDisplayLanguage()), ErrorKind.info); 	
		MessageRecord localeUndefinedHint = new MessageRecord( "persistant_local_info", String.format( "Available locales are %s.", asYetTestetLocalesString), ErrorKind.info); 	
		
		if ( verified) 
			return new VerificationResult<String>( this, adjustInput, verified, Arrays.asList( inputAcceptedSuccess, localeHint));
		else
			return new VerificationResult<String>( this, adjustInput, verified, Arrays.asList( inputRejectedWarning, localeUndefinedHint));	
	}


	
}
