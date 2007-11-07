package freestyleLearningGroup.independent.plotter.parser;

public class FLGFpNeg extends FLGFpFunction {
    public int getPriority() { return 100; }

    public double compute(double[] varList) {
        return -op1.compute(varList);
    }

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
    private static void log(Object msgObj) { }
}
