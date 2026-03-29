package com.QuantityMeasurementApp.unit;

public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public String getUnitName() {
        return name();
    }

	@Override
	public double convertToBaseUnit(double value) {
		// TODO Auto-generated method stub
		return value*factor;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		// TODO Auto-generated method stub
		return baseValue / factor;
	}
}