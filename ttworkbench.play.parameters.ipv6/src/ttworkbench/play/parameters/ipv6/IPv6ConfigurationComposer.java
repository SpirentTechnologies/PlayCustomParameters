package ttworkbench.play.parameters.ipv6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import ttworkbench.play.parameters.ipv6.editors.AbstractEditor;
import ttworkbench.play.parameters.ipv6.editors.DefaultEditor;
import ttworkbench.play.parameters.ipv6.editors.IPv6Editor;
import ttworkbench.play.parameters.ipv6.editors.IntegerEditor;
import ttworkbench.play.parameters.ipv6.editors.MacAddressEditor;
import ttworkbench.play.parameters.ipv6.editors.ValidatingEditor;
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

	private static final String TYPE_MATCH_INTEGER = "^(UInt\\d{0,2}|Int\\d{0,2})$";
	
	
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
			ValidatingEditor<?> editor;
			if ( parameter.getType().matches( TYPE_MATCH_INTEGER))
			  editor = new IntegerEditor();	
			else 
				continue;//editor = new IPv6Editor();
			theConfigurator.assign( editor, IPv6Widget, parameter);
			njetValidator.registerForMessages( editor);
			yeahValidator.registerForMessages( editor);
		}
		
		
		
				
		
		theConfigurator.assign( new IPv6Validator(), IPv6Widget, new ArrayList<IParameter>(parameters));
		theConfigurator.assign( njetValidator, IPv6Widget, new ArrayList<IParameter>(parameters));	
		theConfigurator.assign( yeahValidator, IPv6Widget, new ArrayList<IParameter>(parameters));
		
		theConfigurator.assign( new IPv6ValueProvider(), IPv6Widget, new ArrayList<IParameter>(parameters));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 

	
	
	
	private class ParameterMap {
		
		private Map<String,IParameter> idToParameterMap;
		
		public ParameterMap( final IConfigurator theConfigurator) {
			loadParameters( theConfigurator);
		}
		
		private void loadParameters( IConfigurator theConfigurator) {
			idToParameterMap = new HashMap<String, IParameter>();
			Set<IParameter> parameters = theConfigurator.getParameterModel().getParameters();
			for (IParameter parameter : parameters) {
				idToParameterMap.put( parameter.getId(), parameter);
			}
		}
		
		public IParameter getParameterById( final String theId) {
			return idToParameterMap.get( theId);
		}
		
		public Collection<IParameter> getAllParameters() {
			return idToParameterMap.values();
		}

		public boolean isEmpty() {
			return idToParameterMap.isEmpty();
		}
		
	}
	
	private interface IWidgetComposer {
		void compose();
	}
	
	private abstract class WidgetComposer implements IWidgetComposer {
		
		private final IConfigurator configurator;
		private final ParameterMap parametersMap;
		
		public WidgetComposer( final IConfigurator theConfigurator, final ParameterMap theParametersMap) {
		  this.configurator = theConfigurator;
		  this.parametersMap = theParametersMap;				
		}
		
		protected IConfigurator getConfigurator() {
			return configurator;
		}
		
		protected ParameterMap getParametersMap() {
			return parametersMap;
		}
		
		public abstract void compose();
		
	}
	
	private class DefaultWidgetComposer extends WidgetComposer {

		private static final String TYPE_MATCH_INTEGER = "^(UInt\\d{0,2}|Int\\d{0,2})$";

		public DefaultWidgetComposer( IConfigurator theConfigurator, ParameterMap theParameters) {
			super( theConfigurator, theParameters);
		}

		@Override
		public void compose() {

			IWidget defaultWidget = new DefaultWidget();
			getConfigurator().addWidget( defaultWidget);

			// TODO: replace demo composition 
			Collection<IParameter> parameters = getParametersMap().getAllParameters();
			for (IParameter parameter : parameters) {
				IParameterEditor editor = new DefaultEditor();
				if(parameter.getType().matches( TYPE_MATCH_INTEGER)) {
					editor = new IntegerEditor();
				}
				getConfigurator().assign( editor, defaultWidget, parameter);
			}
		}

	}
	
	
	private class FibWidgetComposer extends WidgetComposer {
		
		// get relevant parameters
		final IParameter<IntegerValue> parameter_PX_N = getParametersMap().getParameterById( "PX_N");
		final IParameter<IntegerValue> parameter_PX_FIB_NUMBER = getParametersMap().getParameterById( "PX_FIB_NUMBER");
		final IParameter<IntegerValue> parameter_PX_FIB_SUCC_NUMBER = getParametersMap().getParameterById( "PX_FIB_SUCC_NUMBER");
		

		public FibWidgetComposer( IConfigurator theConfigurator, ParameterMap theParametersMap) {
			super( theConfigurator, theParametersMap);

		}

		private BigInteger getFibonacciNumber( BigInteger theValue) {
			if ( theValue.compareTo( new BigInteger( "0")) <= 0)
				return new BigInteger( "0");
			if ( theValue.compareTo( new BigInteger( "1")) <= 0)
				return new BigInteger( "1");
			return getFibonacciNumber( theValue.subtract( new BigInteger( "1"))).add( getFibonacciNumber( theValue.subtract( new BigInteger( "2")))); 
		}

		private boolean isFibonacciNumber( BigInteger theValue) {
			int comparision;
			BigInteger n = new BigInteger( "0");
			do { 
				comparision = theValue.compareTo( getFibonacciNumber( n));
				if ( comparision == 0)
					return true;
				n = n.add(  new BigInteger( "1"));
			} while( comparision > 0);
			return false;
		}

		private BigInteger nextFibonacciNumber( BigInteger theValue) {
			int comparision;
			BigInteger n = new BigInteger( "0");
			BigInteger nextFib;
			do { 
				nextFib = getFibonacciNumber( n);
				comparision = theValue.compareTo( nextFib);
				n = n.add(  new BigInteger( "1"));
			} while( comparision > 0);
			return nextFib;
		}

		@Override
		public void compose() {
			IWidget fibWidget = new FibWidget();
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

			IParameterValidator fibSeqValidator_PX_N = new AbstractValidator( "Fibonacci Sequence Validator", ""){
				@Override
				protected List<ValidationResult> validateParameter( IParameter parameter) {
					List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

					BigInteger inputSeqenceNumber =( (IntegerValue) parameter.getValue()).getTheNumber();
					BigInteger inputFibValue = parameter_PX_FIB_NUMBER.getValue().getTheNumber();
					BigInteger fibValue = getFibonacciNumber( inputSeqenceNumber);

          if ( inputFibValue.compareTo( fibValue) != 0) {
          	validationResults.add( new ValidationResult( String.format( "%s: %s is NOT a the fibonacci number of %s.", this.getTitle(), inputFibValue, fibValue), ErrorKind.warning, "tag_is_not_fib_of_n"));		
          }
          return validationResults;
				}

			};		

			IParameterValidator fibSuccValidator = new AbstractValidator("Fibonacci Successor Validator", ""){

				@Override
				protected List<ValidationResult> validateParameter( IParameter parameter) {


					List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
					/*					BigInteger fibSuccValue = ((IntegerValue)usedParameters.get( "fibSucc").getValue()).getTheNumber();
					BigInteger fibValue = ((IntegerValue)usedParameters.get( "fib").getValue()).getTheNumber();
					BigInteger fibNextValue = nextFibonacciNumber( fibValue);

					if ( fibSuccValue.compareTo( fibNextValue) == 0) {
						validationResults.add( new ValidationResult(  String.format( "%s: %s is the successor of %s.", this.getTitle(), fibSuccValue, fibValue), ErrorKind.success, "tag_succ_fib"));
					} else {
						validationResults.add( new ValidationResult(  String.format( "%s: %s is NOT the successor of %s.", this.getTitle(), fibSuccValue, fibValue), ErrorKind.error, "tag_succ_fib"));
						validationResults.add( new ValidationResult(  String.format( "%s: %s is successor of %s.", this.getTitle(), fibNextValue, fibValue), ErrorKind.info, "tag_succ_fib_hint"));    	
					}*/
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
			fibValidator_PX_FIB_SUCC_NUMBER.registerForMessages( editor_PX_FIB_SUCC_NUMBER);
			fibSeqValidator_PX_N.registerForMessages( editor_PX_FIB_NUMBER);

			// assign validators to the parameters they have to check
			getConfigurator().assign( fibValidator_PX_FIB_NUMBER, fibWidget, parameter_PX_FIB_NUMBER);
			getConfigurator().assign( fibValidator_PX_FIB_SUCC_NUMBER, fibWidget, parameter_PX_FIB_SUCC_NUMBER);
			getConfigurator().assign( fibSeqValidator_PX_N, fibWidget, parameter_PX_N);





		}
	}



	@Override
	// TODO refactor: rename method to "compose()" ?
	public void createWidgets(IConfigurator theConfigurator) {
		ParameterMap parametersMap = new ParameterMap( theConfigurator);
		if ( parametersMap.isEmpty())
			return;

		List<IWidgetComposer> widgetComposers = new ArrayList<IWidgetComposer>();
		// first added widget will be set automatically as default widget.
		widgetComposers.add( new DefaultWidgetComposer( theConfigurator, parametersMap));
		widgetComposers.add( new FibWidgetComposer( theConfigurator, parametersMap));

		theConfigurator.beginConfigure();
		for (IWidgetComposer widgetComposer : widgetComposers) {
			widgetComposer.compose();
		}

		//createAndComposeIPv6Widget( theConfigurator);
		theConfigurator.endConfigure();
	}



}
