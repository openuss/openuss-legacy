///////////////////////////////////////////////////////////////////////////////
// Class: LGFpSin
//
// Notes:
///////////////////////////////////////////////////////////////////////////////

package freestyleLearningGroup.independent.plotter.parser;

public class FLGFpSin extends FLGFpFunction {
    public int getPriority() { return 4; }

    public boolean isFunction() { return true; }

    public double compute(double[] varList) { return Math.sin(op1.compute(varList)); }

    public void init(FLGFpFunctionStack operandenStack) throws FLGFpFunctionFormatException {
        if (operandenStack.size() < 1) throw new FLGFpFunctionFormatException();
        else {
            op1 = operandenStack.pop();
            log("" + this + ":::::" + op1);
            operandenStack.push(this);
        }
    }

    /**
     * Ersetzt das �bliche <code>System.out.println<code>.
     * @see <{LGLog}>
     */
    private static void log(Object msgObj) {
    }
}
