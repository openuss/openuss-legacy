// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.security;

/**
 * @see org.openuss.security.Group
 */
public class GroupDaoImpl extends org.openuss.security.GroupDaoBase {

	public Group groupItemToEntity(GroupItem groupItem) {
		Group group = loadGroupFromGroupListItem(groupItem);
		groupItemToEntity(groupItem, group, false);
		return group;
	}
	
	private Group loadGroupFromGroupListItem(GroupItem groupItem) {
		Group group = null;
		if (groupItem.getId() != null) {
			group = load(groupItem.getId());
		}
		if (group == null) {
			group = Group.Factory.newInstance();
		}
		return group;
	}
}