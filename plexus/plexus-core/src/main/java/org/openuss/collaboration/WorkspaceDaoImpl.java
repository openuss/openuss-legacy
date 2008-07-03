// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.openuss.security.User;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * @author  Projektseminar WS 07/08, Team Collaboration
 * @see org.openuss.collaboration.Workspace
 */
public class WorkspaceDaoImpl
    extends org.openuss.collaboration.WorkspaceDaoBase
{
    /**
     * @see org.openuss.collaboration.WorkspaceDao#toWorkspaceInfo(org.openuss.collaboration.Workspace, org.openuss.collaboration.WorkspaceInfo)
     */
    public void toWorkspaceInfo(
        org.openuss.collaboration.Workspace sourceEntity,
        org.openuss.collaboration.WorkspaceInfo targetVO)
    {
        super.toWorkspaceInfo(sourceEntity, targetVO);
    }

    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     * 
     * @param workspaceInfo of the workspace to be loaded
     * @return the loaded workspace
     */
    private org.openuss.collaboration.Workspace loadWorkspaceFromWorkspaceInfo(org.openuss.collaboration.WorkspaceInfo workspaceInfo)
    {
        Workspace workspace;
        if (workspaceInfo.getId() != null) {
        	workspace = this.load(workspaceInfo.getId());
        } else {
            workspace = Workspace.Factory.newInstance();
        }
        return workspace;
    }

    
    /**
     * @see org.openuss.collaboration.WorkspaceDao#workspaceInfoToEntity(org.openuss.collaboration.WorkspaceInfo)
     */
    public org.openuss.collaboration.Workspace workspaceInfoToEntity(org.openuss.collaboration.WorkspaceInfo workspaceInfo)
    {
        org.openuss.collaboration.Workspace entity = this.loadWorkspaceFromWorkspaceInfo(workspaceInfo);
        this.workspaceInfoToEntity(workspaceInfo, entity, true);
        return entity;
    }


    /**
     * @see org.openuss.collaboration.WorkspaceDao#workspaceInfoToEntity(org.openuss.collaboration.WorkspaceInfo, org.openuss.collaboration.Workspace)
     */
    public void workspaceInfoToEntity(
        org.openuss.collaboration.WorkspaceInfo sourceVO,
        org.openuss.collaboration.Workspace targetEntity,
        boolean copyIfNull)
    {
        super.workspaceInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

    @SuppressWarnings("unchecked")
	@Override
	protected List handleFindByUser(final User user) throws Exception {
		final String queryString = 
		" SELECT WORKSPACES_FK " +
		" FROM USER2WORKSPACES " +
		" WHERE :userId = USER_FK";
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createSQLQuery(queryString);
				queryObject.setParameter("userId", user.getId());
				List<Object[]> results = queryObject.list();
				return results;
			}
		}, true);
	}

}