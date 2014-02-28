package ttworkbench.play.parameters.ipv6;

import java.util.ArrayList;
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
import ttworkbench.play.parameters.ipv6.widgets.IPv6Widget;

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
	
	@Override
	// TODO refactor: rename method to "compose()" ?
	public void createWidgets(IConfigurator theConfigurator) {
		theConfigurator.beginConfigure();
		// first added widget will be set automatically as default widget.
		createAndComposeDefaultWidget( theConfigurator);
		createAndComposeIPv6Widget( theConfigurator);
		theConfigurator.endConfigure();
	}

}
