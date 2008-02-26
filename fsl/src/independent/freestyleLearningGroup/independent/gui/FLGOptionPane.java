package freestyleLearningGroup.independent.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import freestyleLearningGroup.independent.util.FLGInternationalization;

public class FLGOptionPane extends JDialog implements ActionListener {
    public final static int YES_NO_OPTION = JOptionPane.YES_NO_OPTION;
    public final static int YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION;
    public final static int OK_CANCEL_OPTION = JOptionPane.OK_CANCEL_OPTION;
    public final static int YES_OPTION = JOptionPane.YES_OPTION;
    public final static int APPROVE_OPTION = -99;
    public final static int OK_OPTION = APPROVE_OPTION;
    public final static int NO_OPTION = JOptionPane.NO_OPTION;
    public final static int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
    public final static int DEFAULT_OPTION = JOptionPane.DEFAULT_OPTION;
    public static final int QUESTION_MESSAGE = JOptionPane.QUESTION_MESSAGE;
    public static final int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    public static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
    public static final int INFORMATION_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
    public static final int PLAIN_MESSAGE = JOptionPane.PLAIN_MESSAGE;
    private FLGInternationalization internationalization;
    private int returnValue;
    private JPanel dialogPanel;
    private JPanel centerPanel;
    private JPanel buttonPanel;
    private JButton defaultButton;
    private JButton[] buttons;
    private final int MAX_NO_BUTTONS = 3;
    private FLGDialogInputVerifier dialogInputVerifier;

    public FLGOptionPane(String message, String title, int buttonsType, int messageType) {
        super(FLGUIUtilities.getMainFrame(), true);
        internationalization = new FLGInternationalization("freestyleLearningGroup.independent.gui.internationalization",
            FLGOptionPane.class.getClassLoader());
        createAllButContent(title, buttonsType, messageType);
        JTextArea label = new JTextArea();
        label.setEditable(false);
        label.setOpaque(false);
        label.setBackground(super.getBackground());
        label.setFont(super.getFont());
        label.setLineWrap(true);
        label.setWrapStyleWord(true);
        FLGScrollPane scrollPane = new FLGScrollPane(label);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        centerPanel.add(scrollPane);
        addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    setVisible(false);
                }
            });
        label.setText(message);
    }

    public FLGOptionPane(FLGDialogInputVerifier dialogInputVerifier, Component component, String title,
        int buttonsType, int messageType) {
            this(dialogInputVerifier, component, title, buttonsType, messageType, true);
    }

    public FLGOptionPane(FLGDialogInputVerifier dialogInputVerifier, Component component, String title,
        int buttonsType, int messageType, boolean modal) {
            super(FLGUIUtilities.getMainFrame(), modal);
            this.dialogInputVerifier = dialogInputVerifier;
            internationalization = new FLGInternationalization("freestyleLearningGroup.independent.gui.internationalization",
                FLGOptionPane.class.getClassLoader());
            createAllButContent(title, buttonsType, messageType);
            centerPanel.add(component);
            pack();
            java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setLocation((int)(screenDim.getWidth() - getWidth()) / 2,
                (int)(screenDim.getHeight() - getHeight()) / 2);
        }

    public FLGOptionPane(FLGDialogInputVerifier dialogInputVerifier, Component component, String title, int buttonsType,
        int messageType, String[] buttonsDescription) {
            super(FLGUIUtilities.getMainFrame(), true);
            this.dialogInputVerifier = dialogInputVerifier;
            internationalization = new FLGInternationalization("freestyleLearningGroup.independent.gui.internationalization",
                FLGOptionPane.class.getClassLoader());
            createAllButContent(title, buttonsType, messageType, buttonsDescription);
            centerPanel.add(component);
            pack();
            java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setLocation((int)(screenDim.getWidth() - getWidth()) / 2,
                (int)(screenDim.getHeight() - getHeight()) / 2);
    }

    public FLGOptionPane(Frame frame, FLGDialogInputVerifier dialogInputVerifier, Component component, String title, int buttonsType,
        int messageType, String[] buttonsDescription) {
            super(frame, true);
            this.dialogInputVerifier = dialogInputVerifier;
            internationalization = new FLGInternationalization("freestyleLearningGroup.independent.gui.internationalization",
                FLGOptionPane.class.getClassLoader());
            createAllButContent(title, buttonsType, messageType, buttonsDescription);
            centerPanel.add(component);
            pack();
            java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            setLocation((int)(screenDim.getWidth() - getWidth()) / 2,
                (int)(screenDim.getHeight() - getHeight()) / 2);
    }

    private void createAllButContent(String title, int buttonsType, int messageType) {
        String[] buttonsDescription = new String[3];
        switch(buttonsType) {
            case YES_NO_CANCEL_OPTION:
                buttonsDescription[0] = internationalization.getString("button.yes");
                buttonsDescription[1] = internationalization.getString("button.no");
                buttonsDescription[2] = internationalization.getString("button.cancel");
                break;
            case YES_NO_OPTION:
                buttonsDescription[0] = internationalization.getString("button.yes");
                buttonsDescription[1] = internationalization.getString("button.no");
                break;
            case OK_CANCEL_OPTION:
                buttonsDescription[0] = internationalization.getString("button.ok");
                buttonsDescription[1] = internationalization.getString("button.cancel");
                break;
            case OK_OPTION:
                buttonsDescription[0] = internationalization.getString("button.ok");
                break;
            default:
                buttonsDescription[0] = internationalization.getString("button.ok");
        }
        createAllButContent(title, buttonsType, messageType, buttonsDescription);
    }

    private void createAllButContent(String title, int buttonsType, int messageType, String[] buttonsDescription) {
        setTitle(title);
        getContentPane().setLayout(new BorderLayout());
        dialogPanel = new FLGEffectPanel("FLGDialog.background", true);
        dialogPanel.setLayout(new BorderLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        getContentPane().add(dialogPanel);
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dialogPanel.add(centerPanel, BorderLayout.CENTER);
        setIcon(centerPanel, messageType);
        createButtonPanel(buttonsType, buttonsDescription);
    }

    private void createButtonPanel(int buttonsType, String[] buttonsDescription) {
        Color backgroundColor = FLGUIUtilities.BASE_COLOR4;
        UIManager.put("FLGDialog.background", backgroundColor);
        buttons = new JButton[MAX_NO_BUTTONS];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = null;
        }
        buttonPanel = new JPanel(new FLGEqualSizeLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        switch (buttonsType) {
            case YES_NO_CANCEL_OPTION:
                buttons[0] = new FLGTextButton3D(buttonsDescription[0], backgroundColor);
                buttons[1] = new FLGTextButton3D(buttonsDescription[1], backgroundColor);
                buttons[2] = new FLGTextButton3D(buttonsDescription[2], backgroundColor);
                buttons[0].setActionCommand("" + YES_OPTION);
                buttons[1].setActionCommand("" + NO_OPTION);
                buttons[2].setActionCommand("" + CANCEL_OPTION);
                break;
            case YES_NO_OPTION:
                buttons[0] = new FLGTextButton3D(buttonsDescription[0], backgroundColor);
                buttons[1] = new FLGTextButton3D(buttonsDescription[1], backgroundColor);
                buttons[0].setActionCommand("" + YES_OPTION);
                buttons[1].setActionCommand("" + NO_OPTION);
                break;
            case OK_CANCEL_OPTION:
                buttons[0] = new FLGTextButton3D(buttonsDescription[0], backgroundColor);
                buttons[1] = new FLGTextButton3D(buttonsDescription[1], backgroundColor);
                buttons[0].setActionCommand("" + OK_OPTION);
                buttons[1].setActionCommand("" + CANCEL_OPTION);
                break;
            case OK_OPTION:
                buttons[0] = new FLGTextButton3D(buttonsDescription[0], backgroundColor);
                buttons[0].setActionCommand("" + OK_OPTION);
                break;
            default:
                buttons[0] = new FLGTextButton3D(buttonsDescription[0], backgroundColor);
                buttons[0].setActionCommand("" + OK_OPTION);
        }
        buttons[0].addActionListener(this);
        buttonPanel.add(buttons[0]);
        if (buttons[1] != null) {
            buttons[1].addActionListener(this);
            buttonPanel.add(buttons[1]);
        }
        if (buttons[2] != null) {
            buttons[2].addActionListener(this);
            buttonPanel.add(buttons[2]);
        }
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
        rootPane.setDefaultButton(buttons[0]);        
        int lastButtonIndex = 2;
        while (buttons[lastButtonIndex] == null) {
            lastButtonIndex--;
        }
        if (lastButtonIndex > 0) {
            buttons[lastButtonIndex].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "cancel");
            buttons[lastButtonIndex].getActionMap().put("cancel",
                new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                        performRequestedOption(CANCEL_OPTION);
                    }
                });
        }
    }

    private void setDefaultButton(JButton button) {
        rootPane.setDefaultButton(button);
        buttons[0].getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "OK");
        buttons[0].getActionMap().put("OK",
            new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    performRequestedOption(OK_OPTION);
                }
            });
    }

    private void setIcon(JPanel panel, int messageType) {
        if (messageType != PLAIN_MESSAGE) {
            JLabel iconLabel = new JLabel();
            if (messageType == QUESTION_MESSAGE)
                iconLabel.setIcon(new ImageIcon(loadImage("optionPane_IconQuery.gif")));
            else if (messageType == WARNING_MESSAGE)
                iconLabel.setIcon(new ImageIcon(loadImage("optionPane_IconWarning.gif")));
            else if (messageType == ERROR_MESSAGE)
                iconLabel.setIcon(new ImageIcon(loadImage("optionPane_IconError.gif")));
            else if (messageType == INFORMATION_MESSAGE)
                iconLabel.setIcon(new ImageIcon(loadImage("optionPane_IconInfo.gif")));
            iconLabel.setVerticalAlignment(JLabel.TOP);
            iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            panel.add(iconLabel, BorderLayout.WEST);
        }
    }

    private Image loadImage(String imageFileName) {
        return FLGImageUtility.loadImageAndWait(FLGOptionPane.class.getClassLoader().getResource("freestyleLearningGroup/independent/gui/images/" +
            imageFileName));
    }

    public static int showConfirmDialog(String message, String title, int buttonsType, int messageType) {
        FLGOptionPane dialog = new FLGOptionPane(message, title, buttonsType, messageType);
        return showDialog(dialog);
    }

    public static int showConfirmDialog(Component component, String title, JButton defaultButton,
        int buttonsType, int messageType) {
            FLGOptionPane dialog = new FLGOptionPane(null, component, title, buttonsType, messageType);
            dialog.setDefaultButton(defaultButton);
            return showDialog(dialog);
    }

    public static int showConfirmDialog(Component component, String title, JButton defaultButton,
        int buttonsType, int messageType, String[] buttonLabels) {
            FLGOptionPane dialog = new FLGOptionPane(null, component, title, buttonsType, messageType, buttonLabels);
            dialog.setDefaultButton(defaultButton);
            return showDialog(dialog);
    }

    public static int showConfirmDialog(FLGDialogInputVerifier dialogInputVerifier, Component component, String title,
        int buttonsType, int messageType) {
            FLGOptionPane dialog = new FLGOptionPane(dialogInputVerifier, component, title, buttonsType, messageType);
            return showDialog(dialog);
    }

    public static int showConfirmDialog(FLGDialogInputVerifier dialogInputVerifier, Component component, String title,
        int buttonsType, int messageType, String[] buttonLabels) {
            FLGOptionPane dialog = new FLGOptionPane(dialogInputVerifier, component, title, buttonsType, messageType, buttonLabels);
            return showDialog(dialog);
    }

    public static int showConfirmDialog(Frame frame, FLGDialogInputVerifier dialogInputVerifier, Component component, String title,
        int buttonsType, int messageType, String[] buttonLabels) {
            FLGOptionPane dialog = new FLGOptionPane(frame, dialogInputVerifier, component, title, buttonsType, messageType, buttonLabels);
            return showDialog(dialog);
    }

    public static int showConfirmDialog(Component component, String title, int buttonsType, int messageType) {
        FLGOptionPane dialog = new FLGOptionPane(null, component, title, buttonsType, messageType);
        return showDialog(dialog);
    }

    public static int showConfirmDialog(Component component, String title, int buttonsType, int messageType, boolean modal) {
        FLGOptionPane dialog = new FLGOptionPane(null, component, title, buttonsType, messageType, modal);
        return showDialog(dialog);
    }

    public static int showConfirmDialog(Component component, String title, int buttonsType, int messageType,
        String[] buttonsDescription) {
            FLGOptionPane dialog = new FLGOptionPane(null, component, title, buttonsType, messageType, buttonsDescription);
            return showDialog(dialog);
    }

    public static int showConfirmDialog(Frame frame, Component component, String title, int buttonsType, int messageType,
        String[] buttonsDescription) {
            FLGOptionPane dialog = new FLGOptionPane(frame, null, component, title, buttonsType, messageType, buttonsDescription);
            return showDialog(dialog);
    }

    public static int showMessageDialog(String message, String title, int messageType) {
        FLGOptionPane dialog = new FLGOptionPane(message, title, DEFAULT_OPTION, messageType);
        return showDialog(dialog);
    }

    public static int showDialog(FLGOptionPane dialog) {
        java.awt.Dimension screenDim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        dialog.pack();
        dialog.setSize(dialog.getPreferredSize().width + 10, dialog.getPreferredSize().height + 50);
        dialog.setLocation((int)(screenDim.getWidth() - dialog.getWidth()) / 2,
            (int)(screenDim.getHeight() - dialog.getHeight()) / 2);
        dialog.setVisible(true);
        return dialog.returnValue;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0] || e.getSource() == buttons[1] || e.getSource() == buttons[2])
            returnValue = Integer.parseInt(e.getActionCommand());
        else
            returnValue = CANCEL_OPTION;
        performRequestedOption(returnValue);
    }

    private void performRequestedOption(int option) {
        returnValue = option;
        if (dialogInputVerifier != null && option == OK_OPTION) {
            String errorString = dialogInputVerifier.verifyInput();
            if (errorString != null) {
                String message = internationalization.getString("text.theInputInTheFollowingFieldsIsWrong") + ":" + "\n\n";
                message += errorString;
                showMessageDialog(message, internationalization.getString("text.error"), ERROR_MESSAGE);
                return;
            }
        }
        setVisible(false);
    }
    
    public void configurationChanged() {
        repaint();
    }
    
    /**
     * Carsten Fiedler modified 01.09.2005
     * Returns event values, e.g. CLOSE-Event.
     * @return int
     */
    public int getReturnValue() {
    	return returnValue;
    }

}
