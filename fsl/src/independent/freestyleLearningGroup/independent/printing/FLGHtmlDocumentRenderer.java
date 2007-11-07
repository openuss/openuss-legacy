package freestyleLearningGroup.independent.printing;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.text.*;
import java.net.URL;
import javax.swing.text.html.*;

/*
        class prints out the content of a JEditorPane
 
 */
public class FLGHtmlDocumentRenderer implements Printable {
    // Used to keep track of when the page to print changes.
    private int currentPage = -1;
    
    // pane for formating and printing
    private JEditorPane paneToPrint;
    
    // Location of the current page end.
    private double pageEndY = 0;
    
    // Location of the current page start.
    private double pageStartY = 0;
    
    // boolean to allow control over whether
    // pages too wide to fit on a page will be scaled.
    private boolean scaleWidthToFit = true;

  	/**
  	 *  @param <code>JEditorPane</code> pane to print
  	 *  @return <code>JEditorPane</code> pane for preview
  	 */
  	public JEditorPane printPreview(JEditorPane jedPane) {
		JEditorPane jeditorPane = new JEditorPane();
		String documentType = jedPane.getContentType();
		Document doc = jedPane.getDocument();
		jeditorPane.setContentType(documentType);
		jeditorPane.setDocument(doc);
        StyleSheet styleSheet = ((HTMLDocument)jeditorPane.getDocument()).getStyleSheet();
        styleSheet.importStyleSheet(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/printing/printStyleSheet.css"));
     	return jeditorPane;
	}

  	/**
  	 * @param <code>JEditorPane</code> htmlPane
  	 */
    public void print(JEditorPane htmlPane) {
        String documentType;
        Document document;
        paneToPrint = new JEditorPane();
	
        // get document and content types of htmlPane
        document = htmlPane.getDocument();
        documentType = htmlPane.getContentType();
        
        // set document and content types for paneToPrint
		paneToPrint.setContentType(documentType);
        paneToPrint.setDocument(document);
        
        StyleSheet styleSheet = ((HTMLDocument)paneToPrint.getDocument()).getStyleSheet();
        styleSheet.importStyleSheet(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/printing/printStyleSheet.css"));
        paneToPrint.repaint();
   }

    /**
     * @param <code>Graphics</code>graphics
     * @param <code>PageFormat</code>pageFormat
     * @param <code>int</code>pageIndex
     * @return <code>int</code>
     */
   public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        double scale = 1.0;
        Graphics2D graphics2D;
        // View stores information about the look of a text component
        View rootView;
        
        // The Graphics object is cast to a Graphics2D object to allow for scaling.
        graphics2D = (Graphics2D) graphics;
       
        // The JEditorPane is laid out using the width of a printable page. This will
        // handle line breaks. If the JEditorPane cannot be sized at the width of the
        // graphics clip, scaling will be allowed.
        paneToPrint.setSize((int) pageFormat.getImageableWidth(),Integer.MAX_VALUE);
        // lay out the containers subcomponents
        paneToPrint.validate();
        
        // The root view of the JEditorPane is obtained. By examining this root
        // view and all of its children, printView will be able to determine
        // the location of each printable element of the document.
        
        rootView = paneToPrint.getUI().getRootView(paneToPrint);
        
        // If the scaleWidthToFit option is chosen, a scaling ratio is
        // determined, and the graphics2D object is scaled.
        if ((scaleWidthToFit) && (paneToPrint.getMinimumSize().getWidth() >
        pageFormat.getImageableWidth())) {
            scale = pageFormat.getImageableWidth()/paneToPrint.getMinimumSize().getWidth();
            graphics2D.scale(scale,scale);
        }
	
        graphics2D.setClip((int) (pageFormat.getImageableX()/scale),
    		(int) (pageFormat.getImageableY()/scale),
    		(int) (pageFormat.getImageableWidth()/scale),
    		(int) (pageFormat.getImageableHeight()/scale));
    		        
        // currentPage is checked to see if this is a new page to render. If so,
        // pageStartY and pageEndY are reset.
        if (pageIndex > currentPage) {
            currentPage = pageIndex;
            pageStartY += pageEndY;
            pageEndY = graphics2D.getClipBounds().getHeight();
        }
        
        // To match the coordinates of the printable clip of graphics2D and the
        // allocation rectangle which will be used to lay out the views,
        // graphics2D is translated to begin at the printable X and Y
        // coordinates of the graphics clip.
        graphics2D.translate(graphics2D.getClipBounds().getX(),graphics2D.getClipBounds().getY());
        
        // An allocation Rectangle is created to represent the layout of the
        // Views.
        Rectangle allocation = new Rectangle(0,(int)-pageStartY,(int)(paneToPrint.getMinimumSize().getWidth()),
        (int) (paneToPrint.getPreferredSize().getHeight()));
        
        // The printView method is called to paint the page. Its return value
        // will indicate if a page has been rendered.
        if (printView(graphics2D,allocation,rootView)) {return Printable.PAGE_EXISTS;}
        else {
            pageStartY = 0;
            pageEndY = 0;
            currentPage = -1;
            return Printable.NO_SUCH_PAGE;
        }
    }
    
   /*  printView is a recursive method which iterates through the tree structure
    	of the view sent to it. If the view sent to printView is a branch view,
    	that is one with children, the method calls itself on each of these
    	children. If the view is a leaf view, that is a view without children which
    	represents an actual piece of text to be painted, printView attempts to
    	render the view to the Graphics2D object.
    */
    
  /**
   * @param <code>Graphics2D</code> graphics2D
   * @param <code>Shape</code> allocation
   * @param <code>View</code> view
   * @return <code>boolean</code> true if page exits 
   */
   protected boolean printView(Graphics2D graphics2D, Shape allocation,
		  View view) {
	   boolean pageExists = false;
	   Rectangle clipRectangle = graphics2D.getClipBounds();
	   Shape childAllocation;
	   View childView;
	   if (view.getViewCount() > 0) {
		   for (int i = 0; i < view.getViewCount(); i++) {
			   childAllocation = view.getChildAllocation(i,allocation);
			   if (childAllocation != null) {
				   childView = view.getView(i);
				   if (printView(graphics2D,childAllocation,childView)) {
					   pageExists = true;
				   }
			   }
		   }
	   } else {
    		// any view starts after the beginning of the current printable page
        	// there are pages to print
        	if (allocation.getBounds().getMaxY() >= clipRectangle.getY()) {
            		pageExists = true;
            		// When a leaf view is taller than the printable area of a page, it
            		// cannot, of course, be broken down to fit a single page. Such a View
            		// will be printed whenever it intersects with the Graphics2D clip.
            		if ((allocation.getBounds().getHeight() > clipRectangle.getHeight()) &&
            			(allocation.intersects(clipRectangle))) {
                		view.paint(graphics2D,allocation);
            		} else {
                		// If a leaf view intersects the printable area of the graphics clip and
                		// fits vertically within the printable area, it will be rendered.
                		if (allocation.getBounds().getY() >= clipRectangle.getY()) {
                    			if (allocation.getBounds().getMaxY() <= clipRectangle.getMaxY()) {
                        			view.paint(graphics2D,allocation);
                    			} else {
                       				// If a leaf view does not exceed the printable area of a page but does
                        			// not fit vertically within the Graphics2D clip of the current page, the
                        			// method records that this page should end at the start of the view.
                        			// This information is stored in pageEndY.
                        			if (allocation.getBounds().getY() < pageEndY) {
                            				pageEndY = allocation.getBounds().getY();
                        			}
                    			}
                		}
            		}
        	}
        }
        return pageExists;
    }
}