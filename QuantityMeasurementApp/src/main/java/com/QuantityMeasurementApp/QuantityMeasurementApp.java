package com.QuantityMeasurementApp;

public class QuantityMeasurementApp {

	// ===== ENUM =====
	enum LengthUnit {

		FEET(1.0), INCH(1.0 / 12);

		private final double conversionFactor;

		LengthUnit(double conversionFactor) {
			this.conversionFactor = conversionFactor;
		}

		public double getConversionFactor() {
			return conversionFactor;
		}
	}

	// ✅ Make this static
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

	public static void main(String[] args) {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

		System.out.println("Are Equal: " + q1.equals(q2));

		QuantityLength q3 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q4 = new QuantityLength(1.0, LengthUnit.INCH);

		System.out.println("Are Equal: " + q3.equals(q4));
	}
}
