package ttworkbench.play.parameters.ipv6.editors.octet;

import java.math.BigInteger;


/**
 * Use this enum instead of a ValueProvider, cause to check against all possible octet values is far from being efficient like test the codomain boundaries.
 * @see module /TTsuite-IPv6_1.1.3/ttcn3/Library/LibCommon/LibCommon_DataString.ttcn3
 */
public enum OctetType {
	UNDEFINED( "Undefined", null, null),
	
	// unsigned integer
	OCT( "octetstring", 0L, null),
	OCT_1( "Oct1", 1L, 1L),
	OCT_2( "Oct2", 2L, 2L),
	OCT_3( "Oct3", 3L, 3L),
	OCT_4( "Oct4", 4L, 4L),
	OCT_5( "Oct5", 5L, 5L),
	OCT_6( "Oct6", 6L, 6L),
	OCT_7( "Oct7", 7L, 7L),
	OCT_8( "Oct8", 8L, 8L),
	OCT_9( "Oct9", 9L, 9L),
	OCT_10( "Oct10", 10L, 10L),
	OCT_11( "Oct11", 11L, 11L),
	OCT_12( "Oct12", 12L, 12L),
	OCT_13( "Oct13", 13L, 13L),
	OCT_14( "Oct14", 14L, 14L),
	OCT_15( "Oct15", 15L, 15L),
	OCT_16( "Oct16", 16L, 16L),
	OCT_20( "Oct20", 20L, 20L),
	OCT_24( "Oct24", 24L, 24L),
	
	OCT_80( "Oct80", 80L, 80L),
	OCT_128( "Oct128", 128L, 128L),
	OCT_160( "Oct160", 160L, 160L),
	OCT_320( "Oct320", 320L, 320L),
	OCT_640( "Oct640", 640L, 640L),
	OCT_1280( "Oct1280", 1280L, 1280L),
	OCT_1380( "Oct1380", 1380L, 1380L),	
	

	OCT_0_TO_255( "Oct0to255", 0L, 255L),
	OCT_1_TO_15( "Oct1to15", 1L, 15L),
	OCT_6_TO_15( "Oct6to15", 6L, 15L),
	OCT_1_TO_128( "Oct1to128", 1L, 128L),
	OCT_1_TO_254( "Oct1to254", 1L, 254L),
	OCT_1_TO_255( "Oct1to255", 1L, 255L);
	
	private final String typeName;
	private final Long minOctets;
	private final Long maxOctets;
	
	private OctetType( final String theTypeName, final Long theMinOctets, final Long theMaxOctets) {
		this.typeName = theTypeName;
		this.minOctets = theMinOctets;
		this.maxOctets = theMaxOctets;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public Long getMinOctets() {
		return minOctets;
	}
	
	public Long getMaxOctets() {
		return maxOctets;
	}
	
	public static OctetType valueOfTypeName( final String theTypeName) {
		for (OctetType type : values()) {
			if (type.typeName.equals( theTypeName))
				return type;
		}
		return OctetType.UNDEFINED;
	}
}