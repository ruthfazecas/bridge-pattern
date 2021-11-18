package org.example.model;


public enum VAT {
    FOOD(0.09), DESERTS(0.1), SODAS(0.12), NATURAL_DRINKS(0.05);

    private final double percent;

    VAT(double percent) {
        this.percent = percent;
    }

    public double getPercent() {
        return percent;
    }
}
