package com.QuantityMeasurementApp.unit;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    @Override
    public double getConversionFactor() {
        return factor;
    }

    @Override
    public String getUnitName() {
        return name();
    }

	@Override
	public double convertToBaseUnit(double value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		// TODO Auto-generated method stub
		return 0;
	}
}