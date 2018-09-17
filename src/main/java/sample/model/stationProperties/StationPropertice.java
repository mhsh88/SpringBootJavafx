package sample.model.stationProperties;

import sample.model.base.BaseModel;

import java.util.Arrays;
import java.util.Objects;

public class StationPropertice extends BaseModel {
    private Double[] component = new Double[21];
    private String city;
    private String province;
    private String area;
    private String nominalCapacity;
    private String address;
    private double inputTemp;
    private double inputPressure;
    private double outputTemp;
    private double outputPressure;
    private Double environmentTemp;
    private double windVelocity;
    private double debi;

    public Double[] getComponent() {
        return component;
    }

    public void setComponent(Double[] component) {
        this.component = component;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNominalCapacity() {
        return nominalCapacity;
    }

    public void setNominalCapacity(String nominalCapacity) {
        this.nominalCapacity = nominalCapacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getInputTemp() {
        return inputTemp + 273.15;
    }

    public void setInputTemp(double inputTemp) {
        this.inputTemp = inputTemp;
    }

    public double getInputPressure() {
        return inputPressure + 101.325;
    }

    public void setInputPressure(double inputPressure) {
        this.inputPressure = inputPressure;
    }

    public double getOutputTemp() {
        return outputTemp + 273.15;
    }

    public void setOutputTemp(double outputTemp) {
        this.outputTemp = outputTemp;
    }

    public double getOutputPressure() {
        return outputPressure + 101.325;
    }

    public void setOutputPressure(double outputPressure) {
        this.outputPressure = outputPressure;
    }

    public Double getEnvironmentTemp() {
        if(environmentTemp == null){
            return environmentTemp;
        }
        return environmentTemp + 273.15;
    }

    public void setEnvironmentTemp(Double environmentTemp) {
        this.environmentTemp = environmentTemp;
    }

    public double getWindVelocity() {
        return windVelocity;
    }

    public void setWindVelocity(double windVelocity) {
        this.windVelocity = windVelocity;
    }

    public double getDebi() {
        return debi;
    }

    public void setDebi(double debi) {
        this.debi = debi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationPropertice)) return false;
        StationPropertice that = (StationPropertice) o;
        return Double.compare(that.getInputTemp(), getInputTemp()) == 0 &&
                Double.compare(that.getInputPressure(), getInputPressure()) == 0 &&
                Double.compare(that.getOutputTemp(), getOutputTemp()) == 0 &&
                Double.compare(that.getOutputPressure(), getOutputPressure()) == 0 &&
                Double.compare(that.getEnvironmentTemp(), getEnvironmentTemp()) == 0 &&
                Double.compare(that.getWindVelocity(), getWindVelocity()) == 0 &&
                Double.compare(that.getDebi(), getDebi()) == 0 &&
                Arrays.equals(getComponent(), that.getComponent()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getArea(), that.getArea()) &&
                Objects.equals(getNominalCapacity(), that.getNominalCapacity()) &&
                Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getCity(), getProvince(), getArea(), getNominalCapacity(), getAddress(), getInputTemp(), getInputPressure(), getOutputTemp(), getOutputPressure(), getEnvironmentTemp(), getWindVelocity(), getDebi());
        result = 31 * result + Arrays.hashCode(getComponent());
        return result;
    }

    @Override
    public String toString() {
        return "StationLogic Propertis{" +
                "component=" + Arrays.toString(component) +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", area='" + area + '\'' +
                ", nominal Capacity='" + nominalCapacity + '\'' +
                ", address='" + address + '\'' +
                ", input Temp=" + inputTemp +
                ", input Pressure=" + inputPressure +
                ", output Temp=" + outputTemp +
                ", output Pressure=" + outputPressure +
                ", environment Temp=" + environmentTemp +
                ", wind Velocity=" + windVelocity +
                ", debi=" + debi +
                '}';
    }
}
