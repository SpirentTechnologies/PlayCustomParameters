package ttworkbench.play.parameters.ipv6;

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

}
