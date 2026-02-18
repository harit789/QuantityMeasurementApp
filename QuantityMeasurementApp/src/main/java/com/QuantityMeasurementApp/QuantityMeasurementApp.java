package com.QuantityMeasurementApp;

public class QuantityMeasurementApp {

	// Feet Class
	static class Feet {
		private final double value;

		public Feet(double value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;

			if (obj == null)
				return false;

			if (getClass() != obj.getClass())
				return false;

			Feet other = (Feet) obj;

			return Double.compare(this.value, other.value) == 0;
		}
	}

	// Inches Class
	static class Inches {
		private final double value;

		public Inches(double value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj)
				return true;

			if (obj == null)
				return false;

			if (getClass() != obj.getClass())
				return false;

			Inches other = (Inches) obj;

			return Double.compare(this.value, other.value) == 0;
		}
	}

	public static boolean checkFeetEquality(double value1, double value2) {
		Feet f1 = new Feet(value1);
		Feet f2 = new Feet(value2);
		return f1.equals(f2);
	}

	public static boolean checkInchesEquality(double value1, double value2) {
		Inches i1 = new Inches(value1);
		Inches i2 = new Inches(value2);
		return i1.equals(i2);
	}

	public static void main(String[] args) {

		System.out.println("Feet Equality (1.0 ft, 1.0 ft): " + checkFeetEquality(1.0, 1.0));

		System.out.println("Inches Equality (1.0 in, 1.0 in): " + checkInchesEquality(1.0, 1.0));
	}
}
