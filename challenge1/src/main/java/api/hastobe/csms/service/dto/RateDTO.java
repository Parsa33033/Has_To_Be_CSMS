package api.hastobe.csms.service.dto;

import api.hastobe.csms.constants.Calc;

import java.io.Serializable;

public class RateDTO implements Serializable {

    private float energy;

    private float time;

    private float transaction;

    public float getEnergy() {
        return Float.valueOf(Calc.decimalPoint3.format(energy));
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getTime() {
        return Float.valueOf(Calc.decimalPoint3.format(time));
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTransaction() {
        return Float.valueOf(Calc.decimalPoint3.format(transaction));
    }

    public void setTransaction(float transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "RateDTO{" +
                "energy=" + String.format("%.3f", energy) +
                ", time=" + String.format("%.3f", time) +
                ", transaction=" + String.format("%.3f", transaction) +
                '}';
    }
}
