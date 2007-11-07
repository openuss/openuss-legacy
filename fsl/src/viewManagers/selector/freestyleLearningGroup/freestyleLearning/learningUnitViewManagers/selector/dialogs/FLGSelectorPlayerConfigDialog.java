package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;


/**
 * FLGSelectorPlayerConfigDialog.
 * Dialog for Selector Multi-Player Configuration.
 * @author Carsten Fiedler
 */
public class FLGSelectorPlayerConfigDialog {
	private FLGInternationalization internationalization;
	private FLGOptionPane optionPane;
	private JList playerList;
	private DefaultListModel listModel;
	private JTextField nameTextField;
	private MenuActionListener menuListener;
	private JPopupMenu popup;
	private JPanel mainPanel;
	private boolean returnValue = false;
	
	/**
	 * Initilizes Dialog.
	 */
	public void init() {
		internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs.internationalization",
				getClass().getClassLoader());
        listModel = new DefaultListModel();
        playerList = new JList(listModel);
        // init list with default names
        listModel.addElement(internationalization.getString("selector.dialogs.playerConfDialog.defaultPlayer") + " 1");
        listModel.addElement(internationalization.getString("selector.dialogs.playerConfDialog.defaultPlayer") + " 2");
        menuListener = new MenuActionListener();
        // init popupmenu
        buildIndependentUI();
	}
	
	/**
	 * Builds independent UI.
	 */
	public void buildIndependentUI() {
		playerList.addMouseListener(
			new MouseAdapter() {
				// show popup menu and set selection path in tree
	            public void mouseReleased(MouseEvent e) {
	            	if (e.isPopupTrigger()) {
	            		if (!listModel.isEmpty()) {
	            			if (playerList.getSelectedValues().length==1) {
	            				initPopupMenu();
	    	                    popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	    	                    if ((playerList.getSelectedIndex())==-1) {
	    	                    	playerList.setSelectedIndex(0);
	    	                    }
	            			} else {
	            				initPopupMenu();
	    	                    popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	    	                    if ((playerList.getSelectedIndex())==-1) {
	    	                    	playerList.setSelectedIndex(0);
	    	                    }
	                        }
	            		} else {
	            			// list is empty, deactivate edit and delete menu item
	                        initPopupMenu();
	                    }
	                }
	            }
	            
	            public void mousePressed(MouseEvent e) {
	            	if (e.isPopupTrigger()) {
	            		if (!listModel.isEmpty()) {
	            			if (playerList.getSelectedValues().length==1) {
	            				initPopupMenu();
	    	                    popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	    	                    if ((playerList.getSelectedIndex())==-1) {
	    	                    	playerList.setSelectedIndex(0);
	    	                    }
	            			} else {
	            				initPopupMenu();
	    	                    popup.show((JComponent)e.getSource(), e.getX(), e.getY());
	    	                    if ((playerList.getSelectedIndex())==-1) {
	    	                    	playerList.setSelectedIndex(0);
	    	                    }
	                        }
	            		} else {
	            			// list is empty, deactivate edit and delete menu item
	                        initPopupMenu();
	                    }
	                }
	            }
			});
        
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel namePanel = new JPanel(new FLGColumnLayout());
		namePanel.setBorder(new EmptyBorder(5,5,5,5));
		nameTextField = new JTextField(20);
		nameTextField.setText(internationalization.getString("selector.dialogs.playerConfDialog.newPlayer"));
		nameTextField.setPreferredSize(new Dimension(27,27));
		namePanel.add(nameTextField,FLGColumnLayout.CENTER);
		JButton addPlayerButton = new JButton(
				internationalization.getString("selector.dialogs.playerConfDialog.addPlayerButton"));
		addPlayerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkIfUserAlreadExits(nameTextField.getText())) {
					// open message that user already exits
					JPanel messagePanel = new JPanel();
                    messagePanel.add(new JLabel(internationalization.getString("selector.dialogs.playerConfDialog.optionPane.PlayerAlreadyExits")));
					FLGOptionPane.showConfirmDialog(messagePanel,
                             internationalization.getString("selector.dialogs.playerConfDialog.optionPane.windowTitle"),
                             FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
				} else if (nameTextField.getText().equals("")) {
					// open message that no name is inserted
					JPanel messagePanel = new JPanel();
                    messagePanel.add(new JLabel(internationalization.getString("selector.dialogs.playerConfDialog.optionPane.noPlayerInserted")));
					FLGOptionPane.showConfirmDialog(messagePanel,
                             internationalization.getString("selector.dialogs.playerConfDialog.optionPane.windowTitle"),
                             FLGOptionPane.OK_OPTION, FLGOptionPane.ERROR_MESSAGE);
				} else {
					listModel.addElement(nameTextField.getText());
					nameTextField.setText("");
				}
			}
		});
		namePanel.add(addPlayerButton,FLGColumnLayout.CENTEREND);
		mainPanel.add(namePanel,BorderLayout.NORTH);
		
		
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		listPanel.add(new JScrollPane(playerList),BorderLayout.CENTER);
		mainPanel.add(listPanel,BorderLayout.CENTER);
		
		//mainPanel.add(new JScrollPane(playerList),BorderLayout.CENTER);
	}
	
	/**
	 * Builds independent UI.
	 */
	public void buildDependentUI() {
		// init OptionPane
     	String[] buttonsDescription = new String[2];
    	buttonsDescription[0] = internationalization.getString("selector.dialogs.playerConfDialog.optionPane.startButton");
		buttonsDescription[1] = internationalization.getString("selector.dialogs.playerConfDialog.optionPane.cancelButton");
		optionPane = new FLGOptionPane(null,mainPanel,
				internationalization.getString("selector.dialogs.playerConfDialog.dialogTitle"),
          		FLGOptionPane.OK_CANCEL_OPTION,FLGOptionPane.PLAIN_MESSAGE,buttonsDescription);
		optionPane.setVisible(true);
		if(optionPane.getReturnValue()==FLGOptionPane.OK_OPTION) {
			returnValue=true;
		}
	}
	
	/**
	 * Opens dialog by invoking buildDependentUI.
	 */
	public boolean showDialog() {
		buildDependentUI();
		return returnValue;
	}
	
	/**
	 * Returns user array.
	 * @return String[] user array
	 */
	public Object[] getPlayers() {
		Object[] users = listModel.toArray(); 
		return users;
	}
	
	private void initPopupMenu() {
		popup = new JPopupMenu();
	    
		// edit user
	    JMenuItem addItem = new JMenuItem(
	    		internationalization.getString("selector.dialogs.playerConfDialog.popup.editElement"));
	    addItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
	    addItem.addActionListener(menuListener);
	    // delete user
	    JMenuItem removeItem = new JMenuItem(
	    		internationalization.getString("selector.dialogs.playerConfDialog.popup.deleteElement"));
	    removeItem.setFont(new Font("SansSerif", Font.PLAIN, 12));
	    removeItem.addActionListener(menuListener);
	  
	    if (!listModel.isEmpty()) {
	    	addItem.setEnabled(true);
	    	removeItem.setEnabled(true);
	    } else {
	    	addItem.setEnabled(false);
	    	removeItem.setEnabled(false);
	    }
	    
	    popup.add(addItem);
	    popup.add(removeItem);
	    popup.setOpaque(true);
	}
	
	private boolean checkIfUserAlreadExits(String newUser) {
		boolean userExists = false;
		for(int i=0; i<listModel.size();i++) {
			if (listModel.getElementAt(i).equals(newUser)) {
				userExists = true;
				break;
			}
		}
		return userExists;
	}

	/**
     * Inner class for invoking popup actions.
     */
    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // delete user
            if (ae.getActionCommand().equals(
            		internationalization.getString("selector.dialogs.playerConfDialog.popup.deleteElement"))) {
            	listModel.remove(playerList.getSelectedIndex());
             
            } 
            // edit user, e.g. default user name
            if (ae.getActionCommand().equals(
            		internationalization.getString("selector.dialogs.playerConfDialog.popup.editElement"))) {
            	nameTextField.setText((String)playerList.getSelectedValue());
            	// remove entry
            	listModel.remove(playerList.getSelectedIndex());
            }
        }
    }
}
