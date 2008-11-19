package org.openuss.commands;

/**
 * 
 * Cluster Command Execution Service
 * 
 * @author Ingo Dueppe
 *
 */
public class ClusterCommand {
	
	private ClusterCommandProcessor clusterCommandProcessor;

	public void processNodeCommands() {
		clusterCommandProcessor.processNodeCommands();
	}

	public void processCommand(final Long commandId) {
		clusterCommandProcessor.processCommand(commandId);
	}
	
	public ClusterCommandProcessor getClusterCommandProcessor() {
		return clusterCommandProcessor;
	}

	public void setClusterCommandProcessor(final ClusterCommandProcessor clusterCommandProcessor) {
		this.clusterCommandProcessor = clusterCommandProcessor;
	}
	

}
