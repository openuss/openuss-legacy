package freestyleLearningGroup.independent.plotter.parser;

public class FLGFpAdd extends FLGFpFunction {
    public int getPriority() {
        return 1;
    }

    public double compute(double[] varList) {
        return op1.compute(varList) + op2.compute(varList);
    }
}
