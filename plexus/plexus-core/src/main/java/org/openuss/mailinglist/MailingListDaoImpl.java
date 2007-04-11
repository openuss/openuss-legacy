// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.mailinglist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.openuss.mail.RecipientInfo;
import org.openuss.security.User;

/**
 * @see org.openuss.mail.MailingList
 */
public class MailingListDaoImpl extends MailingListDaoBase
{
    /**
     * @see org.openuss.mail.MailingListDao#toMailingListInfo(org.openuss.mail.MailingList, org.openuss.mail.MailingListInfo)
     */
    public void toMailingListInfo(
        MailingList sourceEntity,
        MailingListInfo targetVO)
    {
        super.toMailingListInfo(sourceEntity, targetVO);
        // WARNING! No conversion for targetVO.recipients (can't convert sourceEntity.getRecipients():org.openuss.security.User to RecipientInfo
    }


    /**
     * @see MailingListDao#toMailingListInfo(MailingList)
     */
    public MailingListInfo toMailingListInfo(final MailingList entity)
    {
        MailingListInfo mli = super.toMailingListInfo(entity);
        Set<User> recipients= entity.getRecipients();
        Iterator i = recipients.iterator();
        Collection<RecipientInfo> riList = new ArrayList<RecipientInfo>();
        User user;
        while(i.hasNext()){
        	user = (User)i.next();
        	RecipientInfo ri = new RecipientInfo();
        	ri.setId(user.getId());
        	ri.setName(user.getUsername());
        	ri.setEmail(user.getEmail());
        	riList.add(ri);
        }
        mli.setRecipients(riList);
        return mli;
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private MailingList loadMailingListFromMailingListInfo(MailingListInfo mailingListInfo)
    {
        MailingList mailingList = this.load(mailingListInfo.getId());
        if (mailingList == null)
        {
            mailingList = MailingList.Factory.newInstance();
        }
        return mailingList;
    }

    
    /**
     * @see MailingListDao#mailingListInfoToEntity(MailingListInfo)
     */
    public MailingList mailingListInfoToEntity(MailingListInfo mailingListInfo)
    {
        // @todo verify behavior of mailingListInfoToEntity
        MailingList entity = this.loadMailingListFromMailingListInfo(mailingListInfo);
        this.mailingListInfoToEntity(mailingListInfo, entity, true);
        return entity;
    }


    /**
     * @see MailingListDao#mailingListInfoToEntity(MailingListInfo, MailingList)
     */
    public void mailingListInfoToEntity(
        MailingListInfo sourceVO,
        MailingList targetEntity,
        boolean copyIfNull)
    {
        // @todo verify behavior of mailingListInfoToEntity
        super.mailingListInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }


	@Override
	protected MailingListInfo handleGetByDomainIdentifier(Long domainIdentifier) throws Exception {
        MailingList mailingList = this.findByDomainIdentity(domainIdentifier);
        if (mailingList == null)
        {
            mailingList = MailingList.Factory.newInstance();
            mailingList.setDomainIdentity(domainIdentifier);
            this.create(mailingList);
        }
        return this.toMailingListInfo(mailingList);
	}

}