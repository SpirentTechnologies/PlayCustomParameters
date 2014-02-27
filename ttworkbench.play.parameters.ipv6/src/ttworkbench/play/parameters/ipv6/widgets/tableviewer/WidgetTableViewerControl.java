package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class WidgetTableViewerControl {


	protected class ColumnDefinition {
		public ParameterEditorColumnType columnType;
		public String name;
		public int direction;
		public ColumnDefinition(ParameterEditorColumnType columnType, String name, int direction) {
			this.columnType = columnType;
			this.name = name;
			this.direction = direction;
		}
	}
	
	private Composite composite;
	private Composite scrolledComposite;
	private Combo combo;
	private TableViewer tv;
	private TableViewerLabelProvider parameterLabelProvider = new TableViewerLabelProvider();
	private LinkedList<ParameterEditorHolder> holders = new LinkedList<ParameterEditorHolder>();
	private LinkedList<ColumnDefinition> columnDefinitions = new LinkedList<WidgetTableViewerControl.ColumnDefinition>();

	
	public WidgetTableViewerControl(Composite parent) {
		this.scrolledComposite = parent;
	}

	public Composite getComposite() {
		
		if(composite==null) {
			createComposite();
		}
		
		return composite;
	}

	private void createComposite() {
		composite = new Composite( scrolledComposite, SWT.NONE);
		composite.setLayout( new GridLayout( 1, false));

		// Create the combo to hold the team names
		combo = new Combo( composite, SWT.READ_ONLY);
		combo.setLayoutData( new GridData( GridData.FILL_HORIZONTAL));

		// Set the content and label providers
		tv = new TableViewer( composite, SWT.FULL_SELECTION);
		
		tv.setContentProvider( new TableViewerContentProvider());
		tv.setLabelProvider( parameterLabelProvider);
		tv.setSorter( new TableViewerSorter());
		
		configureTableColumns();
		configureTableContents();
	}

	
	public TableViewer getTableViewer() {
		return tv;
	}

	private void configureTableColumns() {
		for(final ColumnDefinition coldef : columnDefinitions) {
			TableColumn tc = createTableViewerColumn(coldef);
			tc.addSelectionListener( new SelectionAdapter() {
				public void widgetSelected(SelectionEvent event) {
					( (TableViewerSorter) tv.getSorter()).doSort( coldef.columnType);
					tv.refresh();
				}
			});
			parameterLabelProvider.addColumn( coldef.columnType);
		}
	}

	
	protected TableColumn createTableViewerColumn(ColumnDefinition coldef) {
		TableColumn tc = new TableColumn( tv.getTable(), coldef.direction);
		tc.setText( coldef.name);
		return tc;
	}

	public WidgetTableViewerControl addTableColumn(ParameterEditorColumnType columnType, String name) {
		addTableColumn(columnType, name, SWT.LEFT);
		return this;
	}
	
	public WidgetTableViewerControl addTableColumn(ParameterEditorColumnType columnType, String name, int direction) {
		columnDefinitions.add( new ColumnDefinition(columnType, name, direction));
		return this;
	}


	public WidgetTableViewerControl addParameterEditorHolders(String label, Collection<IParameterEditor<?>> editors) {
		holders.add( new ParameterEditorHolder(label, editors.toArray( new IParameterEditor<?>[0])));
		return this;
	}
	public WidgetTableViewerControl addParameterEditorHolders(String label, IParameterEditor<?>... editors) {
		holders.add( new ParameterEditorHolder(label, editors));
		return this;
	}
	
	
	private void configureTableContents() {
		for (ParameterEditorHolder editor : holders) {
			combo.add(editor.getLabel());
		}

		// Add a listener to change the tableviewer's input
		combo.addSelectionListener( new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				update(holders.get( ((Combo) event.widget).getSelectionIndex()));
			}
		});

		// Select the first item
		combo.select(0);
		update(holders.getFirst());

		// Pack the columns
		Table table = tv.getTable();
		for(TableColumn tc : table.getColumns()) {
			tc.pack();
		}

		// Turn on the header and the lines
		table.setLayoutData( new GridData( GridData.FILL_BOTH));		
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

	/**
	 * Updates the application with the selected team
	 * 
	 * @param team
	 *            the team
	 */
	private void update(ParameterEditorHolder parameterEditorHolder) {
		tv.setInput(parameterEditorHolder);
	}

}
