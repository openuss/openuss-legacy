package freestyleLearningGroup.independent.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JTextArea;

/**
 * Service class for printing <code>Components</code>. This class is used by
 * <code>FLGPrintExt</code>. One component at a time is printed with a given title and comment string.
 * @see LGPrintExt
 */
public class FLGPrintableWrapper implements Printable {
    private Component component;
    private String title;
    private String headline;
    private String comment;
    private boolean drawHeadline;
    private boolean drawComment;
    private boolean drawTitle;
    private boolean scaleToFit;
    private Font headlineFont;
    private Font titleFont;
    private int paperHeight, paperWidth, componentHeight, componentWidth;
    private double scaleFactorX, scaleFactorY, scaleFactor;
    private int titleFontHeight;
    private int headlineAreaHeight;
    private int titleAreaHeight;
    private int componentAreaHeight;
    private int commentAreaHeight;
    private int headlineLength;
    private int headlineFontHeight;
    private int headlineFontAscent;
    private Graphics2D g2;

    /**
     * Create wrapper for component.
     * @param <code>component</code> Component to be printed
     */
    public FLGPrintableWrapper(Component component) {
        this(component, null, null, null);
    }

    /**
     * Create wrapper for component. A headline String will be placed on top of the page.
     * @param <code>component</code> Component to be printed
     * @param <code>headline</code> header String
     */
    public FLGPrintableWrapper(Component component, String headline) {
        this(component, headline, null, null);
    }

    /**
     * Create wrapper for component. A headline String will be placed on top of the page, and a comment or description can be
     * given to be displayed below the printed component.
     * @param <code>component</code> Component to be printed
     * @param <code>headline</code> header String
     * @param <code>comment</code> comment or description
     */
    public FLGPrintableWrapper(Component component, String headline, String comment) {
        this(component, headline, null, comment);
    }

    /**
     * Create wrapper for component. A headline String will be placed on top of the page, and a comment or description can be
     * given to be displayed below the printed component. Between the headline and component an additional
     * title string can be placed.
     * @param <code>component</code> Component to be printed
     * @param <code>headline</code> header String
     * @param <code>title</code> title String
     * @param <code>comment</code> comment or description
     */
    public FLGPrintableWrapper(Component component, String headline, String title, String comment) {
        this.component = component;
        this.headline = headline;
        this.title = title;
        this.comment = comment;
    }

    /**
     * Create wrapper for component. A headline String will be placed on top of the page, and a comment or description can be
     * given to be displayed below the printed component. Between the headline and component an additional
     * title string can be placed. If desired, the component will be scaled to paper size
     * @param <code>component</code> Component to be printed
     * @param <code>headline</code> header String
     * @param <code>title</code> title String
     * @param <code>comment</code> comment or description
     * @param <code>scaleToFit</code> true if print should be scaled to paper size
     */
    public FLGPrintableWrapper(Component component, String headline, String title, String comment, boolean scaleToFit) {
        this.component = component;
        this.headline = headline;
        this.title = title;
        this.comment = comment;
        this.scaleToFit = scaleToFit;
    }

    public void setScaleToFit(boolean scaleToFit) {
        this.scaleToFit = scaleToFit;
    }

    private void init(Graphics graphicContext, PageFormat pageFormat, int pageIndex) {
        drawHeadline = (headline != null && headline.length() > 0);
        drawTitle = (title != null && title.length() > 0);
        drawComment = (comment != null && comment.length() > 0);
        // set fonts
        headlineFont = new Font("Helvetica", Font.PLAIN, 10);
        titleFont = new Font("Helvetica", Font.BOLD, 12);
        // calculate dimensions of print page, component, headline and title
        paperHeight = (int)pageFormat.getImageableHeight();
        paperWidth = (int)pageFormat.getImageableWidth();
        componentHeight = component.getSize().height;
        componentWidth = component.getSize().width;
        if (scaleToFit) {
            scaleFactorX = (double)paperWidth / componentWidth;
            // scaleFactorY = (double)paperHeight / componentHeight;
            scaleFactorY = (double)(paperHeight * 0.6d) / componentHeight; // max 60% of paper height
        }
        else {
            scaleFactorX = 1.0;
            scaleFactorY = 1.0;
        }
        scaleFactor = Math.min(scaleFactorX, scaleFactorY);
        g2 = (Graphics2D)graphicContext;
        g2.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY());
        g2.setFont(headlineFont);
        FontMetrics headlineMetrics = g2.getFontMetrics();
        headlineLength = headlineMetrics.stringWidth(headline);
        headlineFontHeight = headlineMetrics.getHeight();
        headlineFontAscent = headlineMetrics.getAscent();
        g2.setFont(titleFont);
        FontMetrics titleMetrics = g2.getFontMetrics();
        titleFontHeight = headlineMetrics.getHeight();
        // page is divided in 4 areas: headline-, title-, component- and comment-area
        if (drawHeadline) headlineAreaHeight = 2 * headlineFontHeight;
        else
            headlineAreaHeight = 0;
        if (drawTitle) titleAreaHeight = 2 * titleFontHeight;
        else
            titleAreaHeight = 0;
        componentAreaHeight = (int)(componentHeight * scaleFactor);
        if (drawComment) commentAreaHeight = paperHeight - componentAreaHeight;
        else
            commentAreaHeight = 0;
    }

    private void drawHeadline() {
        g2.setFont(headlineFont);
        g2.setColor(Color.black);
        g2.drawString(headline, 0, headlineFontAscent);
        // underline headline
        int linePosY = headlineFontHeight;
        g2.drawLine(0, linePosY, paperWidth, linePosY);
    }

    private void drawTitle() {
        g2.setFont(titleFont);
        g2.drawString(title, 0, headlineAreaHeight + titleFontHeight);
        g2.setFont(headlineFont);
    }

    private void drawComponent(PageFormat pageFormat) {
        double yOffset = headlineAreaHeight + titleAreaHeight;
        double xOffset = 0;
        if (scaleToFit) xOffset = (paperWidth - scaleFactor * componentWidth) / 2;
        g2.translate(xOffset, yOffset);
        g2.scale(scaleFactor, scaleFactor); // scale
        component.paint(g2);
        g2.scale(1d / scaleFactor, 1d / scaleFactor); // undo scaling
        g2.translate(-xOffset, -yOffset); // undo transformation
    }

    private void drawComment() {
        double yOffset = componentAreaHeight + headlineAreaHeight + titleAreaHeight + headlineFontHeight * 2;
        g2.setFont(headlineFont);
        g2.setColor(Color.black);
        int lineOffset = (int)yOffset - 3;
        g2.drawLine(0, lineOffset, paperWidth, lineOffset);
        JTextArea ta = new JTextArea();
        ta.setFont(headlineFont);
        ta.setText(comment);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setSize(paperWidth, commentAreaHeight);
        g2.translate(0, yOffset);
        ta.paint(g2);
        g2.translate(0, -yOffset); // undo transformation
    }

    /**
     * Prints Component within specified Graphic Context
     * @param <code>graphicContext</code> the graphic context
     * @param <code>pageFormat</code> page format for printing
     * @param <code>pageIndex</code> page of page to be printed
     * @return <code>Printable.PAGE_EXISTS</code> if successfull, otherwise <code>Printable.NO_SUCH_PAGE</code>
     */
    public int print(Graphics graphicContext, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        init(graphicContext, pageFormat, pageIndex);
        // print headline
        if (drawHeadline) drawHeadline();
        // print title
        if (drawTitle) drawTitle();
        // print component
        drawComponent(pageFormat);
        // print comment
        if (drawComment) drawComment();
        // reset origin
        g2.translate(-(int)pageFormat.getImageableX(), -(int)pageFormat.getImageableY());
        return Printable.PAGE_EXISTS;
    }
}
