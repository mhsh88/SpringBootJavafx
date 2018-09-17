package sample.model.run;

import sample.model.base.BaseModel;
import sample.model.pipeLine.PipeLine;

import java.util.ArrayList;
import java.util.Objects;

public class Runs extends BaseModel {
    ArrayList<Run> runs = new ArrayList<Run>();
    Collector collector;

    public Runs() {
    }

    public Runs(ArrayList<Run> runs, Collector collector) {
        this.runs = runs;
        this.collector =  collector;
    }

    public ArrayList<Run> getRuns() {
        return runs;
    }

    public void setRuns(ArrayList<Run> runs) {
        this.runs = runs;
    }

    public PipeLine getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Runs runs1 = (Runs) o;
        return Objects.equals(runs, runs1.runs) &&
                Objects.equals(collector, runs1.collector);
    }

    @Override
    public int hashCode() {

        return Objects.hash(runs, collector);
    }

    @Override
    public String toString() {
        return "Runs{" +
                "runs=" + runs +
                ", collector=" + collector +
                '}';
    }
}
