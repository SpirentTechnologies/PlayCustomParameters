package ttworkbench.play.parameters.ipv6.widgets.tableviewer;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import ttworkbench.play.parameters.ipv6.common.ParameterValueUtil;

import com.testingtech.ttworkbench.ttman.parameters.api.IParameterEditor;

public class WidgetTableViewerAdvancedControl extends WidgetTableViewerControl {
	
	public WidgetTableViewerAdvancedControl(Composite parent) {
		super(parent);
	}
	

	@Override
	protected TableColumn createTableViewerColumn(final ColumnDefinition coldef) {
		TableColumn tc = super.createTableViewerColumn( coldef);
		final TableViewerColumn tvc = new TableViewerColumn( getTableViewer(), tc);
		tvc.setLabelProvider(new CellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				Object obj = cell.getElement();
				if(obj instanceof IParameterEditor<?>) {
					cell.setText( String.valueOf( coldef.columnType.valueOf( (IParameterEditor<?>) obj)));
				}
			}
		});
		
		if(coldef.columnType == ParameterEditorColumnType.COLUMN_PARAMETER_VALUE) {
			tvc.setEditingSupport( new EditingSupport(getTableViewer()) {

				@Override
				protected void setValue(Object entity, Object value) {
					if(entity instanceof IParameterEditor<?>) {
						IParameterEditor<?> editor = (IParameterEditor<?>) entity;
						Object currValue = editor.getParameter().getValue();
						if(!currValue.equals( value)) {
							ParameterValueUtil.setValue( editor.getParameter(), String.valueOf( value));
						}
					}
					getTableViewer().refresh();
				}
				
				@Override
				protected Object getValue(Object obj) {
					Object out = null;
					if(obj instanceof IParameterEditor<?>) {
						IParameterEditor<?> editor = (IParameterEditor<?>) obj;
						out = coldef.columnType.valueOf(editor);
					}
					return out;
				}
				
				@Override
				protected CellEditor getCellEditor(final Object obj) {
					Table table = getTableViewer().getTable();
					if(obj instanceof IParameterEditor) {
						return new CellParameterEditor(table) {
							
							@SuppressWarnings("unchecked")
							public IParameterEditor<Object> getEditor() {
								return (IParameterEditor<Object>) obj;
							}
						};
					}
					return new TextCellEditor(table);
				}
				
				@Override
				protected boolean canEdit(Object obj) {
					boolean canEdit = obj instanceof IParameterEditor<?>; // && ((IParameterEditor<?>) obj).isEnabled();
					return canEdit;
				}
			});
		}
		
		return tc;
	}

}
