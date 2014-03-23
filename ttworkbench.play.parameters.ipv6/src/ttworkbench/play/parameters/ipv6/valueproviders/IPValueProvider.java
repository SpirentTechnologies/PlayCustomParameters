package ttworkbench.play.parameters.ipv6.valueproviders;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class IPValueProvider<I extends InetAddress> implements IParameterValueProvider {

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

	@SuppressWarnings("hiding")
	@Override
	public <String> Set<String> getAvailableValues(IParameter<String> theParameter) {

		Set<String> values = new HashSet<String>();

		Enumeration<NetworkInterface> networkInterfaces;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress i = inetAddresses.nextElement();
					if (ipType.isAssignableFrom( i.getClass())) {
						values.add( (String) i.toString());
					}
				}
			}
		} catch (SocketException e) {
			System.err.println( "No Networkadapters found");
		}
		return values;
	}
}
