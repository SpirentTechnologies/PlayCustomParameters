package ttworkbench.play.parameters.ipv6.editors.common.editwidgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;


@Deprecated
public class TextAdapter implements IEditableWidgetAdapter<Text> {


	@Override
	public void setTextForWidget( String theText, Text theWidget) {
		theWidget.setText( theText);
	}

	@Override
	public String getTextFromWidget(Text theWidget) {
		return theWidget.getText();
	}

	@Override
	public String getTextByEvent(Event theEvent) throws UnhandledEventException {
		switch ( theEvent.type) {
			case SWT.Verify:
				Text text = (Text) theEvent.widget;
				String currentText = text.getText();
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
	public Class<Text> getSupportedWidget() {
		return Text.class;
	}
	


}
