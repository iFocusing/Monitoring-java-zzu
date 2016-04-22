package bean;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/4/10.
 */
public class Data {
    private long did;
    private java.sql.Timestamp samplingTime;
    private double outTemperature;
    private double wireTemperature;
    private double sag;
    private double electricity;
    private double voltage;
    private double humidity;
    private long nid;





    public long getNid() {
        return nid;
    }

    public Data(long did, Timestamp samplingTime, double outTemperature, double wireTemperature, double sag, double electricity, double voltage, double humidity, long nid) {
        this.did = did;
        this.samplingTime = samplingTime;
        this.outTemperature = outTemperature;
        this.wireTemperature = wireTemperature;
        this.sag = sag;
        this.electricity = electricity;
        this.voltage = voltage;
        this.humidity = humidity;
        this.nid = nid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getSag() {
        return sag;
    }

    public void setSag(double sag) {
        this.sag = sag;
    }

    public double getWireTemperature() {
        return wireTemperature;
    }

    public void setWireTemperature(double wireTemperature) {
        this.wireTemperature = wireTemperature;
    }

    public double getOutTemperature() {
        return outTemperature;
    }

    public void setOutTemperature(double outTemperature) {
        this.outTemperature = outTemperature;
    }

    public Timestamp getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Timestamp samplingTime) {
        this.samplingTime = samplingTime;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }


}
