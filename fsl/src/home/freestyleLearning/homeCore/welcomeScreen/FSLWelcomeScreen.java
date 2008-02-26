/*
 * FSLWelcomeScreenDialog.java
 *
 * Created on 4. November 2005, 19:52
 */

package freestyleLearning.homeCore.welcomeScreen;

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

import freestyleLearning.homeCore.*;
import freestyleLearning.homeCore.welcomeScreen.data.xmlBinding.*;
import freestyleLearning.homeCore.learningUnitsManager.*;
import freestyleLearning.learningUnitViewAPI.events.learningUnitEvent.*;
import freestyleLearningGroup.independent.gui.*;
import freestyleLearningGroup.independent.util.*;

public class FSLWelcomeScreen extends JDialog implements FSLLearningUnitEventGenerator {
    
    private FSLWelcomeScreenContentPanel welcomeContentPane;
    private FLGInternationalization internationalization;
    private FLGTextButton3D buttonOK;
    private JCheckBox checkBox_showOnStartup;
    private HyperlinkListener hyperlinkListener;
    private URL helpUrl;
    private boolean editMode;
    private File htmlFile;
    private final String WELCOME_SCREEN_DIRECTORYNAME = "welcomeScreen";
    private java.util.List learningUnitEventListeners;
    private FSLLearningUnitsManager learningUnitsManager;
    private JFrame fslFrame;
    private File learningUnitsDirectory;
    
    public FSLWelcomeScreen(JFrame fslFrame, FSLLearningUnitsManager learningUnitsManager, File learningUnitsDirectory) {
        super(fslFrame, true);
        internationalization = new FLGInternationalization("freestyleLearning.homeCore.welcomeScreen.internationalization",
            FSLWelcomeScreen.class.getClassLoader());
        this.learningUnitsManager = learningUnitsManager;
        this.learningUnitsDirectory = learningUnitsDirectory;
        this.fslFrame = fslFrame;
        learningUnitEventListeners = new ArrayList();
        java.awt.Dimension screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int pos_x = (int)(screenDimension.width / 3. / 2);
        int pos_y = (int)(screenDimension.height / 3. / 2);
        addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
               new FSLWelcomeScreen.CloseAction().actionPerformed(null);
           }
        });
        setSize(screenDimension.width * 2 / 3, screenDimension.height * 2 / 3);
        setLocation((int)(screenDimension.width - getWidth()) / 2, (int)(screenDimension.height - getHeight()) / 2);
        setTitle(internationalization.getString("welcomeFrame.title"));
        editMode = false;
        buildIndependentUI();
        buildDependentUI();
    }
    
    public void setVisible(boolean visible) {
        // overridden to prevent showing welcome screen before mainframe
        if (visible) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (!fslFrame.isShowing()) {
                        try {
                            Thread.sleep(100);
                        }
                        catch(InterruptedException e) { }
                    }
                    FSLWelcomeScreen.super.setVisible(true);
                }
            });
            t.start();
        }
        else {
            super.setVisible(false);
        }
    }
    
    public void setDisplayOnStartup(boolean displayOnStartup) {
        checkBox_showOnStartup.setSelected(displayOnStartup);
        learningUnitsManager.setDisplayWelcomeScreen(checkBox_showOnStartup.isSelected());
    }

    public static void main(String[] args) {
        final JFrame owner = new JFrame("test");
        owner.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        owner.setSize(200,100);
        FSLWelcomeScreen ws = new FSLWelcomeScreen(owner, null, new File("D:/Java/FSL/dev/bin/learningUnits"));
        ws.setVisible(true);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1009);
                }
                catch(InterruptedException e) { }
                owner.setVisible(true);
            }
        });
        t.start();
    }
    
    protected void buildIndependentUI() {
        // html content pane
        welcomeContentPane = new FSLWelcomeScreenContentPanel();
        welcomeContentPane.init(this, new FSLWelcomeScreen.FSLWelcomeScreen_HyperlinkAdapter(), editMode, learningUnitsDirectory); 
        welcomeContentPane.buildIndependentUI();
        
        // configuation line
        JPanel configurationPanel = new JPanel(new FLGColumnLayout());
        configurationPanel.setOpaque(false);
        configurationPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        checkBox_showOnStartup = new JCheckBox(internationalization.getString("checkbox.showOnStartup.label"), true);
        checkBox_showOnStartup.setOpaque(false);
        checkBox_showOnStartup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                learningUnitsManager.setDisplayWelcomeScreen(checkBox_showOnStartup.isSelected());
            }
        });
        configurationPanel.add(checkBox_showOnStartup, FLGColumnLayout.LEFTEND);
        
        // Content Panel contains html pane and configuration line
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(welcomeContentPane, BorderLayout.CENTER);
        contentPanel.add(configurationPanel, BorderLayout.SOUTH);
        
        // Button panel (bottom)
        JPanel welcomeButtonPanel = createButtonPanel();

        // main panel contains content- and button panel
        JPanel mainPanel = new FLGEffectPanel("FLGDialog.background", false);
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(welcomeButtonPanel, BorderLayout.SOUTH);
        mainPanel.setBorder(BorderFactory.createLineBorder(FLGUIUtilities.BASE_COLOR4, 10));
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        JMenu menu = new JMenu();
        JMenuItem item = new JMenuItem("Switch Edit Mode");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchEditMode();
            }
        });
        item.setAccelerator(KeyStroke.getKeyStroke('E', KeyEvent.CTRL_MASK));
        menu.add(item);
        JMenuBar menubar = new JMenuBar();
        menubar.add(menu);
        setJMenuBar(menubar);        
    }
    
    public void buildDependentUI() {
        welcomeContentPane.buildDependentUI();
    }

    private void switchEditMode(boolean editMode) {
        this.editMode = editMode;
        FSLLearningUnitEvent event = FSLLearningUnitEvent.createLearningUnitEditModeChangedEvent(editMode);
        fireLearningUnitEvent(event);
        buildDependentUI();
    }
    
    private void switchEditMode() {
        switchEditMode(!editMode);
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new FLGEffectPanel("FLGDialog.background", false);
        buttonPanel.setOpaque(false);
        buttonOK = new FLGTextButton3D(internationalization.getString("button.close.label"), (Color)UIManager.get("FLGDialog.background"));
        buttonOK.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                }
            });
        buttonPanel.add(buttonOK);
        buttonOK.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ok");
        buttonOK.getActionMap().put("ok", new CloseAction());
        buttonOK.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        buttonOK.getActionMap().put("cancel", new CloseAction());
        return buttonPanel;
    }
    
    public void addLearningUnitListener(FSLLearningUnitListener listener) {
        if (!learningUnitEventListeners.contains(listener)) {
            learningUnitEventListeners.add(listener);
        }
    }
    
    public void removeLearningUnitListener(FSLLearningUnitListener listener) {
        learningUnitEventListeners.remove(listener);
    }
    
    public void fireLearningUnitEvent(FSLLearningUnitEvent event) {
        for (int i = 0; i < learningUnitEventListeners.size(); i++) {
            ((FSLLearningUnitListener)learningUnitEventListeners.get(i)).learningUnitEditModeChanged(event);
        }
    }
    
    public void requestEditMode(boolean enableEditMode) {
        switchEditMode(enableEditMode);
    }
    
    private class CloseAction extends AbstractAction {
        public void actionPerformed(ActionEvent actionEvent) {
            switchEditMode(false);
            setVisible(false);
        }
    }

    private class FSLWelcomeScreen_HyperlinkAdapter implements HyperlinkListener {
        public void hyperlinkUpdate(HyperlinkEvent e) {
            if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
            }
            if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
            }
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                String linkId = e.getURL().toString().substring(e.getURL().toString().lastIndexOf('l'));
                String[] linkTargetIds = welcomeContentPane.getLinkTargetIds(linkId);
                learningUnitsManager.followLearningUnitViewElementLink(linkTargetIds[0], linkTargetIds[1], linkTargetIds[2]);
                FSLWelcomeScreen.this.setVisible(false);
            }
        }
    }
}
