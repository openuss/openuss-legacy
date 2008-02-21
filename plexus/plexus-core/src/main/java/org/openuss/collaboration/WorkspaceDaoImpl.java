// OpenUSS - Open Source University Support System
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.openuss.collaboration;

import java.util.List;

/**
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
        // @todo verify behavior of toWorkspaceInfo
        super.toWorkspaceInfo(sourceEntity, targetVO);
    }


    /**
     * @see org.openuss.collaboration.WorkspaceDao#toWorkspaceInfo(org.openuss.collaboration.Workspace)
     */
    public org.openuss.collaboration.WorkspaceInfo toWorkspaceInfo(final org.openuss.collaboration.Workspace entity)
    {
        // @todo verify behavior of toWorkspaceInfo
        return super.toWorkspaceInfo(entity);
    }


    /**
     * Retrieves the entity object that is associated with the specified value object
     * from the object store. If no such entity object exists in the object store,
     * a new, blank entity is created
     */
    private org.openuss.collaboration.Workspace loadWorkspaceFromWorkspaceInfo(org.openuss.collaboration.WorkspaceInfo workspaceInfo)
    {
        Workspace workspace = null;
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
        // @todo verify behavior of workspaceInfoToEntity
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
        // @todo verify behavior of workspaceInfoToEntity
        super.workspaceInfoToEntity(sourceVO, targetEntity, copyIfNull);
    }

}