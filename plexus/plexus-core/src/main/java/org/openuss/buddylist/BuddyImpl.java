// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openuss.security.User;

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
    public void removeTag(java.lang.String tagContent)
    {
    	Set<Tag> allTags = getTags();
    	for(Tag tag : allTags){
    		if(tag.getTag().equals(tagContent)){
    			allTags.remove(tag);
    			tag.getBuddies().remove(this);
    			if(tag.getBuddies().size()==0){
    				User user = tag.getUser();
    				user.getUsedTags().remove(tag);
    				tag.setUser(null);
    			}
    		}
    	}
    }

    /**
     * @throws Exception 
     * @see org.openuss.buddylist.Buddy#addTag(java.lang.String)
     */
    public void addTag(java.lang.String tagContent)
    {    	   	
    	for(Tag candidateTag : getUser().getUsedTags()){
    		if(candidateTag.getTag().equals(tagContent)){
    			if(getallTags().contains(candidateTag)){
    				return;
    			} else {
    				getTags().add(candidateTag);
    				candidateTag.getBuddies().add(this);
    				return;
    			}
    		}
    	}
    	Tag tag = Tag.Factory.newInstance(tagContent, getUser());
    	tag.getBuddies().add(this);
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