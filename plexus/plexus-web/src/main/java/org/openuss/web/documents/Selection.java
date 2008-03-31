package org.openuss.web.documents;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;

/**
 * This class handels the selection of a set of object.
 * 
 * @author Ingo Dueppe
 * 
 * @param <T>
 */
public class Selection<T> implements Serializable{

	private static final long serialVersionUID = -1738811426145531329L;

	private static final Logger logger = Logger.getLogger(Selection.class);

	private boolean state;
	private boolean modified;

	private Map<T, Boolean> map = new HashMap<T, Boolean>();

	public void clear() {
		map.clear();
		state = false;
		modified = false;
	}
	
	public void setEntries(List<T> entries) {
		Map<T, Boolean> newMap = new HashMap<T, Boolean>();
		for (T entry: entries) {
			if (map.containsKey(entry)) {
				newMap.put(entry, map.get(entry));
			} else {
				newMap.put(entry, state);
			}
		}
		map = newMap;
	}
	
	public boolean isSelected(Object key) {
		Boolean bool = map.get(key);
		return bool == null? false : bool;
	}
	
	public Map<T, Boolean> getMap() {
		return map;
	}

	public void setMap(Map<T, Boolean> map) {
		this.map = map;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void switchSelection(ValueChangeEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("switching selection from " + event.getOldValue() + " to " + event.getNewValue());
		}
		modified = true;
	}

	public void processSwitch() {
		if (modified) {
			modified = false;
			for (Map.Entry<T, Boolean> entry : map.entrySet()) {
				entry.setValue(state);
			}
		}
	}
}
