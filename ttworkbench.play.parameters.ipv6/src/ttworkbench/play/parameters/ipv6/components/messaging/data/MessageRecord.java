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
package ttworkbench.play.parameters.ipv6.components.messaging.data;

import ttworkbench.play.parameters.ipv6.common.IParameterControl;

import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class MessageRecord {
	
	public final String tag;
	
	public String message;
	
	public ErrorKind errorKind;
	
	public final IParameterControl<?,?> causer;
	
	public MessageRecord( final String theTag, final String theMessage, final ErrorKind theErrorKind, final IParameterControl<?,?> theCauser) {
		super();
		this.tag = theTag;
		this.message = theMessage;
		this.errorKind = theErrorKind;
		this.causer = theCauser;
	}
	
	public MessageRecord( final String theTag, final String theMessage, final ErrorKind theErrorKind) {
	  this( theTag, theMessage, theErrorKind, null);
	}
	
	public MessageRecord( final String theMessage, final ErrorKind theErrorKind) {
		this( null, theMessage, theErrorKind);
	}

	public boolean hasTag() {
		return tag != null && !tag.isEmpty();
	}
	
	
	@Override
	public int hashCode() {
	  int hash = 1;
    hash = hash * 17 + (tag == null ? 0 : tag.hashCode());
    hash = hash * 31 + message.hashCode();
    hash = hash * 13 + errorKind.hashCode();
    hash = hash * 23 + (causer == null ? 0 : causer.hashCode()); 
    return hash;
	}
	
	@Override
	public String toString() {
		 return String.format( "MessageRecord@%d( tag: %s, message: %s, errorkind: %s, causer: %s)",hashCode(), tag, message, errorKind.toString(), causer);
	}
	
	
	
}
