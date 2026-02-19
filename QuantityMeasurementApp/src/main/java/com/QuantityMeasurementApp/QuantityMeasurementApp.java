package com.QuantityMeasurementApp;

public class QuantityMeasurementApp {

	// ENUM
	enum LengthUnit {

		FEET(1.0), 
		INCH(1.0 / 12), 
		YARD(3.0), 
		CENTIMETER(0.393701 / 12);

		private final double conversionFactor;

		LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	// ===== GENERIC CLASS =====
	public static class QuantityLength {

		private final double value;
		private final LengthUnit unit;

		public QuantityLength(double value, LengthUnit unit) {

			if (unit == null) {
				throw new IllegalArgumentException("Unit cannot be null");
			}

			this.value = value;
			this.unit = unit;
		}

		private double toFeet() {
			return this.value * this.unit.getConversionFactor();
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;

			if (obj == null)
				return false;

			if (getClass() != obj.getClass())
				return false;

			QuantityLength other = (QuantityLength) obj;

			return Double.compare(this.toFeet(), other.toFeet()) == 0;
		}
	}

	// MAIN METHOD 
	public static void main(String[] args) {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);

		QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);

		System.out.println("1 Yard == 3 Feet ? " + q1.equals(q2));

		QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARD);

		QuantityLength q4 = new QuantityLength(36.0, LengthUnit.INCH);

		System.out.println("1 Yard == 36 Inches ? " + q3.equals(q4));

		QuantityLength q5 = new QuantityLength(1.0, LengthUnit.CENTIMETER);

		QuantityLength q6 = new QuantityLength(0.393701, LengthUnit.INCH);

		System.out.println("1 cm == 0.393701 inch ? " + q5.equals(q6));
	}
}
