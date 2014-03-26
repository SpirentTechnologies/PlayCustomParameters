//package ttworkbench.play.parameters.ipv6.editors.floatingpoint;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.custom.CLabel;
//import org.eclipse.swt.events.FocusEvent;
//import org.eclipse.swt.events.FocusListener;
//import org.eclipse.swt.events.ModifyEvent;
//import org.eclipse.swt.events.ModifyListener;
//import org.eclipse.swt.events.VerifyEvent;
//import org.eclipse.swt.events.VerifyListener;
//import org.eclipse.swt.graphics.Color;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Text;
//
//import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;
//import ttworkbench.play.parameters.ipv6.components.messaging.data.MessageRecord;
//import ttworkbench.play.parameters.ipv6.customize.IValidatingEditorLookAndBehaviour;
//import ttworkbench.play.parameters.ipv6.customize.RowEditorLookAndBehaviour;
//import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
//import ttworkbench.play.parameters.ipv6.editors.verification.IVerifyingControl;
//import ttworkbench.play.parameters.ipv6.editors.verification.widgets.VerifyingText;
//
//import com.testingtech.muttcn.values.FloatValue;
//import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
//
//public class FloatingPointEditor extends ValidatingEditor<FloatValue> {
//
//	private static final String TITLE = "Floatingpoint Editor";
//	private static final String DESCRIPTION = "";
//
//	private final Display display = Display.getCurrent();
//
//	private CLabel label;
//	private Text text;
//	private EventHandler handler; // not a generic handler
//
//	private IVerifyingControl<?, FloatValue> inputControl;
//
//	public FloatingPointEditor() {
//		super( TITLE, DESCRIPTION);
//	}
//
//	@Override
//	public IValidatingEditorLookAndBehaviour getDefaultLookAndBehaviour() {
//		return new RowEditorLookAndBehaviour();
//	}
//
//	@Override
//	protected void createEditRow(Composite theContainer) {
//		// TODO remove
//		theContainer.setBackground( display.getSystemColor( SWT.COLOR_GREEN));
//
//		label = new CLabel( theContainer, SWT.LEFT);
//		label.setText( this.getParameter().getName());
//
//		inputControl = new VerifyingText<FloatValue>( getParameter(), theContainer, SWT.BORDER | SWT.SINGLE);
//		Text text = (Text) inputControl.getControl();
//		text.setText( getParameter().getValue().getTheNumber().toString());
//		this.handler = new EventHandler();
//		// setVerifyListenerToControl( inputControl);
//		text.addVerifyListener( handler);
//		text.addFocusListener( new FocusListener() {
//
//			@Override
//			public void focusGained(FocusEvent e) {
//				return;
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				if (inputControl.getText().isEmpty()) {
//					inputControl.setText( "0.0");
//				}
//
//			}
//		});
//
////		inputControl.setListener( new IVerificationListener<String>() {
////
////			@Override
////			public void beforeVerification(VerificationEvent<String> e) {
////
////				if (e.inputToVerify.matches( ",")) {
////					e.inputToVerify = ".";
////					getMessageView().flashMessage(
////							new MessageRecord( ", not as floatingpoint seperator allowed. Automatical changed.", ErrorKind.info));
////				}
////			}
////
////			@Override
////			public void afterVerificationStep(VerificationEvent<String> e) {
////				// TODO Auto-generated method stub
////
////			}
////
////			@Override
////			public void afterVerification(VerificationEvent<String> e) {
////				
//////				if (e.doit) {
//////					inputControl.forceText( e.inputToVerify);
//////				}
////				
////				for (VerificationResult<String> r : e.verificationResults) {
////					getMessageView().flashMessages( r.messages);
////					e.doit &= r.verified;
////				}
////				
////				
////			}
////		});
//	}
//
//	@Override
//	protected void updateParameterValue() {
//		this.handler.ignore = true;
//		// this.getParameter().getValue().getTheContent()
//		text.setText( ParameterValueUtil.getValue( this.getParameter()));
//		this.handler.ignore = false;
//	}
//
//	private class EventHandler implements VerifyListener, ModifyListener, FocusListener {
//
//		final Color COLOR_HELP = display.getSystemColor( SWT.COLOR_GRAY);
//		final Color COLOR_NORMAL = display.getSystemColor( SWT.COLOR_BLACK);
//
//		protected boolean ignore = false;
//		/* indicates the State, with no Input */
//		protected boolean empty = true;
//		private Text input = text;
//
//		public EventHandler() {
//			super();
//		}
//
//		@Override
//		public void focusGained(FocusEvent e) {
//			if (empty) {
//				setText( "");
//			}
//		}
//
//		@Override
//		public void focusLost(FocusEvent e) {
//			if (empty) {
//				setText( getParameter().getDefaultValue().getTheNumber().toPlainString());
//				input.setForeground( COLOR_HELP);
//			}
//		}
//
//		@Override
//		public void modifyText(ModifyEvent e) {
//			if (!ignore) {
//				if (empty) {
//					text.setForeground( COLOR_NORMAL);
//					empty = false;
//				} else {
//					if (input.getText().isEmpty())
//						empty = true;
//				}
//			}
//		}
//
//		@Override
//		public void verifyText(VerifyEvent e) {
//			e.doit = false;
//
//			if (!ignore) {
//				if (empty) {
//					return;
//				}
//
//				if (e.character == ',') {
//					getMessageView().flashMessage(
//							new MessageRecord( ", not as floatingpoint seperator allowed. Automatical changed.", ErrorKind.info));
//				}
//				
//				
//				e.doit = String.valueOf( e.character).matches( "[0-9]");
//				if (!e.doit) {
//					getMessageView().flashMessage(
//							new MessageRecord( "Invalid input, only numbers allowed. Input rejected", ErrorKind.error));
//				}
//			}
//		}
//
//		private void setText(String theText) {
//			ignore = true;
//			input.setText( theText);
//			ignore = false;
//		}
//	}
//}
