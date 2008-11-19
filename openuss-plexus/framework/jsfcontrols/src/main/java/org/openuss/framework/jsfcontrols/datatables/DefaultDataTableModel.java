package org.openuss.framework.jsfcontrols.datatables;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.datascroller.ScrollerActionEvent;


/**
 * Default DataTableModel to manage selected row and page navigation
 * @author Ingo Dueppe
 * 
 * @deprecated
 */
public class DefaultDataTableModel {
	public final static int DEFAULT_ROWS = 5;

	public final static int NO_ROW_SELECTED = -1;

	private int rowIndex;
	private int rowsPerPage = DEFAULT_ROWS;
	private int selectedRowIndex = NO_ROW_SELECTED;
	
	private int pageIndex;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getSelectedRowIndex() {
		return selectedRowIndex;
	}

	/**
	 * Defines the currently selected row index. 
	 * If no row is selected set the row index to -1. 
	 * @param selectedRowIndex
	 * 
	 */
	public void setSelectedRowIndex(int selectedRowIndex) {
		this.selectedRowIndex = selectedRowIndex;
		this.setRowIndex(selectedRowIndex);
	}
	
	/**
	 * Generate rowClasses attribute for table component
	 * Workaround for <a href="https://issues.apache.org/jira/browse/TOMAHAWK-523">tomahawk issue 523</a> 
	 * @return string of classes
	 */
	public String getRowClasses() {
		StringBuilder classes = new StringBuilder();
		int lastRowOnPage = rowIndex+rowsPerPage;
		
		for (int i = rowIndex; i < lastRowOnPage; i++) {
			if (i > rowIndex) {
				classes.append(", ");
			}

			if (i == selectedRowIndex) {
				classes.append("selectedRow");
			} else if (i % 2 == 0) {
				classes.append("even");
			} else {
				classes.append("odd");
			}
		}

		return classes.toString();
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = roundDownRowIndex(rowIndex);
	}

	/**
	 * Calculate the index of first row on the page 
	 * @param rowIndex
	 * @return
	 */
	private int roundDownRowIndex(int rowIndex) {
		int page = rowIndex / rowsPerPage;
		int roundedIndex = Math.abs(page * rowsPerPage);
		return roundedIndex;
	}
	
	/**
	 * Listen to datascroller to be informed on page changes
	 * @param event
	 */
	public void scrollerActionEvent(ActionEvent event) {
		if (event instanceof ScrollerActionEvent) {
			int page = ((ScrollerActionEvent) event).getPageIndex();
			String facet = ((ScrollerActionEvent) event).getScrollerfacet();
			if (page == -1) {
				if ("next".equals(facet)) {
					page = pageIndex + 1;
				} else if ("first".equals(facet)) {
					page = 0;
				} else if ("previous".equals(facet)) {
					page = pageIndex - 1;
				} else if ("last".equals(facet)) {
					page = pageIndex + 1;
				}
			}
			setPageIndex(page);
			setRowIndex((page-1)*rowsPerPage);
		}
	}
}
