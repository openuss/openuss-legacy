package freestyleLearningGroup.independent.tourCreator;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * This class represents a FLG tour. It is responsible for self loading, saving and holding the tour elements.
 * @author  Steffen Wachenfeld
 */
public class FLGStandardTour implements FLGTour{
    
    /**
     * Name of the FLG tour
     */
    private String m_tourName = "Unbenannt";
    /**
     * Icon of the FLG tour
     */
    private ImageIcon m_tourIcon;
    
    /**
     * Vector holding the tour elements (synchronized data type).
     * Objects are of interface type FLGTourElement
     */
    private Vector m_tourElements = new Vector();
    
    /**
     * Vector holding registered tourChangeListeners which will be notified if the tour was modified.
     */
    private Vector m_tourChangeListener = new Vector();
    
    
    /** Creates a new instance of FLGTour */
    public FLGStandardTour() {
    }//FLGStandardTour
    
  
    /**
     * Adds the given tour element as throws elast element.
     * @param a_tourElement tour element to be added at the last position
     */
    public void addElement(FLGTourElement a_tourElement){
        this.m_tourElements.add(a_tourElement);
        this.fireTourChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, this.m_tourElements.size()-1, this.m_tourElements.size()-1));
    }
    
    
    /**
     * Notifies all registered ChangeListener
     */
    public void fireTourChanged(ListDataEvent a_listDataEvent){
       //todo
                
        switch(a_listDataEvent.getType())
        {
           case ListDataEvent.CONTENTS_CHANGED:
              for(int i=0; i < this.m_tourChangeListener.size(); i++)
                 ((ListDataListener)this.m_tourChangeListener.elementAt(i)).contentsChanged(a_listDataEvent);
              break;
              
           case ListDataEvent.INTERVAL_ADDED:
              for(int i=0; i < this.m_tourChangeListener.size(); i++)
                  ((ListDataListener)this.m_tourChangeListener.elementAt(i)).intervalAdded(a_listDataEvent);
              break;
            
           case ListDataEvent.INTERVAL_REMOVED:
              for(int i=0; i < this.m_tourChangeListener.size(); i++)
                 ((ListDataListener)this.m_tourChangeListener.elementAt(i)).intervalRemoved(a_listDataEvent);
              break;
            
        }//switch
    }//fireTourChanged
    
    
    /**
     * Returns the tour element at the specified zero based tourElementPosition.
     * Inherited from ListModel
     * @param a_tourElementPosition zero based position of tour element
     * @return the specified tour element object.
     */
    public Object getElementAt(int a_tourElementPosition){
        return this.m_tourElements.elementAt(a_tourElementPosition);
    }//getElementAt
    
    
    public String getTourName(){
        return this.m_tourName;
    }//getTourName
      
    public void setTourName(String tourName) {
        this.m_tourName = tourName;
    }

    public void setTourIcon(ImageIcon icon) {
        m_tourIcon = icon;
    }
    
    public ImageIcon getTourIcon() {
        return m_tourIcon;
    }
    
   /**
     * Moves the Element of the specified Interval. The first element will have the targetPosition afterwards.
     * @param ai_beginIndex index of the first Element to be moved (inclusive)
     * @param ai_endIndex  index of the last element to be moved (inclusive)
     * @param ai_targetBeginIndex target index for insertion
     */
    public boolean moveElements(int ai_beginIndex, int ai_endIndex, int ai_targetBeginIndex)
    {
        //DEBUG
        //System.out.println("moveElements called - begin: "+ ai_beginIndex +
        //                   " end: "+ ai_endIndex + " targetIndex: " + ai_targetBeginIndex);
        
        //check parameters
        if( ai_beginIndex < 0 ||
            ai_endIndex >= this.m_tourElements.size() ||
            ai_endIndex < ai_beginIndex )
        {
            //System.out.println("Debug Info: moveElements aborted: wrong begin/end indexes");
            return false;
        }//if - checkInterval
        if( ai_targetBeginIndex < 0 ||
            ai_targetBeginIndex >= (this.m_tourElements.size() - ai_endIndex + ai_beginIndex))
        {
            //System.out.println("Debug Info: MoveElements aborted: wrong target index");
            return false;
        }//if - checkTarget
    
        //start to move and play move sound 
        FLGStandardTourCreator l_tourCreator = FLGStandardTourCreator.getInstance();
        /**
        if(l_tourCreator.m_currentTourCreatorOptions.mb_playSoundMove)
        {
            l_tourCreator.playSound(l_tourCreator.m_soundMove);
        }**/
        //subList from-inclusive, to-exclusive
        int li_elementsToMove = ai_endIndex + 1 -ai_beginIndex;
        Object[] l_tempList_ = new Object[li_elementsToMove];
        
        if(ai_targetBeginIndex < ai_beginIndex) //insert before
        {
            for(int i=0; i < li_elementsToMove; i++)//store and remove
            {
                l_tempList_[i] = this.m_tourElements.remove(ai_endIndex - i);
            }
            for(int i=0; i < li_elementsToMove; i++)//insert
            {
                this.m_tourElements.insertElementAt(l_tempList_[i], ai_targetBeginIndex);
            }
        }
        else //insert behind
        {
            for(int i=0; i < li_elementsToMove; i++)//store and remove
            {
                l_tempList_[i] = this.m_tourElements.remove(ai_endIndex - i);
            }
            for(int i=0; i < li_elementsToMove; i++)//insert
            {
                this.m_tourElements.insertElementAt(l_tempList_[i], ai_targetBeginIndex);
            }
        }
        
        this.fireTourChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, this.m_tourElements.size()-1));
        return true;
        
    }//moveElements
        
    
    /**
     * Removes the given tourElement
     * @param a_tourElement the element to be removed
     */
    public void removeElement(FLGTourElement a_tourElement){
        int l_index = this.m_tourElements.indexOf(a_tourElement);
        if(l_index >= 0)
        {
            this.m_tourElements.remove(l_index);
            this.fireTourChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, l_index, l_index));
        }
    }//removeElement
      
    
    /**
     * Removes the Elements at the specified range.
     * @param a_beginIndex first Element to be removed
     * @param a_endIndex last Element to be removed (!)
     */
    public void removeElements(int a_beginIndex, int a_endIndex)
    {
        synchronized(this.m_tourElements)
        {
            if( (a_beginIndex <= a_endIndex) &&
                (a_beginIndex >= 0) &&
                (a_endIndex < this.m_tourElements.size()) )
            {
                for(int i=a_endIndex; i>=a_beginIndex; i--)
                    this.m_tourElements.remove(i);
                this.fireTourChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, a_beginIndex, a_endIndex));
            }
        }
    }//removeElements
    
 
    /**
     * Registeres the given TourChangeListener object. The TourChangeListener will be notified after changes at the tour.
     */
    public void addListDataListener(ListDataListener a_changeListener){
        this.m_tourChangeListener.add(a_changeListener);
    }
    
    
    /**
     * Removes the given TourChangeListener object. The ListDataListener will not be notified any more.
     */
    public void removeListDataListener(ListDataListener a_changeListener){
        this.m_tourChangeListener.add(a_changeListener);
    }
    
    
    /**
     * Returns the current number of tour elements belonging to this tour
     * @return number of tour elements
     */
    public int getSize(){
        return this.m_tourElements.size();
    }//getSize
}
