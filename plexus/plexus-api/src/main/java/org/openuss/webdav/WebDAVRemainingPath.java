package org.openuss.webdav;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * A WebDAV path that is currently being resolved.
 * 
 * This object is not imutable!
 */
public class WebDAVRemainingPath extends WebDAVPath {
	/**
	 * Magic value for {@link #advance(int)} 
	 */
	public static int ADVANCE_ALL = Integer.MAX_VALUE;
	
	/**
	 * The list of yet unresolved elements.
	 */
	protected List<String> elemsToResolve;
	
	protected WebDAVRemainingPath(List<String> pathElems, String clientPath, List<String> elemsToResolve) {
		super(pathElems, clientPath);

		this.elemsToResolve = elemsToResolve;
	}

	/**
	 * @return The next path element to resolve or null when all elements are resolved.
	 */
	public String getNextToResolve() {
		return moreToResolve() ? elemsToResolve.get(0) : null;
	}
	
	/**
	 * @return true iff at least one more path element has to be resolved.
	 */
	public boolean moreToResolve() {
		return toResolveCount() > 0;
	}
	
	/**
	 * @return The number of path elements yet to resolve.
	 */
	public int toResolveCount() {
		return elemsToResolve.size();
	}
	
	/**
	 * @return The list of elements yet to resolve.
	 * 	Changing it changes the state of this object!
	 */
	public List<String> getToResolve() {
		return elemsToResolve;
	}
	
	/**
	 * Resolve one further element.
	 * 
	 * @return moreToResolve before the action.
	 */
	public boolean advance() {
		return advance(1) == 1;
	}
	
	/**
	 * Resolve further elements.
	 * 
	 * @param steps The number of elements to resolve or ADVANCE_ALL.
	 * @return The number of steps taken. This number is positive if elements were resolved, negative if it went the other way.
	 */
	public int advance(int steps) {
		int res = 0;
		
		while ((steps-- > 0) && !elemsToResolve.isEmpty()) {
			pathElems.add(elemsToResolve.remove(0));
			res++;
		}
		while ((steps++ < 0) && !pathElems.isEmpty()) {
			elemsToResolve.add(0, pathElems.remove(pathElems.size() - 1));
			res--;
		}
		
		return res;
	}
	
	/**
	 * Find out the common path of two paths.
	 * Both remaining 
	 * 
	 * @param other The other remaining path element.
	 * @return The common part of both paths.
	 */
	public WebDAVPath common(WebDAVRemainingPath other) {
		List<String> thisToResolve;
		List<String> otherToResolve;
		List<String> commonElements = new ArrayList<String>();
		
		if (((WebDAVPath)this).semanticEqual(other)) {
			commonElements.addAll(getList());
			thisToResolve = getToResolve();
			otherToResolve = other.getToResolve();
		} else {
			// Different already resolved elements
			thisToResolve = addLists(getList(), getToResolve()); 
			otherToResolve = addLists(getList(), getToResolve());
		}
		
		ListIterator<String> mit = thisToResolve.listIterator();
		ListIterator<String> oit = otherToResolve.listIterator();
		String s;
		// Compare each element, add to result list iff equal
		while (mit.hasNext() && oit.hasNext() &&
				((s = (mit.next())).equals(oit.next()))) {
			commonElements.add(s);
		}
		// Advance pointers
		
		
		return new WebDAVPath(commonElements);
	}
	
	/**
	 * @param l1 The first sublist.
	 * @param l2 The second sublist.
	 * @return A list with all elements of l1 and l2 in the order l1 < l2.
	 */
	protected static List<String> addLists(List<String> l1, List<String> l2) {
		List<String> l = new ArrayList<String>(l1.size() + l2.size());
		l.addAll(l1);
		l.addAll(l2);
		return l;
	}
}
