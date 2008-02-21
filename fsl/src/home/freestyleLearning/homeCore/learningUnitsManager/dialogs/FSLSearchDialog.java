/* Generated by Freestyle Learning Group */

package freestyleLearning.homeCore.learningUnitsManager.dialogs;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;

import freestyleLearning.homeCore.learningUnitsManager.*;
import freestyleLearning.learningUnitViewAPI.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FSLSearchDialog {
    private JPanel componentsPanel;
    private JPanel searchComponentsPanel;
    private JPanel resultComponentsPanel;
    private JPanel resultListPanel;
    private JList resultJList;
    private JTextField tf_searchTerm;
    private JButton button_search;
    private JRadioButton rb_searchSingleTerms;
    private JRadioButton rb_searchCompleteTerm;
    private FLGInternationalization internationalization;
    private String searchString;
    private String searchStringUTF_8;
    private String originalLearningUnitDirectory;
    private String userLearningUnitDirectory;
    private File indexDirectory;
    private Vector resultList;
    private Vector resultElementList;
    private FSLLearningUnitViewsManager learningUnitViewsManager;
    private Vector foundElements;
    private boolean searchCompleteTerm;
    private boolean newSearch;
    private boolean searchingUTF_8;
    private int totalValue;
    private int proceededValue;
    private int selectedIndex;
    private FLGImageProgressDialog indexingProgessDialog;
    private String[] fileFilter = { ".xml", ".html", ".htm" };
    private Object[] resultLinks;
    private String[] targetElement;
    
    public FSLSearchDialog(FSLLearningUnitViewsManager learningUnitViewsManager, String originalLearningUnitDirectory,
        String userLearningUnitDirectory) {
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.learningUnitsManager.dialogs.internationalization",
        FSLSearchDialog.class.getClassLoader());
        this.learningUnitViewsManager = learningUnitViewsManager;
        this.originalLearningUnitDirectory = originalLearningUnitDirectory;
        this.userLearningUnitDirectory = userLearningUnitDirectory;
        buildIndependentUI();
        buildDependentUI();
    }
    
    public void createIndex(String originalLearningUnitDirectory, String userLearningUnitDirectory) {
        File originalLearningUnitDirectoryFile = new File(originalLearningUnitDirectory);
        File userLearningUnitDirectoryFile = new File(userLearningUnitDirectory);
        FLGUIUtilities.startLongLastingOperation();
        this.originalLearningUnitDirectory = originalLearningUnitDirectory;
        this.userLearningUnitDirectory = userLearningUnitDirectory;
        resultList.removeAllElements();
        indexDirectory = new File(userLearningUnitDirectory + "/index");
        indexDirectory.mkdirs();
        totalValue = (int)FLGFileUtility.fileCount(new File(originalLearningUnitDirectory), fileFilter);
        indexingProgessDialog = new FLGImageProgressDialog(null, 0, 100, 0,
            getClass().getClassLoader().getResource("freestyleLearning/homeCore/images/fsl.gif"), (Color)UIManager.get("FSLColorBlue"),
        (Color)UIManager.get("FSLColorRed"));
        indexingProgessDialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        indexingProgessDialog.setDisplayText(internationalization.getString("label.createIndex.text"));
        try {
            IndexWriter writer = new IndexWriter(indexDirectory.getAbsolutePath(), new StandardAnalyzer(), indexDirectory.exists());
            proceededValue = 0;
            if (originalLearningUnitDirectoryFile.exists()) {
                indexDocs(writer, originalLearningUnitDirectoryFile);
            }
            if (userLearningUnitDirectoryFile.exists()) {
                indexDocs(writer, userLearningUnitDirectoryFile);
            }
            writer.optimize();
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        FLGUIUtilities.stopLongLastingOperation();
        indexingProgessDialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        indexingProgessDialog.dispose();
    }
    
    private void updateIndexingProgressDialog(int proceededValue, String label) {
        final int barValue = (int)(proceededValue * 100d / totalValue);
        indexingProgessDialog.setBarValue(barValue);
        indexingProgessDialog.setDisplayText(internationalization.getString("label.createIndex.text") + " " + label);
    }
    
    public void indexDocs(IndexWriter writer, File file) throws Exception {
        if (file.isDirectory()) {
            if (!file.getName().equals("index")) {
                String[] files = file.list();
                for (int i = 0; i < files.length; i++) {
                    indexDocs(writer, new File(file, files[i]));
                }
            }
        }
        else {
            for (int i = 0; i < fileFilter.length; i++) {
                if (file.getName().endsWith(fileFilter[i])) {
                    updateIndexingProgressDialog(++proceededValue, file.getName());
                    writer.addDocument(FSLFileDocument.Document(file));
                    break;
                }
            }
        }
    }
    
    public boolean showDialog() {
//        buildDependentUI();
        String[] buttonLabels = { internationalization.getString("button.show.label"), internationalization.getString("button.close.label") };
        int returnValue = FLGOptionPane.showConfirmDialog(componentsPanel, internationalization.getString("label.searchDialog.title"),
        button_search, FLGOptionPane.OK_CANCEL_OPTION, FLGOptionPane.PLAIN_MESSAGE, buttonLabels);
        return returnValue == FLGOptionPane.OK_OPTION;
    }
    
    protected void buildDependentUI() {
        tf_searchTerm.setText(searchString);
        tf_searchTerm.requestFocus();
        tf_searchTerm.selectAll();
        rebuildList();
    }
    
    private void rebuildList() {
        if (!searchingUTF_8) {
            resultElementList.removeAllElements();
            foundElements = new Vector();
        }
        String separatorChar = System.getProperty("file.separator");
        int separatorIndex = originalLearningUnitDirectory.lastIndexOf(separatorChar);
        if (separatorIndex < 0) separatorIndex = originalLearningUnitDirectory.lastIndexOf("/");
        
        String learningUnitDirectoryName = originalLearningUnitDirectory.substring(separatorIndex + 1);
        for (int i = 0; i < resultList.size(); i++) {
            String filePath = resultList.get(i).toString();
            String subDirectoryPath = filePath.substring(filePath.lastIndexOf(learningUnitDirectoryName) +
                learningUnitDirectoryName.length() + 1);
            if (subDirectoryPath.endsWith(".html") || subDirectoryPath.endsWith(".htm")) {
                Object[] foundElement = getElementForSubDiectoryPath(subDirectoryPath);
                FSLLearningUnitViewElement element = (FSLLearningUnitViewElement)foundElement[1];
                if (element != null && !foundElements.contains(element)) {
                    resultElementList.add(foundElement[0].toString());
                    foundElements.add(element);
                }
            }
            if (subDirectoryPath.endsWith(".xml")) {
                Vector elements = getElementsForDescriptorEntry(subDirectoryPath);
                for (int j = 0; j < elements.size(); j++) {
                    Object[] foundElement = (Object[]) elements.get(j);
                    FSLLearningUnitViewElement element = (FSLLearningUnitViewElement)foundElement[1];
                    if (element != null && !foundElements.contains(element)) {
                        resultElementList.add(foundElement[0].toString());
                        foundElements.add(element);
                    }
                }
            }
        }
        if (searchingUTF_8) {
            fillLinkList();
        }
    }
    
    private void fillLinkList() {
        searchingUTF_8 = false;        
        // fill link list
        resultLinks = new Object[resultElementList.size()];
        String[] [] viewManagerIdsAndTitles = learningUnitViewsManager.getInstalledLearningUnitViewManagersIdsAndTitles();
        if (newSearch && resultElementList.size() == 0) {
            showNoResultsMessage();
            newSearch = false;
        }
        else {
            for (int i = 0; i < resultElementList.size(); i++) {
                String label = resultElementList.get(i).toString();
                String resultManager = label.substring(0, label.indexOf(" - ")).trim();
                String targetElementId = ((FSLLearningUnitViewElement)foundElements.get(i)).getId();
                String targetElementTitle = ((FSLLearningUnitViewElement)foundElements.get(i)).getTitle();
                String targetManagerId = null;
                for (int j = 0; j < viewManagerIdsAndTitles.length; j++) {
                    if (viewManagerIdsAndTitles[j] [1].equals(resultManager)) {
                        targetManagerId = viewManagerIdsAndTitles[j] [0];
                        break;
                    }
                }
                String[] resultTarget = { targetManagerId, targetElementId , targetElementTitle};
                resultLinks[i] = resultTarget;
            }
        }
        resultJList.updateUI();
    }
    
    /** if search string found in content file
     *  identify Learning Unit View Element referring to this file
     *  @return Object array containing 
     *  <ul><li> label for display list [0] 
     *      <li> element reference in [1]
     *      <li> element's manager reference [2]
     *  </ul>
     */
    public Object[] getElementForSubDiectoryPath(String subDirectoryPath) {
        Object[] foundElement = new Object[3];
        int separatorIndex = -1;
        FSLLearningUnitViewElement element = null;
        String[] [] viewManagerIdsAndTitles = learningUnitViewsManager.getInstalledLearningUnitViewManagersIdsAndTitles();
        for (int i = 0; i < viewManagerIdsAndTitles.length; i++) {
            String learningUnitViewDirectoryName = learningUnitViewsManager.getLearningUnitViewManagerDirectoryName(viewManagerIdsAndTitles[i] [0]);
            separatorIndex = subDirectoryPath.indexOf(System.getProperty("file.separator"));
            if (separatorIndex < 1) separatorIndex = 0; // subDirectoryPath.indexOf("/");
            String subDirectoryViewDirectoryName = subDirectoryPath.substring(0, separatorIndex);
            if (learningUnitViewDirectoryName.trim().equals(subDirectoryViewDirectoryName.trim())) {
                String contentFileName = subDirectoryPath.substring(separatorIndex + 1, subDirectoryPath.length());
                FSLLearningUnitViewManager manager = learningUnitViewsManager.getLearningUnitViewManager(viewManagerIdsAndTitles[i] [0]);
                if (manager != null) {
                    element = manager.findLearningUnitViewElement(contentFileName);
                    if (element != null) {
                        String elementName = viewManagerIdsAndTitles[i] [1] + " - " + element.getTitle();
                        foundElement[0] = elementName;
                        foundElement[1] = element;
                        foundElement[2] = manager;
                    }
                    break;
                }
            }
        }
        return foundElement;
    }
    
    // if search string found in descriptor file,
    // loop elements to identify referred targets
    // returns Vector containing Object arrays
    // 			containing label for display list [1]
    // 			and element reference in [2]
    private Vector getElementsForDescriptorEntry(String descriptorFileName) {
        Vector foundElements = new Vector();
        int separatorIndex = -1;
        String fileSeparator = System.getProperty("file.separator");
        String[] [] viewManagerIdsAndTitles = learningUnitViewsManager.getInstalledLearningUnitViewManagersIdsAndTitles();
        for (int i = 0; i < viewManagerIdsAndTitles.length; i++) {
            String learningUnitViewDirectoryName = learningUnitViewsManager.getLearningUnitViewManagerDirectoryName(viewManagerIdsAndTitles[i] [0]);
            separatorIndex = descriptorFileName.indexOf(fileSeparator);
            if (separatorIndex > 0) {
                String subDirectoryViewDirectoryName = descriptorFileName.substring(0, separatorIndex);
                if (learningUnitViewDirectoryName.equals(subDirectoryViewDirectoryName)) {
                    FSLLearningUnitViewManager manager =
                    learningUnitViewsManager.getLearningUnitViewManager(viewManagerIdsAndTitles[i] [0]);
                    if (manager != null) {
                        FSLLearningUnitViewElementsManager elementsManager = manager.getLearningUnitViewElementsManager();
                        if (elementsManager != null) {
                            String[] elementIds = manager.getLearningUnitViewElementsManager().getAllLearningUnitViewElementIds();
                            for (int j = 0; j < elementIds.length; j++) {
                                FSLLearningUnitViewElement element =
                                elementsManager.getLearningUnitViewElement(elementIds[j], false);
                                if (element != null && element.getTitle().toLowerCase().indexOf(searchString.toLowerCase()) >= 0) {
                                    String elementName = viewManagerIdsAndTitles[i] [1] + " - " + element.getTitle();
                                    Object[] foundElement = new Object[2];
                                    foundElement[0] = elementName;
                                    foundElement[1] = element;
                                    foundElements.add(foundElement);
                                }
                            }
                        }
                    }
                }
            }
        }
        return foundElements;
    }
    
    protected void buildIndependentUI() {
        componentsPanel = new JPanel(new BorderLayout());
        searchComponentsPanel = new JPanel(new FLGColumnLayout());
        searchComponentsPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("label.searchDialog.find.text")));
        button_search = new JButton();
        button_search.setAction(
        new AbstractAction(internationalization.getString("button.searchDialog.find.text")) {
            public void actionPerformed(ActionEvent e) {
                if (tf_searchTerm.getText().length() > 0) {
                    newSearch = true;
                    searchString = tf_searchTerm.getText();
                    String searchStringHTML = FLGHtmlUtilities.createHTMLwithEntities(searchString);
                    try {
                        File file = new File(indexDirectory + "/seachString_UTF8.txt");
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                        writer.write(searchString);
                        writer.flush();
                        writer.close();
                        
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        searchStringUTF_8 = reader.readLine();
                        reader.close();
                        System.out.println("\nsearchStringUTF_8: " + searchStringUTF_8);
                    }
                    catch(Exception ex) {
                        System.out.println("Error during search: " + ex);
                    }
                    searchingUTF_8 = false;
                    find(searchStringHTML);
                    searchingUTF_8 = true;
                    System.out.println("searching UTF-8...");
                    find(searchStringUTF_8);
                }
                else {
                    searchingUTF_8 = false;
                    FLGOptionPane.showMessageDialog(internationalization.getString("message.noSearchString.message"),
                    internationalization.getString("message.searchResults.title"), FLGOptionPane.INFORMATION_MESSAGE);
                    resultJList.removeAll();
                    resultJList.updateUI();
                }
            }
        });
        tf_searchTerm = new JTextField(20);
        tf_searchTerm.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button_search.getAction().actionPerformed(e);
            }
        });
        rb_searchCompleteTerm = new JRadioButton(internationalization.getString("label.searchDialog.radiobutton.completeTerm"));
        rb_searchSingleTerms = new JRadioButton(internationalization.getString("label.searchDialog.radiobutton.singleTerms"));
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rb_searchCompleteTerm);
        buttonGroup.add(rb_searchSingleTerms);
        rb_searchCompleteTerm.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchCompleteTerm = true;
            }
        });
        rb_searchSingleTerms.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchCompleteTerm = false;
            }
        });
        rb_searchCompleteTerm.setSelected(true);
        searchCompleteTerm = true;
        searchComponentsPanel.add(tf_searchTerm, FLGColumnLayout.LEFT);
        searchComponentsPanel.add(button_search, FLGColumnLayout.LEFTEND);
        searchComponentsPanel.add(rb_searchCompleteTerm, FLGColumnLayout.LEFTEND);
        searchComponentsPanel.add(rb_searchSingleTerms, FLGColumnLayout.LEFTEND);
        resultComponentsPanel = new JPanel(new BorderLayout());
        resultList = new Vector();
        resultElementList = new Vector();
        resultJList = new JList(resultElementList);
        resultJList.addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        selectedIndex = resultJList.getSelectedIndex();                       
                    }
                }
            });
        resultListPanel = new JPanel(new BorderLayout());
        resultListPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("border.results.title")));
        JScrollPane listPane = new JScrollPane(resultJList);
        listPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
        BorderFactory.createLoweredBevelBorder()));
        resultListPanel.add(listPane);
        resultListPanel.setPreferredSize(new Dimension(300, 200));
        resultComponentsPanel.add(resultListPanel);
        componentsPanel.add(searchComponentsPanel, BorderLayout.NORTH);
        componentsPanel.add(resultComponentsPanel, BorderLayout.CENTER);
    }
    
    public String[] getTargetElement() {
        try {
            targetElement = (String[]) resultLinks[selectedIndex];
        }
        catch(Exception e) {}
        return targetElement;
    }
    
    private String replaceSpaces(String term) {
        char[] chars = term.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case ' ': {
                    sb.append('+');
                    break;
                }
                default:
                    sb.append(chars[i]);
            }
        }
        return new String(sb);
    }
    
    /**
     * Find file names of all files with matching content
     * @param <code>termToFind</code> term to search for
     * @return <code>List</code>containing all content file names with hits
     */
    public java.util.List find(String termToFind) {
        FLGUIUtilities.startLongLastingOperation();
        button_search.setEnabled(false);
        tf_searchTerm.setEnabled(false);
        resultList.removeAllElements();
        try {
            Searcher searcher = new IndexSearcher(indexDirectory.getAbsolutePath());
            Analyzer analyzer = new StandardAnalyzer();
            String line = termToFind;
            if (searchCompleteTerm) line = replaceSpaces(line);
            Query query = QueryParser.parse(line, "contents", analyzer);
            Hits hits = searcher.search(query);
//            System.out.println(hits.length() + " total matching documents");
            if (hits.length() > 0) {
                for (int i = 0; i < hits.length(); i++) {
                    Document doc = hits.doc(i);
                    String path = doc.get("path");
                    if (path != null) {
                        resultList.addElement(path);
                    }
                }
            }
            searcher.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        FLGUIUtilities.stopLongLastingOperation();
        button_search.setEnabled(true);
        tf_searchTerm.setEnabled(true);
        buildDependentUI();
        return resultList;
    }
    
    private void showNoResultsMessage() {
        FLGOptionPane.showMessageDialog(internationalization.getString("message.nothingFound.message"),
        internationalization.getString("message.searchResults.title"), FLGOptionPane.INFORMATION_MESSAGE);
    }
}