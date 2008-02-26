package freestyleLearningGroup.independent.plotter.parser;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import freestyleLearningGroup.independent.plotter.FLGFunction3D;
import freestyleLearningGroup.independent.plotter.FLGFunctionGroup3D;
import freestyleLearningGroup.independent.plotter.FLGPlotter3D;

public class ParseExample extends JFrame {
    JTextField if_funktionsvorschrift = new JTextField(20);
    JTextField if_defBereichXAnfang = new JTextField(20);
    JTextField if_defBereichXEnde = new JTextField(20);
    JTextField if_defBereichZAnfang = new JTextField(20);
    JTextField if_defBereichZEnde = new JTextField(20);
    FLGFpFunction myFunction;
    FLGPlotter3D plotter;

    public ParseExample() {
        JPanel fieldPanel = new JPanel(new GridLayout(5, 2));
        fieldPanel.add(new JLabel("Funktionsvorschrift"));
        fieldPanel.add(if_funktionsvorschrift);
        fieldPanel.add(new JLabel("X: Von"));
        fieldPanel.add(if_defBereichXAnfang);
        fieldPanel.add(new JLabel("X: Bis"));
        fieldPanel.add(if_defBereichXEnde);
        fieldPanel.add(new JLabel("Z: Von"));
        fieldPanel.add(if_defBereichZAnfang);
        fieldPanel.add(new JLabel("Z: Bis"));
        fieldPanel.add(if_defBereichZEnde);

/*
        if_defBereichXEnde.setText("5");
        if_defBereichXAnfang.setText("-5");
        if_defBereichZAnfang.setText("-5");
        if_defBereichZEnde.setText("5");
*/

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", fieldPanel);
        getContentPane().add("South", new ParseButton("Parse"));
    }

    class ParseButton extends Button {
        public ParseButton(String text) {
            super(text);
            enableEvents(AWTEvent.ACTION_EVENT_MASK);
        }

        public void processActionEvent(ActionEvent event) {
            String formel = if_funktionsvorschrift.getText();
            double defBereichXAnfang, defBereichXEnde;
            double defBereichZAnfang, defBereichZEnde;
            try {
                defBereichXAnfang = Double.valueOf(if_defBereichXAnfang.getText()).doubleValue();
                defBereichXEnde = Double.valueOf(if_defBereichXEnde.getText()).doubleValue();
                defBereichZAnfang = Double.valueOf(if_defBereichZAnfang.getText()).doubleValue();
                defBereichZEnde = Double.valueOf(if_defBereichZEnde.getText()).doubleValue();
                String[] variablen = {"x", "z"};
                myFunction = FLGFpFunctionFactory.parseFunction(formel, variablen);
                FLGFunction3D function = new FLGFunction3D(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                    Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY) {
                        double[] belegung = new double[2];
                        public double calculate(double x, double z) {
                            belegung[0] = x;
                            belegung[1] = z;
                            return myFunction.compute(belegung);
                        }
                };
                FLGFunctionGroup3D functionGroup = new
                    FLGFunctionGroup3D(defBereichXAnfang, defBereichXEnde, defBereichZAnfang, defBereichZEnde, 20);
                functionGroup.addFunction(function);
                plotter.setFunctionGroup(functionGroup);
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }


    public static void main(String[] args) {
        ParseExample plotterFrame = new ParseExample();
        plotterFrame.setSize(800, 600);
        plotterFrame.setVisible(true);
    }
}
