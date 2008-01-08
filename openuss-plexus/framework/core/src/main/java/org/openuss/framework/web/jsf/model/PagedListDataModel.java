package org.openuss.framework.web.jsf.model;

import org.apache.log4j.Logger;

import javax.faces.model.DataModel;

/**
 * A special type of JSF DataModel to allow a datatable and datascroller to page
 * through a large set of data without having to hold the entire set of data in
 * memory at once.
 * <p>
 * Any time a managed bean wants to avoid holding an entire dataset, the managed
 * bean should declare an inner class which extends this class and implements
 * the fetchData method. This method is called as needed when the table requires
 * data that isn't available in the current data page held by this object.
 * <p>
 * This does require the managed bean (and in general the business method that
 * the managed bean uses) to provide the data wrapped in a DataPage object that
 * provides info on the full size of the dataset.
 * 
 * Inspired by myfaces-wiki: 
 * <a href="http://wiki.apache.org/myfaces/WorkingWithLargeTables"/>working with large tables</a>
 * 
 * @author Ingo Düppe
 *  
 */
public abstract class PagedListDataModel<T> extends DataModel {

	private static final Logger logger = Logger.getLogger(PagedListDataModel.class);

	int pageSize;
	int rowIndex;
	DataPage<T> page;

	/*
	 * Create a datamodel that pages through the data showing the specified
	 * number of rows on each page.
	 */
	public PagedListDataModel(int pageSize) {
		super();
		this.pageSize = pageSize;
		this.rowIndex = -1;
		this.page = null;
	}

	/**
	 * Not used in this class; data is fetched via a callback to the fetchData
	 * method rather than by explicitly assigning a list.
	 */
	@Override
	public void setWrappedData(Object o) {
		throw new UnsupportedOperationException("setWrappedData");
	}

	@Override
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * Specify what the "current row" within the dataset is. Note that the
	 * UIData component will repeatedly call this method followed by getRowData
	 * to obtain the objects to render in the table.
	 */
	@Override
	public void setRowIndex(int index) {
		rowIndex = index;
	}

	/**
	 * Return the total number of rows of data available (not just the number of
	 * rows in the current page!).
	 */
	@Override
	public int getRowCount() {
		return getPage().getDatasetSize();
	}

	/**
	 * Return a DataPage object; if one is not currently available then fetch
	 * one. Note that this doesn't ensure that the datapage returned includes
	 * the current rowIndex row; see getRowData.
	 */
	private DataPage<T> getPage() {
		if (page != null) {
			return page;
		}
		int rowIndex = getRowIndex();
		int startRow = rowIndex;
		if (rowIndex == -1) {
			// even when no row is selected, we still need a page
			// object so that we know the amount of data available.
			startRow = 0;
		}

		// invoke method on enclosing class
		page = fetchPage(startRow, getPageSize());
		return page;
	}


	/**
	 * Return the object corresponding to the current rowIndex. If the DataPage
	 * object currently cached doesn't include that index then fetchPage is
	 * called to retrieve the appropriate page.
	 */
	@Override
	public Object getRowData() {
		if (rowIndex < 0) {
			throw new IllegalArgumentException("Invalid rowIndex for PagedListDataModel; not within page");
		}

		// ensure page exists; if rowIndex is beyond dataset size, then
		// we should still get back a DataPage object with the dataset size
		// in it...
		if (page == null) {
			page = fetchPage(rowIndex, getPageSize());
		}

		// Check if rowIndex is equal to startRow,
		// useful for dynamic sorting on pages
		if (rowIndex == page.getStartRow()) {
			page = fetchPage(rowIndex, getPageSize());
		}

		int datasetSize = page.getDatasetSize();
		int startRow = page.getStartRow();
		int nRows = page.getData().size();
		int endRow = startRow + nRows;

		if (rowIndex >= datasetSize) {
			throw new IllegalArgumentException("Invalid rowIndex");
		}

		if (rowIndex < startRow) {
			page = fetchPage(rowIndex, getPageSize());
			startRow = page.getStartRow();
		} else if (rowIndex >= endRow) {
			page = fetchPage(rowIndex, getPageSize());
			startRow = page.getStartRow();
		}

		return page.getData().get(rowIndex - startRow);
	}

	@Override
	public Object getWrappedData() {
		return page.getData();
	}

	/**
	 * Return true if the rowIndex value is currently set to a value that
	 * matches some element in the dataset. Note that it may match a row that is
	 * not in the currently cached DataPage; if so then when getRowData is
	 * called the required DataPage will be fetched by calling fetchData.
	 */
	@Override
	public boolean isRowAvailable() {
		DataPage<T> page = getPage();
		if (page == null)
			return false;

		int rowIndex = getRowIndex();
		if (rowIndex < 0) {
			return false;
		} else if (rowIndex >= page.getDatasetSize()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Method which must be implemented in cooperation with the managed bean
	 * class to fetch data on demand.
	 */
	public abstract DataPage<T> fetchPage(int startRow, int pageSize);

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int checkFirstRow(int firstRow) {
		final DataPage<T> page = getPage();
		if (page != null) {
			int size = page.getDatasetSize();
			if ((size-1 < firstRow) && (size > 0 || firstRow > 0)) {
				firstRow = getPage().getDatasetSize() - getPageSize();
				if (firstRow < 0) {
					firstRow = 0;
				}
				if (logger.isDebugEnabled()) {
					logger.debug("reducing firstRow to "+ firstRow+" to point on the last page!");
				}
			}
		}
		return firstRow;
	}

}