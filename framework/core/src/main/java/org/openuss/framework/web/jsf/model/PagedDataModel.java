package org.openuss.framework.web.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;

import org.apache.log4j.Logger;
import org.apache.myfaces.custom.datascroller.ScrollerActionEvent;

/**
 * 
 * @author Ingo Dueppe
 *
 * @param <T> type of the rowdata
 */
public class PagedDataModel<T> extends DataModel implements Serializable, ListDataModel<T> {
	
	public static final Logger logger = Logger.getLogger(PagedDataModel.class);
	public static final long serialVersionUID = 6455292483377762042L;

	public static final int DEFAULT_ROWS_PER_PAGE = 5;
	public static final int NO_ROW_SELECTED = -1;

	private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;

	// State properties
	
	private int rowIndex = 0;
	private int selectedRowIndex = NO_ROW_SELECTED;
	private int firstRowIndex = 0;
	
	private List<T> data = new ArrayList<T>();
	
	public PagedDataModel() {
		logger.debug("init");
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setData(List<T> data) {
		setWrappedData(data);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public List<T> getData() {
		return data;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getRowData() {
		if (rowIndex < 0 && rowIndex >= data.size()) {
			throw new IllegalArgumentException("Invalid rowIndex for PagedListDataModel; not within page");
		}
		return data.get(rowIndex);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getWrappedData() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRowAvailable() {
		return rowIndex >= 0 && rowIndex < data.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setWrappedData(Object o) {
		logger.debug("setting wrapped data");
		
		T selected = getSelectedData();

		if (o instanceof List) {
			this.data = (List<T>) o;
		} else if (o instanceof DataModel) {
			this.data = (List<T>) ((DataModel)o).getWrappedData();
			
		}

		if (selected != null && data.contains(selected)) {
			selectedRowIndex = data.indexOf(selected) ;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public T getSelectedData() {
		if (data == null || selectedRowIndex < 0) {
			return null;
		}
		if (selectedRowIndex < firstRowIndex) {
			// The current perserveDataModel doesn't contain the needed selected row.
			// The current data is from a different page
			logger.debug("Selected row is not available!");
			return null;
		}
		
		int index = selectedRowIndex;
		
		if (index >= 0 && index < data.size()) {
			return data.get(index);
		} else {
			logger.debug("Selected row is not available! <-----------------------------");
			return null;
		}
	}
	
	/**
	 * @inheritDoc
	 */
	public void setSelectedData(T selected) {
		if (data == null || selected == null) {
			selectedRowIndex = NO_ROW_SELECTED;
		} else {
			selectedRowIndex = data.indexOf(selected);
		}
	}

	/**
	 * Generate rowClasses attribute for table component Workaround for <a
	 * href="https://issues.apache.org/jira/browse/TOMAHAWK-523">tomahawk issue 523</a>
	 * 
	 * @return string of classes
	 */
	public String getRowClasses() {
		StringBuilder classes = new StringBuilder();
		int lastRowIndexOnPage = firstRowIndex + rowsPerPage;
		for (int i = firstRowIndex; i < lastRowIndexOnPage; i++) {
			if (i == selectedRowIndex) {
				classes.append("selectedRow");
			} else if (i % 2 == 0) {
				classes.append("even");
			} else {
				classes.append("odd");
			}
			if (i < lastRowIndexOnPage - 1) {
				classes.append(", ");
			}
		}
		return classes.toString();
	}

	/**
	 * Defines the number of rows on a page
	 * @return int
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * Defines the number of rows on a page
	 * @param rowsPerPage
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSelectedRowIndex() {
		return selectedRowIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSelectedRowIndex(int selectedRowIndex) {
		this.selectedRowIndex = selectedRowIndex;
	}

	/**
	 * Defines the index of the record that will be displayed on the first row of the page.
	 * @param firstRow
	 */
	public void setFirstRowIndex(int firstRowIndex) {
		this.firstRowIndex = firstRowIndex;
	}
	
	/**
	 * The first row index of the currently selected page.
	 * @return x >= 0
	 */
	public int getFirstRowIndex() {
		if (data == null || firstRowIndex >= data.size() ) {
			firstRowIndex = 0;
		}
		return firstRowIndex;
	}

	/**
	 * Listen to datascroller to be informed on page changes
	 * 
	 * @param event
	 */
	public void scrollerActionEvent(ActionEvent event) {
		if (event instanceof ScrollerActionEvent) {
			int page = ((ScrollerActionEvent) event).getPageIndex();
			String facet = ((ScrollerActionEvent) event).getScrollerfacet();
			if (page == -1) {
				if ("next".equals(facet)) {
					firstRowIndex += rowsPerPage;
				} else if ("first".equals(facet)) {
					firstRowIndex = 0;
				} else if ("previous".equals(facet)) {
					firstRowIndex -= rowsPerPage;
				} else if ("last".equals(facet) && data != null) {
					int size = data.size();
					firstRowIndex = size - (size % rowsPerPage);
				}
			} else {
				firstRowIndex = (page - 1) * rowsPerPage;
			}
			if (firstRowIndex < 0) {
				firstRowIndex = 0;
			}
		}
	}
}
