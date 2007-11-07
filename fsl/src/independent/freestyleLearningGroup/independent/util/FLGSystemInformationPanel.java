/*
 * FLGSystemInformationPanel.java
 *
 * Created on 16. September 2003, 13:13
 */

package freestyleLearningGroup.independent.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import freestyleLearningGroup.independent.gui.FLGColumnLayout;
import freestyleLearningGroup.independent.gui.FLGImageUtility;
import freestyleLearningGroup.independent.gui.FLGUIUtilities;
import freestyleLearningGroup.independent.plotter.FLGBarChartPiled2D;
import freestyleLearningGroup.independent.plotter.FLGData2D;
import freestyleLearningGroup.independent.plotter.FLGDataGroup2D;
import freestyleLearningGroup.independent.plotter.FLGPlotter2D;
import freestyleLearningGroup.independent.plotter.FLGPoint2D;
import freestyleLearningGroup.independent.plotter.FLGPolygon2D;
import freestyleLearningGroup.independent.plotter.FLGPolygonGroup2D;

/**
 * @author Mirko Wahn, Steffen Wachenfeld
 */
public class FLGSystemInformationPanel extends JPanel {
    public static Color defaultBarColor_memoryUsed = Color.red;
    public static Color defaultBarColor_memoryFree = Color.green;
    public static Color defaultHistoryColor_memoryUsed = Color.red;
    public static Color defaultHistoryColor_memoryFree = new Color(0,196,0);
    private Color barColor_memoryUsed;
    private Color barColor_memoryFree;
    private Color historyColor_memoryUsed;
    private Color historyColor_memoryFree;
    private final int NO_HISTORY_PLOTS = 60;
    private Color topColor = FLGUIUtilities.BASE_COLOR3;
    private Color bottomColor = FLGUIUtilities.BASE_COLOR1;    
    private FLGInternationalization internationalization;
    private FLGBarChartPiled2D barChart;
    private java.util.TimerTask timerTask;
    private java.util.Timer timer;
    private JLabel labelSystemInfo;
    private JPanel historyPanel;
    private FLGData2D[] data2D;
    private FLGPoint2D[] freeMemoryPoints;
    private FLGPoint2D[] allocatedMemoryPoints;
    private FLGPoint2D[] maxMemoryPoints;
    private FLGPlotter2D historyPlot;
    private FLGPolygonGroup2D polygonGroup;
    private FLGPolygon2D freeMemoryPolygon;
    private FLGPolygon2D allocatedMemoryPolygon;
    private FLGPolygon2D maxMemoryPolygon;
    private JLabel label_free;
    private JLabel label_used;
    private JLabel label_reserved;
    private JLabel label_max;
    private JLabel label_javaName;
    private JButton button_free;
    private JLabel label_gc;
    private int scaleIndex = 0;
    private ImageIcon buttonImage;

    public FLGSystemInformationPanel(Color barColor_memoryUsed, Color  barColor_memoryFree, 
        Color historyColor_memoryUsed, Color historyColor_memoryFree) {
        this.barColor_memoryFree = barColor_memoryFree;
        this.barColor_memoryUsed = barColor_memoryUsed;
        this.historyColor_memoryFree = historyColor_memoryFree;
        this.historyColor_memoryUsed = historyColor_memoryUsed;
        internationalization = new FLGInternationalization("freestyleLearningGroup.independent.util.internationalization",
            getClass().getClassLoader());
        initComponents();
        timerTask = new java.util.TimerTask(){
            public void run(){
                refresh();
            }
        };
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 500);
    }

    public FLGSystemInformationPanel() {
        this(defaultBarColor_memoryUsed, defaultBarColor_memoryFree, defaultHistoryColor_memoryUsed, defaultHistoryColor_memoryFree);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JTabbedPane graphicPane = new JTabbedPane();
        graphicPane.setOpaque(false);
        graphicPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // Bar chart panel
        JPanel barChartPanel = new JPanel(new BorderLayout());
        FLGDataGroup2D[] dataGroup = new FLGDataGroup2D[3];
        data2D = new FLGData2D[dataGroup.length];
        for (int i = 0; i < dataGroup.length; i++) {
            dataGroup[i] = new FLGDataGroup2D();
            data2D[i] = new FLGData2D("", 0);
            dataGroup[i].add(data2D[i]);
        }
        data2D[0].setValue(Runtime.getRuntime().totalMemory()/1024 - Runtime.getRuntime().freeMemory()/1024);
        data2D[1].setValue(Runtime.getRuntime().freeMemory()/1024);
        data2D[2].setValue(Runtime.getRuntime().maxMemory()/1024 - Runtime.getRuntime().totalMemory()/1024);

        String[] legendNames = { internationalization.getString("label.systemInfo.free.text"),
                                 internationalization.getString("label.systemInfo.allocated.text"),
                                 internationalization.getString("label.systemInfo.max.text") };
        Color[] colors = {barColor_memoryUsed, barColor_memoryFree, Color.white};
        barChart = new FLGBarChartPiled2D(dataGroup, colors, legendNames, "", "kB", false, false, false);
        barChart.setPaintWithGradient(true);
        barChart.setMaxNoAxisLabels(6);
        barChart.setDrawBarBorders(false);
        barChartPanel.add(barChart, BorderLayout.CENTER);

        // history Panel
        historyPanel = new JPanel(new BorderLayout());
        freeMemoryPolygon = new FLGPolygon2D("Free");
        freeMemoryPolygon.setColor(historyColor_memoryUsed);
        allocatedMemoryPolygon = new FLGPolygon2D("Allocated");
        allocatedMemoryPolygon.setColor(historyColor_memoryFree);
        maxMemoryPolygon = new FLGPolygon2D("Max");
        maxMemoryPolygon.setColor(Color.gray);

        freeMemoryPoints = new FLGPoint2D[NO_HISTORY_PLOTS];
        allocatedMemoryPoints = new FLGPoint2D[NO_HISTORY_PLOTS];
        maxMemoryPoints = new FLGPoint2D[NO_HISTORY_PLOTS];
        for (int i = 0; i < freeMemoryPoints.length - 1; i++) {
            freeMemoryPoints[i] = new FLGPoint2D(i+1,0);
            freeMemoryPolygon.add(freeMemoryPoints[i]);
            allocatedMemoryPoints[i] = new FLGPoint2D(i+1,0);
            allocatedMemoryPolygon.add(allocatedMemoryPoints[i]);
            maxMemoryPoints[i] = new FLGPoint2D(i+1,0);
            maxMemoryPolygon.add(maxMemoryPoints[i]);
        }
        freeMemoryPoints[freeMemoryPoints.length-1] = new FLGPoint2D(freeMemoryPoints.length-1, Runtime.getRuntime().totalMemory()/1024 - Runtime.getRuntime().freeMemory()/1024);
        freeMemoryPolygon.add(freeMemoryPoints[freeMemoryPoints.length-1]);
        allocatedMemoryPoints[allocatedMemoryPoints.length-1] = new FLGPoint2D(allocatedMemoryPoints.length-1, Runtime.getRuntime().totalMemory()/1024);
        allocatedMemoryPolygon.add(allocatedMemoryPoints[allocatedMemoryPoints.length-1]);
        maxMemoryPoints[maxMemoryPoints.length-1] = new FLGPoint2D(maxMemoryPoints.length-1, Runtime.getRuntime().maxMemory()/1024);
        maxMemoryPolygon.add(maxMemoryPoints[maxMemoryPoints.length-1]);
        polygonGroup = new FLGPolygonGroup2D();
        polygonGroup.add(maxMemoryPolygon);
        polygonGroup.add(freeMemoryPolygon);
        polygonGroup.add(allocatedMemoryPolygon);

        historyPlot = new FLGPlotter2D(polygonGroup, "100.0%", "kB");
        historyPlot.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                switchScale();
            }
        });
        historyPlot.showLegend(false);
        historyPlot.setMaxNoXAxisLabels(10);
        historyPanel.add(historyPlot, BorderLayout.CENTER);

        // Detail info panel
        JPanel dataPanel = new JPanel(new BorderLayout());
        dataPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("label.systemInfo.vm.text")));
        JPanel infoInnerPanel = new JPanel(new FLGColumnLayout());
        infoInnerPanel.setOpaque(false);
        infoInnerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel infoInnerMainPanel = new JPanel(new BorderLayout());
        infoInnerMainPanel.setOpaque(false);
        JLabel label_freeText = new JLabel(internationalization.getString("label.systemInfo.free.text") + ":");
        JLabel label_usedText = new JLabel(internationalization.getString("label.systemInfo.used.text") + ":");
        JLabel label_reservedText = new JLabel(internationalization.getString("label.systemInfo.allocated.text") + ":");
        JLabel label_maxText = new JLabel(internationalization.getString("label.systemInfo.max.text") + ":");

        label_free = new JLabel("" + (Runtime.getRuntime().freeMemory()/1024) + " kB");
        label_used = new JLabel("" + (Runtime.getRuntime().totalMemory()/1024 - Runtime.getRuntime().freeMemory()/1024) + " kB");
        label_reserved = new JLabel("" + (Runtime.getRuntime().totalMemory()/1024) + " kB");
        label_max = new JLabel("" + (Runtime.getRuntime().maxMemory()/1024) + " kB");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonImage = new ImageIcon(loadImage("garbage.gif"));
        button_free = new JButton(internationalization.getString("button.freeMem.text"), buttonImage);
        button_free.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.gc();
            }
        });
        button_free.setOpaque(false);
        button_free.setFocusPainted(false);
        button_free.setPreferredSize(new Dimension(button_free.getPreferredSize().width, (int)(button_free.getPreferredSize().height*1.4)));
        buttonPanel.add(button_free);

        infoInnerPanel.add(label_usedText, FLGColumnLayout.LEFT);
        infoInnerPanel.add(label_used, FLGColumnLayout.RIGHTEND);
        infoInnerPanel.add(label_freeText, FLGColumnLayout.LEFT);
        infoInnerPanel.add(label_free, FLGColumnLayout.RIGHTEND);
        infoInnerPanel.add(label_reservedText, FLGColumnLayout.LEFT);
        infoInnerPanel.add(label_reserved, FLGColumnLayout.RIGHTEND);
        infoInnerPanel.add(label_maxText, FLGColumnLayout.LEFT);
        infoInnerPanel.add(label_max, FLGColumnLayout.RIGHTEND);
        infoInnerPanel.add(new JLabel(" "), FLGColumnLayout.RIGHTEND);
        
        infoInnerMainPanel.add(infoInnerPanel, BorderLayout.CENTER);
        infoInnerMainPanel.add(buttonPanel, BorderLayout.SOUTH);
        infoPanel.add(infoInnerMainPanel, BorderLayout.CENTER);

        // Environment panel
        JPanel environmentPanel = new JPanel(new BorderLayout());       
        JPanel environmentInnerPanel = new JPanel(new FLGColumnLayout());       
        environmentPanel.setBorder(BorderFactory.createTitledBorder(internationalization.getString("label.systemInfo.environment.text")));
        environmentInnerPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        JLabel label_os = new JLabel(internationalization.getString("label.systemInfo.os.text") + ":");
        JLabel label_osName = new JLabel("" + System.getProperty("os.name"));
        JLabel label_arch = new JLabel(internationalization.getString("label.systemInfo.arch.text") + ":");
        JLabel label_archName = new JLabel("" + System.getProperty("os.arch"));
        JLabel label_java = new JLabel(internationalization.getString("label.systemInfo.javaVersion.text") + ":");
        JLabel label_javaName = new JLabel("" + System.getProperty("java.version"));
        JLabel label_vendor = new JLabel(internationalization.getString("label.systemInfo.javaVendor.text") + ":");
        JLabel label_vendorName = new JLabel("" + System.getProperty("java.vendor"));
        
        environmentInnerPanel.add(label_os, FLGColumnLayout.LEFT);
        environmentInnerPanel.add(label_osName, FLGColumnLayout.LEFTEND);
        environmentInnerPanel.add(label_arch, FLGColumnLayout.LEFT);
        environmentInnerPanel.add(label_archName, FLGColumnLayout.LEFTEND);
        environmentInnerPanel.add(label_java, FLGColumnLayout.LEFT);
        environmentInnerPanel.add(label_javaName, FLGColumnLayout.LEFTEND);
//        environmentInnerPanel.add(label_vendor, FLGColumnLayout.LEFT);
//        environmentInnerPanel.add(label_vendorName, FLGColumnLayout.LEFTEND);
        environmentInnerPanel.setOpaque(false);

        environmentPanel.add(environmentInnerPanel, BorderLayout.CENTER);
        dataPanel.add(infoPanel, BorderLayout.SOUTH);
        dataPanel.add(environmentPanel, BorderLayout.CENTER);
       
        graphicPane.setPreferredSize(new Dimension(250,400));
        graphicPane.addTab(internationalization.getString("tab.bar.label"), barChartPanel);
        graphicPane.addTab(internationalization.getString("tab.history.label"), historyPanel);        
        
        add(graphicPane, BorderLayout.CENTER);
        add(dataPanel, BorderLayout.WEST);
    }
    
    private void switchScale() {
        scaleIndex = ++scaleIndex%3;
    }

    private void refresh() {
        if (this.isShowing()) {
            // update labels
            label_free.setText("" + (Runtime.getRuntime().freeMemory()/1024) + " kB");
            label_used.setText("" + (Runtime.getRuntime().totalMemory()/1024 - Runtime.getRuntime().freeMemory()/1024) + " kB");
            label_reserved.setText("" + (Runtime.getRuntime().totalMemory()/1024) + " kB");
            label_max.setText("" + (Runtime.getRuntime().maxMemory()/1024) + " kB");
            // update barChart
            data2D[0].setName(FLGMathExtension.roundDouble(100d*(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/Runtime.getRuntime().maxMemory(), 1) + "%");
            data2D[0].setValue(Runtime.getRuntime().totalMemory()/1024 - Runtime.getRuntime().freeMemory()/1024);
            data2D[1].setValue(Runtime.getRuntime().freeMemory()/1024);
            data2D[2].setValue(Runtime.getRuntime().maxMemory()/1024 - Runtime.getRuntime().totalMemory()/1024);
            // update history
            for (int i = 0; i < freeMemoryPoints.length - 1; i++) {
                freeMemoryPoints[i].setY(freeMemoryPoints[i+1].getY());
                allocatedMemoryPoints[i].setY(allocatedMemoryPoints[i+1].getY());
                maxMemoryPoints[i].setY(maxMemoryPoints[i+1].getY());
            }
            freeMemoryPoints[freeMemoryPoints.length - 1].setY(Runtime.getRuntime().totalMemory()/1024 - Runtime.getRuntime().freeMemory()/1024);
            allocatedMemoryPoints[allocatedMemoryPoints.length - 1].setY(Runtime.getRuntime().totalMemory()/1024);
            maxMemoryPoints[maxMemoryPoints.length - 1].setY(Runtime.getRuntime().maxMemory()/1024);
            historyPlot.setXAxisLabel(FLGMathExtension.roundDouble(100d*(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/Runtime.getRuntime().maxMemory(), 1) + "%");
            repaint();
        }
    }

    public Image loadImage(String imageFileName) {
        Image image = FLGImageUtility.loadImageAndWait(getClass().getClassLoader().getResource("freestyleLearningGroup/independent/util/images/" +
            imageFileName));
        return FLGImageUtility.createAntiAliasedImage(image, 30, 30);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("System Info");
        FLGSystemInformationPanel panel = new FLGSystemInformationPanel(); 
        f.setIconImage(new ImageIcon(panel.loadImage("garbage.gif")).getImage());
        f.getContentPane().add(panel);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.pack();
        f.show();
    }

}

