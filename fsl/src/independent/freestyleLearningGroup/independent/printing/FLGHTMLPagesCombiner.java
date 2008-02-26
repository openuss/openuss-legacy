package freestyleLearningGroup.independent.printing;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.text.html.*;
import java.net.*;
import java.awt.*;

import freestyleLearningGroup.independent.gui.FLGHtmlUtilities;

/**
 * FSLHTMLPagesCombiner.java
 * Class combines HTMLPages in specified directory in one JEditorPane
 * @author  Carsten Fiedler, 30.03.2004
 * modified by Carsten Fiedler, 02.07.2004
 **/
public class FLGHTMLPagesCombiner {
    private JEditorPane combPane;
    private FLGPrintPreview printWin;
    private FLGHtmlDocumentRenderer dr = new FLGHtmlDocumentRenderer();
    private StringBuffer allFilesHTMLText;
    private FLGHtmlUtilities HTMLTextCleaner;
    
    public FLGHTMLPagesCombiner(String unitTitle) {
        combPane = new JEditorPane();
        combPane.setContentType("text/html");
        printWin = new FLGPrintPreview();
        allFilesHTMLText =  new StringBuffer();
        /** init StringBuffer with header **/
        allFilesHTMLText.append("<html><h1>" + unitTitle +"</h1><head></head><body>");
    }
    
    public void addTitleOfFolderElement(String folderTitle) {
        allFilesHTMLText.append("<p><h2>" + folderTitle + "</h2></p>");
    }
    
    public void addHtmlElement(String elementsPath, String elementsTitle) {
        try {
            // get elements title to build headline
            allFilesHTMLText.append("<p><h3>" + elementsTitle + "</h3></p>");
            
            /**
             * Each Html-Header
             * <html><head></head><body>
             * has to removed
             **/
            FileReader actualReaderPosition = new FileReader(elementsPath);
            int actualChar = 0;
            // search for <body>
            boolean foundHeader = false;
            while (!foundHeader) {
                StringBuffer headerBuffer = new StringBuffer();
                actualChar = actualReaderPosition.read();
                if (((char)actualChar) == '<') {
                    headerBuffer.append((char)actualChar);
                    // check next 5 chars
                    for (int z=0; z<5; z++) {
                        actualChar = actualReaderPosition.read();
                        headerBuffer.append((char) actualChar);
                    }
                    if ((headerBuffer.toString()).equals("<body>")) {
                        foundHeader = true;
                    }
                }
            }
            /**
             * search if end </body> of file has been reached,
             * otherwise append chars to StringBuffer
             **/
            boolean foundEnd = false;
            while (!foundEnd) {
                StringBuffer endBuffer = new StringBuffer();
                actualChar = ((char) actualReaderPosition.read());
                if (((char)actualChar)=='<') {
                    endBuffer.append((char)actualChar);
                    // check next 6 chars
                    for (int z=0; z<6; z++) {
                        endBuffer.append((char) actualReaderPosition.read());
                    }
                    if ((endBuffer.toString()).equals("</body>")) {
                        foundEnd = true;
                    } else {
                        // put last 7 chars (endBuffer) into buffer
                        allFilesHTMLText.append(endBuffer);
                    }
                } 
                else { 
                    allFilesHTMLText.append((char)actualChar); 
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Could not read: " + elementsPath);
        }
    }
    
    public void finalizeAndSetBase(String base) {
        File f = new File(base);
        // set end of combined HTML-file
        allFilesHTMLText.append("</body></html>");
        // remove font colors etc.
        combPane.setText(HTMLTextCleaner.createPrintableHtmlText(new String(allFilesHTMLText)));
        try {
            // set Base for images etc.
            HTMLDocument doc;
            doc = (HTMLDocument)combPane.getDocument();
            doc.setBase(new URL("file:///" + f.getAbsolutePath()  + "/"));
            //open print preview
            printWin.setPane(dr.printPreview(combPane));
            printWin.show();
        }
        catch (Exception exp) {
            System.out.println("Could not set Base to combined HTML-File!");
        }
    }
}

