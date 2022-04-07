package api.hastobe.csms.service.dto;

import java.io.Serializable;
import java.time.Instant;

public class CdrDTO implements Serializable {

    private Long meterStart;

    private String timestampStart;

    private Long meterStop;

    private String timestampStop;

    public Long getMeterStart() {
        return meterStart;
    }

    public void setMeterStart(Long meterStart) {
        this.meterStart = meterStart;
    }

    public String getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(String timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Long getMeterStop() {
        return meterStop;
    }

    public void setMeterStop(Long meterStop) {
        this.meterStop = meterStop;
    }

    public String getTimestampStop() {
        return timestampStop;
    }

    public void setTimestampStop(String timestampStop) {
        this.timestampStop = timestampStop;
    }

    @Override
    public String toString() {
        return "CdrDTO{" +
                "meterStart=" + meterStart +
                ", timestampStart='" + timestampStart + '\'' +
                ", meterStop=" + meterStop +
                ", timestampStop='" + timestampStop + '\'' +
                '}';
    }
}
