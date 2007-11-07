/*
 * FLGMediaPoolPDFElementContentPanel.java
 *
 * Created on 19. Mai 2006, 14:28
 */

package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementsContentsPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearning.learningUnitViewAPI.elementsContentsPanel.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitViewEvent.*;
import freestyleLearningGroup.independent.util.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.data.xmlBindingSubclasses.*;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.events.learningUnitViewEvent.*;

// PDF imports
import org.jpedal.*;

/**
 *
 * @author  Mirko Wahn
 */
public class FLGMediaPoolPDFElementContentPanel extends FSLAbstractLearningUnitViewElementContentPanel {
    
    private PdfDecoder pdfDecoder;
    private int currentPage = 1;
    private float currentScale = 1f;
    private float scaleFactor = 0.1f;
    private FLGInternationalization internationalization;
    private JScrollPane sp_pdf;
    private String password;
    private JSlider pageSlider;
    private JPanel sliderPanel;
    
    public void init(FSLLearningUnitViewManager learningUnitViewManager, FSLLearningUnitEventGenerator learningUnitEventGenerator, boolean editMode) {
        super.init(learningUnitViewManager, learningUnitEventGenerator, editMode);
        internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.mediaPool.elementsContentsPanel.internationalization", getClass().getClassLoader());
        learningUnitViewManager.addLearningUnitViewListener(new PDFElementContentPanel_Adapter());
        learningUnitEventGenerator.addLearningUnitListener(new PDFElementContentPanel_UnitAdapter());
        pdfDecoder = new PdfDecoder();
    }
    
    /**
     * Returns printable component.
     * @return FLGMediaPoolPictureElementContentPanel this
     */
    protected java.awt.Component getPrintableComponent() {
        return this;
    }
    
    /**
     * Returns edit tool bar components.
     * Media Pool does not implement tool bar.
     * @return JComponent[] components
     */
    protected JComponent[] getEditToolBarComponents() {
        return null;
    }
    
    /**
     * Builds independent UI.
     */
    protected void buildIndependentUI() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground((Color)UIManager.get("FSLMainFrameColor1"));
        Hashtable labelTable = new Hashtable();
        for (int ix = 1; ix <= 10; ix++) labelTable.put(new Integer(ix), new JLabel("" + ix));
        pageSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        pageSlider.setMinorTickSpacing(1);              
        pageSlider.setSnapToTicks(true);
        pageSlider.setPaintLabels(true);
        pageSlider.setPaintTicks(true);
        pageSlider.setLabelTable(labelTable);
        pageSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                currentPage = pageSlider.getValue();
                if (!pageSlider.getValueIsAdjusting()) updatePDFView();
            }
        });
        pageSlider.setEnabled(false);
        sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.add(pageSlider);

        sp_pdf = new JScrollPane();
        sp_pdf.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp_pdf.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp_pdf.setViewportView(pdfDecoder);
        add(sp_pdf, BorderLayout.CENTER);
        add(sliderPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Builds dependent UI.
     * @param boolean reloadIfAlreadyLoaded
     */
    protected void buildDependentUI(boolean reloadIfAlreadyLoaded) {
        FLGMediaPoolElement mediaPoolElement = (FLGMediaPoolElement) learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewManager.getActiveLearningUnitViewElementId(),false);
        if (mediaPoolElement != null && mediaPoolElement.getType().equals(FLGMediaPoolElement.FILE_TYPE_PDF)) {
            String executableFileName = mediaPoolElement.getMediaFileName();
            File executableFile = learningUnitViewElementsManager.resolveRelativeFileName(executableFileName, mediaPoolElement);
            pdfDecoder = loadPDF(executableFile.getAbsolutePath());            

            Hashtable labelTable = new Hashtable();
            if (pdfDecoder.getPageCount() > 1) {
                int labelStep = max((int)(pdfDecoder.getPageCount()/10.), 1);
                pageSlider.setMaximum(pdfDecoder.getPageCount());
                pageSlider.setMajorTickSpacing(max(1, (int)(pdfDecoder.getPageCount()/10.)));
                for (int ix = 1; ix <= pdfDecoder.getPageCount(); ix += labelStep) {
                    labelTable.put(new Integer(ix), new JLabel("" + ix));
                }
                pageSlider.setLabelTable(labelTable);
            }
            else {
                labelTable.put(new Integer(1), new JLabel("" + 1));
                pageSlider.setMaximum(1);
            }
            pageSlider.setValue(1);
            pageSlider.setEnabled(true);
            sp_pdf.setViewportView(pdfDecoder);
            updatePDFView();
        }
    }
    
    private int min(int value1, int value2) {
        if (value1 <= value2) return value1;
        return value2;
    }
    
    private int max(int value1, int value2) {
        if (value1 >= value2) return value1;
        return value2;
    }
    
    private void updatePDFView() {
        try {
            pdfDecoder.decodePage(currentPage);
            pdfDecoder.setPageParameters(currentScale, currentPage);
        }
        catch (Exception e) {
            String message = internationalization.getString("dialog.errorLoadingPDF.message") + "\n\n" + e.getMessage();
            String title = internationalization.getString("dialog.errorLoadingPDF.title");
            FLGOptionPane.showMessageDialog(message, title, FLGOptionPane.ERROR_MESSAGE);
        }
        FLGMediaPoolViewEvent mediaPoolViewEvent = (FLGMediaPoolViewEvent)FLGMediaPoolViewEvent.createViewSpecificEvent(
            FLGMediaPoolViewEvent.MEDIA_NEW_PAGE_DISPLAYED, learningUnitViewManager.getActiveLearningUnitViewElementId());
        mediaPoolViewEvent.currentPage = currentPage; 
        mediaPoolViewEvent.pageCount = pdfDecoder.getPageCount(); 
        learningUnitViewManager.fireLearningUnitViewEvent(mediaPoolViewEvent);
        pdfDecoder.invalidate();
        repaint();
    }    
    
    private PdfDecoder loadPDF(String absoluteFileName) {
        PdfDecoder pdfDecoder = new PdfDecoder();
        try{
            //this opens the PDF and reads its internal details
            pdfDecoder.openPdfFile(absoluteFileName);
            
            // check encryption
            if(pdfDecoder.isEncrypted()){
                while(!pdfDecoder.isFileViewable()) {
                    password = JOptionPane.showInputDialog(this, internationalization.getString("dialog.enterPassword.message"));
                    pdfDecoder.setEncryptionPassword(password);
//                    pdfDecoder.verifyAccess();
                }
            }
        }
        catch(Exception e){
            String message = internationalization.getString("dialog.errorLoadingPDF.message") + "\n\n" + e.getMessage();
            String title = internationalization.getString("dialog.errorLoadingPDF.title");
            pdfDecoder.closePdfFile();
        }
        return pdfDecoder;        
    }
    
    /**
     * Returns, if panel is modified by user.
     * @return boolean isModifiedByUserInput
     */
    public boolean isModifiedByUserInput() {
        return false;
    }
    
    /**
     * Saves user changes.
     */
    public void saveUserChanges() {
    	learningUnitViewElementId = learningUnitViewManager.getActiveLearningUnitViewElementId();
    	FLGMediaPoolElement learningUnitViewElement = (FLGMediaPoolElement)learningUnitViewElementsManager.getLearningUnitViewElement(learningUnitViewElementId, false);
    	learningUnitViewElement.setLastModificationDate(String.valueOf(new Date().getTime()));
    }
    
    class PDFElementContentPanel_UnitAdapter extends FSLLearningUnitAdapter {
        public void learningUnitEditModeChanged(FSLLearningUnitEvent event) { 
            if (event.isEditMode()) {
                removeAll();
            }
            else {
                add(sp_pdf, BorderLayout.CENTER);
                add(sliderPanel, BorderLayout.SOUTH);
//                buildDependentUI(true);
            }
        }
    }
    
    class PDFElementContentPanel_Adapter extends FSLLearningUnitViewAdapter {
        public void learningUnitViewSpecificEventOccurred(FSLLearningUnitViewEvent event) {
            if (event instanceof FLGMediaPoolViewEvent) {
                switch(((FLGMediaPoolViewEvent)event).getEventSpecificType()) {
                    case FLGMediaPoolViewEvent.MEDIA_NEXT_PAGE: {
                        if (currentPage < pdfDecoder.getPageCount()) {
                            pageSlider.setValue(++currentPage);
                        }
                        updatePDFView();
                        break;
                    }
                    case FLGMediaPoolViewEvent.MEDIA_PREVIOUS_PAGE: {
                        if (currentPage > 1) {
                            pageSlider.setValue(--currentPage);
                        }
                        updatePDFView();
                        break;
                    }
                    case FLGMediaPoolViewEvent.MEDIA_SCALE_INCREASED: {
                        currentScale = currentScale * (1.0f + scaleFactor);
                        updatePDFView();
                        break;
                    }
                    case FLGMediaPoolViewEvent.MEDIA_SCALE_DECREASED: {
                        currentScale = currentScale * (1.0f - scaleFactor);
                        updatePDFView();
                        break;
                    }
                    case FLGMediaPoolViewEvent.MEDIA_SCALE_PAGE_100: {
                        currentScale = 1.0f;
                        updatePDFView();
                        break;
                    }
                    case FLGMediaPoolViewEvent.MEDIA_SCALE_PAGE_WIDTH: {
                        pdfDecoder.setPageParameters(1.0f, currentPage);
                        currentScale = (float)(getSize().getWidth() / pdfDecoder.getPreferredSize().getWidth());
                        updatePDFView();
                        break;
                    }
                    case FLGMediaPoolViewEvent.MEDIA_SCALE_PAGE_HEIGHT: {
                        pdfDecoder.setPageParameters(1.0f, currentPage);
                        currentScale = (float)(getSize().getHeight() / pdfDecoder.getPreferredSize().getHeight());
                        updatePDFView();
                        break;
                    }
                }
            }
        }
        
    }
}
