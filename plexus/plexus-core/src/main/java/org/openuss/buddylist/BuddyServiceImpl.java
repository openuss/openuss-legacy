// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand
 * @author Ralf Plattfaut
 */
package org.openuss.buddylist;

import java.util.*;

import org.openuss.internalMessage.*;
import org.openuss.security.*;

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
        //test wether userToAdd equals current user
        if(user.getId().equals(userToAdd.getId()))
        	throw new Exception("You cannot add yourself");
        BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(user.getId());
        if(buddyList == null){
        	buddyList = getBuddyListDao().create(user.getId());
        }
    	//test wether user is already added
        for(Buddy buddy : buddyList.getBuddies()){
        	if(buddy.getUser().getId().equals(userToAdd.getId()))
        		throw new Exception("User is already added");
        }
        Buddy buddy = Buddy.Factory.newInstance();
        buddy.setAuthorized(false);
        buddy.setBuddyList(buddyList);
        buddy.setUser(getUserDao().load(userToAdd.getId()));
        buddy = getBuddyDao().create(buddy);
        buddyList.getBuddies().add(buddy);
        //inform about request
        System.out.println("Sending message");
        InternalMessageInfo imInfo = new InternalMessageInfo();
        imInfo.setSenderDisplayName(user.getDisplayName());
        imInfo.setSenderId(user.getId());
        imInfo.setMessageDate(new Date());
        //TODO make information dynamically
        imInfo.setSubject("New Buddy Request");
        imInfo.setContent("do you want to be my buddy? /n Don't forget to make this dynamically!");
        InternalMessageRecipientsInfo imrecInfo = new InternalMessageRecipientsInfo();
        imrecInfo.setInternalMessageInfo(imInfo);
        imrecInfo.setRead(false);
        imrecInfo.setRecipientDisplayName(buddy.getUser().getDisplayName());
        imrecInfo.setRecipientId(buddy.getUser().getId());
        List<InternalMessageRecipientsInfo> internalMessageRecipientsInfos = new LinkedList();
        internalMessageRecipientsInfos.add(imrecInfo);
        imInfo.setInternalMessageRecipientsInfos(internalMessageRecipientsInfos);
        imrecInfo.setInternalMessageInfo(imInfo);
        getInternalMessageService().sendInternalMessage(imInfo);
    }

    /**
     * @see org.openuss.buddylist.BuddyService#deleteBuddy(org.openuss.buddylist.BuddyInfo)
     */
    protected void handleDeleteBuddy(org.openuss.buddylist.BuddyInfo buddyInfo)
        throws java.lang.Exception
    {
    	Buddy buddy = getBuddyDao().load(buddyInfo.getId());
    	buddy.getBuddyList().getBuddies().remove(buddy);
    	for(Tag tag : buddy.getTags()){
    		tag.getBuddies().remove(buddy);
    		if(tag.getBuddies().size()==0){
    			tag.getBuddyList().getTags().remove(tag);
    			getTagDao().remove(tag);
    		}
    	}
    	getBuddyDao().remove(buddy);
    }

    /**
     * @see org.openuss.buddylist.BuddyService#addTag(org.openuss.buddylist.BuddyInfo, java.lang.String)
     */
    protected void handleAddTag(org.openuss.buddylist.BuddyInfo buddyInfo, java.lang.String tagString)
        throws java.lang.Exception
    {
    	tagString = tagString.toLowerCase();
    	if(tagString.length()<=2)
    		throw new BuddyApplicationException("Tag too short");
    	if(tagString.equals("penis"))
    		throw new BuddyApplicationException("Tag too short");
    	Buddy buddy = getBuddyDao().load(buddyInfo.getId());
    	//search tag
    	User user = getSecurityService().getCurrentUser();
    	BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(user.getId());
    	Tag tag = null;
    	for(Tag tagIterate : buddyList.getTags()){
    		if(tagIterate.getTag().equals(tagString)){
    			tag = tagIterate;
    			break;
    		}
    	}
    	if(tag == null){
    		tag = getTagDao().create(tagString);
    		buddyList.getTags().add(tag);
    		tag.setBuddyList(buddyList);
    	} else if(buddy.getTags().contains(tag)){
    		return;
    	}
    	buddy.getTags().add(tag);
    	tag.getBuddies().add(buddy);
    	getTagDao().update(tag);
    	getBuddyDao().update(buddy);
    	getBuddyListDao().update(buddyList);
    }

    /**
     * @see org.openuss.buddylist.BuddyService#deleteTag(org.openuss.buddylist.BuddyInfo, java.lang.String)
     */
    protected void handleDeleteTag(org.openuss.buddylist.BuddyInfo buddyInfo, java.lang.String tagString)
        throws java.lang.Exception
    {
    	tagString = tagString.toLowerCase();
    	Buddy buddy = getBuddyDao().load(buddyInfo.getId());
    	for(Tag tag : buddy.getTags()){
    		if(tag.getTag().equals(tagString)){
    			//delete tag
    			buddy.getTags().remove(tag);
    			tag.getBuddies().remove(buddy);
    			if(tag.getBuddies().size()==0){
    				tag.getBuddyList().getTags().remove(tag);
    				tag.setBuddyList(null);
    				getTagDao().remove(tag);
    			}
    			return;
    		}
    	}
    	throw new Exception("Tag does not exist at user");
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getAllUsedTags()
     */
    protected java.util.List handleGetAllUsedTags()
        throws java.lang.Exception
    {
        User user = getSecurityService().getCurrentUser();
        BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(user.getId());
        LinkedList<String> tagList = new LinkedList<String>();
        for (Tag tag : buddyList.getTags()){
        	tagList.add(tag.getTag());
        }
        Collections.sort(tagList);
        return tagList;
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getBuddyList()
     */
    protected java.util.List handleGetBuddyList()
        throws java.lang.Exception
    {
        User user = getSecurityService().getCurrentUser();
        BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(user.getId());
        if(buddyList == null){
        	buddyList = getBuddyListDao().create(user.getId());
        }
        Set<Buddy> buddySet = buddyList.getBuddies();
        ArrayList<BuddyInfo> buddys = new ArrayList<BuddyInfo>();
        for(Buddy buddy : buddySet){
        	if(buddy.isAuthorized()){
        		BuddyInfo buddyToAdd = getBuddyDao().toBuddyInfo(buddy);
        		buddyToAdd.setRequesterName(user.getDisplayName());
        		buddyToAdd.setRequestingPictureId(user.getImageId());
        		buddys.add(buddyToAdd);
        	}
        }    
        return buddys;
    }

    /**
     * @see org.openuss.buddylist.BuddyService#authorizeBuddyRequest(org.openuss.buddylist.BuddyInfo, boolean)
     */
    protected void handleAuthorizeBuddyRequest(org.openuss.buddylist.BuddyInfo buddyInfo, boolean authorize)
        throws java.lang.Exception
    {
        Buddy buddy = getBuddyDao().load(buddyInfo.getId());
        if(authorize){
        	buddy.setAuthorized(true);
        } else {
        	this.deleteBuddy(buddyInfo);
        }
    }

    /**
     * @see org.openuss.buddylist.BuddyService#getAllOpenRequests()
     */
    protected java.util.List handleGetAllOpenRequests()
        throws java.lang.Exception
    {
    	User user = getSecurityService().getCurrentUser();
        List<Buddy> allRequests = getBuddyDao().findByUser(user);
        List<BuddyInfo> requests = new ArrayList();
        for(Buddy buddy : allRequests){
        	if(!buddy.isAuthorized()){
        		BuddyInfo toAdd = getBuddyDao().toBuddyInfo(buddy);
        		User userBuddy = getUserDao().load(buddy.getBuddyList().getDomainIdentifier());
        		toAdd.setRequesterId(userBuddy.getId());
        		toAdd.setRequestingPictureId(userBuddy.getImageId());
        		toAdd.setRequesterName(userBuddy.getDisplayName());
        		requests.add(toAdd);
        	}
        }
        return requests;
    }

	@Override
	protected List handleGetAllBuddysByTag(String tagString) throws Exception {
		User user = getSecurityService().getCurrentUser();
		BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(user.getId());
		Tag tag = null;
		for(Tag tagCandidate : buddyList.getTags()){
			if(tagCandidate.getTag().equalsIgnoreCase(tagString)){
				tag = tagCandidate;
				break;
			}
		}
		if(tag==null)
			throw new Exception("Tag not found");
		return getBuddyDao().findByTag(getBuddyDao().TRANSFORM_BUDDYINFO, tag);
	}
}
