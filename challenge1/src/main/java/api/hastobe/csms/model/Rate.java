package api.hastobe.csms.model;

public class Rate {
    private float energy;

    private float time;

    private float transaction;

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTransaction() {
        return transaction;
    }

    public void setTransaction(float transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "energy=" + energy +
                ", time=" + time +
                ", transaction=" + transaction +
                '}';
    }
}
