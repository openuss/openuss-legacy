///////////////////////////////////////////////////////////////////////////////
// Class: LGFpVar
//
// Notes:
///////////////////////////////////////////////////////////////////////////////

package freestyleLearningGroup.independent.plotter.parser;

public class FLGFpVar extends FLGFpFunction {
    int ind;

    public FLGFpVar(int ind) { this.ind = ind; }

    public double compute(double[] varList) { return varList[ind]; }

    public int getPriority() { return 0; }

    public String toString() { return "Variable: " + ind; }
}
