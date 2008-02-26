package freestyleLearningGroup.independent.tourCreator;


/**
 * All classes to be used as customized FLG Tour have to implement this interface. 
 * All FLG tours are currently limited to one corresponding FLG unit.
 * @author Steffen Wachenfeld
 */
public interface FLGTour extends javax.swing.ListModel {
    
    /**
     * Returns the name of the FLG tour
     * @return name of the LFG tour
     */
    public String getTourName();
    
    
    /**
     * Adds the given tour element as throws elast element.
     * @param a_tourElement tour element to be added at the last position
     */
    public void addElement(FLGTourElement a_tourElement);
    
    /**
     * Removes the given tourElement
     * @param a_tourElement the element to be removed
     */
    public void removeElement(FLGTourElement a_tourElement);
       
    /**
     * Removes the Elements at the specified range.
     * @param a_beginIndex
     * @param a_endIndex
     */
    public void removeElements(int a_beginIndex, int a_endIndex);
    
    /**
     * Moves the Element of the specified Interval. The Interval will be copied and inserted at the specified position and then removed from its old position.
     * @param ai_beginIndex index of the first Element to be moved
     * @param ai_endIndex  index of the last element to be moved
     * @param ai_targetBeginIndex target index for insertion
     * @return true if movement was successfully executed, else false
     */
    public boolean moveElements(int ai_beginIndex, int ai_endIndex, int ai_targetBeginIndex);
    
    /**
     * inherited from Interace ListModel
     *
     * void addListDataListener(ListDataLIstener l)
     *
     * void removeListDataListener((ListDataLIstener l)
     *
     *
     *
     * Returns the tour element at the specified zero based tourElementPosition
     * @param a_tourElementPosition zero based position of tour element
     * @return the specified tour element object. Returns NULL if specified position is invalid or does not hold a tour element.
     *
     * //inherited from ListModel
     * //public FLGTourElement getElementAt(int a_tourElementPosition);
     */
    
    /**
     * Returns the current number of tour elements belonging to this tour
     * @return number of tour elements
     *
     * //inherited from ListModel
     * //public int getSize();
     */
}
