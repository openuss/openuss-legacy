package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

import javax.swing.*;


import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGSelectorGridObjectPropertiesDialog {
	private FLGInternationalization internationalization;
	private FLGOptionPane optionPane;
	private JTextField textField_textElement;
	private JTextField textField_imageElement;
	private String textElementTitle;
	private String imageElementTitle;
	private String selectedFile;
	private JButton imageSearchButton;
	private JPanel imageElementDataPanel;
	private JPanel textElementDataPanel;
	private JPanel imagePreviewPanel;
	private JPanel textBackgroundPanel;
	private JPanel dialogPanel;
	private JButton colorChooserButton;
	private JButton textColorChooserButton;
	private JTabbedPane dialogTabbedPane;
	private Color selectedColor;
	private Image scaledImage;
	private boolean imageSelected = false;
	private File imageFile;
	private int width=250;
	private int height=250;
	
	/**
	 * Constructor.
	 * Inits internationalization and builds independent UI.
	 */
	public FLGSelectorGridObjectPropertiesDialog() {
		internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs.internationalization",
	            getClass().getClassLoader());
		textField_textElement = new JTextField(25);
		textField_imageElement = new JTextField(25);
		buildIndependentUI();
	}
	
	/**
	 * Builds independent UI.
	 */
	public void buildIndependentUI() {
		/**** complete dialog panel ****/
    	dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dialogTabbedPane = new JTabbedPane();
        
        /**** text element data panel ****/
        textElementDataPanel = new JPanel(new FLGColumnLayout());
        textElementDataPanel.add(new JLabel(internationalization.getString("selector.dialogs.newGridObjectDialog.elementTitle")),FLGColumnLayout.LEFT);
        textField_textElement = new JTextField(25);
        textElementDataPanel.add(textField_textElement, FLGColumnLayout.LEFTEND);
        textColorChooserButton = new JButton(internationalization.getString("selector.dialogs.newGridObjectDialog.colorChooserButton"));
        textBackgroundPanel = new JPanel();
        textBackgroundPanel.setPreferredSize(new Dimension(50,25));
        textBackgroundPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textBackgroundPanel.setBackground((Color)UIManager.get("FSLMainFrameColor1"));
        textColorChooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open color chosser dialog
                selectedColor = JColorChooser.showDialog(null, internationalization.getString("dialog.colorChooser.title"), (Color)UIManager.get("FSLMainFrameColor1"));
                if(selectedColor == null) {
                	selectedColor = (Color)UIManager.get("FSLMainFrameColor1");
                }
                textBackgroundPanel.setBackground(selectedColor);
			}
        });
        textElementDataPanel.add(new JLabel(""), FLGColumnLayout.LEFT);
        JPanel colorPanel = new JPanel(new GridBagLayout());
        colorPanel.add(textBackgroundPanel);
        colorPanel.add(textColorChooserButton);
        textElementDataPanel.add(colorPanel, FLGColumnLayout.LEFTEND);
       
        JScrollPane sp_textElement = new JScrollPane(textElementDataPanel);
        sp_textElement.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        /**** image element data panel ****/
        imageElementDataPanel = new JPanel(new FLGColumnLayout());
        // title panel
        JPanel imageTitlePanel = new JPanel(new FLGColumnLayout());
        imageTitlePanel.add(new JLabel(internationalization.getString("selector.dialogs.newGridObjectDialog.elementTitle")),FLGColumnLayout.LEFT);
        textField_imageElement = new JTextField(25);
        imageTitlePanel.add(textField_imageElement,FLGColumnLayout.LEFTEND);
        imageElementDataPanel.add(imageTitlePanel,FLGColumnLayout.LEFTEND);
        // search button
		JPanel searchButtonPanel = new JPanel(new GridBagLayout());
		imageSearchButton = new JButton(internationalization.getString("selector.dialogs.newGridObjectDialog.imageSearchButton"));
		imageSearchButton.setEnabled(true);
		searchButtonPanel.add(imageSearchButton);
        // color chooser button
        colorChooserButton = new JButton(internationalization.getString("selector.dialogs.newGridObjectDialog.colorChooserButton"));
        imageSearchButton.setEnabled(true);
        searchButtonPanel.add(colorChooserButton);
		imageElementDataPanel.add(searchButtonPanel,FLGColumnLayout.LEFTEND);
		// image preview panel
		imagePreviewPanel = new JPanel(new BorderLayout());
		imagePreviewPanel.setPreferredSize(new Dimension(width-10,height-10));
		imagePreviewPanel.setBorder(BorderFactory.createEtchedBorder());
		imagePreviewPanel.setEnabled(true);
		imageElementDataPanel.add(imagePreviewPanel, FLGColumnLayout.LEFTEND);
		// action listener for search button		
		imageSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionPane.dispose();
				// open file chooser for selecting image
				JFileChooser fileDialog = new JFileChooser();
				String[] fileExtensions = { ".png" ,".jpg", ".gif"};
		        fileDialog.setFileFilter(new FLGUIUtilities.FLGFileFilter(fileExtensions, 
		        		internationalization.getString("selector.configurationPanel.newImageObjectFileDialog.text")));
				fileDialog.setDialogTitle(internationalization.getString("selector.dialogs.newGridObjectDialog.imageFileChooser_tilte"));
		        java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		        fileDialog.setLocation((int)(screenDim.getWidth() - fileDialog.getWidth()) / 2,
		        (int)(screenDim.getHeight() - fileDialog.getHeight()) / 2);
		        if (fileDialog.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
		        	selectedFile = fileDialog.getSelectedFile().getName();
		        	// load and scale image
		        	imageFile = fileDialog.getSelectedFile();
		        	scaledImage = scaleImage(loadImage(fileDialog.getSelectedFile()), width, height);
		            // insert scaled image into preview panel 
		        	imagePreviewPanel.removeAll();
		        	if(selectedColor != null) {
		        		imagePreviewPanel.setBackground(selectedColor);
		        	} 
		        	imagePreviewPanel.add(new JLabel(new ImageIcon(scaledImage)),BorderLayout.CENTER);
		        	optionPane = new FLGOptionPane(null, dialogPanel, internationalization.getString("selector.dialogs.newGridObjectDialog.title"), 
					         FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
				   	optionPane.setVisible(true);
		        }
			}
		});
		// action listener for color chooser button
		colorChooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open color chosser dialog
                selectedColor = JColorChooser.showDialog(null, internationalization.getString("dialog.colorChooser.title"), (Color)UIManager.get("FSLMainFrameColor1"));
                if(selectedColor == null) {
                	selectedColor = (Color)UIManager.get("FSLMainFrameColor1");
                }
                // update image panel
                imagePreviewPanel.removeAll();
                imagePreviewPanel.setBackground(selectedColor);
                if(imageFile != null) {
                	scaledImage = scaleImage(loadImage(imageFile), width, height);
                	imagePreviewPanel.add(new JLabel(new ImageIcon(scaledImage)),BorderLayout.CENTER);
                }
            	//optionPane = new FLGOptionPane(null, dialogPanel, internationalization.getString("selector.dialogs.newGridObjectDialog.title"), 
				  //       FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
               	//soptionPane.setVisible(true);
			}
		});
		
        JScrollPane sp_imageElement = new JScrollPane(imageElementDataPanel);
        sp_imageElement.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        /**** build tabbed pane ****/
        dialogTabbedPane.addTab(internationalization.getString("selector.dialogs.newGridObjectDialog.dialogTabbedPane.text.title"),sp_textElement);
        dialogTabbedPane.addTab(internationalization.getString("selector.dialogs.newGridObjectDialog.dialogTabbedPane.image.title"),sp_imageElement);
        dialogPanel.add(dialogTabbedPane, BorderLayout.CENTER);
	}

	private void activateImageTab() {
		dialogTabbedPane.setSelectedIndex(1);
		dialogTabbedPane.setEnabledAt(0,false);
	}
	
	private void activateTextTab() {
		dialogTabbedPane.setSelectedIndex(0);
		dialogTabbedPane.setEnabledAt(1,false);
	}
	
	/**
	 * Opens OptionPane.
	 */
	public void showDialog() {
		 optionPane = new FLGOptionPane(null, dialogPanel, 
				 internationalization.getString("selector.dialogs.gridObjectPropertiesDialog.title"), 
		         FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE);
		 optionPane.setVisible(true);
	}
   
	/**
	 * Returns true if user wants to add a new element;
	 * @return boolean
	 */
	public boolean insertNewElement() {
		boolean returnValue = false;
	  	if (optionPane.getReturnValue()==FLGOptionPane.CANCEL_OPTION) {
	  		returnValue = false;
	  	} else if (optionPane.getReturnValue()==FLGOptionPane.OK_OPTION) {
	  		returnValue = true;
	  	}
	  	return returnValue;
	}
	
	/**
	 * Returns true if user selected an image.
	 * @return boolean
	 */
	public boolean imageSelected() {
		if (dialogTabbedPane.getSelectedIndex()==1) {
			imageSelected = true;
		} else {
			imageSelected = false;
		}
		return imageSelected;
	}

	/**
	 * Returns name of selected image file name.
	 * @return String selectedImageFile
	 */
	public String getImageFileName() {
		return selectedFile;
	}
	
	/**
	 * Returns selected image file.
	 * @return File selectedImageFile
	 */
	public File getImageFile() {
		return imageFile;
	}
	
	/**
	 * Sets image file in preview panel.
	 * @param File imageFile
	 */
	public void setImageFile(File imageFile) {
		this.imageFile=imageFile;
		selectedFile=imageFile.getName();
		imagePreviewPanel.add(new JLabel(new ImageIcon(scaleImage(loadImage(imageFile),width,height))),BorderLayout.CENTER);
		imageSearchButton.setEnabled(true);
		imagePreviewPanel.repaint();
		imagePreviewPanel.updateUI();
	}
	
	/**
	 * Returns new element title.
	 * @return String newText
	 */
	public String getText() {
		textElementTitle = textField_textElement.getText();
		imageElementTitle = textField_imageElement.getText();
		if (imageSelected()) {
			return imageElementTitle;
		} else {
			return textElementTitle;
		}
	}
	
	/**
	 * Sets text element title.
	 * @param String title
	 */
	public void setTextElementTitle(String title) {
		textField_textElement.setText(title);
		textElementTitle = title;
		imageSelected = false;
		activateTextTab();
	}
	
	public void setTextBackgroundColor(Color color) {
		textBackgroundPanel.setBackground(color);
	}
	
	public Color getTextBackgroundColor() {
		return textBackgroundPanel.getBackground();
	}
	
	public void setImageBackgroundColor(Color color) {
		imagePreviewPanel.setBackground(color);
	}
	
	public Color getImageBackgroundColor() {
		return imagePreviewPanel.getBackground();
	}
	
	
	/**
	 * Sets text element title.
	 * @param String title
	 */
	public void setImageElementTitle(String title) {
		textField_imageElement.setText(title);
		imageElementTitle = title;
		imageSelected = true;
		activateImageTab();
	}
	
	/**
	 * Returns height.
	 * @return int height 
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns width.
	 * @return int width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns scaled image from user selection.
	 * @return Image scaledImage
	 */
	public Image getScaledImage() {
		return scaledImage;
	}
	
    private Image loadImage(File imageFileName) {
    	URL imageUrl = null;
    	try {
    		imageUrl = imageFileName.toURL();
    	} catch (Exception e) { e.printStackTrace();}
    	return FLGImageUtility.loadImageAndWait(imageUrl);
    }
    
	private Image scaleImage(Image imageToScale, int w, int h) {
        // create scaled image
		Image newImage = imageToScale;
		ImageObserver observer = new ImageObserver() {
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        };
        int height = newImage.getHeight(observer);
        int width  = newImage.getWidth(observer);
        if(imageToScale.getHeight(observer) >= imageToScale.getWidth(observer)) {
        	scaledImage = imageToScale.getScaledInstance(-1, h, Image.SCALE_SMOOTH);
        } else {
        	scaledImage = imageToScale.getScaledInstance(w, -1, Image.SCALE_SMOOTH);
        }
        return scaledImage;
    }    
}