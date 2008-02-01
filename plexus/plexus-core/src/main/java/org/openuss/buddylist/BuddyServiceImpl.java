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
     * @see org.openuss.buddylist.BuddyService#addBuddy(org.openuss.security.User, org.openuss.security.UserInfo)
     */
    protected void handleAddBuddy(org.openuss.security.User user, org.openuss.security.UserInfo userToAdd){
      	// @todo test wether userToAdd is already buddy of user
    	User newBuddy = getUserDao().userInfoToEntity(userToAdd);
      	Buddy buddy = getBuddyDao().create(false, newBuddy, user);
      	user.getBuddyList().add(buddy);
      	newBuddy.getBuddies().add(buddy);
    }

    /**
     * @see org.openuss.buddylist.BuddyService#deleteBuddy(org.openuss.security.User, org.openuss.buddylist.BuddyInfo)
     */
    protected void handleDeleteBuddy(org.openuss.security.User user, org.openuss.buddylist.BuddyInfo buddy)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteBuddy(org.openuss.security.User user, org.openuss.buddylist.BuddyInfo buddy)
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.BuddyService.handleDeleteBuddy(org.openuss.security.User user, org.openuss.buddylist.BuddyInfo buddy) Not implemented!");
    }

    /**
     * @see org.openuss.buddylist.BuddyService#addTag(org.openuss.buddylist.BuddyInfo, java.lang.String)
     */
    protected void handleAddTag(org.openuss.buddylist.BuddyInfo buddy, java.lang.String tag)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAddTag(org.openuss.buddylist.BuddyInfo buddy, java.lang.String tag)
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.BuddyService.handleAddTag(org.openuss.buddylist.BuddyInfo buddy, java.lang.String tag) Not implemented!");
    }

    /**
     * @see org.openuss.buddylist.BuddyService#deleteTag(org.openuss.buddylist.BuddyInfo, java.lang.String)
     */
    protected void handleDeleteTag(org.openuss.buddylist.BuddyInfo buddy, java.lang.String tag)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteTag(org.openuss.buddylist.BuddyInfo buddy, java.lang.String tag)
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.BuddyService.handleDeleteTag(org.openuss.buddylist.BuddyInfo buddy, java.lang.String tag) Not implemented!");
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getAllUsedTags(org.openuss.security.User)
     */
    protected java.util.List handleGetAllUsedTags(org.openuss.security.User user)
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetAllUsedTags(org.openuss.security.User user)
        return null;
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getBuddyList(org.openuss.security.User)
     */
    protected java.util.List handleGetBuddyList(org.openuss.security.User user)
        throws java.lang.Exception
    {
    	// @TODO Test wether BuddyList is empty
    	List<Buddy> BuddyList = new java.util.ArrayList();
    	Set<Buddy> allCandidates = user.getBuddyList();
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
    	Buddy authorizeBuddy = getBuddyDao().buddyInfoToEntity(buddy);
    	if(authorize == true){
    		//authorize buddy
    		authorizeBuddy.setAuthorized(true);
    	} else {
    		//delete buddy
            // @todo implement protected void handleAuthorizeBuddyRequest(org.openuss.buddylist.BuddyInfo buddy, boolean authorize)
            throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.BuddyService.handleAuthorizeBuddyRequest(org.openuss.buddylist.BuddyInfo buddy, boolean authorize) Not implemented!");
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