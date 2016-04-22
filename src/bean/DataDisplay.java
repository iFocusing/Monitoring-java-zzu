package bean;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataDisplay {
    private long pid;
    private long did;
    private String samplingTime;
    private double outTemperature;
    private double wireTemperature;
    private double sag;
    private double electricity;
    private double voltage;
    private double humidity;
    private long nid;
    private long location;
    private String name;
    private String source;

    public DataDisplay() {
    }

    public DataDisplay(long pid, long did, String samplingTime, double outTemperature, double wireTemperature, double sag, double electricity, double voltage, double humidity, long nid, long location, String name, String source) {
        this.pid = pid;
        this.did = did;
        this.samplingTime = samplingTime;
        this.outTemperature = outTemperature;
        this.wireTemperature = wireTemperature;
        this.sag = sag;
        this.electricity = electricity;
        this.voltage = voltage;
        this.humidity = humidity;
        this.nid = nid;
        this.location = location;
        this.name = name;
        this.source = source;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public String getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(String samplingTime) {
        this.samplingTime = samplingTime;
    }

    public double getOutTemperature() {
        return outTemperature;
    }

    public void setOutTemperature(double outTemperature) {
        this.outTemperature = outTemperature;
    }

    public double getWireTemperature() {
        return wireTemperature;
    }

    public void setWireTemperature(double wireTemperature) {
        this.wireTemperature = wireTemperature;
    }

    public double getSag() {
        return sag;
    }

    public void setSag(double sag) {
        this.sag = sag;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public long getNid() {
        return nid;
    }

    public void setNid(long nid) {
        this.nid = nid;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
