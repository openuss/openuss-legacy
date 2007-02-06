package org.openuss.framework.deploy;

/**
 * @author Ingo D�ppe
 * 
 * Inspired by:
 * @author  Ivica Cardic
 * @author  Brian Wing Shun Chan
 *
 */
public interface HotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException;

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException;

}