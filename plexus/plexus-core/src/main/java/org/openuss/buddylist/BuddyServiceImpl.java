// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand
 * @author Ralf Plattfaut
 */
package org.openuss.buddylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openuss.security.User;
import org.openuss.security.UserInfo;

/**
 * @see org.openuss.buddylist.BuddyService
 */
public class BuddyServiceImpl extends org.openuss.buddylist.BuddyServiceBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(BuddyServiceImpl.class);

	/**
	 * @see org.openuss.buddylist.BuddyService#addBuddy(org.openuss.security.UserInfo)
	 */
	protected void handleAddBuddy(org.openuss.security.UserInfo userToAdd)
			throws java.lang.Exception {
		User user = getSecurityService().getCurrentUser();
		// test wether userToAdd equals current user
		if (user.getId().equals(userToAdd.getId())){
			throw new BuddyApplicationException("You cannot add yourself");
		}
		BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(
				user.getId());
		if (buddyList == null) {
			buddyList = getBuddyListDao().create(user.getId());
		}
		// test wether user is already added
		for (Buddy buddy : buddyList.getBuddies()) {
			if (buddy.getUser().getId().equals(userToAdd.getId())){
				throw new BuddyApplicationException("User is already added");
			}
		}
		Buddy buddy = Buddy.Factory.newInstance();
		buddy.setAuthorized(false);
		buddy.setBuddyList(buddyList);
		buddy.setUser(getUserDao().load(userToAdd.getId()));
		buddy = getBuddyDao().create(buddy);
		buddyList.getBuddies().add(buddy);

	}

	/**
	 * @see org.openuss.buddylist.BuddyService#deleteBuddy(org.openuss.buddylist.BuddyInfo)
	 */
	protected void handleDeleteBuddy(org.openuss.buddylist.BuddyInfo buddyInfo)
			throws java.lang.Exception {
		Buddy buddy = getBuddyDao().load(buddyInfo.getId());
		buddy.getBuddyList().getBuddies().remove(buddy);
		for (Tag tag : buddy.getTags()) {
			tag.getBuddies().remove(buddy);
			if (tag.getBuddies().size() == 0) {
				tag.getBuddyList().getTags().remove(tag);
				getTagDao().remove(tag);
			}
		}
		buddy.setBuddyList(null);
		buddy.setTags(null);
		buddy.setUser(null);
		getBuddyDao().remove(buddy);
	}

	/**
	 * @see org.openuss.buddylist.BuddyService#addTag(org.openuss.buddylist.BuddyInfo,
	 *      java.lang.String)
	 */
	protected int handleAddTag(BuddyInfo buddyInfo,
			String tagString2) throws java.lang.Exception {
		logger.debug("process add tag for tagString " + tagString2);
		String tagString = tagString2.trim().toLowerCase();
		if (tagString.length() <= 2) {
			return 0;
		}
		int index = tagString.indexOf(',');
		if (index >= 0) {
			String substring = tagString.substring(0, index);
			int number = 0;
			if (substring.length() > 2) {
				number = this.addTag(buddyInfo, substring);
			}
			substring = tagString.substring(index + 1, tagString.length());
			if (substring.length() > 2) {
				number = number + this.addTag(buddyInfo, substring);
			}
			return number;
		} else {
			Buddy buddy = getBuddyDao().load(buddyInfo.getId());
			// search tag
			User user = getSecurityService().getCurrentUser();
			BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(
					user.getId());
			Tag tag = null;
			for (Tag tagIterate : buddyList.getTags()) {
				if (tagIterate.getTag().equals(tagString)) {
					tag = tagIterate;
					break;
				}
			}
			if (tag == null) {
				tag = getTagDao().create(tagString);
				buddyList.getTags().add(tag);
				tag.setBuddyList(buddyList);
			} else if (buddy.getTags().contains(tag)) {
				return 0;
			}
			buddy.getTags().add(tag);
			tag.getBuddies().add(buddy);
			getTagDao().update(tag);
			getBuddyDao().update(buddy);
			getBuddyListDao().update(buddyList);
			return 1;
		}
	}

	/**
	 * @see org.openuss.buddylist.BuddyService#deleteTag(org.openuss.buddylist.BuddyInfo,
	 *      java.lang.String)
	 */
	protected void handleDeleteTag(org.openuss.buddylist.BuddyInfo buddyInfo,
			java.lang.String tagString) throws java.lang.Exception {
		Buddy buddy = getBuddyDao().load(buddyInfo.getId());
		for (Tag tag : buddy.getTags()) {
			if (tag.getTag().equalsIgnoreCase(tagString)) {
				// delete tag
				buddy.getTags().remove(tag);
				tag.getBuddies().remove(buddy);
				if (tag.getBuddies().size() == 0) {
					tag.getBuddyList().getTags().remove(tag);
					tag.setBuddyList(null);
					getTagDao().remove(tag);
				}
				return;
			}
		}
		throw new BuddyApplicationException("Tag does not exist at user");
	}

	/**
	 * @see org.openuss.buddylist.BuddyService#getAllUsedTags()
	 */
	protected java.util.List handleGetAllUsedTags() throws java.lang.Exception {
		User user = getSecurityService().getCurrentUser();
		BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(
				user.getId());
		if (buddyList == null) {
			buddyList = getBuddyListDao().create(user.getId());
		}
		LinkedList<String> tagList = new LinkedList<String>();
		for (Tag tag : buddyList.getTags()) {
			tagList.add(tag.getTag());
		}
		Collections.sort(tagList);
		return tagList;
	}

	/**
	 * @see org.openuss.buddylist.BuddyService#getBuddyList()
	 */
	protected java.util.List handleGetBuddyList() throws java.lang.Exception {
		User user = getSecurityService().getCurrentUser();
		BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(
				user.getId());
		if (buddyList == null) {
			buddyList = getBuddyListDao().create(user.getId());
		}
		Set<Buddy> buddySet = buddyList.getBuddies();
		ArrayList<BuddyInfo> buddys = new ArrayList<BuddyInfo>();
		for (Buddy buddy : buddySet) {
			if (buddy.isAuthorized()) {
				BuddyInfo buddyToAdd = getBuddyDao().toBuddyInfo(buddy);
				buddyToAdd.setRequesterName(user.getDisplayName());
				buddyToAdd.setRequestingPictureId(user.getImageId());
				buddys.add(buddyToAdd);
			}
		}
		return buddys;
	}

	/**
	 * @see org.openuss.buddylist.BuddyService#authorizeBuddyRequest(org.openuss.buddylist.BuddyInfo,
	 *      boolean)
	 */
	protected void handleAuthorizeBuddyRequest(
			org.openuss.buddylist.BuddyInfo buddyInfo, boolean authorize)
			throws java.lang.Exception {
		Buddy buddy = getBuddyDao().load(buddyInfo.getId());
		if (authorize) {
			buddy.setAuthorized(true);
		} else {
			this.deleteBuddy(buddyInfo);
		}
	}

	/**
	 * @see org.openuss.buddylist.BuddyService#getAllOpenRequests()
	 */
	protected java.util.List handleGetAllOpenRequests()
			throws java.lang.Exception {
		User user = getSecurityService().getCurrentUser();
		List<Buddy> allRequests = getBuddyDao().findByUser(user);
		List<BuddyInfo> requests = new ArrayList();
		for (Buddy buddy : allRequests) {
			if (!buddy.isAuthorized()) {
				BuddyInfo toAdd = getBuddyDao().toBuddyInfo(buddy);
				User userBuddy = getUserDao().load(
						buddy.getBuddyList().getDomainIdentifier());
				toAdd.setRequesterId(userBuddy.getId());
				toAdd.setRequestingPictureId(userBuddy.getImageId());
				toAdd.setRequesterName(userBuddy.getDisplayName());
				requests.add(toAdd);
			}
		}
		return requests;
	}

	@Override
	protected List handleGetAllBuddysByTag(String tagString) throws BuddyApplicationException {
		List<BuddyInfo> results = new LinkedList<BuddyInfo>();
		if (!this.getAllUsedTags().contains(tagString)) {
			return results;
		}
		User user = getSecurityService().getCurrentUser();
		BuddyList buddyList = getBuddyListDao().findByDomainIdentifier(
				user.getId());
		Tag tag = null;
		for (Tag tagCandidate : buddyList.getTags()) {
			if (tagCandidate.getTag().equalsIgnoreCase(tagString)) {
				tag = tagCandidate;
				break;
			}
		}
		if (tag == null){
			throw new BuddyApplicationException("Tag not found");
		}
		for (BuddyInfo buddyCandidate : (List<BuddyInfo>) this.getBuddyList()) {
			if (buddyCandidate.getTags().contains(tagString)) {
				results.add(buddyCandidate);
			}
		}
		return results;
	}

	@Override
	protected boolean handleIsUserBuddy(User buddy) throws BuddyApplicationException {
		User user = getSecurityService().getCurrentUser();
		if (user == null){
			throw new BuddyApplicationException("No current user");
		}
		BuddyList buddylist = getBuddyListDao().findByDomainIdentifier(
				user.getId());
		if (buddylist == null || buddylist.getBuddies() == null){
			return false;
		}
		for (Buddy b : buddylist.getBuddies()) {
			if (b.getUser().equals(buddy)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean handleIsUserBuddy(UserInfo userInfo) throws BuddyApplicationException {
		return this.isUserBuddy(userInfo.getId());
	}

	@Override
	protected boolean handleIsUserBuddy(Long userId) throws BuddyApplicationException {
		User user = getUserDao().load(userId);
		if (user == null){
			throw new BuddyApplicationException("User could not be found!");
		}
		return this.isUserBuddy(user);
	}

	@Override
	protected BuddyInfo handleGetBuddy(Long buddyId) throws BuddyApplicationException {
		BuddyInfo buddy = (BuddyInfo) getBuddyDao().load(
				BuddyDao.TRANSFORM_BUDDYINFO, buddyId);
		if (this.getBuddyList().contains(buddy)){
			return buddy;
		}
		else{
			throw new BuddyApplicationException("Not the buddy of the requesting user");
		}
	}
}
