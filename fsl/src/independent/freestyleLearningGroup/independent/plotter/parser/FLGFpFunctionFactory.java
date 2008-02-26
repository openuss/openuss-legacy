package freestyleLearningGroup.independent.plotter.parser;


/**
 * Die Klasse <code>LGFpFunctionFactory</code> enth&auml;lt alle Methoden
 * die zur Erzeugung und zum Parsen von Formeln in textueller Form benötigt werden.<p>
 * Die diversen <code>create...</code>-Methoden und die <code>getFunctionStrings()</code>-Methode
 * dienen der Erzeugung von atomaren Funktionen, die der Benutzer dann selbst,
 * wenn gewollt, zu komplexeren Formeln zusammensetzen kann.<p>
 * Die wichtigste Methode ist aber die <code>parseFunction</code>-Methode, an die
 * die textuelle Repr&auml;sentation einer Formel &uuml;bergeben wird und spezifiziert wird,
 * was davon Variablennamen sind. Zur&uuml;ck bekommt man eine Funktion, die
 * die &uuml;bergebene Formel f&uuml;r beliebige Werte berechnen kann. <p> <b>Beispiel 1</b>: Eine einfache Formel. <p> <pre>
 *      String[] vars = {"x","y"};
 *      try {
 *        LGFpFunction easy = LGFpFunctionFactory.parseFunction("x+2*y",vars);
 *        double[] values = {1.0, 2.0};
 *        log(easy.compute(values));
 *      } catch (Exception e) { }
 *  </pre> </p> <p> <b>Beispiel 2</b>: Das folgende Beispiel ist so nicht lauff&auml;hig,
 * sondern zeigt vielmehr mit welchen Erg&auml;nzungen man zu einem
 * 3D Plotter Eingabefelder hinzuf&uuml;gen kann, um die Funktion, die
 * der Plotter zeichnen soll, vom Benutzer eingegeben werden kann.<p>
 * Interessant ist hier besonders wie aus ein <code>LGFpFuntion<code> eine <code>FLGFunction3D<code> gemacht wird. <p> <pre>
 * import de.uni_muenster.wi.lg.plotter.*;
 * import de.uni_muenster.wi.lg.parser.*;
 *
 * import java.awt.*;
 * import java.awt.event.*;
 * import javax.swing.*;
 *
 * public class ParseExample extends JFrame{
 *  JTextField              if_funktionsvorschrift = new JTextField(20);
 *  JTextField              if_defBereichXAnfang = new JTextField(20);
 *  JTextField              if_defBereichXEnde = new JTextField(20);
 *  JTextField              if_defBereichZAnfang = new JTextField(20);
 *  JTextField              if_defBereichZEnde = new JTextField(20);
 *
 *  LGFpFunction myFunction;
 *  LGPlotter3D plotter;
 *
 *  public ParseExample() {
 *      JPanel fieldPanel = new JPanel(new GridLayout(5,2));
 *
 *      fieldPanel.add(new JLabel("Funktionsvorschrift"));
 *      fieldPanel.add(if_funktionsvorschrift);
 *      fieldPanel.add(new JLabel("X: Von"));
 *      fieldPanel.add(if_defBereichXAnfang);
 *      fieldPanel.add(new JLabel("X: Bis"));
 *      fieldPanel.add(if_defBereichXEnde);
 *      fieldPanel.add(new JLabel("Z: Von"));
 *      fieldPanel.add(if_defBereichZAnfang);
 *      fieldPanel.add(new JLabel("Z: Bis"));
 *      fieldPanel.add(if_defBereichZEnde);
 *
 *      getContentPane().setLayout(new BorderLayout());
 *      getContentPane().add("Center", fieldPanel);
 *      getContentPane().add("South", new ParseButton("Parse"));
 *  }
 *
 *  class ParseButton extends Button {
 *      public ParseButton (String text) {
 *          super(text);
 *        enableEvents(AWTEvent.ACTION_EVENT_MASK);
 *      }
 *
 *      public void processActionEvent(ActionEvent event) {
 *          String formel = if_funktionsvorschrift.getText();
 *          double  defBereichXAnfang, defBereichXEnde;
 *          double  defBereichZAnfang, defBereichZEnde;
 *
 *          try {
 *              defBereichXAnfang = Double.valueOf(if_defBereichXAnfang.getText()).doubleValue();
 *              defBereichXEnde = Double.valueOf(if_defBereichXEnde.getText()).doubleValue();
 *              defBereichZAnfang = Double.valueOf(if_defBereichZAnfang.getText()).doubleValue();
 *              defBereichZEnde = Double.valueOf(if_defBereichZEnde.getText()).doubleValue();
 *
 *              String[]    variablen = {"x", "z"};
 *              myFunction = LGFpFunctionFactory.parseFunction(formel, variablen);
 *
 *              FLGFunction3D function = new FLGFunction3D(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
 *                                                                                                  Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY) {
 *                  double[] belegung = new double[2];
 *                  public double calculate(double x, double z) {
 *                      belegung[0] = x;
 *                      belegung[1] = z;
 *                      return myFunction.compute(belegung);
 *                  }
 *              };
 *
 *              FLGFunctionGroup3D functionGroup = new FLGFunctionGroup3D(defBereichXAnfang, defBereichXEnde,
 *                                                                                                                              defBereichZAnfang, defBereichZEnde, 20);
 *              functionGroup.addFunction(function);
 *
 *              plotter.setFunctionGroup(functionGroup);
 *          }
 *          catch (Exception ex) {  log(ex);    }
 *      }
 *  }
 *  public static void main(String[] args) {
 *      ParseExample plotterFrame = new ParseExample();
 *      plotterFrame.setSize(800,600);
 *      plotterFrame.setVisible(true);
 *  }
 * }
 *  </pre> </p>
 */
public class FLGFpFunctionFactory {
    //
    // An diese beiden String-Arrays müssen neu erstellte Funktionen angehängt werden.
    // Das erste String-Array stellt die Symbole dar, die in der textuellen Repräsentation
    // einer Funktion als Funktion erkannt werden soll, dier zweite String-Array die
    // entsprechend Klasse. Ergo müssen Funktionssymbol und Funktionsklasse immer an
    // der gleichen Position in den beiden Arrays stehen.
    //
    private static final String[] functionStrings =
        {"+", "-", "*", "/", "^", "sqrt", "sin", "cos", "exp"};
    private static final String[] functionClassNames =
        {"FLGFpAdd", "FLGFpSub", "FLGFpMult", "FLGFpDiv", "FLGFpPow", "FLGFpSqrt", "FLGFpSin", "FLGFpCos", "FLGFpExp"};
    //
    // Der Operanden- und der Operatoren-Stack werden bei der Erzeugung einer
    // "parsebaren" Funktion benötigt.
    //
    private static FLGFpFunctionStack operandStack = new FLGFpFunctionStack();
    private static FLGFpFunctionStack operatorStack = new FLGFpFunctionStack();

    /** Niemand soll diese Klasse instantiieren. */
    private FLGFpFunctionFactory() {
    }

    /**
     * Liefert die erlaubten Funktionen dieser <code>FunctionFactory</code> in Form ihrer
     * textuellen Repr&auml;sentationen, die in Formeln verwandt werden d&uuml;rfen.
     * @return  die erlaubten Funktionen, bspw.{"+", "-", "sqrt"}.
     */
    public static String[] getFunctionStrings() { return functionStrings; }

    /**
     * Liefert eine neue Funktion, die die spezifizierte Konstante repr&auml;sentiert.
     * @param   c   die Konstante.
     * @return  die erzeugte Funktion.
     * @see         <{freestyleLearningGroup.independent.plotter.parser.FLGFpConst}>
     */
    public static FLGFpFunction createConst(double c) { return new FLGFpConst(c); }

    /**
     * Liefert eine neue Funktion, die die spezifizierte Variable repr&auml;sentiert.
     * @param   c   die Variable.
     * @return  die erzeugte Funktion.
     * @see         <{freestyleLearningGroup.independent.plotter.parser.FLGFpVar}>
     */
    public static FLGFpFunction createVar(int ind) { return new FLGFpVar(ind); }

    /**
     * Liefert eine neue Funktion <code>LGFpNeg</code>, die eine Negation darstellt.
     * @return  die erzeugte Negation.
     * @see         <{freestyleLearningGroup.independent.plotter.parser.FLGFpNeg}>
     */
    public static FLGFpFunction createNegOp() { return new FLGFpNeg(); }

    /**
     * Liefert eine neue Funktion, die durch das spezifizierte Symbol beschrieben wird, falls eine
     * solche Funktion existiert. Bspw. liefert diese Methode aufgerufen mit dem Param
     * <code>"+"</code> eine Additionsfunktion.
     * @param   name    das mathematische Symol der zu erzeugenden Funktion.
     * @return  die erzeugte Funktion oder <code>null</code>, falls keine so benannte Funktion existiert.
     */
    public static FLGFpFunction createFunction(String name) {
        int ind = 0;
        int length = functionStrings.length;
        while ((ind < length) && (!(functionStrings[ind].equals(name)))) ind++;
        if (ind < length) {
            FLGFpFunction f = null;
            try {
                f = (FLGFpFunction)Class.forName("freestyleLearningGroup.independent.plotter.parser." +
                    functionClassNames[ind]).newInstance();
            }
            catch (Exception e) { log(e); }
            return f;
        }
        else
            return null;
    }

    /**
     * Zu der in textueller Form spezifizierten Formel, wird die <code>LGFpFunction</code>
     * geliefert, mit der dann die Formel durch den Aufruf derer Methode
     * <code>compute(double[] varList)</code> f&uuml;r beliebige zu &uuml;bergebende Werte
     * berechnet werden kann.<p> Bei der Benutzung dieser Methode muss beachtet werden, dass <p>
     * a) die Variablen in der in textueller Form spezifizierten Formel und in dem danach
     * angegebenen String-Array von Variablennamen &uuml;bereinstimmen,<p>
     * b) in der in textueller Form spezifizierten Formel keine Leerzeichen vorkommmen,<p>
     * c) beim Aufruf der <code>compute(double[] varList)</code>-Methode der
     * Ergebnisfunktion die Werte mit der die Funktion berechnet werden soll in der
     * richtigen Reihenfolge angegeben werden und <p> d) die <code>LGFpFunctionFormatException</code> abgefangen wird.<p>
     * @see     LGFpFunction#compute(double[] varList)
     * @param   function            die zu parsende Funktion in textueller Form.
     * @param   variableNames   die in der Funktion vorkommenden Variablen.
     * @return                          die Funktion, mit der die Formel f&uuml;r beliebige Werte berechnet werden kann.
     * @exception   LGFpFunctionFormatException wenn die in textueller Form
     * angegebene Funktion nicht formalisiert werden kann.
     */
    public static FLGFpFunction parseFunction(String function, String[] variableNames) throws FLGFpFunctionFormatException {
        operandStack = new FLGFpFunctionStack();
        operatorStack = new FLGFpFunctionStack();
        int pos = 0;
        int length = function.length();
        boolean vorzeichen = false;
        boolean vorzeichen_moeglich = true;
        boolean lastWasOperand = false;
        FLGFpFunction[] variables = null;
        if (!bracketsInFunctionOk(function)) throw new FLGFpFunctionFormatException();
        // Variablen erzeugen
        if (variableNames != null) {
            variables = new FLGFpFunction[variableNames.length];
            for (int v = 0; v < variableNames.length; v++)
                variables[v] = createVar(v); // AS functionCreator.createVar(v);
        }
        while (pos < length) {
            char c = function.charAt(pos);
            // Falls es eine Zahl ist
            if (Character.isDigit(c)) {
                int endPos = getNumberEndPos(function, pos);
                try {
                    double zahl = Double.valueOf(function.substring(pos, endPos)).doubleValue();
                    if (vorzeichen) { zahl = -zahl; vorzeichen = false; }
                    else if (lastWasOperand) {
                        FLGFpFunction funct = createFunction("*"); // AS functionCreator.createFunction("*");
                        if (operatorStack.getPriority() > funct.getPriority())
                            reduce(funct.getPriority());
                        operatorStack.push(funct);
                    }
                    operandStack.push(createConst(zahl)); // AS functionCreator.createConst(zahl));
                }
                catch (Exception e) { throw new FLGFpFunctionFormatException(); }
                lastWasOperand = true;
                vorzeichen_moeglich = false;
                pos = endPos;
            }
            // sonst
            else {
                // Vorzeichen
                if (function.startsWith("-", pos) && vorzeichen_moeglich) {
                    vorzeichen = true;
                    vorzeichen_moeglich = false;
                    pos++;
                }
                // Klammer auf
                else if (function.startsWith("(", pos)) {
                    vorzeichen_moeglich = true;
                    if (vorzeichen) {
                        operatorStack.push(createNegOp()); // AS functionCreator.createNegOp());
                        vorzeichen = false;
                    }
                    else if (lastWasOperand) {
                        FLGFpFunction funct = createFunction("*"); // AS functionCreator.createFunction("*");
                        if (operatorStack.getPriority() > funct.getPriority())
                            reduce(funct.getPriority());
                        operatorStack.push(funct);
                        lastWasOperand = false;
                    }
                    operatorStack.push(null);
                    pos++;
                }
                // Klammer zu
                else if (function.startsWith(")", pos)) {
                    vorzeichen_moeglich = false;
                    lastWasOperand = true;
                    reduce(0);
                    pos++;
                }
                // Funktion
                else {
                    // AS String[]  functionStrings = functionCreator.getFunctionStrings();
                    int noFunctions = functionStrings.length;
                    boolean isFunction = false;
                    int f = 0;
                    while ((f < noFunctions) && !(isFunction)) {
                        if (function.startsWith(functionStrings[f], pos)) {
                            FLGFpFunction funct = createFunction(functionStrings[f]); // AS
                            // functionCreator.createFunction(functionStrings[f]);
                            if (funct.isFunction() && lastWasOperand) {
                                FLGFpFunction funct2 = createFunction("*"); // AS
                                // functionCreator.createFunction("*");
                                if (operatorStack.getPriority() > funct2.getPriority())
                                    reduce(funct2.getPriority());
                                operatorStack.push(funct2);
                                lastWasOperand = false;
                            }
                            if (operatorStack.getPriority() > funct.getPriority())
                                reduce(funct.getPriority());
                            // wichtige Stelle: Neg-Op trotz höherer Priorität vor funct auf den Stack
                            // (da der Operand von funct noch nicht geparst)
                            if (vorzeichen && funct.isFunction()) {
                                operatorStack.push(createNegOp()); // AS functionCreator.createNegOp());
                                vorzeichen = false;
                            }
                            operatorStack.push(funct);
                            isFunction = true;
                            lastWasOperand = false;
                            vorzeichen = false;
                            vorzeichen_moeglich = true;
                            pos += functionStrings[f].length();
                        }
                        else
                            f++;
                    }
                    if (!(isFunction)) {
                        // Variable ?
                        int v = 0;
                        int noVariables = variableNames.length;
                        boolean isVariable = false;
                        while ((v < noVariables) && !(isVariable)) {
                            if (function.startsWith(variableNames[v], pos)) {
                                if (vorzeichen) {
                                    operatorStack.push(createNegOp()); // AS functionCreator.createNegOp());
                                    vorzeichen = false;
                                }
                                else if (lastWasOperand) {
                                    FLGFpFunction funct = createFunction("*"); // AS
                                    // functionCreator.createFunction("*");
                                    if (operatorStack.getPriority() > funct.getPriority())
                                        reduce(funct.getPriority());
                                    operatorStack.push(funct);
                                }
                                operandStack.push(variables[v]);
                                isVariable = true;
                                lastWasOperand = true;
                                vorzeichen = false;
                                vorzeichen_moeglich = false;
                                pos += variableNames[v].length();
                            }
                            else
                                v++;
                        }
                        if (!(isVariable))
                            throw new FLGFpFunctionFormatException();
                    };
                }
            }
        }
        reduce(0);
        if ((operandStack.size() > 1) || (operatorStack.size() > 0))
            throw new FLGFpFunctionFormatException();
        else
            return operandStack.pop();
    }

    /**
     * Pr&uuml;ft die Anzahl der ge&ouml;ffneten und geschlossenen Klammern im spezifizierten Text.
     * @param text  der zu &uuml;berpr&uuml;fende <code>String</code>.
     * @return          <code>true</code> wenn die Anzahl der ge&ouml;ffneten der
     * Anzahl der geschlossenen Klammern entspricht, sonst <code>false</code>.
     */
    private static boolean bracketsInFunctionOk(String text) {
        int noOpenBrackets = 0, noClosedBrackets = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '(') noOpenBrackets++;
            if (text.charAt(i) == ')') noClosedBrackets++;
        }
        return (noOpenBrackets == noClosedBrackets);
    }

    /**
     * Operanden und Operator-Stack werden abgebaut, wenn die n&auml;chste Funktion eine
     * niedrigere Priorit&auml;t hat, als die Funktion auf dem Operatorstack.
     */
    private static void reduce(int priority) throws FLGFpFunctionFormatException {
        // leicht gedreht, hat man noch zwei Zeilen gespart, AS
        while ((!(operatorStack.isEmpty())) && operatorStack.getPriority() >= priority) {
            FLGFpFunction function = operatorStack.pop();
            if (function != null) function.init(operandStack);
            else
                break;
        }
    }

    /**
     * Liefert die letzte Position der Zahl in dem spezifizierten <code>String</code>, die
     * an der spezifizierten Position anf&auml;ngt.
     * @param   function    die textuelle Darstellung einer Funktion.
     * @param startPos  die Position an der im Text die Zahl anf&auml;ngt.
     * @return                  die Position an der im Text die Zahl zu Ende ist.
     */
    private static int getNumberEndPos(String function, int startPos) {
        int length = function.length();
        for (int endPos = startPos; endPos < length; endPos++) {
            char c = function.charAt(endPos);
            if (!(Character.isDigit(c) || (c == '.'))) return endPos;
        }
        return length;
    }

    /**
     * Ersetzt das übliche <code>System.out.println<code>.
     * @see <{LGLog}>
     */
    private static void log(Object msgObj) {
    }
}
