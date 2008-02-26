package freestyleLearningGroup.independent.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

/**
 * This class can be used for comfortably printing <code>Component</code>s.
 * @author Freestyle Learning Group
 * @see FLGPrintableWrapper
 */
public class FLGPrintExt {
    /** This class should not be instanciated */
    private FLGPrintExt() { }

    /**
     * This method can be invoked to print any given <code>Component</code> c.
     * @param <code>component</code>: the component
     * @param <code>headline</code>: a header line
     * @param <code>title</code>: a title
     * @param <code>comment</code>: a description or comment
     * @param <code>comment</code>: true if print should fit paper size
     */
    public static void printComponent(Component component, String headline, String title, String comment, boolean scaleToFit) {
        boolean doubleBufferingEnabled = RepaintManager.currentManager(component).isDoubleBufferingEnabled();
        if (doubleBufferingEnabled)
            RepaintManager.currentManager(component).setDoubleBufferingEnabled(false);
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat defaultFormat = new PageFormat();
        defaultFormat = pj.defaultPage(defaultFormat);
        Dimension pageSize = new Dimension((int)defaultFormat.getImageableWidth(), (int)defaultFormat.getImageableHeight());
        pj.setPrintable(new FLGPrintableWrapper(component, headline, title, comment, scaleToFit));
        try {
            pj.print();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        if (component instanceof Container)
            ((Container)component).doLayout();
        if (doubleBufferingEnabled)
            RepaintManager.currentManager(component).setDoubleBufferingEnabled(true);
    }

    /**
     * This method can be invoked to print any given <code>Component</code> c.
     * @param <code>component</code>: the component
     * @param <code>headline</code>: a header line
     * @param <code>title</code>: a title
     * @param <code>comment</code>: a description or comment
     */
    public static void printComponent(Component component, String headline, String title, String comment) {
        printComponent(component, headline, title, comment, true);
    }
}
