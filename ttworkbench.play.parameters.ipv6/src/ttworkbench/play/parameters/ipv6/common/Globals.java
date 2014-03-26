package ttworkbench.play.parameters.ipv6.common;

import com.testingtech.ttworkbench.ttman.parameters.api.IConfiguration;

public class Globals {

	private static IConfiguration configuration;
	
	public static void setConfiguration( IConfiguration theConfiguration) {
	  configuration = theConfiguration;
	}
	
	public static IConfiguration getConfiguration() {
		return configuration;
	}
	
	public static boolean hasConfiguration() {
		return configuration != null;
	}
	
	
}
