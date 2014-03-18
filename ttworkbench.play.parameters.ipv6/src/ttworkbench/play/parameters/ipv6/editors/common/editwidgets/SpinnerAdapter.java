package ttworkbench.play.parameters.ipv6.editors.common.editwidgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Spinner;


@Deprecated
public class SpinnerAdapter implements IEditableWidgetAdapter<Spinner> {


	@Override
	public void setTextForWidget( String theText, Spinner theWidget) {
		theWidget.setSelection( Integer.valueOf( theText));
	}

	@Override
	public String getTextFromWidget(Spinner theWidget) {
		return theWidget.getText();
	}

	@Override
	public String getTextByEvent(Event theEvent) throws UnhandledEventException {
		switch ( theEvent.type) {
			case SWT.Verify:

				Spinner spinner = (Spinner) theEvent.widget;
				String currentText = spinner.getText();
				Character key = theEvent.character;
				String insertion = (key == '\b') ? "" : theEvent.text; 
				int beginIndex = theEvent.start;
				int endIndex = theEvent.end;
				String leftString = currentText.substring( 0, beginIndex);
				String rightString = currentText.substring( endIndex, currentText.length());
				String modifiedText = leftString + insertion + rightString;

				if ( modifiedText.isEmpty())
					modifiedText = "0";

				return modifiedText;
			default: 
				throw new UnhandledEventException();
		}
	}

	@Override
	public Class<Spinner> getSupportedWidget() {
		return Spinner.class;
	}
	


}
