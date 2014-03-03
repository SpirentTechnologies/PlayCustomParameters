package ttworkbench.play.parameters.ipv6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;
import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.ipv6.editors.IPv6Editor;
import ttworkbench.play.parameters.ipv6.editors.IntegerEditor;
import ttworkbench.play.parameters.ipv6.editors.MacAddressEditor;
import ttworkbench.play.parameters.ipv6.validators.AbstractValidator;
import ttworkbench.play.parameters.ipv6.validators.IPv6Validator;
import ttworkbench.play.parameters.ipv6.valueproviders.IPv6ValueProvider;
import ttworkbench.play.parameters.ipv6.widgets.DefaultWidget;
import ttworkbench.play.parameters.ipv6.widgets.FibWidget;
import ttworkbench.play.parameters.ipv6.widgets.IPv6Widget;

import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurationComposer;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterValidator;
import com.testingtech.ttworkbench.ttman.parameters.api.IWidget;
import com.testingtech.ttworkbench.ttman.parameters.validation.ErrorKind;
import com.testingtech.ttworkbench.ttman.parameters.validation.ValidationResult;

public class IPv6ConfigurationComposer implements IConfigurationComposer {

	private static void createAndComposeDefaultWidget( IConfigurator theConfigurator) {
		IWidget defaultWidget = new DefaultWidget();
		theConfigurator.addWidget( defaultWidget);
		
		// TODO: replace demo composition 
		Set<IParameter> parameters = theConfigurator.getParameterModel().getParameters();
		for (IParameter parameter : parameters) {
			theConfigurator.assign( new DefaultEditor(), parameter, defaultWidget);	
		}
	}
	
	
	private static void createAndComposeIPv6Widget( IConfigurator theConfigurator) {
		IWidget IPv6Widget = new IPv6Widget();
		theConfigurator.addWidget( IPv6Widget);
		
		IParameterValidator njetValidator = new AbstractValidator( "No Validator", ""){
            @Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {
					List<ValidationResult> l = new ArrayList<ValidationResult>();
					l.add( new ValidationResult( "nay-sayer", ErrorKind.error));
					return l;
			}
            
		};
		
		IParameterValidator yeahValidator = new AbstractValidator("Yes Validator", ""){

			@Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {
				List<ValidationResult> l = new ArrayList<ValidationResult>();
				l.add( new ValidationResult( "yea-sayer", ErrorKind.success));
				l.add( new ValidationResult( "gasbag", ErrorKind.info));
				return l;
			}
			
		};

		
		
		// TODO: replace demo composition 
		Set<IParameter> parameters = theConfigurator.getParameterModel().getParameters();
		for (IParameter parameter : parameters) {
			AbstractEditor editor;
			if ( parameter.getType().matches( "^(UInt\\d{0,2}|Int\\d{0,2})$"))
			  editor = new IntegerEditor();	
			else 
				continue;//editor = new IPv6Editor();
			theConfigurator.assign( editor, parameter, IPv6Widget);
			njetValidator.registerForMessages( editor);
			yeahValidator.registerForMessages( editor);
		}
		
		
		
				
		
		theConfigurator.assign( new IPv6Validator(), new ArrayList<IParameter>(parameters), IPv6Widget);
		theConfigurator.assign( njetValidator, new ArrayList<IParameter>(parameters), IPv6Widget);	
		theConfigurator.assign( yeahValidator, new ArrayList<IParameter>(parameters), IPv6Widget);
		
		theConfigurator.assign( new IPv6ValueProvider(), new ArrayList<IParameter>(parameters), IPv6Widget);
	}
	
	
	
	
	
	
	
  private static BigInteger getFibonacciNumber( BigInteger theValue) {
  	if ( theValue.compareTo( new BigInteger( "0")) <= 0)
			return new BigInteger( "0");
  	if ( theValue.compareTo( new BigInteger( "0")) <= 1)
			return new BigInteger( "1");
  	return getFibonacciNumber( theValue.subtract( new BigInteger( "1"))).add( getFibonacciNumber( theValue.subtract( new BigInteger( "2")))); 
	}
	
	private static boolean isFibonacciNumber( BigInteger theValue) {
		int comparision;
		BigInteger n = new BigInteger( "0");
		do { 
			comparision = theValue.compareTo( getFibonacciNumber( n));
			if ( comparision == 0)
				return true;
			n = n.add(  new BigInteger( "1"));
		} while( !( comparision > 0));
		return false;
	}
	
	private static BigInteger nextFibonacciNumber( BigInteger theValue) {
		int comparision;
		BigInteger n = new BigInteger( "0");
		BigInteger nextFib;
		do { 
			nextFib = getFibonacciNumber( n);
			comparision = theValue.compareTo( nextFib);
			n = n.add(  new BigInteger( "1"));
		} while( !( comparision > 0));
		return nextFib;
	}
	
	
	private static void createAndComposeFibWidget( IConfigurator theConfigurator) {
		IWidget FibWidget = new FibWidget();
		theConfigurator.addWidget( FibWidget);
		
		IParameterValidator fibValidator = new AbstractValidator( "Fibonacci Validator", ""){
            @Override
            protected List<ValidationResult> validateParameter( IParameter parameter) {
            	List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

            	BigInteger theValue =( (IntegerValue) parameter.getValue()).getTheNumber(); 
            	if ( isFibonacciNumber( theValue))
            		validationResults.add( new ValidationResult( String.format( "%s: %s is a fibonacci number.", this.getTitle(), theValue), ErrorKind.success));
            	else {
            		validationResults.add( new ValidationResult( String.format( "%s: %s is NOT a fibonacci number.", this.getTitle(), theValue), ErrorKind.error));
            	  validationResults.add( new ValidationResult( String.format( "%s: Next succeeding fibonacci number to %s is %s.", this.getTitle(), theValue, nextFibonacciNumber( theValue)), ErrorKind.info));
            	}
							return validationResults;
            }

		};
		
		IParameterValidator fibSuccValidator = new AbstractValidator("Fibonacci Successor Validator", ""){

			@Override
			protected List<ValidationResult> validateParameter( IParameter parameter) {
	    	List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
	    	
	    	BigInteger theValue =( (IntegerValue) parameter.getValue()).getTheNumber(); 
	    	// TODO need validateWidget or getParametersOfWidget or something like this ...
				return validationResults;
			}
			
		};

		
		
		Set<IParameter> parameters = theConfigurator.getParameterModel().getParameters();
		List<IParameter> parameterSelection = new ArrayList<IParameter>();
		for (IParameter parameter : parameters) {
			if ( parameter.getName().equals( "LibDemo_ModuleParameters.PX_N")) {
				AbstractEditor editorN = new IntegerEditor();
				parameterSelection.add( parameter);
				theConfigurator.assign( editorN, parameter, FibWidget); 
			}
			
			if ( parameter.getName().equals( "LibDemo_ModuleParameters.PX_FIB_NUMBER")) {
				AbstractEditor editorFibNumber = new IntegerEditor();
			  fibValidator.registerForMessages( editorFibNumber);
			  parameterSelection.add( parameter);
				theConfigurator.assign( editorFibNumber, parameter, FibWidget); 
			}
		}
		
		theConfigurator.assign( fibValidator, parameterSelection, FibWidget);
	}
	
	@Override
	// TODO refactor: rename method to "compose()" ?
	public void createWidgets(IConfigurator theConfigurator) {
		theConfigurator.beginConfigure();
		// first added widget will be set automatically as default widget.
		createAndComposeDefaultWidget( theConfigurator);
		//createAndComposeIPv6Widget( theConfigurator);
		createAndComposeFibWidget( theConfigurator);
		theConfigurator.endConfigure();
	}

}
