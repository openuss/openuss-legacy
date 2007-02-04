package org.openuss.framework.web.jsf.model;

import java.io.Serializable;

import javax.faces.component.UIData;
import javax.faces.model.DataModel;

import org.apache.log4j.Logger;
import org.openuss.framework.web.jsf.controller.BaseBean;

/**
 * Generic PagedTable 
 * 
 * @author Ingo Dueppe
 * 
 * @param <T>
 */
public abstract class AbstractPagedTable<T> extends BaseBean{

	private static final Logger logger = Logger.getLogger(AbstractPagedTable.class);

	public static final int DEFAULT_ROWS_PER_PAGE = 10;
	
	private PagedTableMemento state = new PagedTableMemento();
	
	private UIData table;
	private DataModel data;
	
	public abstract DataPage<T> getDataPage(int startRow, int pageSize);
	
	public AbstractPagedTable() {
		logger.debug("init");
		connectMementoWithSession();
	}
	

	public DataModel getData() {
		// check whether or not data is available and it is not a preserved data model
		if (data == null || !(data instanceof PagedDataModel<?>)) {
			logger.debug("creating new LocalDataModel");
			data = new LocalDataModel(state.rowsPerPage);
		}
		return data;
	}

	public void setData(DataModel data) {
		this.data = data;
	}


	/**
	 * The currently selected row object
	 * @return T or null
	 */
	public T getRowData() {
		if (table != null) {
			return (T) table.getRowData();
		} else {
			return null;
		}
	}
	
	/**
	 * Index of the currently selected row
	 * @return index or -1
	 */
	public int getRowIndex() {
		if (table != null) {
			return table.getRowIndex();
		} else {
			return -1;
		}
	}

	/**
	 * LocalDataModel of <T>
	 */
	private class LocalDataModel extends PagedListDataModel<T> {

		public LocalDataModel(int pageSize) {
			super(pageSize);
		}
		
		@Override
		public int getPageSize() {
			if (table != null) {
				return getTable().getRows();
			} else {
				return state.rowsPerPage;
			}
		}

		@Override
		public DataPage<T> fetchPage(int startRow, int pageSize) {
			return getDataPage(startRow, pageSize);
		}

	}

	public boolean isAscending() {
		return state.ascending;
	}

	public void setAscending(boolean sortAscending) {
		state.ascending = sortAscending;
	}

	public String getSortColumn() {
		return state.sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		logger.debug("setSortColumn(sortColumn=" + sortColumn + ")"); //$NON-NLS-1$ //$NON-NLS-2$

		state.sortColumn = sortColumn;
	}

	public UIData getTable() {
		return table;
	}

	public void setTable(UIData table) {
		this.table = table;
	}
	
	public int getRowsPerPage() {
		return state.rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		state.rowsPerPage = rowsPerPage;
	}


	public int getFirstRow() {
		return state.firstRow;
	}

	public void setFirstRow(int firstRow) {
		logger.debug("setFirstRow(firstRow=" + firstRow + ")"); //$NON-NLS-1$ //$NON-NLS-2$

		state.firstRow = firstRow;
	}

	protected void connectMementoWithSession() {
		String sessionKey = getMementoSessionKey();
		if (containsSessionKey(sessionKey)) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch paged table state into session under "+sessionKey);
			}
			state = (PagedTableMemento) getSessionAttribute(sessionKey);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("store paged table state from session under "+sessionKey);
			}
			setSessionAttribute(sessionKey,state);
		}
	}
	
	/**
	 * Generates a session key of the name of this class. 
	 * This method needs to ensure that no other table uses the same session key.
	 * 
	 * @return unique session key 
	 */
	protected String getMementoSessionKey() {
		return this.getClass().getName();
	}
	
	/**
	 * Memento of the PagedTable representing its current internal state 
	 * @author Ingo Dueppe
	 */
	private static class PagedTableMemento implements Serializable {

		private static final long serialVersionUID = -4565453284559704002L;
		
		private String sortColumn;
		private boolean ascending = true;
		private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
		private int firstRow = 0;
		
	}

}
