package org.openuss.framework.web.jsf.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.component.UIData;
import javax.faces.event.PhaseId;
import javax.faces.model.DataModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.myfaces.custom.datascroller.HtmlDataScroller;
import org.openuss.framework.web.jsf.controller.BaseBean;
import org.springframework.beans.support.PropertyComparator;

/**
 * Generic PagedTable
 * 
 * @author Ingo Dueppe
 * 
 * @param <T>
 */
public abstract class AbstractPagedTable<T> extends BaseBean {

	private static final Logger logger = Logger.getLogger(AbstractPagedTable.class);

	public static final int DEFAULT_ROWS_PER_PAGE = 10;

	private PagedTableMemento state = new PagedTableMemento();

	private UIData table;
	private HtmlDataScroller scroller;
	private DataModel data;
	
	public abstract DataPage<T> getDataPage(int startRow, int pageSize);

	public AbstractPagedTable() {
		logger.debug("init");
		connectMementoWithSession();
	}

	public DataModel getData() {
		// check whether or not data is available and it is not a preserved data
		// model
		if (data == null || !(data instanceof PagedListDataModel<?>)) {
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
	 * 
	 * @return T or null
	 */
	@SuppressWarnings("unchecked")
	public T getRowData() {
		if (table != null) {
			return (T) table.getRowData();
		} else {
			return null;
		}
	}

	/**
	 * Index of the currently selected row
	 * 
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

	private boolean firstRowChecked = false;
	
	public int getFirstRow() {
		// Should check if the firstRow index greater then datasize
		// This may only happen within the render-response phase.
		// Maybe this class should be register its own phase listener
		// LocalDataModel model = (LocalDataModel) getData();
		// state.firstRow = model.checkFirstRow(state.firstRow);
		// DONE - id 21.03.2007 - see PagedListPhaseListener
		if (isInRenderResponsePhase() && !firstRowChecked) {
			logger.debug("checking first row");
			state.firstRow = ((LocalDataModel) getData()).checkFirstRow(state.firstRow);
			firstRowChecked = true;
		} else if (!isInRenderResponsePhase()){
			firstRowChecked = false;
		}
		return state.firstRow;
	}

	private boolean isInRenderResponsePhase() {
		return PhaseId.RENDER_RESPONSE.equals(PagedListPhaseListener.currentPhase());
	}

	public void setFirstRow(int firstRow) {
		if (firstRow < 0) {
			logger.debug("setFirstRow(firstRow=" + 0 + ")");
			state.firstRow = 0;
		} else {
			logger.debug("setFirstRow(firstRow=" + firstRow + ")");
			state.firstRow = firstRow;
		}
	}

	protected void connectMementoWithSession() {
		String sessionKey = getMementoSessionKey();
		if (containsSessionKey(sessionKey)) {
			if (logger.isDebugEnabled()) {
				logger.debug("fetch paged table state into session under " + sessionKey);
			}
			state = (PagedTableMemento) getSessionAttribute(sessionKey);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("store paged table state from session under " + sessionKey);
			}
			setSessionAttribute(sessionKey, state);
		}
	}

	/**
	 * Generates a session key of the name of this class. This method needs to
	 * ensure that no other table uses the same session key.
	 * 
	 * @return unique session key
	 */
	protected String getMementoSessionKey() {
		return this.getClass().getName();
	}

	/**
	 * Default property sort method
	 * 
	 * @param periods
	 */
	@SuppressWarnings("unchecked")
	protected void sort(List<T> list) {
		if (StringUtils.isNotBlank(getSortColumn())) {
			Collections.sort(list, new PropertyComparator(getSortColumn(), true, isAscending()));
		}
	}

	/**
	 * Memento of the PagedTable representing its current internal state
	 * 
	 * @author Ingo Dueppe
	 */
	private static class PagedTableMemento implements Serializable {

		private static final long serialVersionUID = -4565453284559704002L;

		private String sortColumn;
		private boolean ascending = true;
		private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
		private int firstRow = 0;

	}

	public HtmlDataScroller getScroller() {
		return scroller;
	}

	public void setScroller(HtmlDataScroller scroller) {
		this.scroller = scroller;
	}
}
