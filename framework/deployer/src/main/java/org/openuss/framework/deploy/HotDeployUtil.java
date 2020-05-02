package org.openuss.framework.deploy;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * @author Ingo Düppe Inspired by:
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class HotDeployUtil {

	private static final Logger logger = Logger.getLogger(HotDeployUtil.class);

	private static HotDeployUtil instance = new HotDeployUtil();

	private List<HotDeployListener> listeners;

	private List<HotDeployEvent> events;

	private HotDeployUtil() {
		listeners = new Vector<HotDeployListener>();
		events = new Vector<HotDeployEvent>();
	}

	public static void fireDeployEvent(HotDeployEvent event) {
		instance.handleFireDeployEvent(event);
	}

	public static void fireUndeployEvent(HotDeployEvent event) {
		instance.handlefireUndeployEvent(event);
	}

	public static void registerListener(HotDeployListener listener) {
		instance.handleRegisterListener(listener);
	}
	
	public static void unregisterListener(HotDeployListener listener) {
		instance.handleUnregisterListener(listener);
	}

	/**
	 * Flush all events to the registered listeners.
	 * After call the events stack is closed. 
	 * No more events are captured anymore.
	 */
	public static void flushEvents() {
		instance.handleFlushEvents();
	}
	
	/**
	 * Resets the hot deploy util without carring of the event stack.
	 * Convience method for unit testing.
	 */
	public static void reset() {
		instance = new HotDeployUtil();
	}

	private void handleFireDeployEvent(HotDeployEvent event) {
		if (event == null || event.getServletContext() == null || event.getContextClassLoader() == null) {
			logger.error("HotDeployEvent doesn't contain all information!");
			return;
		} 
			
		logger.debug("Deploy event from "+event.getServletContext().getServletContextName());
		// Log event
		events.add(event);
		// Fire event
		doFireDeployEvent(event);
	}

	private void doFireDeployEvent(HotDeployEvent event) {
		for (HotDeployListener listener : listeners) {
			try {
				listener.invokeDeploy(event);
			} catch (HotDeployException e) {
				logger.error(e);
			}
		}
	}

	private void handlefireUndeployEvent(HotDeployEvent event) {
		if (event == null || event.getServletContext() == null || event.getContextClassLoader() == null) {
			logger.error("HotDeployEvent doesn't contain all information!");
			return;
		}
		logger.debug("Undeply event from "+event.getServletContext().getServletContextName());
		// Log event
		events.add(event);
		// Fire event
		doFireUndeployEvent(event);
	}

	private void doFireUndeployEvent(HotDeployEvent event) {
		for (HotDeployListener listener : listeners) {
			try {
				listener.invokeUndeploy(event);
			} catch (HotDeployException e) {
				logger.error(e);
			}
		}
	}

	private void handleRegisterListener(HotDeployListener listener) {
		listeners.add(listener);
	}

	private void handleUnregisterListener(HotDeployListener listener) {
		listeners.remove(listener);
	}

	private void handleFlushEvents() {
		// fire all loged events 
		if (events != null) {
			for(HotDeployEvent event : events) {
				doFireDeployEvent(event);
			}
		}
	}
	
}