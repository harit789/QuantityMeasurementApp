package com.QuantityMeasurementApp.unit;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        public double convertToBaseUnit(double value) {
            return value;
        }

        public double convertFromBaseUnit(double base) {
            return base;
        }

        public String getUnitName() {
            return "Celsius";
        }
    },

    FAHRENHEIT {
        public double convertToBaseUnit(double value) {
            return (value - 32) * 5 / 9;
        }

        public double convertFromBaseUnit(double base) {
            return (base * 9 / 5) + 32;
        }

        public String getUnitName() {
            return "Fahrenheit";
        }
    };

    // Temperature does NOT support arithmetic
    SupportsArithmetic supportsArithmetic = () -> false;

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support " + operation + " operation");
    }

    @Override
    public double getConversionFactor() {
        return 1.0;
    }
}