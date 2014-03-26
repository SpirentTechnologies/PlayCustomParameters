package ttworkbench.play.parameters.ipv6.valueproviders;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.muttcn.values.impl.StringValueImpl;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

public class MacValueProvider implements IParameterValueProvider{

	@Override
	public void setAttribute(String theName, String theValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parametersChanged(List<IParameter<?>> theParameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Set<T> getAvailableValues(IParameter<T> theParameter) {
		// TODO Auto-generated method stub
		Set<T> values = new HashSet<T>();
		try {
	    Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
	    int index = 0;
	    while(networks.hasMoreElements()) {
	      NetworkInterface network = networks.nextElement();
	      byte[] mac = network.getHardwareAddress();

	      
	      if(mac != null) {
	        System.out.print("Current MAC address : ");
	        System.out.println(index);

	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < mac.length; i++) {
	          sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
	        }
	        System.out.println(sb.toString());
	        values.add( (T) newString(sb.toString()));
	      }
	      index++;
	    }
	  } catch (SocketException e){
	    e.printStackTrace();
	  }
		return values;
	}
	
	private StringValue newString( String theString) {
		StringValue stringValue = new StringValueImpl() {};
		stringValue.setTheContent( theString);
	  return stringValue;
	}

}
