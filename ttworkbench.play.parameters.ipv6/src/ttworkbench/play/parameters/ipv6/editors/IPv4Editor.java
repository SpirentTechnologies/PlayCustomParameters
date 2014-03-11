package ttworkbench.play.parameters.ipv6.editors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import ttworkbench.play.parameters.ipv6.customize.DefaultEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;

import com.testingtech.muttcn.values.StringValue;

/*IPv4 Format: nnn.nnn.nnn.nnn*/

public class IPv4Editor extends ValidatingEditor<StringValue> {

	final String DEFAULTTEXT = "000.000.000.000";
	final String REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";

	public IPv4Editor( String theTitle, String theDescription) {
		super( theTitle, theDescription);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new DefaultEditorLookAndBehaviour();
	}

	@Override
	protected void createEditRow(Composite theContainer) {
		final Pattern pattern = Pattern.compile( REGEX);
		final Text text = new Text( theContainer, SWT.NONE);
		text.setText( DEFAULTTEXT);

		text.addListener( SWT.Verify, new Listener() {
			boolean ignore;

			@Override
			public void handleEvent(Event e) {
				if (ignore)
					return;
				e.doit = false;
				if (e.start > 14 || e.end > 15)
					return;
				StringBuffer buffer = new StringBuffer( e.text);

				if (e.character == '\b') {
					handleBackspace( e, buffer);
					return;
				}

				StringBuffer newText = new StringBuffer( DEFAULTTEXT);
				char[] chars = e.text.toCharArray();
				int index = e.start - 1;
				for (int i = 0; i < e.text.length(); i++) {
					index++;
					if (newText.charAt( index) == chars[i])
						continue;
					if (( ( index + 1) % 4) == 0)
						index++;
					if (index >= newText.length())
						return;
					newText.setCharAt( index, chars[i]);
				}
				// if text is selected, do not paste beyond range of selection
				if (e.start < e.end && index + 1 != e.end)
					return;
				Matcher matcher = pattern.matcher( newText);
				if (matcher.lookingAt()) {
					text.setSelection( e.start, index + 1);
					ignore = true;
					text.insert( newText.substring( e.start, index + 1));
					ignore = false;
				}

			}

			protected StringBuffer handleBackspace(Event e, StringBuffer buffer) {
				for (int i = e.start; i < e.end; i++) {
					switch (( i + 1) % 4) {
						case 0: {
							buffer.append( '.');
							break;
						}
						case 1:
						case 2:
						case 3: {
							buffer.append( '0');
							break;
						}
						default:
					}
				}
				text.setSelection( e.start, e.start + buffer.length());
				ignore = true;
				text.insert( buffer.toString());
				ignore = false;
				// move cursor backwards over separators
				if (( ( e.start + 1) % 4) == 0)
					e.start--;
				text.setSelection( e.start, e.start);
				return buffer;
			}

		});

	}

}
