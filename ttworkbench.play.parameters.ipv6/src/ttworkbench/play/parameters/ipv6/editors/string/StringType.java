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
package ttworkbench.play.parameters.ipv6.editors.string;



/**
 * Use this enum instead of a ValueProvider, cause to check against all possible octet values is far from being efficient like test the codomain boundaries.
 * @see module /TTsuite-IPv6_1.1.3/ttcn3/Library/LibCommon/LibCommon_TextStrings.ttcn3
 */
public enum StringType {
	UNDEFINED( "Undefined", null, null),

	STRING( "charstring", 0L, null),
	STRING_1( "String1", 1L, 1L),
	STRING_2( "String2", 2L, 2L),
	STRING_3( "String3", 3L, 3L),
	STRING_4( "String4", 4L, 4L),
	STRING_5( "String5", 5L, 5L),
	STRING_6( "String6", 6L, 6L),
	STRING_7( "String7", 7L, 7L),
	STRING_8( "String8", 8L, 8L),
	STRING_9( "String9", 9L, 9L),
	STRING_10( "String10", 10L, 10L),
	STRING_11( "String11", 11L, 11L),
	STRING_12( "String12", 12L, 12L),
	STRING_13( "String13", 13L, 13L),
	STRING_14( "String14", 14L, 14L),
	STRING_15( "String15", 15L, 15L),
	STRING_16( "String16", 16L, 16L),
	
	STRING_1_TO_63( "String1To63", 1L, 63L),
	STRING_1_TO_64( "String1To64", 1L, 64L),
	STRING_1_TO_127( "String1To127", 1L, 127L),
	STRING_1_TO_128( "String1To128", 1L, 128L),
	STRING_1_TO_255( "String1to255", 1L, 255L);
	
	
	private final String typeName;
	private final Long minChars;
	private final Long maxChars;
	
	private StringType( final String theTypeName, final Long theMinChars, final Long theMaxChars) {
		this.typeName = theTypeName;
		this.minChars = theMinChars;
		this.maxChars = theMaxChars;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public Long getMaxChars() {
		return maxChars;
	}
	
	public Long getMinChars() {
		return minChars;
	}
	
	public static StringType valueOfTypeName( final String theTypeName) {
		for (StringType type : values()) {
			if (type.typeName.equals( theTypeName))
				return type;
		}
		return StringType.UNDEFINED;
	}
}
