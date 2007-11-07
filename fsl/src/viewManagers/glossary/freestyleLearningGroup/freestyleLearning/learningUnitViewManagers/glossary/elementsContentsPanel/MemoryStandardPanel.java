package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.
    glossary.elementsContentsPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import freestyleLearning.learningUnitViewAPI.FSLLearningUnitViewElementsManager;
import freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.glossary.data.xmlBindingSubclasses.FLGGlossaryElement;
import freestyleLearningGroup.independent.gui.FLGHtmlPane;

public class MemoryStandardPanel
    extends JPanel {

  public final static short CONTENT_FIRST = 0;
  public final static short TITLE_FIRST = 1;

  protected FLGMemoryContentPanel m_memoryContentPanel;
  protected short m_showMode;
  protected JPanel m_titlePanelContainer = new JPanel();
  protected JPanel m_contentPanelContainer = new JPanel();
  protected FLGHtmlPane m_titleEmptyPanel = new FLGHtmlPane();
  protected FLGHtmlPane m_contentEmptyPanel = new FLGHtmlPane();
  protected FLGHtmlPane m_titleHtmlPane = new FLGHtmlPane();
  protected FLGHtmlPane m_contentHtmlPane = new FLGHtmlPane();


  public MemoryStandardPanel(FLGMemoryContentPanel memoryContentPanel) {	
    m_memoryContentPanel = memoryContentPanel;
    setLayout(new BorderLayout(5, 5));
    LineBorder contentPanelContainerBorder =
        (LineBorder) BorderFactory.createLineBorder(Color.blue, 5);
    m_titlePanelContainer.setLayout(new CardLayout());
    m_titleHtmlPane.setEditable(false);
    m_titlePanelContainer.add("covered",
                              m_titleEmptyPanel);
    m_titlePanelContainer.add("uncovered",
                              m_titleHtmlPane);
    m_titlePanelContainer.setBorder(contentPanelContainerBorder);
    m_contentPanelContainer.setLayout(new CardLayout());
    JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
    			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.getViewport().add(m_contentHtmlPane);
    m_contentHtmlPane.setSuppressHyperlinks(true);
    m_contentHtmlPane.setEditable(false);
    m_contentPanelContainer.add("covered",
                                m_contentEmptyPanel);
    m_contentPanelContainer.add("uncovered",
                                scrollPane);
    m_contentPanelContainer.setBorder(contentPanelContainerBorder);
    m_contentEmptyPanel.setText("<html><h1><center>think!</h1></html>");
    m_contentEmptyPanel.setEditable(false);
    m_titleEmptyPanel.setText("<html><h1><center>think!</h1></html>");
    m_titleEmptyPanel.setEditable(false);
    add(m_titlePanelContainer,
        BorderLayout.NORTH);
    add(m_contentPanelContainer,
        BorderLayout.CENTER);
  }

  public void setLearningUnitViewElementsManager(
      FSLLearningUnitViewElementsManager learningUnitViewManager) {

  }

  public void setShowMode(short showMode) {
    m_showMode = showMode;
  }

  public void setGlossaryElement(FLGGlossaryElement element) throws Exception {
    coverAll();
    m_contentHtmlPane.loadFile(
        m_memoryContentPanel.getLearningUnitViewManager()
        .getLearningUnitViewElementsManager()
        .resolveRelativeFileName(
        element.getHtmlFileName(),
        element),
        true);
    m_titleHtmlPane.setText("<html><h1>"
                            + element.getTitle()
                            + "</h1></html>");
    if (m_showMode == CONTENT_FIRST)
      ( (CardLayout) m_contentPanelContainer.getLayout()).show(
          m_contentPanelContainer,
          "uncovered");
    else
      ( (CardLayout) m_titlePanelContainer.getLayout()).show(
          m_titlePanelContainer,
          "uncovered");
  }

  public void uncover() {
    if (m_showMode == TITLE_FIRST)
      ( (CardLayout) m_contentPanelContainer.getLayout()).show(
          m_contentPanelContainer,
          "uncovered");
    else
      ( (CardLayout) m_titlePanelContainer.getLayout()).show(
          m_titlePanelContainer,
          "uncovered");
  }

  public void coverAll() {
    ( (CardLayout) m_titlePanelContainer.getLayout()).show(
        m_titlePanelContainer,
        "covered");
    ( (CardLayout) m_contentPanelContainer.getLayout()).show(
        m_contentPanelContainer,
        "covered");
  }
}