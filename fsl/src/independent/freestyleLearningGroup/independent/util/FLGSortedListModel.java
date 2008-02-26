package freestyleLearningGroup.independent.util;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractListModel;

/**
 * This class implements a lexicographical order on <code>Vector</code>. If a new Element is added
 * by calling <code>addElement</code>, the new element is inserted at the right position.
 * Different orderings may be implemented by overriding <code>addElement</code>.
 */
public class FLGSortedListModel extends AbstractListModel {
    private Vector listData;

    public FLGSortedListModel() {
        listData = new Vector();
    }

    /** Add an object to the list */
    public void addElement(Object object) {
        // find first greater object with toString
        Iterator iterator = listData.iterator();
        String objectString = object.toString();
        String compare;
        int index = 0;
        while (iterator.hasNext()) {
            compare = iterator.next().toString();
            if (objectString.compareTo(compare) < 0) break;
            index++;
        }
        listData.add(index, object);
        this.fireIntervalAdded(this, listData.size(), listData.size());
    }

    /** Remove object at position <code>index</code> from list */
    public void removeElement(int index) {
        listData.remove(index);
        this.fireIntervalRemoved(this, index, index);
    }

    /** Removes all of the elements from this list */
    public void clear() { listData.clear(); }

    /** Returns number of elements in the list */
    public int getSize() { return listData.size(); }

    /** Returns object at position <code>index</code> */
    public Object getElementAt(int index) {
        return listData.elementAt(index);
    }

    /** Returns complete data of list as <code>iterator</code> */
    public Iterator iterator() { return listData.iterator(); }
}
