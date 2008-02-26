///////////////////////////////////////////////////////////////////////////////
// Class: LGFpDiv
//
// Notes:
///////////////////////////////////////////////////////////////////////////////

package freestyleLearningGroup.independent.plotter.parser;

public class FLGFpDiv extends FLGFpFunction {
    public int getPriority() { return 2; }

    public double compute(double[] varList) {
        return op1.compute(varList) / op2.compute(varList);
    }
}
