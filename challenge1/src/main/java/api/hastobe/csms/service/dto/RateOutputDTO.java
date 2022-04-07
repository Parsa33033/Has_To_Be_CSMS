package api.hastobe.csms.service.dto;

import api.hastobe.csms.constants.Calc;

import java.io.Serializable;

public class RateOutputDTO implements Serializable {
    private float total;
    private RateDTO component;

    public float getTotal() {
        return Float.valueOf(Calc.decimalPoint2.format(total));
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public RateDTO getComponent() {
        return component;
    }

    public void setComponent(RateDTO component) {
        this.component = component;
    }

    @Override
    public String toString() {
        return "RateOutputDTO{" +
                "total=" + String.format("%.2f", total) +
                ", component=" + component +
                '}';
    }
}
