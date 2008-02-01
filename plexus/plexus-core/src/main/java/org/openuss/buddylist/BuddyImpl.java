// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.ArrayList;
import java.util.List;

/**
 * @see org.openuss.buddylist.Buddy
 */
public class BuddyImpl
    extends org.openuss.buddylist.BuddyBase
	implements org.openuss.buddylist.Buddy
{
    /**
     * The serial version UID of this class. Needed for serialization.
     */
    private static final long serialVersionUID = -4489119748549757902L;

    /**
     * @see org.openuss.buddylist.Buddy#removeTag(java.lang.String)
     */
    public void removeTag(java.lang.String tag)
    {
        // @todo implement public void removeTag(java.lang.String tag)
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.Buddy.removeTag(java.lang.String tag) Not implemented!");
    }

    /**
     * @throws Exception 
     * @see org.openuss.buddylist.Buddy#addTag(java.lang.String)
     */
    public void addTag(java.lang.String tagContent)
    {    	   	
    	System.out.println("ADD TAG");
    	List<Tag> allTags = new ArrayList();
    	allTags.addAll(getUser().getUsedTags());
    	for(Tag candidateTag : allTags){
    		if(candidateTag.getTag().equals(tagContent)){
    			if(getallTags().contains(tagContent)){
    				System.out.println("ADD TAG - Tag already added");
    				return;
    			} else {
    				getallTags().add(candidateTag);
    				candidateTag.getBuddies().add(this);
    				System.out.println("ADD TAG - Tag added");
    				return;
    			}
    		}
    	}
    	Tag tag = Tag.Factory.newInstance(tagContent, getUser());
    	tag.getBuddies().add(this);
    	System.out.println("ADD TAG - Tag created");
    	getTags().add(tag); 
    	tag.setUser(getUser());
    	getUser().getUsedTags().add(tag);
    }

    /**
     * @see org.openuss.buddylist.Buddy#getallTags()
     */
    public java.util.List getallTags()
    {
    	ArrayList allTags = new ArrayList();
    	allTags.addAll(super.getTags());
        return allTags;
    }

}