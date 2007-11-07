package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * FLGSelectorStartDialog.
 * Dialog for Selector Start Configuration.
 * @author Carsten Fiedler
 */
public class FLGSelectorStartDialog {
	private FLGInternationalization internationalization;
	private FLGOptionPane optionPane;
	private JPanel configurationPanel;
	private int width;
	private int height;
	private int gameSpeed;
	private double gameLength;
	
	/**
	 * Initilizes Dialog.
	 * @param width
	 * @param height
	 * @param gameSpeed
	 * @param gameLength
	 */
	public void init(int width, int height, int gameSpeed, double gameLength) {
		this.width = width;
		this.height = height;
		this.gameSpeed = gameSpeed;
		this.gameLength = gameLength;
		buildIndependentUI();
	}
	
	/**
	 * Builds independent UI.
	 */
	public void buildIndependentUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEtchedBorder());
		// build configuration Panel
		internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.selector.dialogs.internationalization",
				getClass().getClassLoader());
		configurationPanel = new JPanel();
		configurationPanel.setLayout(new FLGColumnLayout());
		// slider for playgroundsize
		JLabel playGroundSizeLabel = new JLabel(internationalization.getString("selector.dialogs.startDialog.playGroundSizeLabel"));
		playGroundSizeLabel.setBorder(new EmptyBorder(5,5,5,5));
		configurationPanel.add(playGroundSizeLabel,FLGColumnLayout.CENTEREND);
		JSlider playGroundSizeSlider = new JSlider(1,4);
		playGroundSizeSlider.setBorder(new EmptyBorder(5,5,5,5));
		playGroundSizeSlider.setMajorTickSpacing(1);
		playGroundSizeSlider.setPaintLabels(true);
		playGroundSizeSlider.setSnapToTicks(true);
		playGroundSizeSlider.setPaintTicks(true);
		java.util.Dictionary table = playGroundSizeSlider.getLabelTable();
		table.put(new Integer(1),new JLabel(internationalization.getString("selector.dialogs.startDialog.playGroundSizeSilder.33")));
		table.put(new Integer(2),new JLabel(internationalization.getString("selector.dialogs.startDialog.playGroundSizeSilder.44")));
		table.put(new Integer(3),new JLabel(internationalization.getString("selector.dialogs.startDialog.playGroundSizeSilder.66")));
		table.put(new Integer(4),new JLabel(internationalization.getString("selector.dialogs.startDialog.playGroundSizeSilder.88")));
		playGroundSizeSlider.setLabelTable(table);
		if (width==3) {playGroundSizeSlider.setValue(1);}
		if (width==4) {playGroundSizeSlider.setValue(2);}
		if (width==6) {playGroundSizeSlider.setValue(3);}
		if (width==8) {playGroundSizeSlider.setValue(4);}
		playGroundSizeSlider.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
		    	JSlider sl = (JSlider)e.getSource();
		    	int option = sl.getValue();
		    	switch(option) { 
		    		case 1 :  {
		    			width = 3;
		    			height = 3;
		    			break;
		    		}
		    		case 2 :  {
		    			width = 4;
		    			height = 4;
		    			break;
		    		}
		    		case 3 :  {
		    			width = 6;
		    			height = 6;
		    			break;
		    		}
		    		case 4 :  {
		    			width = 8;
		    			height = 8;
		    			break;
		    		}
		    	}
		    }
		});
		configurationPanel.add(playGroundSizeSlider,FLGColumnLayout.CENTEREND);
		// slider for image speed, easy, normal, expert mode
		JLabel playSpeedLabel = new JLabel(internationalization.getString("selector.dialogs.startDialog.playSpeedSlider")); 
		playSpeedLabel.setBorder(new EmptyBorder(5,5,5,5));
		configurationPanel.add(playSpeedLabel,FLGColumnLayout.CENTEREND);
		JSlider playSpeedSilder = new JSlider(1,3);
		playSpeedSilder.setBorder(new EmptyBorder(5,5,5,5));
		playSpeedSilder.setMajorTickSpacing(1);
		playSpeedSilder.setPaintLabels(true);
		playSpeedSilder.setSnapToTicks(true);
		playSpeedSilder.setPaintTicks(true);
		table = playSpeedSilder.getLabelTable();
		table.put(new Integer(1),new JLabel(internationalization.getString("selector.dialogs.startDialog.playSpeedSlider.easy")));
		table.put(new Integer(2),new JLabel(internationalization.getString("selector.dialogs.startDialog.playSpeedSlider.normal")));
		table.put(new Integer(3),new JLabel(internationalization.getString("selector.dialogs.startDialog.playSpeedSlider.expert")));
		playSpeedSilder.setLabelTable(table);
		
		if (gameSpeed==3) { playSpeedSilder.setValue(1); gameSpeed=1; }
		if (gameSpeed==2) { playSpeedSilder.setValue(2); gameSpeed=2; }
		if (gameSpeed==1) { playSpeedSilder.setValue(3); gameSpeed=3; }
		
		playSpeedSilder.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
		    	JSlider sl = (JSlider)e.getSource();
		    	gameSpeed = sl.getValue();
		    }
		});
		configurationPanel.add(playSpeedSilder,FLGColumnLayout.CENTEREND);
		// slider for game length, max. is 5 minutes 
		JLabel playLengthLabel = new JLabel(internationalization.getString("selector.dialogs.startDialog.playLengthSlider"));
		playLengthLabel.setBorder(new EmptyBorder(5,5,5,5));
		configurationPanel.add(playLengthLabel,FLGColumnLayout.CENTEREND);
		JSlider playLengthSilder = new JSlider(1,4);
		playLengthSilder.setBorder(new EmptyBorder(5,5,5,5));
		playLengthSilder.setMajorTickSpacing(1);
		playLengthSilder.setPaintLabels(true);
		playLengthSilder.setSnapToTicks(true);
		playLengthSilder.setPaintTicks(true);
		
		table = playLengthSilder.getLabelTable();
		table.put(new Integer(1),new JLabel("30"));
		table.put(new Integer(2),new JLabel("60"));
		table.put(new Integer(3),new JLabel("90"));
		table.put(new Integer(4),new JLabel("120"));
		playLengthSilder.setLabelTable(table);
		
		if (gameLength==0.5) {playLengthSilder.setValue(1);}
		if (gameLength==1.0) {playLengthSilder.setValue(2);}
		if (gameLength==1.5) {playLengthSilder.setValue(3);}
		if (gameLength==2.0) {playLengthSilder.setValue(4);}
		
		playLengthSilder.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
		    	JSlider sl = (JSlider)e.getSource();
		    	gameLength = (double) (sl.getValue()) / 2.0;
		    }
		});
		configurationPanel.add(playLengthSilder,FLGColumnLayout.CENTEREND);
		mainPanel.add(configurationPanel,BorderLayout.CENTER);
		// init OptionPane
     	String[] buttonsDescription = new String[2];
    	buttonsDescription[0] = internationalization.getString("selector.dialogs.startDialog.optionPane.startButton");
		buttonsDescription[1] = internationalization.getString("selector.dialogs.startDialog.optionPane.cancelButton");
		optionPane = new FLGOptionPane(null,mainPanel,
				internationalization.getString("selector.dialogs.startDialog.dialogTitle"),
          		FLGOptionPane.OK_CANCEL_OPTION,FLGOptionPane.PLAIN_MESSAGE,buttonsDescription);
		optionPane.setVisible(true);
	}
	
	/**
	 * Returns OptionPane decision.
	 */
	public boolean userWantsToPlay() {
		boolean userWantsToPlay=false;
		if (optionPane.getReturnValue()==FLGOptionPane.OK_OPTION){
			userWantsToPlay=true;
		}
		return userWantsToPlay;
	}
	
	/**
	 * Returns selected playground height.
	 * @return int height
	 */
	public int getSelectedPlayGroundHeight() {
		return height;
	}
	
	/**
	 * Returns selected playground width.
	 * @return int width
	 */
	public int getSelectedPlayGroundWidth() {
		return width;
	}
	
	/**
	 * Returns selected game speed.
	 * 1 : easy
	 * 2 : normal
	 * 3 : heavy
	 * @return int gamespeed
	 */
	public int getSelectedGameSpeed() {
		if (gameSpeed==1){
			return 3;
		} else if (gameSpeed==3) {
			return 1;
		} else {
			return 2;
		}
	}
	
	/**
	 * Returns selected play time in minutes.
	 * @return int playtime;
	 */
	public double getSelectedGameLength() {
		return gameLength;
	}
}
