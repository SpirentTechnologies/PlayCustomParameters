package ttworkbench.play.parameters.ipv6.editors;

import java.math.BigInteger;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.testingtech.muttcn.statements.ConstDeclaration;
import com.testingtech.muttcn.values.IntegerValue;
import com.testingtech.muttcn.values.impl.IntegerValueImpl;
import com.testingtech.ttworkbench.ttman.parameters.api.IConfigurator;
import com.testingtech.ttworkbench.ttman.parameters.api.IParameter;

public class IntegerEditor extends AbstractEditor<IntegerValue> {
	
	/**
	 * @see module /TTsuite-IPv6_1.1.3/ttcn3/Library/LibCommon/LibCommon_BasicTypesAndValues.ttcn3
	 */
	private enum IntegerType {
		UNDEFINED( "Undefined", +0L, -0L),
		
		// unsigned integer
		UNSIGNED_INT( "UInt", 0L, Long.MAX_VALUE),
		UNSIGNED_INT_01( "UInt1", 0L, 1L),
		UNSIGNED_INT_02( "UInt2", 0L, 3L),
		UNSIGNED_INT_03( "UInt3", 0L, 7L),
		UNSIGNED_INT_04( "UInt4", 0L, 15L),
		UNSIGNED_INT_05( "UInt5", 0L, 31L),
		UNSIGNED_INT_06( "UInt6", 0L, 63L),
		UNSIGNED_INT_07( "UInt7", 0L, 127L),
		UNSIGNED_INT_08( "UInt8", 0L, 255L),
		UNSIGNED_INT_09( "UInt9", 0L, 511L),
		UNSIGNED_INT_10( "UInt10", 0L, 1023L),
		UNSIGNED_INT_11( "UInt11", 0L, 2047L),
		UNSIGNED_INT_12( "UInt12", 0L, 4095L),
		UNSIGNED_INT_13( "UInt13", 0L, 8191L),
		UNSIGNED_INT_14( "UInt14", 0L, 16383L),
		UNSIGNED_INT_15( "UInt15", 0L, 32767L),
		UNSIGNED_INT_16( "UInt16", 0L, 65535L),
		UNSIGNED_INT_17( "UInt17", 0L, 131071L),
		UNSIGNED_INT_18( "UInt18", 0L, 262143L),
		UNSIGNED_INT_19( "UInt19", 0L, 524287L),
		UNSIGNED_INT_20( "UInt20", 0L, 1048575L),
		UNSIGNED_INT_21( "UInt21", 0L, 2097151L),
		UNSIGNED_INT_22( "UInt22", 0L, 4194303L),
		UNSIGNED_INT_23( "UInt23", 0L, 8388607L),
		UNSIGNED_INT_24( "UInt24", 0L, 16777215L),
		UNSIGNED_INT_25( "UInt25", 0L, 33554431L),
		UNSIGNED_INT_26( "UInt26", 0L, 67108863L),
		UNSIGNED_INT_27( "UInt27", 0L, 134217727L),
		UNSIGNED_INT_28( "UInt28", 0L, 268435456L),
		UNSIGNED_INT_29( "UInt29", 0L, 536870911L),
		UNSIGNED_INT_30( "UInt30", 0L, 1073741823L),
		UNSIGNED_INT_31( "UInt31", 0L, 2147483647L),
		UNSIGNED_INT_32( "UInt32", 0L, 4294967295L),
		UNSIGNED_INT_48( "UInt48", 0L, 281474976710655L),
		@Erroneous
		UNSIGNED_INT_64( "UInt64", 0L, 281474976710655L),// Bug TAU//18446744073709551615;
		// signed integer
		SIGNED_INT( "Int", Long.MIN_VALUE, Long.MAX_VALUE),
		SIGNED_INT_01( "Int1", -1L, 0L),
		SIGNED_INT_02( "Int2", -2L, 1L),
		SIGNED_INT_03( "Int3", -4L, 3L),
		SIGNED_INT_04( "Int4", -8L, 7L),
		SIGNED_INT_05( "Int5", -16L, 15L),
		SIGNED_INT_06( "Int6", -32L, 31L),
		SIGNED_INT_07( "Int7", -64L, 63L),
		SIGNED_INT_08( "Int8", -128L, 127L),
		SIGNED_INT_09( "Int9", -256L, 255L),
		SIGNED_INT_10( "Int10", -512L, 511L),
		SIGNED_INT_11( "Int11", -1024L, 1023L),
		SIGNED_INT_12( "Int12", -2048L, 2047L),
		SIGNED_INT_13( "Int13", -4096L, 4095L),
		SIGNED_INT_14( "Int14", -8192L, 8191L),
		SIGNED_INT_15( "Int15", -16384L, 16383L),
		SIGNED_INT_16( "Int16", -32768L, 32767L),
		SIGNED_INT_17( "Int17", -65536L, 65535L),
		SIGNED_INT_18( "Int18", -131072L, 131071L),
		SIGNED_INT_19( "Int19", -262144L, 262143L),
		SIGNED_INT_20( "Int20", -524288L, 524287L),
		SIGNED_INT_21( "Int21", -1048576L, 1048575L),
		SIGNED_INT_22( "Int22", -2097152L, 2097151L),
		SIGNED_INT_23( "Int23", -4194304L, 4194303L),
		SIGNED_INT_24( "Int24", -8388608L, 8388607L),
		SIGNED_INT_25( "Int25", -16777216L, 16777215L),
		SIGNED_INT_26( "Int26", -33554432L, 33554431L),
		SIGNED_INT_27( "Int27", -67108864L, 67108863L),
		SIGNED_INT_28( "Int28", -134217728L, 134217727L),
		SIGNED_INT_29( "Int29", -268435457L, 268435456L),
		SIGNED_INT_30( "Int30", -536870912L, 536870911L),
		SIGNED_INT_31( "Int31", -1073741824L, 1073741823L),
		SIGNED_INT_32( "Int32", -2147483648L, 2147483647L);
		
		private final String typeName;
		private final Long minValue;
		private final Long maxValue;
		
		private IntegerType( final String theTypeName, final Long theMinValue, final Long theMaxValue) {
			this.typeName = theTypeName;
			this.minValue = theMinValue;
			this.maxValue = theMaxValue;
		}
		
		public String getTypeName() {
			return typeName; 
		}
		
		public Long getMinValue() {
			return minValue;
		}
		
		public Long getMaxValue() {
			return maxValue;
		}
		
		public static IntegerType valueOfTypeName( final String theTypeName) {
			for (IntegerType type : values()) {
				if (type.typeName.equals( theTypeName))
					return type;
			}
			return IntegerType.UNDEFINED;
		}
	}


	
	

	private static final String TITLE = "Integer Editor";
	private static final String DESCRIPTION = "";
	private static IntegerType integerType = IntegerType.UNSIGNED_INT;
	
	public IntegerEditor() {
		super( TITLE, DESCRIPTION);
	}
	
	@Override
	public void setParameter(IParameter<IntegerValue> theParameter) {
		super.setParameter( theParameter);
		determineIntegerType();
	}

	private void determineIntegerType() {
		String parameterType = getParameter().getType();
		integerType = IntegerType.valueOfTypeName( parameterType);
	}
	
	
	private Layout extractLayoutFromParams( final Layout theDefaultLayout, final Object ...theParams) {
		Layout layout = theDefaultLayout; 
		for (Object object : theParams) {
			if ( object instanceof Layout)
		    layout = (Layout) object;
		}
		return layout;
	}
	
	private Object[] extractLayoutDataFromParams( final Object theDefaultLayoutData, final int theCountOfCells, final Object[] theParams) {
		if ( theCountOfCells < 1) {
			Object[] defaultResult = {theDefaultLayoutData};
			return defaultResult;
		}
		
		Object[] layoutData = new Object[theCountOfCells];
		layoutData[0] = theDefaultLayoutData;
		
		int i = 0;
		for (Object object : theParams) {
			if ( object instanceof GridData || 
				   object instanceof RowData ||	  
			     object instanceof FormData) {
				layoutData[i] = object;
				i++;
				if ( i >= theCountOfCells)
					break;
			}
		} 
		
		if ( i < theCountOfCells) {
			for ( int j = i; j < layoutData.length; j++) {
				layoutData[j] = layoutData[0];
			}
		}
		
		return layoutData;
	}

	@Override
	public Composite createControl(Composite theParent, Object... theParams) {
		
		// TODO solve problems with GridLayout: Width of each cell in a row has the width of the smallest cell. 
		
		Layout layout = extractLayoutFromParams( new RowLayout(), theParams);
		Object[] layoutData = extractLayoutDataFromParams( new RowData(), 3, theParams);
		
		Composite container = new Composite( theParent, SWT.None);
		container.setLayout( layout);
		
    CLabel label = new CLabel( container, SWT.LEFT);
		label.setText( this.getParameter().getName().replaceFirst( this.getParameter().getModuleName() + ".", "") + ": ");
		label.setLayoutData( layoutData[0]);
		
		if ( integerType.getMinValue() < Integer.MIN_VALUE ||
				 integerType.getMaxValue() > Integer.MAX_VALUE) {
			Text text = new Text( container, SWT.BORDER);
			text.setText( getParameter().getValue().getTheNumber().toString());
     

			text.setTextLimit( integerType.getMaxValue().toString().length());
			text.setLayoutData( layoutData[1]);
		} else {
			Spinner spinner = new Spinner ( container, SWT.BORDER);
			spinner.setMinimum( integerType.getMinValue().intValue());
			spinner.setMaximum( integerType.getMaxValue().intValue());
			spinner.setSelection( getParameter().getValue().getTheNumber().intValue());
			spinner.setIncrement( 1);
			spinner.setPageIncrement( 100);
			spinner.setTextLimit( integerType.getMaxValue().toString().length());
			spinner.setLayoutData( layoutData[1]);
		}
		
    label = new CLabel( container, SWT.LEFT);
		label.setText( this.getParameter().getDescription());
		label.setLayoutData( layoutData[2]);
		
		container.setSize( container.computeSize( SWT.DEFAULT, SWT.DEFAULT));
		container.layout();
		
		return container;
	}


}
