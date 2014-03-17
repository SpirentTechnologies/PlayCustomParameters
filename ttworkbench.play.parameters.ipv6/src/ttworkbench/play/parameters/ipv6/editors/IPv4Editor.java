package ttworkbench.play.parameters.ipv6.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.customize.StackEditorLookAndBehaviour;
import ttworkbench.play.parameters.ipv6.editors.verifier.IPv4Verifier;
import ttworkbench.play.parameters.ipv6.editors.verifier.IVerifier;

import com.testingtech.muttcn.values.StringValue;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;

/*IPv4 Format: nnn.nnn.nnn.nnn*/

public class IPv4Editor extends ValidatingEditor<StringValue> implements VerifyListener, ModifyListener {

	private static final String TITLE = "IPv4 Editor";
	private static final String DESCRIPTION = "";

	final IVerifier verifier = new IPv4Verifier();
	final Display display = Display.getCurrent();

	private StyledText text;
	private boolean ignore;

	public IPv4Editor() {
		super( TITLE, DESCRIPTION);
	}

	@Override
	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
		return new StackEditorLookAndBehaviour();
	}

	@Override
	protected void createEditRow(Composite theContainer) {

		Object[] layoutData = this.getLookAndBehaviour().getLayoutDataOfControls();
		CLabel label = new CLabel( theContainer, SWT.LEFT);
		label.setText( this.getParameter().getName() + "_ADDRESS");
		label.setLayoutData( layoutData[0]);

		createTextInputWidget( theContainer, layoutData[0]);
	}

	private void createTextInputWidget(Composite theComposite, Object theLayoutData) {

		text = new StyledText( theComposite, SWT.BORDER | SWT.SINGLE);
		text.addVerifyListener( this);
		text.addModifyListener( this);
		text.setTextLimit( 30);
		setValueHelp();
	}

	@Override
	public void verifyText(VerifyEvent e) {

		if (ignore)
			return;
		// Prevent the Event to be executed. Every manipulation on the Text must
		// be by Hand
		// e.doit = false;

		if (e.character == '\b') {
			return;
		} else {
			String insertion = ( e.character == '\b') ? "" : e.text;
			int beginIndex = e.start;
			int endIndex = e.end;
			String leftString = text.getText().substring( 0, beginIndex);
			String rightString = text.getText().substring( endIndex, text.getText().length());
			String modifiedText = leftString + insertion + rightString;
			if (this.verifier.verify( modifiedText)) {
				this.getMessageView().flashMessage( "noValidInput", verifier.getNotValidMessage(), ErrorKind.success);
			} else {
				this.getMessageView().flashMessage( "noValidInput", verifier.getNotValidMessage(), ErrorKind.warning);
			}

		}
		//
		// // Behavior for pasting
		// StringBuffer newText = new StringBuffer( DEFAULTTEXT);
		// char[] chars = e.text.toCharArray();
		// int index = ( e.text.length() > 1) ? -1 : e.start - 1;
		// for (int i = 0; i < e.text.length(); i++) {
		// index++;
		// if (newText.charAt( index) == chars[i])
		// continue;
		// if (( ( index + 1) % 4) == 0)
		// index++;
		// if (index >= newText.length())
		// return;
		// newText.setCharAt( index, chars[i]);
		// }
		// // if text is selected, do not paste beyond range of selection
		// if (e.start < e.end && index + 1 != e.end)
		// return;
		// Matcher matcher = pattern.matcher( newText);
		// if (matcher.lookingAt()) {
		// text.setSelection( e.start, index + 1);
		// ignore = true;
		// text.insert( newText.substring( e.start, index + 1));
		// ignore = false;
		// } else {
		// getMessageView().flashMessage( "", newText.toString() +
		// " is not a valid IPv4-Address", ErrorKind.warning);
		// }

		// }
		//
		// protected StringBuffer handleBackspace(VerifyEvent e, StringBuffer
		// buffer) {
		// for (int i = e.start; i < e.end; i++) {
		// buffer.append( DEFAULTTEXT.charAt( i));
		// }
		// text.setSelection( e.start, e.start + buffer.length());
		// ignore = true;
		// text.insert( buffer.toString());
		// ignore = false;
		// text.setSelection( e.start, e.start);
		// return buffer;
		//
	}

	@Override
	public void modifyText(ModifyEvent e) {

		if (text.getText() == "") {
			setValueHelp();

		} else {
			text.setForeground( display.getSystemColor( SWT.COLOR_BLACK));
		}
	}

	private void setValueHelp() {
		text.setText( verifier.getValueHelp());
		text.setForeground( display.getSystemColor( SWT.COLOR_GRAY));
	}
}
