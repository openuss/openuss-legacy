package org.openuss.framework.web.jsf.model;

import java.util.List;

/**
 * @author Ingo Dueppe
 *
 * @param <T>
 */
public interface ListDataModel<T> {

	/**
	 * @param List<T> data
	 */
	public abstract void setData(List<T> data);

	/**
	 * @return List<T>
	 */
	public abstract List<T> getData();

	/**
	 * Selected Data
	 * @return the selected object or null
	 */
	public abstract T getSelectedData();

	/**
	 * Define the selected Data
	 * @return the selected object or null
	 */
	public abstract void setSelectedData(T selected);

	/**
	 * Retrieve the currently selected record index;
	 * @return index of the selected row or -1 if no row is selected
	 */
	public abstract int getSelectedRowIndex();

	/**
	 * Set the selected row index starting at 0 or -1 if no row is selected
	 * @param selectedRowIndex
	 */
	public abstract void setSelectedRowIndex(int selectedRowIndex);

}