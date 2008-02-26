package freestyleLearningGroup.independent.plotter.parser;

public class FLGFpConst extends FLGFpFunction {
    double c;

    public FLGFpConst(double c) { this.c = c; }

    public double compute(double[] varList) { return c; }

    public int getPriority() { return 0; }

    public String toString() { return "KONSTANTE: " + c; }
}
