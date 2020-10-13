package eu.qcloud.troubleshooting;

public class DataForParetto {

    private String troubleShootingName;

    private Float cumulative;

    private Long value;

    public String getTroubleShootingName() {
        return troubleShootingName;
    }

    public void setTroubleShootingName(String troubleShootingName) {
        this.troubleShootingName = troubleShootingName;
    }

    public Float getCumulative() {
        return cumulative;
    }

    public void setCumulative(Float cumulative) {
        this.cumulative = cumulative;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public DataForParetto() {
    }

    public DataForParetto(String troubleShootingName, Float cumulative, Long value) {
        this.troubleShootingName = troubleShootingName;
        this.cumulative = cumulative;
        this.value = value;
    }



}
