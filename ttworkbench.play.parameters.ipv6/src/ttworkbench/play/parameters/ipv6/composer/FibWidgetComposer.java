package ttworkbench.play.parameters.ipv6.composer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import ttworkbench.play.parameters.ipv6.ParameterMap;
import ttworkbench.play.parameters.ipv6.components.messageviews.IMessageView;
import ttworkbench.play.parameters.ipv6.editors.IntegerEditor;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
import ttworkbench.play.parameters.ipv6.validators.AbstractValidator;
import ttworkbench.play.parameters.ipv6.widgets.FibWidget;
import ttworkbench.play.parameters.ipv6.widgets.NotifyingWidget;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public class FibWidgetComposer extends WidgetComposer {
	
	// get relevant parameters
	final IParameter<IntegerValue> parameter_PX_N = getParametersMap().getParameterById( "PX_N");
	final IParameter<IntegerValue> parameter_PX_FIB_NUMBER = getParametersMap().getParameterById( "PX_FIB_NUMBER");
	final IParameter<IntegerValue> parameter_PX_FIB_SUCC_NUMBER = getParametersMap().getParameterById( "PX_FIB_SUCC_NUMBER");
	final BigInteger[] fibonacciSequence = new BigInteger[256];

	public FibWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
		super( theConfigurator, theParametersMap);
		calcFibonacciSequence();
	}
	
	private void calcFibonacciSequence() {
		fibonacciSequence[0] = new BigInteger( "0");
		fibonacciSequence[1] = new BigInteger( "1");
		for ( int i = 2; i < 256; i++) {
			fibonacciSequence[i] = fibonacciSequence[i-2].add( fibonacciSequence[i-1]);
		}
	}

	private BigInteger getFibonacciNumber( BigInteger theValue) {
		if ( theValue.intValue() > 255)
			return new BigInteger( "0");
		return fibonacciSequence[ theValue.intValue()];
	}

	private boolean isFibonacciNumber( BigInteger theValue) {
		for (int i = 0; i < fibonacciSequence.length; i++) {
			if ( fibonacciSequence[i].compareTo( theValue) == 0)
				return true;
		} 
		return false;
	}

	private BigInteger nextFibonacciNumber( BigInteger theValue) {
		for (int i = 0; i < fibonacciSequence.length -1; i++) {
			if ( fibonacciSequence[i].compareTo( theValue) > 0)
				return fibonacciSequence[i];
		} 
		return new BigInteger( "0");
	}

	@Override
	public void compose() {
		NotifyingWidget fibWidget = new FibWidget();
					
		getConfigurator().addWidget( fibWidget);

		IParameterValidator fibValidator_PX_FIB_NUMBER = new AbstractValidator( "Fibonacci Validator (Succ)", ""){
			@Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {
				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

				BigInteger theValue =( (IntegerValue) parameter.getValue()).getTheNumber(); 
				if ( isFibonacciNumber( theValue))
					validationResults.add( new ValidationResult( String.format( "%s: %s is a fibonacci number.", this.getTitle(), theValue), ErrorKind.success, "tag_is_fib"));
				else {
					validationResults.add( new ValidationResult( String.format( "%s: %s is NOT a fibonacci number.", this.getTitle(), theValue), ErrorKind.error, "tag_is_fib"));
					validationResults.add( new ValidationResult( String.format( "%s: Next succeeding fibonacci number to %s is %s.", this.getTitle(), theValue, nextFibonacciNumber( theValue)), ErrorKind.info, "tag_fib_hint"));
				}
				return validationResults;
			}

		};
		IParameterValidator fibValidator_PX_FIB_SUCC_NUMBER = new AbstractValidator( "Fibonacci Validator", ""){
			@Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {
				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

				BigInteger theValue =( (IntegerValue) parameter.getValue()).getTheNumber(); 
				if ( isFibonacciNumber( theValue))
					validationResults.add( new ValidationResult( String.format( "%s: %s is a fibonacci number.", this.getTitle(), theValue), ErrorKind.success, "tag_is_fib"));
				else {
					validationResults.add( new ValidationResult( String.format( "%s: %s is NOT a fibonacci number.", this.getTitle(), theValue), ErrorKind.error, "tag_is_fib"));
					validationResults.add( new ValidationResult( String.format( "%s: Next succeeding fibonacci number to %s is %s.", this.getTitle(), theValue, nextFibonacciNumber( theValue)), ErrorKind.info, "tag_fib_hint"));
				}
				return validationResults;
			}

		};		

		IParameterValidator fibSeqValidator = new AbstractValidator( "Fibonacci Sequence Validator", ""){
			@Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {
				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

				BigInteger inputSeqenceNumber = parameter_PX_N.getValue().getTheNumber();
				BigInteger inputFibValue = parameter_PX_FIB_NUMBER.getValue().getTheNumber();
				BigInteger fibValue = getFibonacciNumber( inputSeqenceNumber);

        if ( inputFibValue.compareTo( fibValue) != 0) {
        	validationResults.add( new ValidationResult( String.format( "%s: %s is NOT the fibonacci number of %s.", this.getTitle(), inputFibValue, inputSeqenceNumber), ErrorKind.error, "tag_is_not_fib_of_n"));		
          validationResults.add( new ValidationResult(  String.format( "%s: %s is the fibonacci number of %s.", this.getTitle(), fibValue, inputSeqenceNumber), ErrorKind.info, "tag_is_fib_of_hint")); 
        } else {
        	validationResults.add( new ValidationResult( String.format( "%s: %s is the fibonacci number of %s.", this.getTitle(), inputFibValue, inputSeqenceNumber), ErrorKind.success, "tag_is_not_fib_of_n"));		
        }
        	
        return validationResults;
			}

		};		

		IParameterValidator fibSuccValidator = new AbstractValidator("Fibonacci Successor Validator", ""){

			@Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {


				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
				BigInteger fibSuccValue = parameter_PX_FIB_SUCC_NUMBER.getValue().getTheNumber();
				BigInteger fibValue = parameter_PX_FIB_NUMBER.getValue().getTheNumber();
				BigInteger fibNextValue = nextFibonacciNumber( fibValue);

				if ( fibSuccValue.compareTo( fibNextValue) == 0) {
					validationResults.add( new ValidationResult(  String.format( "%s: %s is the successor of %s.", this.getTitle(), fibSuccValue, fibValue), ErrorKind.success, "tag_succ_fib"));
				} else {
					validationResults.add( new ValidationResult(  String.format( "%s: %s is NOT the successor of %s.", this.getTitle(), fibSuccValue, fibValue), ErrorKind.error, "tag_succ_fib"));
					validationResults.add( new ValidationResult(  String.format( "%s: %s is successor of %s.", this.getTitle(), fibNextValue, fibValue), ErrorKind.info, "tag_succ_fib_hint"));    	
				}
				return validationResults;
			}

		};

		IParameterValidator fibWidgetLayerValidator = new AbstractValidator("Error Counter", ""){

			
			
			@Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {

				List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
				
				Set<IParameterEditor> editors_PX_N = getConfigurator().getEditors( parameter_PX_N);
				Set<IParameterEditor> editors_PX_FIB_NUMBER = getConfigurator().getEditors( parameter_PX_FIB_NUMBER);
				Set<IParameterEditor> editors_PX_FIB_SUCC_NUMBER = getConfigurator().getEditors( parameter_PX_FIB_SUCC_NUMBER);
				
				int totalErrors = 0;
				int totalWarnings = 0;
				IMessageView messagePanel_PX_N = ((ValidatingEditor<?>) editors_PX_N.iterator().next()).getMessageView();
			  totalErrors += messagePanel_PX_N.getMessages( EnumSet.of( ErrorKind.error)).size();
			  totalWarnings += messagePanel_PX_N.getMessages( EnumSet.of( ErrorKind.warning)).size();
			  IMessageView messagePanel_PX_FIB_NUMBER = ((ValidatingEditor<?>) editors_PX_FIB_NUMBER.iterator().next()).getMessageView();
			  totalErrors += messagePanel_PX_FIB_NUMBER.getMessages( EnumSet.of( ErrorKind.error)).size();
			  totalWarnings += messagePanel_PX_FIB_NUMBER.getMessages( EnumSet.of( ErrorKind.warning)).size();
			  IMessageView messagePanel_PX_FIB_SUCC_NUMBER = ((ValidatingEditor<?>) editors_PX_FIB_SUCC_NUMBER.iterator().next()).getMessageView();
			  totalErrors += messagePanel_PX_FIB_SUCC_NUMBER.getMessages( EnumSet.of( ErrorKind.error)).size();
			  totalWarnings += messagePanel_PX_FIB_SUCC_NUMBER.getMessages( EnumSet.of( ErrorKind.warning)).size();
			  
				if ( totalErrors > 0) {
					validationResults.add( new ValidationResult(  String.format( "%s: %s errors.", this.getTitle(), totalErrors), ErrorKind.error, "tag_total_errors"));
				} else {
					validationResults.add( new ValidationResult(  String.format( "%s: No more errors.", this.getTitle()), ErrorKind.success, "tag_total_errors"));
				}
				
				if ( totalWarnings > 0) {
					validationResults.add( new ValidationResult(  String.format( "%s: %s warnings.", this.getTitle(), totalErrors), ErrorKind.warning, "tag_total_warnings"));
				} else {
					validationResults.add( new ValidationResult(  String.format( "%s: No more warnings.", this.getTitle()), ErrorKind.success, "tag_total_warnings"));
				}
				
				return validationResults;
			}

		};

		

		// create an editor for each parameter
		ValidatingEditor<?> editor_PX_N = new IntegerEditor();
		ValidatingEditor<?> editor_PX_FIB_NUMBER = new IntegerEditor();
		ValidatingEditor<?> editor_PX_FIB_SUCC_NUMBER = new IntegerEditor();

		// assign each parameter to the corresponding editor in this widget
		getConfigurator().assign( editor_PX_N, fibWidget, parameter_PX_N);
		getConfigurator().assign( editor_PX_FIB_NUMBER, fibWidget, parameter_PX_FIB_NUMBER);
		getConfigurator().assign( editor_PX_FIB_SUCC_NUMBER, fibWidget, parameter_PX_FIB_SUCC_NUMBER);

		// register editors to corresponding validators
		fibValidator_PX_FIB_NUMBER.registerForMessages( editor_PX_FIB_NUMBER);
		fibValidator_PX_FIB_NUMBER.registerForMessages( fibWidget);
		fibValidator_PX_FIB_SUCC_NUMBER.registerForMessages( editor_PX_FIB_SUCC_NUMBER);
		fibSeqValidator.registerForMessages( editor_PX_FIB_NUMBER);
		fibSuccValidator.registerForMessages( editor_PX_FIB_SUCC_NUMBER);
		fibSuccValidator.registerForActions( editor_PX_FIB_SUCC_NUMBER);
		
		
	  // register widgets to corresponding validators
	//	fibWidgetLayerValidator.registerForMessages( fibWidget);

		// assign validators to the parameters they have to check
		getConfigurator().assign( fibValidator_PX_FIB_NUMBER, fibWidget, parameter_PX_FIB_NUMBER);
		getConfigurator().assign( fibValidator_PX_FIB_SUCC_NUMBER, fibWidget, parameter_PX_FIB_SUCC_NUMBER);
		getConfigurator().assign( fibSeqValidator, fibWidget, parameter_PX_N, parameter_PX_FIB_NUMBER);
		getConfigurator().assign( fibSuccValidator, fibWidget, parameter_PX_FIB_NUMBER, parameter_PX_FIB_SUCC_NUMBER);
		
	}
}