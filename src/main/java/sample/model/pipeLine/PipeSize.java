package sample.model.pipeLine;


import java.io.Serializable;

public class PipeSize implements Serializable {
    double wallThickness;
    double outerDiameter;

    @Override
    public String toString() {
        return "PipeSize{" +
                "wallThickness=" + wallThickness +
                ", outerDiameter=" + outerDiameter +
                ", innerDiameter=" + getInnerDiameter() +
                '}';
    }

    public PipeSize(double wallThickness, double outerDiameter) {
        this.wallThickness = wallThickness;
        this.outerDiameter = outerDiameter;
    }

    public double getWallThickness() {
        return wallThickness;
    }

    public double getOuterDiameter() {
        return outerDiameter;
    }
    public double getInnerDiameter(){

        return outerDiameter - 2 * wallThickness;
    }
}