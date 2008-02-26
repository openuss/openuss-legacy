package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.elementsContentsPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import freestyleLearningGroup.independent.util.FLGInternationalization;

public class MemoryStatisticPanel extends JPanel{

  protected FLGMemoryContentPanel m_memoryContentPanel;
  protected FLGInternationalization m_internationalization;
  Border m_border;
  GridBagLayout m_layout = new GridBagLayout();
  JLabel m_sessionFinishedLabel = new JLabel();
  JLabel m_evaluationLabel = new JLabel();
  JLabel m_avgThinkTimeLabel = new JLabel();
  JTextField m_avgThinkTimeTextField = new JTextField();

  public MemoryStatisticPanel(FLGMemoryContentPanel memoryContentPanel) {
	m_internationalization = new FLGInternationalization(
			"freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.elementsContentsPanel.internationalization", getClass().getClassLoader());
    m_memoryContentPanel = memoryContentPanel;
    jbInit();
  }

  protected void jbInit() {
    m_sessionFinishedLabel.setText(m_internationalization.getString("MemoryStatisticPanel.sessionFinishedMsg")); //$NON-NLS-1$
    this.setBackground(Color.white);
    this.setBorder(m_border);
    m_border = BorderFactory.createLineBorder(Color.blue,5);
    this.setDebugGraphicsOptions(0);
    this.setLayout(m_layout);
    m_evaluationLabel.setFont(new java.awt.Font("Dialog", 1, 24));
    m_evaluationLabel.setText(m_internationalization.getString("MemoryStatisticPanel.evalutationLabel")); //$NON-NLS-1$
    m_avgThinkTimeLabel.setText(m_internationalization.getString("MemoryStatisticPanel.avgThinkTimeLabel")); //$NON-NLS-1$
    m_avgThinkTimeTextField.setPreferredSize(new Dimension(60, 20));
    m_avgThinkTimeTextField.setEditable(false);
    m_avgThinkTimeTextField.setText("");
    this.add(m_sessionFinishedLabel,   new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(m_evaluationLabel,    new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(m_avgThinkTimeLabel,   new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.add(m_avgThinkTimeTextField,   new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

  }

  public void computeStatistic() {
    int elementsTotal = m_memoryContentPanel.getCurrentSession().size();
    long thinkTime = m_memoryContentPanel.getThinkTime();
    m_avgThinkTimeTextField.setText("" + (thinkTime / (elementsTotal * 1000)));
  }
}