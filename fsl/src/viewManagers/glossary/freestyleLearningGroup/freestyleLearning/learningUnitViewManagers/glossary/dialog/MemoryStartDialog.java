package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import freestyleLearningGroup.independent.gui.FLGOptionPane;
import freestyleLearningGroup.independent.gui.FLGTextButton3D;
import freestyleLearningGroup.independent.util.FLGInternationalization;

/**
 * MemoryStartDialog
 * @author Freestyle Learning Group
 */
public class MemoryStartDialog extends JDialog implements ActionListener {
	private FLGInternationalization internationalization;
	JPanel m_dialogPanel = new JPanel();
	BorderLayout m_dialogPanelLayout = new BorderLayout();
	public static final int NUMBER_OF_CATEGORIES = 4;
	public static final int CATEGORY_STRATEGY = 0;
	public static final int SINCE_STRATEGY = 1;
	JPanel m_mainPanel = new JPanel();
	JPanel m_buttonPanel = new JPanel();
	private FLGTextButton3D m_okButton;
	private FLGTextButton3D m_cancelButton;
	GridBagLayout m_mainPanelLayout = new GridBagLayout();
	JPanel m_strategyPanel = new JPanel();
	JPanel m_showModePanel = new JPanel();
	TitledBorder m_strategyBorder;
	TitledBorder m_showModeBorder;
	GridBagLayout m_strategyPanelLayout = new GridBagLayout();
	GridBagLayout m_showModePanelLayout = new GridBagLayout();
	JRadioButton m_categoryRadioButton = new JRadioButton();
	JRadioButton m_notRepeatedSinceRadioButton = new JRadioButton();
	JComboBox m_categoryComboBox = new JComboBox();
	JTextField m_notRepeatedSinceTextField = new JTextField();
	JRadioButton m_titleFirstRadioButton = new JRadioButton();
	JRadioButton m_contentFirstRadioButton = new JRadioButton();
	ButtonGroup m_strategyButtonGroup = new ButtonGroup();
	ButtonGroup m_showModeButtonGroup = new ButtonGroup();
	Border m_mainPanelBorder;
	protected boolean m_canceled = true;

	/**
	 * Constructor
	 * @param <code>Frame</code> frame
	 * @param <code>String</code> title
	 * @param <code>boolean</code> modal
	 */
	public MemoryStartDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
			internationalization = new FLGInternationalization("freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.dialog.internationalization",
					getClass().getClassLoader());
			m_okButton = new FLGTextButton3D(internationalization.getString("memoryStartDialog.okButton"),
					(Color)UIManager.get("FLGDialog.background"));
			m_cancelButton = new FLGTextButton3D(internationalization.getString("memoryStartDialog.cancelButton"),
					(Color)UIManager.get("FLGDialog.background"));
		try {
			jbInit();
			pack();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Constructor.
	 * @param <code>Frame</code> parent
	 */
	protected MemoryStartDialog(Frame parent) {
		this(parent,"",true);
		this.setTitle(internationalization.getString("memoryStartDialog.title"));
		m_okButton.addActionListener(this);
		m_cancelButton.addActionListener(this);
		m_categoryComboBox.addItem(internationalization.getString("memoryStartDialog.categoryComboBox.Start"));
		m_categoryComboBox.addItem(internationalization.getString("memoryStartDialog.categoryComboBox.Light"));
		m_categoryComboBox.addItem(internationalization.getString("memoryStartDialog.categoryComboBox.Medium"));
		m_categoryComboBox.addItem(internationalization.getString("memoryStartDialog.categoryComboBox.Premium"));
		m_categoryComboBox.setSelectedItem(internationalization.getString("memoryStartDialog.categoryComboBox.Start"));
	}

	private void jbInit() throws Exception {
	  	Color backGroundColor = (Color) UIManager.get("FLGDialog.background");
	  	m_buttonPanel.setBackground(backGroundColor);
	  	m_strategyBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),internationalization.getString("memoryStartDialog.memoryStrategy"));
	    m_showModeBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),internationalization.getString("memoryStartDialog.memoryMode"));
	    m_mainPanelBorder = BorderFactory.createLineBorder(backGroundColor,5);
	    m_dialogPanel.setLayout(m_dialogPanelLayout);
	    m_okButton.setActionCommand("ok");
	    m_cancelButton.setActionCommand("cancel");
	    m_mainPanel.setLayout(m_mainPanelLayout);
	    m_strategyPanel.setBorder(m_strategyBorder);
	    m_strategyPanel.setLayout(m_strategyPanelLayout);
	    m_showModePanel.setBorder(m_showModeBorder);
	    m_showModePanel.setLayout(m_showModePanelLayout);
	    m_strategyBorder.setTitle(internationalization.getString("memoryStartDialog.strategyBorder"));
	    m_categoryRadioButton.setSelected(true);
	    m_categoryRadioButton.setText(internationalization.getString("memoryStartDialog.categoryBorder"));
	    m_notRepeatedSinceRadioButton.setText(internationalization.getString("memoryStartDialog.notRepeatedSinceRadioButton"));
	    m_notRepeatedSinceTextField.setPreferredSize(new Dimension(150, 21));
	    m_notRepeatedSinceTextField.setText("");
	    m_categoryComboBox.setPreferredSize(new Dimension(150, 21));
	    m_titleFirstRadioButton.setSelected(true);
	    m_titleFirstRadioButton.setText(internationalization.getString("memoryStartDialog.titleFirstRadioButton"));
	    m_contentFirstRadioButton.setText(internationalization.getString("memoryStartDialog.contentFirstRadioButton"));
	    m_mainPanel.setBorder(m_mainPanelBorder);
	    getContentPane().add(m_dialogPanel);
	    m_dialogPanel.add(m_mainPanel,BorderLayout.CENTER);
	    m_dialogPanel.add(m_buttonPanel,BorderLayout.SOUTH);
	    m_buttonPanel.add(m_okButton,null);
	    m_buttonPanel.add(m_cancelButton,null);
	    m_mainPanel.add(m_strategyPanel,new GridBagConstraints(0, 0, 1, 1, 1.0, 0.5
	            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	    m_mainPanel.add(m_showModePanel,   new GridBagConstraints(0, 1, 1, 1, 1.0, 0.5
	            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	    m_strategyPanel.add(m_categoryRadioButton,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
	    m_strategyPanel.add(m_notRepeatedSinceRadioButton,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
	    m_strategyPanel.add(m_categoryComboBox,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
	    m_strategyPanel.add(m_notRepeatedSinceTextField,   new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
	    m_showModePanel.add(m_titleFirstRadioButton,    new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
	    m_showModePanel.add(m_contentFirstRadioButton,     new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
	    m_strategyButtonGroup.add(m_categoryRadioButton);
	    m_strategyButtonGroup.add(m_notRepeatedSinceRadioButton);
	    m_showModeButtonGroup.add(m_titleFirstRadioButton);
	    m_showModeButtonGroup.add(m_contentFirstRadioButton);
	  }
	
	  public int getCategory() {
	    return m_categoryComboBox.getSelectedIndex();
	  }
	
	  public int getStrategy() {
	    return m_notRepeatedSinceRadioButton.isSelected()
	        ? SINCE_STRATEGY
	        : CATEGORY_STRATEGY;
	  }
	
	  public Date getNotRepeatedSince() {
	    DateFormat dateFormat = DateFormat.getDateInstance(
	        DateFormat.SHORT);
	    try {
	      return dateFormat.parse(m_notRepeatedSinceTextField.getText());
	    }
	    catch (ParseException ex) {
	      // This won't happen as we checked the sanity first below.
	      return null;
	    }
	  }
	
	  public boolean isCanceled() {
	    return m_canceled;
	  }
	
	  public static MemoryStartDialog showStartDialog() {
	  	return showStartDialog(null);
	  }
	  	
	  public static MemoryStartDialog showStartDialog(Frame parent) {
	    MemoryStartDialog dialog = new MemoryStartDialog(parent);
	    Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
	    dialog.pack();
	    dialog.setSize(dialog.getPreferredSize().width + 10,
	    		dialog.getPreferredSize().height);
	    dialog.setLocation(
	    		(int)(screenDim.getWidth() - dialog.getWidth()) / 2,
	    		(int)(screenDim.getHeight() - dialog.getHeight()) / 2);
	    dialog.show();
	    return dialog;
	  }
	
	  public boolean isContentFirst() {
	    return m_contentFirstRadioButton.isSelected();
	  }
	
	  public void actionPerformed(ActionEvent actionEvent) {
	    String actionCommand = actionEvent.getActionCommand();
	    if (actionCommand.equals("ok") && checkSanity()) {
	    	m_canceled = false;
	      setVisible(false);
	      dispose();
	    }
	    else if (actionCommand.equals("cancel")) {
	      setVisible(false);
	      dispose();
	    }
	  }
	
	  private boolean checkSanity() {
	  	if (m_categoryRadioButton.isSelected())
	  		return true;
	    DateFormat dateFormat = DateFormat.getDateInstance(
	        DateFormat.SHORT);
	    try {
	      dateFormat.parse(m_notRepeatedSinceTextField.getText());
	    }
	    catch (ParseException ex) {
	      FLGOptionPane.showMessageDialog(
	          internationalization.getString("memoryStartDialog.checkSanity.optionPane"),
	          internationalization.getString("memoryStartDialog.checkSanity.optionPane"),
	          FLGOptionPane.INFORMATION_MESSAGE);
	      return false;
	    }
	    return true;
	  }
}