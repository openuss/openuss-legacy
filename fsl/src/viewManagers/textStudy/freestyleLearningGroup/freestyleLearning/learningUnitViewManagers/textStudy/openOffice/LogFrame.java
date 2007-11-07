package freestyleLearningGroup.freestyleLearning.learningUnitViewManagers.textStudy.openOffice;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * GUI class showing a window for log messages. Is singelton.
 *
 * @author Edith Schewe
 */
public class LogFrame extends JFrame {
    private static LogFrame singleton;
    private String newline = "\n";
    private JButton clearButton = new JButton();
    private JButton closeButton = new JButton();
    private JPanel jPanel1 = new JPanel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea jTextArea1 = new JTextArea();
    
    private LogFrame() {
        super("OOo Importer Log");
        try {
            jbInit();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        
        jTextArea1.append((new java.util.Date()).toString().substring(11, 19) + " Importer started\n");
        
        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(500, 400);
        
        Dimension frameSize = this.getSize();
        
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        
        this.setLocation((screenSize.width - frameSize.width) / 2,
        (screenSize.height - frameSize.height) / 2);
    }
    
    /**
     * returns an Instance of the Singleton LogFrame
     */
    public static LogFrame getInstance() {
        if (singleton == null) {
            singleton = new LogFrame();
        }
        
        return singleton;
    }
    
    /**
     * appends an line to the log output. It is extended by the actual time.
     *
     * @param lineToAdd line to add ;-)
     */
    public void add(String lineToAdd) {
        final String string = lineToAdd;
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    jTextArea1.append(newline + (new java.util.Date()).toString().substring(11, 19) + " " + string);
                    jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
                }
            });
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * appends a String at the end of the last Line.
     *
     * @param lineToAdd line to add ;-)
     */
    public void append(String lineToAdd) {
        final String string = lineToAdd;
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    jTextArea1.append(string);
                    jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
                }
            });
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * Clears the Log-Frame
     */
    public void clearLog() {
        jTextArea1.setText("");
    }
    
    private void jbInit() throws Exception {
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeButton_actionPerformed(e);
            }
        });
        closeButton.setText("close");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearButton_actionPerformed(e);
            }
        });
        clearButton.setText("clear Log");
        
        //  jTextArea1.setLineWrap(true);
        jTextArea1.setEditable(false);
        jTextArea1.setColumns(40);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(20);
        jPanel1.add(closeButton, null);
        jPanel1.add(clearButton, null);
        this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTextArea1, null);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
    }
    
    void closeButton_actionPerformed(ActionEvent e) {
        this.dispose();
    }
    
    void clearButton_actionPerformed(ActionEvent e) {
        this.clearLog();
    }
}
