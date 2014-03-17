package ttworkbench.play.parameters.ipv6.editors.integer;

import java.math.BigInteger;


/**
 * Use this enum instead of a ValueProvider, cause to check against all possible integers values is far from being efficient like test the codomain boundaries.
 * @see module /TTsuite-IPv6_1.1.3/ttcn3/Library/LibCommon/LibCommon_BasicTypesAndValues.ttcn3
 */
public enum IntegerType {
	UNDEFINED( "Undefined", null, null),
	
	// unsigned integer
	UNSIGNED_INT( "UInt", new BigInteger( "0"), null),
	UNSIGNED_INT_01( "UInt1", new BigInteger( "0"), new BigInteger( "1")),
	UNSIGNED_INT_02( "UInt2", new BigInteger("0"), new BigInteger( "3")),
	UNSIGNED_INT_03( "UInt3", new BigInteger( "0"), new BigInteger( "7")),
	UNSIGNED_INT_04( "UInt4", new BigInteger( "0"), new BigInteger( "15")),
	UNSIGNED_INT_05( "UInt5", new BigInteger( "0"), new BigInteger( "31")),
	UNSIGNED_INT_06( "UInt6", new BigInteger( "0"), new BigInteger( "63")),
	UNSIGNED_INT_07( "UInt7", new BigInteger( "0"), new BigInteger( "127")),
	UNSIGNED_INT_08( "UInt8", new BigInteger( "0"), new BigInteger( "255")),
	UNSIGNED_INT_09( "UInt9", new BigInteger( "0"), new BigInteger( "511")),
	UNSIGNED_INT_10( "UInt10", new BigInteger( "0"), new BigInteger( "1023")),
	UNSIGNED_INT_11( "UInt11", new BigInteger( "0"), new BigInteger( "2047")),
	UNSIGNED_INT_12( "UInt12", new BigInteger( "0"), new BigInteger( "4095")),
	UNSIGNED_INT_13( "UInt13", new BigInteger( "0"), new BigInteger( "8191")),
	UNSIGNED_INT_14( "UInt14", new BigInteger( "0"), new BigInteger( "16383")),
	UNSIGNED_INT_15( "UInt15", new BigInteger( "0"), new BigInteger( "32767")),
	UNSIGNED_INT_16( "UInt16", new BigInteger( "0"), new BigInteger( "65535")),
	UNSIGNED_INT_17( "UInt17", new BigInteger( "0"), new BigInteger( "131071")),
	UNSIGNED_INT_18( "UInt18", new BigInteger( "0"), new BigInteger( "262143")),
	UNSIGNED_INT_19( "UInt19", new BigInteger( "0"), new BigInteger( "524287")),
	UNSIGNED_INT_20( "UInt20", new BigInteger( "0"), new BigInteger( "1048575")),
	UNSIGNED_INT_21( "UInt21", new BigInteger( "0"), new BigInteger( "2097151")),
	UNSIGNED_INT_22( "UInt22", new BigInteger( "0"), new BigInteger( "4194303")),
	UNSIGNED_INT_23( "UInt23", new BigInteger( "0"), new BigInteger( "8388607")),
	UNSIGNED_INT_24( "UInt24", new BigInteger( "0"), new BigInteger( "16777215")),
	UNSIGNED_INT_25( "UInt25", new BigInteger( "0"), new BigInteger( "33554431")),
	UNSIGNED_INT_26( "UInt26", new BigInteger( "0"), new BigInteger( "67108863")),
	UNSIGNED_INT_27( "UInt27", new BigInteger( "0"), new BigInteger( "134217727")),
	UNSIGNED_INT_28( "UInt28", new BigInteger( "0"), new BigInteger( "268435456")),
	UNSIGNED_INT_29( "UInt29", new BigInteger( "0"), new BigInteger( "536870911")),
	UNSIGNED_INT_30( "UInt30", new BigInteger( "0"), new BigInteger( "1073741823")),
	UNSIGNED_INT_31( "UInt31", new BigInteger( "0"), new BigInteger( "2147483647")),
	UNSIGNED_INT_32( "UInt32", new BigInteger( "0"), new BigInteger( "4294967295")),
	UNSIGNED_INT_48( "UInt48", new BigInteger( "0"), new BigInteger( "281474976710655")),
	UNSIGNED_INT_64( "UInt64", new BigInteger( "0"), new BigInteger( "18446744073709551616")),// ignore Bug TAU//18446744073709551615;
	// signed integer
	SIGNED_INT( "Int", null, null),
	SIGNED_INT_01( "Int1", new BigInteger("-1"), new BigInteger( "0")),
	SIGNED_INT_02( "Int2", new BigInteger("-2"), new BigInteger( "1")),
	SIGNED_INT_03( "Int3", new BigInteger("-4"), new BigInteger( "3")),
	SIGNED_INT_04( "Int4", new BigInteger("-8"), new BigInteger( "7")),
	SIGNED_INT_05( "Int5", new BigInteger("-16"), new BigInteger( "15")),
	SIGNED_INT_06( "Int6", new BigInteger("-32"), new BigInteger( "31")),
	SIGNED_INT_07( "Int7", new BigInteger("-64"), new BigInteger( "63")),
	SIGNED_INT_08( "Int8", new BigInteger("-128"), new BigInteger( "127")),
	SIGNED_INT_09( "Int9", new BigInteger("-256"), new BigInteger( "255")),
	SIGNED_INT_10( "Int10", new BigInteger("-512"), new BigInteger( "511")),
	SIGNED_INT_11( "Int11", new BigInteger("-1024"), new BigInteger( "1023")),
	SIGNED_INT_12( "Int12", new BigInteger("-2048"), new BigInteger( "2047")),
	SIGNED_INT_13( "Int13", new BigInteger("-4096"), new BigInteger( "4095")),
	SIGNED_INT_14( "Int14", new BigInteger("-8192"), new BigInteger( "8191")),
	SIGNED_INT_15( "Int15", new BigInteger("-16384"), new BigInteger( "16383")),
	SIGNED_INT_16( "Int16", new BigInteger("-32768"), new BigInteger( "32767")),
	SIGNED_INT_17( "Int17", new BigInteger("-65536"), new BigInteger( "65535")),
	SIGNED_INT_18( "Int18", new BigInteger("-131072"), new BigInteger( "131071")),
	SIGNED_INT_19( "Int19", new BigInteger("-262144"), new BigInteger( "262143")),
	SIGNED_INT_20( "Int20", new BigInteger("-524288"), new BigInteger( "524287")),
	SIGNED_INT_21( "Int21", new BigInteger("-1048576"), new BigInteger( "1048575")),
	SIGNED_INT_22( "Int22", new BigInteger("-2097152"), new BigInteger( "2097151")),
	SIGNED_INT_23( "Int23", new BigInteger("-4194304"), new BigInteger( "4194303")),
	SIGNED_INT_24( "Int24", new BigInteger("-8388608"), new BigInteger( "8388607")),
	SIGNED_INT_25( "Int25", new BigInteger("-16777216"), new BigInteger( "16777215")),
	SIGNED_INT_26( "Int26", new BigInteger("-33554432"), new BigInteger( "33554431")),
	SIGNED_INT_27( "Int27", new BigInteger("-67108864"), new BigInteger( "67108863")),
	SIGNED_INT_28( "Int28", new BigInteger("-134217728"), new BigInteger( "134217727")),
	SIGNED_INT_29( "Int29", new BigInteger("-268435457"), new BigInteger( "268435456")),
	SIGNED_INT_30( "Int30", new BigInteger("-536870912"), new BigInteger( "536870911")),
	SIGNED_INT_31( "Int31", new BigInteger("-1073741824"), new BigInteger( "1073741823")),
	SIGNED_INT_32( "Int32", new BigInteger("-2147483648"), new BigInteger( "2147483647"));
	
	private final String typeName;
	private final BigInteger minValue;
	private final BigInteger maxValue;
	
	private IntegerType( final String theTypeName, final BigInteger theMinValue, final BigInteger theMaxValue) {
		this.typeName = theTypeName;
		this.minValue = theMinValue;
		this.maxValue = theMaxValue;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public BigInteger getMinValue() {
		return minValue;
	}
	
	public BigInteger getMaxValue() {
		return maxValue;
	}
	
	public static IntegerType valueOfTypeName( final String theTypeName) {
		for (IntegerType type : values()) {
			if (type.typeName.equals( theTypeName))
				return type;
		}
		return IntegerType.UNDEFINED;
	}
}