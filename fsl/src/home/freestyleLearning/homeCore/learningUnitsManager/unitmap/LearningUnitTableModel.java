/**
 * A table model displaying information about the selected learning unit.
 * 
 * @author Jens Sieberg, 31.07.2006 - 11.09.2006
 */
package freestyleLearning.homeCore.learningUnitsManager.unitmap;

import javax.swing.table.AbstractTableModel;

import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.DefaultGraphCell;

import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitDescriptor;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class LearningUnitTableModel extends AbstractTableModel implements
		GraphSelectionListener {

	FLGInternationalization internationalization;
	
	String[] columnNames = {"", ""};
	//TODO internationalization & ArrayList
	Object[][] data;

	UnitmapGraph graph;
	
	public LearningUnitTableModel(UnitmapGraph graph, FLGInternationalization internationalization)
	{
		data = new Object[][] { {internationalization.getString("table.id"), ""}, 
	            {internationalization.getString("table.title"), ""}, 
	            {internationalization.getString("table.parentId"), ""}, 
	            {internationalization.getString("table.version"), ""},
	            {internationalization.getString("table.authors"), ""},
	            {internationalization.getString("table.status"), ""} };
		
		this.graph = graph;
		
		this.internationalization = internationalization;
	}
	
	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		
		fireTableCellUpdated(row, col);
	}
	
	public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

	public String getColumnName(int col) {
		return "";
	}
	
	/** GraphSelectionListener **/
	public void valueChanged(GraphSelectionEvent e) {
        DefaultGraphCell cell = (DefaultGraphCell)e.getCell();
        
    	setValueAt("", 0, 1);
    	setValueAt("", 1, 1);
    	setValueAt("", 2, 1);
    	setValueAt("", 3, 1);
    	setValueAt("", 4, 1);
    	setValueAt("", 5, 1);

        if (!graph.isSelectionEmpty())
        {
            FSLLearningUnitDescriptor userObject = null;
            
            if (cell.getUserObject() instanceof FSLLearningUnitDescriptor)
            	userObject = (FSLLearningUnitDescriptor)cell.getUserObject();
            
            if (userObject != null) {
            	setValueAt(userObject.getId(), 0, 1);
            	setValueAt(userObject.getTitle(), 1, 1);
            	setValueAt(userObject.getParentID(), 2, 1);
            	setValueAt(userObject.getVersion(), 3, 1);
            	setValueAt(userObject.getAuthors(), 4, 1);
            	if (!graph.isAuthor())
            	{
            		double progressStatus = graph.getLearningUnitsManager().getLearningUnitProgressStatus(userObject.getId());
            		if (progressStatus == -1)
            			setValueAt("not supported", 5, 1);
            		else
            		{
            			String progressStatusText = String.valueOf((int)(progressStatus * 100)) + "%";
            			setValueAt(progressStatusText, 5, 1);
            		}
            	}
            }
            else
            {
            	if (cell.getUserObject() instanceof String)
            	{
            		setValueAt(cell.getUserObject(), 0, 1);
            	}
            }
        }
	}
}