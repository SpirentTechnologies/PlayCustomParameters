package ttworkbench.play.parameters.ipv6.components.messaging.components;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

public class ErrorKindCounter {

	public interface CountListener {

		void handleIncrementEvent( final ErrorKind theErrorKind, final int theCounterReading);

		void handleDecrementEvent( final ErrorKind theErrorKind, final int theCounterReading);	
	}
	
	private Map<ErrorKind, Integer> counter = new HashMap<ErrorKind, Integer>();
	private CountListener countListener;
	
	public ErrorKindCounter() {
		for (ErrorKind errorKind : EnumSet.allOf( ErrorKind.class)) {
			counter.put( errorKind, 0);
		}
	}
	
	public void inc( final ErrorKind theErrorKind) {
		Integer count = counter.get( theErrorKind);
		counter.put( theErrorKind, ++count);
		countListener.handleIncrementEvent( theErrorKind, count);
	}
	
	public void dec( final ErrorKind theErrorKind) {
		Integer count = counter.get( theErrorKind);
		count = Math.max( count -1, 0);
		counter.put( theErrorKind, count);
		countListener.handleDecrementEvent( theErrorKind, count);
	}
	
	public void setListener( CountListener theListener) {
		this.countListener = theListener;
	}
	
	public int getTotalCount() {
		int total = 0;
		for ( Integer count : counter.values()) {
			total += count;
		}
		return total;
	}
	
	public Integer getCountOfErrorKind( ErrorKind theErrorKind) {
		return counter.get( theErrorKind);
	}
	
	public ErrorKind getHighestErrorKind() {
		if ( 0 < getCountOfErrorKind( ErrorKind.error))
		  return ErrorKind.error;
		if ( 0 < getCountOfErrorKind( ErrorKind.warning))
		  return ErrorKind.warning;
		if ( 0 < getCountOfErrorKind( ErrorKind.info))
		  return ErrorKind.info;
		return ErrorKind.success;
	}
	
}