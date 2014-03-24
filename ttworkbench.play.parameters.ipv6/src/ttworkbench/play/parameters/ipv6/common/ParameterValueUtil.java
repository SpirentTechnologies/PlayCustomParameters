package ttworkbench.play.parameters.ipv6.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.etsi.ttcn.tci.UniversalCharValue;

import com.testingtech.muttcn.expressions.ValueExpression;
import com.testingtech.muttcn.values.BitStringValue;
import com.testingtech.muttcn.values.CallValue;
import com.testingtech.muttcn.values.CharStringValue;
import com.testingtech.muttcn.values.ConstantValue;
import com.testingtech.muttcn.values.Constraint;
import com.testingtech.muttcn.values.ExceptionValue;
import com.testingtech.muttcn.values.FieldValue;
import com.testingtech.muttcn.values.FloatValue;
import com.testingtech.muttcn.values.HexStringValue;
import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.muttcn.values.MessageValue;
import com.testingtech.muttcn.values.ModuleValue;
import com.testingtech.muttcn.values.ObjidValue;
import com.testingtech.muttcn.values.OctetStringValue;
import com.testingtech.muttcn.values.ReplyValue;
import com.testingtech.muttcn.values.SequenceValue;
import com.testingtech.muttcn.values.SignatureValue;
import com.testingtech.muttcn.values.StringValue;
import com.testingtech.muttcn.values.TimerValue;
import com.testingtech.muttcn.values.TupleValue;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class ParameterValueUtil {
	

  public static String getValue( final IParameter<?> theParameter) {
   	Object value = theParameter.getValue();

   	if ( value instanceof BitStringValue) // extends StringValue
  		return ( (BitStringValue) value).getTheContent();  // untested
   	
   	if ( value instanceof CallValue) // extends MessageValue
  		return ( (CallValue) value).getTheProcedure().getTheName().toString();  // untested  	
   	
   	if ( value instanceof CharStringValue) // extends StringValue
  		return ( (CharStringValue) value).getTheContent();  // untested  	 	

   	if ( value instanceof ConstantValue) // extends Attribute
  		return ( (ConstantValue) value).getTheName().toString();  // untested 
   	
   	if ( value instanceof Constraint) 
  		return ( (Constraint) value).getTheName().toString();  // untested 
   	
   	if ( value instanceof ExceptionValue) // extends MessageValue
  		return ( (ExceptionValue) value).getTheException().getTheName().toString();  // untested 

   	if ( value instanceof FieldValue) // extends ValueExpression
  		return ( (FieldValue) value).getTheFieldAssignments().toString();  // untested 
   	
   	if ( value instanceof FloatValue) // extends ttcn.tci.Value
  		return ( (FloatValue) value).getTheNumber().toString();  // untested 
   	
   	if ( value instanceof HexStringValue) // extends StringValue
   		return ( (HexStringValue) value).getTheContent();  // untested 
      	
   	if ( value instanceof IntegerValue) // extends ValueExpression
   		return ( (IntegerValue) value).getTheNumber().toString(); 

  	if ( value instanceof ModuleValue) // extends ValueExpression
   		return ( (ModuleValue) value).getTheDeclarations().toString();  // untested 

  	if ( value instanceof ObjidValue) // extends ValueExpression
   		return ( (ObjidValue) value).getTheObjIdFields().toString();  // untested 

  	if ( value instanceof OctetStringValue) // extends StringValue
   		return ( (OctetStringValue) value).getTheContent();  // untested 

  	if ( value instanceof ReplyValue) // extends MessageValue
   		return ( (ReplyValue) value).getTheReply().toString();  // untested 
  	
  	if ( value instanceof MessageValue) // extends ValueExpression
   		return ( (MessageValue) value).getTheProcedure().getTheName().toString();  // untested 
  	
  	if ( value instanceof SequenceValue) // extends ValueExpression
   		return ( (SequenceValue) value).getTheElements().toString();  // untested 
  	
  	if ( value instanceof SignatureValue) // extends ValueExpression
   		return ( (SignatureValue) value).getTheReturnType() + " " + ( (SignatureValue) value).getTheName() + "(" + ( (SignatureValue) value).getTheParameters() + ")";  // untested
  	
  	if ( value instanceof StringValue) // extends ValueExpression
   		return ( (StringValue) value).getTheContent();  // untested

  	if ( value instanceof TimerValue) // extends ValueExpression
   		return ( (TimerValue) value).getTheDefaultDuration().toString();  // untested
  	
  	if ( value instanceof TupleValue) // extends ValueExpression
   		return ( (TupleValue) value).getTheComponents().toString();  // untested
  	
  	if ( value instanceof UniversalCharValue) // extends ttcn.tci.Value
   		return String.valueOf( ( (UniversalCharValue) value).getUniversalChar());  // untested
  	
  	if ( value instanceof ValueExpression) // extends kernel.Value
   		return ( (ValueExpression) value).toString();
  	
  	// unknown
  	return value.toString();
  }
  
  
  /**
   * Try to set the parameter value by given string.
   * @param theParameter
   * @param theValue
   * @return true, if the parameter value was set, otherwise false. In case there is no setter for string values to this parameter type defined yet, false is always the return value.
   */
  public static boolean setValue( final IParameter<?> theParameter, final String theValue) {
   	Object value = theParameter.getValue();
   	
   	if ( value instanceof BitStringValue) { // extends StringValue
  		( (BitStringValue) value).setTheContent( theValue);  // untested
   	  return true;
   	}
  		
   	if ( value instanceof CallValue) // extends MessageValue
  		return false; //( (CallValue) value).getTheProcedure().getTheName().toString();  // untested  	

   	if ( value instanceof CharStringValue) { // extends StringValue
   		( (CharStringValue) value).setTheContent( theValue);  // untested  	 	
   		return true;
   	}

   	if ( value instanceof ConstantValue) // extends Attribute
  		return false; // ( (ConstantValue) value).getTheName().toString();  // untested 
   	
   	if ( value instanceof Constraint) 
  		return false; // ( (Constraint) value).getTheName().toString();  // untested 
   	
   	if ( value instanceof ExceptionValue) // extends MessageValue
  		return false; // ( (ExceptionValue) value).getTheException().getTheName().toString();  // untested 

   	if ( value instanceof FieldValue) // extends ValueExpression
  		return false; // ( (FieldValue) value).getTheFieldAssignments().toString();  // untested 
   	
   	if ( value instanceof FloatValue) { // extends ttcn.tci.Value
   		( (FloatValue) value).setTheNumber( new BigDecimal( theValue));  // untested 
   		return true;
   	}

   	if ( value instanceof HexStringValue) { // extends StringValue
   		( (HexStringValue) value).setTheContent( theValue);  // untested 
   	  return true;
   	}
  		
   		
   	if ( value instanceof IntegerValue) { // extends ValueExpression
   		( (IntegerValue) value).setTheNumber( new BigInteger( theValue)); 
   		return true;
   	}
	
  	if ( value instanceof ModuleValue) // extends ValueExpression
   		return false; // ( (ModuleValue) value).getTheDeclarations().toString();  // untested 

  	if ( value instanceof ObjidValue) // extends ValueExpression
   		return false; // ( (ObjidValue) value).getTheObjIdFields().toString();  // untested 

  	if ( value instanceof OctetStringValue) { // extends StringValue
   		( (OctetStringValue) value).setTheContent( theValue);  // untested 
   	  return true;
   	}
  		
  	if ( value instanceof ReplyValue) // extends MessageValue
   		return false; // ( (ReplyValue) value).getTheReply().toString();  // untested 
  	
  	if ( value instanceof MessageValue) // extends ValueExpression
   		return false; // ( (MessageValue) value).getTheProcedure().getTheName().toString();  // untested 
  	
  	if ( value instanceof SequenceValue) // extends ValueExpression
   		return false; // ( (SequenceValue) value).getTheElements().toString();  // untested 
  	
  	if ( value instanceof SignatureValue) // extends ValueExpression
   		return false; // ( (SignatureValue) value).getTheReturnType() + " " + ( (SignatureValue) value).getTheName() + "(" + ( (SignatureValue) value).getTheParameters() + ")";  // untested
  	
  	if ( value instanceof StringValue) { // extends ValueExpression
   		( (StringValue) value).setTheContent( theValue);  // untested
   	  return true;
   	}
  		
  	if ( value instanceof TimerValue) // extends ValueExpression
   		return false; // ( (TimerValue) value).getTheDefaultDuration().toString();  // untested
  	
  	if ( value instanceof TupleValue) // extends ValueExpression
   		return false; // ( (TupleValue) value).getTheComponents().toString();  // untested
  	
  	if ( value instanceof UniversalCharValue) { // extends ttcn.tci.Value
  		try {
  			( (UniversalCharValue) value).setUniversalChar( Integer.valueOf( theValue));  // untested
  	    return true;
  		} catch ( NumberFormatException e) {
  			return false;  	
  		}
  	}

  	if ( value instanceof ValueExpression) // extends kernel.Value
   		return false; // ( (ValueExpression) value).toString();
  	
  	// default
  	return false;
  }

}