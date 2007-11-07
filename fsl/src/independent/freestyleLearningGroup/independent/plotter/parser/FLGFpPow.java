///////////////////////////////////////////////////////////////////////////////
// Class: LGFpPow
//
// Notes:
///////////////////////////////////////////////////////////////////////////////

package freestyleLearningGroup.independent.plotter.parser;

public class FLGFpPow extends FLGFpFunction {
    public int getPriority() { return 3; }

    public double compute(double[] varList) {
        return Math.pow(op1.compute(varList), op2.compute(varList));
    }
}
