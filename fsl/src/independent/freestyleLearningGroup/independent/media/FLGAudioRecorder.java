
package freestyleLearningGroup.independent.media;

import java.io.*;
import java.util.*;

import javax.media.*;
import javax.media.format.*;
import javax.media.protocol.*;

/**
 * This class implements an audio-recorder which records audio-data from a microfon (or else).
 * @author Gunnar Thies
 * @version 1.0, 15/04/03
 */
public class FLGAudioRecorder extends Thread {
    private CaptureDeviceInfo di;
    private Vector deviceList;
    private FLGStateHelper sh;
    private Processor p;
    private DataSource source;
    private MediaLocator dest;
    private DataSink filewriter;
    private Player player = null;

    /**
     * Constructor of this class.
     * @param fileName The absolute filename, where the audio-file should be saved.
     */
    public FLGAudioRecorder(String fileName) {
        deviceList = CaptureDeviceManager.getDeviceList(new AudioFormat(AudioFormat.LINEAR, 44100, 16, 2));
        if (deviceList.size() > 0)
            di = (CaptureDeviceInfo)deviceList.firstElement();
        else
            //if there is no device...e.g. no sound-card installed, then give out an error-message
                System.out.println("No recording device.");
        try {
            p = Manager.createProcessor(di.getLocator());
            //FLGStateHelper-Klasse: for init the sound prozessor
            sh = new FLGStateHelper(p);
        } catch (IOException e) {
            System.out.println("Error at recording:" + e);
        } catch (NoProcessorException e) {
            System.out.println("Error at recording: " + e);
        }
        // configutes the recording-processor (with the FLGStateHeper-class)
        if (!sh.configure(10000))
            System.out.println("Error in recording-processor!");
        // sets the output-format of the audio-file
        p.setContentDescriptor(new FileTypeDescriptor(FileTypeDescriptor.WAVE));
        //realizes the recording-processor (StateHelper-class)
        if (!sh.realize(10000))
            System.out.println("Error in recording-processor");
        // get the audio-content
        source = p.getDataOutput();
        //create a new file
        dest = new MediaLocator("file://" + fileName);
        //datasink-objekt is created and opened to write on
        try {
            filewriter = Manager.createDataSink(source, dest);
            filewriter.open();
        } catch (NoDataSinkException e) {
            System.out.println("Error at recording: " + e);
        } catch (IOException e) {
            System.out.println("Error at recording: " + e);
        } catch (SecurityException e) {
            System.out.println("Error at recording: " + e);
        }
        //ready for recording
    }

    /** Method for starting the record-process. */
    public void startRecording() {
        //start the filewriter and.. the record-process.
        try {
            filewriter.start();
        } catch (IOException e) {
            System.out.println("Error at recording : " + e);
        }
        //...the record-process.
        sh.start();
    }

    /** Method for stopping the record-process. */
    public void stopRecording() {
        //stateHelper exits the recording-process.
        sh.close();
        //close dataSink-objekt
        filewriter.close();
    }
}