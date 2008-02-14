// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.buddylist;

import org.openuss.security.User;
import org.openuss.security.UserDao;

/**
 * @see org.openuss.buddylist.BuddyService
 */
public class BuddyServiceImpl
    extends org.openuss.buddylist.BuddyServiceBase
{
    /**
     * @see org.openuss.buddylist.BuddyService#addBuddy(org.openuss.security.UserInfo)
     */
    protected void handleAddBuddy(org.openuss.security.UserInfo userToAdd)
        throws java.lang.Exception
    {
        User user = getSecurityService().getCurrentUser();
        System.out.println(user.getDisplayName());
        BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(user.getId());
        if(buddyList == null)
        	buddyList = BuddyList.Factory.newInstance(user.getId());
        System.out.println(userToAdd.getFirstName() + " " + userToAdd.getId());
        Buddy buddy = getBuddyDao().create(false, buddyList, getUserDao().load(userToAdd.getId()));
        buddyList.getBuddies().add(buddy);   
    }

    /**
     * @see org.openuss.buddylist.BuddyService#deleteBuddy(org.openuss.buddylist.BuddyInfo)
     */
    protected void handleDeleteBuddy(org.openuss.buddylist.BuddyInfo buddy)
        throws java.lang.Exception
    {
        // @todo implement protected void handleDeleteBuddy(org.openuss.buddylist.BuddyInfo buddy)
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.BuddyService.handleDeleteBuddy(org.openuss.buddylist.BuddyInfo buddy) Not implemented!");
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
     * @see org.openuss.buddylist.BuddyService#getAllUsedTags()
     */
    protected java.util.List handleGetAllUsedTags()
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetAllUsedTags()
        return null;
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getBuddyList()
     */
    protected java.util.List handleGetBuddyList()
        throws java.lang.Exception
    {
        User user = getSecurityService().getCurrentUser();
        getBuddyDao();
        return null;
    }

    /**
     * @see org.openuss.buddylist.BuddyService#authorizeBuddyRequest(org.openuss.buddylist.BuddyInfo, boolean)
     */
    protected void handleAuthorizeBuddyRequest(org.openuss.buddylist.BuddyInfo buddy, boolean authorize)
        throws java.lang.Exception
    {
        // @todo implement protected void handleAuthorizeBuddyRequest(org.openuss.buddylist.BuddyInfo buddy, boolean authorize)
        throw new java.lang.UnsupportedOperationException("org.openuss.buddylist.BuddyService.handleAuthorizeBuddyRequest(org.openuss.buddylist.BuddyInfo buddy, boolean authorize) Not implemented!");
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getAllOpenRequests()
     */
    protected java.util.List handleGetAllOpenRequests()
        throws java.lang.Exception
    {
        // @todo implement protected java.util.List handleGetAllOpenRequests()
        return null;
    }
}