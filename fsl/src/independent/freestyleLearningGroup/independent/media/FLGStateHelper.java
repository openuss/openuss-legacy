package freestyleLearningGroup.independent.media;

import javax.media.ConfigureCompleteEvent;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerErrorEvent;
import javax.media.ControllerEvent;
import javax.media.EndOfMediaEvent;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.Processor;
import javax.media.RealizeCompleteEvent;

/**
 * This class helps the recorder-class to configure the sound-processor.
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGStateHelper implements javax.media.ControllerListener {
    Player player = null;
    boolean configured = false;
    boolean realized = false;
    boolean prefetched = false;
    boolean eom = false;
    boolean failed = false;
    boolean closed = false;

    /** Constructor */
    public FLGStateHelper(Player p) {
        player = p;
        p.addControllerListener(this);
    }

    //Configures the processor
    public boolean configure(int timeOutMillis) {
        long startTime = System.currentTimeMillis();
        synchronized(this) {
            if (player instanceof Processor)
                ((Processor)player).configure();
            else
                return false;
            while (!configured && !failed) {
                try {
                    wait(timeOutMillis);
                }
                catch (InterruptedException ie) { }
                if (System.currentTimeMillis() - startTime > timeOutMillis)
                    break;
            }
        }
        return configured;
    }

    //Realizes the processor
    public boolean realize(int timeOutMillis) {
        long startTime = System.currentTimeMillis();
        synchronized(this) {
            player.realize();
            while (!realized && !failed) {
                try {
                    wait(timeOutMillis);
                }
                catch (InterruptedException ie) { }
                if (System.currentTimeMillis() - startTime > timeOutMillis)
                    break;
            }
        }
        return realized;
    }

    public boolean prefetch(int timeOutMillis) {
        long startTime = System.currentTimeMillis();
        synchronized(this) {
            player.prefetch();
            while (!prefetched && !failed) {
                try {
                    wait(timeOutMillis);
                }
                catch (InterruptedException ie) { }
                if (System.currentTimeMillis() - startTime > timeOutMillis)
                    break;
            }
        }
        return prefetched && !failed;
    }

    //starts the recording
    public void start() {
        synchronized(this) {
            player.start();
        }
    }

    //closes the recording-process.
    public void close() {
        synchronized(this) {
            player.close();
            while (!closed) {
                try {
                    wait(100);
                }
                catch (InterruptedException ie) { }
            }
        }
        player.removeControllerListener(this);
    }

    public synchronized void controllerUpdate(ControllerEvent ce) {
        if (ce instanceof RealizeCompleteEvent) {
            realized = true;
        }
        else if (ce instanceof ConfigureCompleteEvent) {
            configured = true;
        }
        else if (ce instanceof PrefetchCompleteEvent) {
            prefetched = true;
        }
        else if (ce instanceof EndOfMediaEvent) {
            eom = true;
        }
        else if (ce instanceof ControllerErrorEvent) {
            failed = true;
        }
        else if (ce instanceof ControllerClosedEvent) {
            closed = true;
        }
        else {
            return;
        }
        notifyAll();
    }
}
