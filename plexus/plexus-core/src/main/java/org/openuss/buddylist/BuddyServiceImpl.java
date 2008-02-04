// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import java.util.*;

import org.openuss.security.*;

/**
 * @see org.openuss.buddylist.BuddyService
 */
public class BuddyServiceImpl
    extends org.openuss.buddylist.BuddyServiceBase
{

    /**
     * @throws Exception 
     * @see org.openuss.buddylist.BuddyService#addBuddy(org.openuss.security.User, org.openuss.security.UserInfo)
     */
    protected void handleAddBuddy(org.openuss.security.User user, org.openuss.security.UserInfo userToAdd) throws Exception{
      	Set<Buddy> buddyList = user.getBuddyList();
      	// test if userToAdd is already buddy of user
      	for(Buddy buddy : buddyList){
    		if(buddy.getBuddy().getId() == userToAdd.getId())
    			throw new Exception("Buddy is already on BuddyList");
    	}
    	User newBuddy = getUserDao().load(userToAdd.getId());
      	Buddy buddy = getBuddyDao().create(false, newBuddy, user);
      	buddyList.add(buddy);
      	newBuddy.getBuddies().add(buddy);
    }

    /**
     * @see org.openuss.buddylist.BuddyService#deleteBuddy(org.openuss.security.User, org.openuss.buddylist.BuddyInfo)
     */
    protected void handleDeleteBuddy(org.openuss.security.User user, org.openuss.buddylist.BuddyInfo buddy)
        throws java.lang.Exception
    {
        // Test if Buddy is on List
    	Set<Buddy> buddyList = user.getBuddyList();
    	Buddy buddyToDelete = getBuddyDao().load(buddy.getId());
    	if(buddyList.contains(buddyToDelete)){
    		for(Tag tag : (List<Tag>)buddyToDelete.getallTags()){
    			buddyToDelete.removeTag(tag.getTag());
    		}
    		buddyList.remove(buddyToDelete);
    		buddyToDelete.getBuddy().getBuddies().remove(buddyToDelete);
    		getBuddyDao().remove(buddyToDelete);
    	} else {
    		throw new Exception("Buddy not on list");
    	}
    }

    /**
     * @see org.openuss.buddylist.BuddyService#addTag(org.openuss.buddylist.BuddyInfo, java.lang.String)
     */
    protected void handleAddTag(org.openuss.buddylist.BuddyInfo buddyInfo, java.lang.String tagContent)
        throws java.lang.Exception
    {
    	getBuddyDao().load(buddyInfo.getId()).addTag(tagContent);
    }

    /**
     * @see org.openuss.buddylist.BuddyService#deleteTag(org.openuss.buddylist.BuddyInfo, java.lang.String)
     */
    protected void handleDeleteTag(org.openuss.buddylist.BuddyInfo buddy, java.lang.String tag)
        throws java.lang.Exception
    {
        getBuddyDao().load(buddy.getId()).removeTag(tag);
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getAllUsedTags(org.openuss.security.User)
     */
    protected java.util.List handleGetAllUsedTags(org.openuss.security.User user)
        throws java.lang.Exception
    {
    	ArrayList userTagList = new ArrayList();
    	userTagList.addAll(user.getUsedTags());
        return userTagList;
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getBuddyList(org.openuss.security.User)
     */
    protected java.util.List handleGetBuddyList(org.openuss.security.User user)
        throws java.lang.Exception
    {
    	Set<Buddy> allCandidates = user.getBuddyList();
    	if(allCandidates.size()==0) return new ArrayList();
    	List<Buddy> BuddyList = new ArrayList();
    	for(Buddy candidate : allCandidates){
    		if(candidate.isAuthorized())
    			BuddyList.add(candidate);
    	}
        return BuddyList;
    }

    /**
     * @see org.openuss.buddylist.BuddyService#authorizeBuddyRequest(org.openuss.buddylist.BuddyInfo, boolean)
     */
    protected void handleAuthorizeBuddyRequest(org.openuss.buddylist.BuddyInfo buddy, boolean authorize)
        throws java.lang.Exception
    {
    	Buddy authorizeBuddy = getBuddyDao().load(buddy.getId());
    	if(authorize == true){
    		//authorize buddy
    		authorizeBuddy.setAuthorized(true);
    	} else {
    		deleteBuddy(authorizeBuddy.getUser(), buddy);
    	}
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getAllOpenRequests(org.openuss.security.User)
     */
    protected java.util.List handleGetAllOpenRequests(org.openuss.security.User user)
        throws java.lang.Exception
    {
        Set<Buddy> allCandidates = user.getBuddies();
        List<BuddyInfo> openRequests = new ArrayList();
        for (Buddy candidate : allCandidates){
        	if(!candidate.isAuthorized()){
        		openRequests.add(getBuddyDao().toBuddyInfo(candidate));
        	}
        }
        return openRequests;
    }
}