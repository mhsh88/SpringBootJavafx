package sample.model.showResultEntity;

import javafx.beans.property.SimpleStringProperty;
import sample.controller.calculate.CalculateController;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Table {

    private final SimpleStringProperty name;
    private final SimpleStringProperty meter;
    private final SimpleStringProperty value;


    DecimalFormat df = new DecimalFormat("#.###");

    public Table(String name, String meter,  String value) {
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        try{
            Double val = Double.parseDouble(value);


            if(meter.equals(CalculateController.PSI)){
                df = new DecimalFormat("#.#");
                df.setRoundingMode(RoundingMode.HALF_EVEN);

                val = (val - 101.235) * 0.145038;


            }
            else if(meter.equals(CalculateController.MPA)){

                val = (val - 101.235) * 0.001;

            }
            else if(meter.equals(CalculateController.KPA)){
                val = (val - 101.235) * 1;

            }
            else if(meter.equals(CalculateController.FARENHEIT)){

                val = val * 9/5 + 32;

            }
            else if(meter.equals(CalculateController.CELCIUS)){

                val = val * 1;

            }
            value = String.valueOf(df.format(val));

        }
        catch (Exception e){

        }

        this.name = new SimpleStringProperty(name);
        this.meter = new SimpleStringProperty(meter);
        this.value = new SimpleStringProperty(value);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public void setMeter(String meter) {
        this.meter.set(meter);
    }

    public String getMeter() {
        return meter.get();
    }
}
