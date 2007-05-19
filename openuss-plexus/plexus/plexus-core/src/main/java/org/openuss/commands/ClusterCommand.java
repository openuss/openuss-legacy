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

	public void processEachCommands() {
		clusterCommandProcessor.processEachCommands();
	}

	public void processOnceCommand() {
		clusterCommandProcessor.processOnceCommand();
	}
	
	public ClusterCommandProcessor getClusterCommandProcessor() {
		return clusterCommandProcessor;
	}

	public void setClusterCommandProcessor(ClusterCommandProcessor clusterCommandProcessor) {
		this.clusterCommandProcessor = clusterCommandProcessor;
	}
	

}
