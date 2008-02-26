/**
 * Unitmap is the interface between 
 * <code>FSLLearningUnitsManager</code> and the Unitmap.
 * 
 * It initializes the user interface <code>UnitmapFrame</code> and 
 * <code>UnitmapGraph</code>. Also it registers at the 
 * <code>FSLUserEventGenerator</code> so that the Unitmap can 
 * handle user and user-role changes.
 * 
 * @author Jens Sieberg, 31.07.2006 - 11.09.2006
 */
package freestyleLearning.homeCore.learningUnitsManager.unitmap;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

import freestyleLearning.homeCore.learningUnitsManager.FSLLearningUnitsManager;
import freestyleLearning.homeCore.learningUnitsManager.data.xmlBindingSubclasses.FSLLearningUnitsDescriptor;
import freestyleLearning.homeCore.usersManager.event.FSLUserEvent;
import freestyleLearning.homeCore.usersManager.event.FSLUserEventGenerator;
import freestyleLearning.homeCore.usersManager.event.FSLUserListener;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;


public class Unitmap {
	
	UnitmapFrame unitmapUI;
	
	FSLLearningUnitsManager learningUnitsManager;
	FSLLearningUnitsDescriptor learningUnitsDescriptor;
	FLGInternationalization internationalization;
	
	File unitmapSaveDirectory;
	
	/**
	 * initilizes the Unitmap-Interface, but does not load the Unitmap UI.
	 * @param learningUnitsManager
	 * @param userEventGenerator
	 * @param learningUnitsDescriptor
	 */
	public Unitmap(FSLLearningUnitsManager learningUnitsManager,
		       	   FSLUserEventGenerator userEventGenerator,
			       FSLLearningUnitsDescriptor learningUnitsDescriptor) {
		this.learningUnitsManager = learningUnitsManager;
		this.learningUnitsDescriptor = learningUnitsDescriptor;
		
		internationalization  = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.unitmap.internationalization",
																Unitmap.class.getClassLoader());
		
		userEventGenerator.addUserListener(getUserListener());
	}
	
	/**
	 * shows the Unitmap UI.
	 * 
	 * If the UI already exists it just brings it to the front, 
	 * otherwhise a new UI is created.
	 */
	public void showUnitmap() {
		if (unitmapUI != null)
		{
			if ( !unitmapUI.isProgressFrameVisible() )
				unitmapUI.setVisible(true);
		}
		else
			createUI();
	}
	
	private void createUI() {
		// Construct Frame
		UnitmapGraph graph= new UnitmapGraph(new UnitmapModel(), learningUnitsManager, learningUnitsDescriptor, unitmapSaveDirectory);
		
		unitmapUI = new UnitmapFrame(graph, internationalization);

		unitmapUI.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		unitmapUI.addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		    	  int returnValue = -1;
		    	  if (unitmapUI.isUnsaved())
		    		  returnValue = FLGOptionPane.showConfirmDialog(internationalization.getString("dialog.save.message"),
		    				  										internationalization.getString("dialog.save.title"), 
		    				  										FLGOptionPane.YES_NO_CANCEL_OPTION, 
		    				  										FLGOptionPane.WARNING_MESSAGE);
		    	  if (returnValue != FLGOptionPane.CANCEL_OPTION)
		    	  {
		    		  if (returnValue == FLGOptionPane.YES_OPTION)
			    		  // Only quit if the graph could be saved
		    			  if ( !((UnitmapGraph)unitmapUI.getGraph()).saveUnitmapData())
		    			  {
		    				  FLGOptionPane.showMessageDialog(internationalization.getString("dialog.save.errorMessage"),
		    						  						  internationalization.getString("dialog.save.title"),
		    						  						  FLGOptionPane.ERROR_MESSAGE);
		    				  return;
		    			  }
		    		  
		    		  e.getWindow().dispose();
		    		  // Delete all references on the UI when closed
		    		  unitmapUI = null;
		    	  }
		    	  
		        }
		});
		
		// Show Frame
		unitmapUI.setVisible(true);
	}
	
	/**
	 * @return a listener which handles user and user-role changes
	 *         for the Unitmap.
	 */
	public FSLUserListener getUserListener() {
		return new FSLUserListener() {

			public void userChanged(FSLUserEvent userEvent) {
				unitmapSaveDirectory = userEvent.getUserDirectory();
			}

			public void userLogout(FSLUserEvent userEvent) {
				// needs not to be handled				
			}

			public void userRoleChanged(FSLUserEvent userEvent) {
				if (unitmapUI != null)
				{   
					unitmapUI.userRoleChanged(userEvent.getUserRole().equals(userEvent.AUTHOR_ROLE));
				}
			}
		};
	}
}