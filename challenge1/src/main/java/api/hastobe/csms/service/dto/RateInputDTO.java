package api.hastobe.csms.service.dto;

import java.io.Serializable;

public class RateInputDTO implements Serializable {
    private RateDTO rate;
    private CdrDTO cdr;

    public RateDTO getRate() {
        return rate;
    }

    public void setRate(RateDTO rate) {
        this.rate = rate;
    }

    public CdrDTO getCdr() {
        return cdr;
    }

    public void setCdr(CdrDTO cdr) {
        this.cdr = cdr;
    }

    @Override
    public String toString() {
        return "RateInputDTO{" +
                "rate=" + rate +
                ", cdr=" + cdr +
                '}';
    }
}
