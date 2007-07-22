package trinidadTest.phaseListener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Einfacher Phase-Listener, der zu Beginn und am Ende 
 *  jeder Phase die Phasen-Id ausgibt.
 *  Dazu muss er in faces-config.xml registriert werden. 
 */
public class MyPhaseListener implements PhaseListener {

	public PhaseId getPhaseId() {
		// wir sind an allen Phasen interessiert
		return PhaseId.ANY_PHASE;
	}

	public void beforePhase(PhaseEvent pe) {
		System.out.println(">>> entering " + pe.getPhaseId());
	}
	
	public void afterPhase(PhaseEvent pe) {
		System.out.println("<<< exiting " + pe.getPhaseId());
	}

}
