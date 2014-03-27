package ttworkbench.play.parameters.ipv6.valueproviders;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.testingtech.muttcn.kernel.Value;
import com.testingtech.muttcn.values.StringValue;
import com.testingtech.muttcn.values.impl.StringValueImpl;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValueProvider;

/**
 * Generic value provider for different IP-Addresses.
 * 
 * @author Thomas Büttner (thomas.buettner@fu-berlin.de)
 * 
 * @param <I>
 *          Class, which describes the IP-Format.
 */
public class IPValueProvider<I extends InetAddress> implements IParameterValueProvider<StringValue> {

	private Class<I> ipType;

	public IPValueProvider( Class<I> clas) {
		ipType = clas;
	}

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



	public Set<StringValue> getAvailableValues(IParameter<StringValue> theParameter) {
		Set<StringValue> values = new HashSet<StringValue>();

		Enumeration<NetworkInterface> networkInterfaces;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress i = inetAddresses.nextElement();
					if (ipType.isAssignableFrom( i.getClass())) {
						StringValue value = new StringValueImpl() {
							
						};
						value.setTheContent( i.toString());
						values.add( value);
					}
				}
			}
		} catch (SocketException e) {
			System.err.println( "No Networkadapters found");
		}
		return values;
	}
}
