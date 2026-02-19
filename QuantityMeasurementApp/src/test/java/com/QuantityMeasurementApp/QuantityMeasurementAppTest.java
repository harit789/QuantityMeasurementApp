package com.QuantityMeasurementApp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

	// Yard Tests

	@Test
	void testEquality_YardToYard_SameValue() {

		QuantityMeasurementApp.QuantityLength q1 = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength q2 = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		assertTrue(q1.equals(q2));
	}

	@Test
	void testEquality_YardToFeet_EquivalentValue() {

		QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(3.0,
				QuantityMeasurementApp.LengthUnit.FEET);

		assertTrue(yard.equals(feet));
	}

	@Test
	void testEquality_YardToInches_EquivalentValue() {

		QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength inch = new QuantityMeasurementApp.QuantityLength(36.0,
				QuantityMeasurementApp.LengthUnit.INCH);

		assertTrue(yard.equals(inch));
	}

	@Test
	void testEquality_YardToFeet_NonEquivalentValue() {

		QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(2.0,
				QuantityMeasurementApp.LengthUnit.FEET);

		assertFalse(yard.equals(feet));
	}

	// Centimeter Tests

	@Test
	void testEquality_CentimeterToInch_EquivalentValue() {

		QuantityMeasurementApp.QuantityLength cm = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.CENTIMETER);

		QuantityMeasurementApp.QuantityLength inch = new QuantityMeasurementApp.QuantityLength(0.393701,
				QuantityMeasurementApp.LengthUnit.INCH);

		assertTrue(cm.equals(inch));
	}

	@Test
	void testEquality_CentimeterToFeet_NonEquivalentValue() {

		QuantityMeasurementApp.QuantityLength cm = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.CENTIMETER);

		QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.FEET);

		assertFalse(cm.equals(feet));
	}

	// Transitive Property

	@Test
	void testEquality_MultiUnit_TransitiveProperty() {

		QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		QuantityMeasurementApp.QuantityLength feet = new QuantityMeasurementApp.QuantityLength(3.0,
				QuantityMeasurementApp.LengthUnit.FEET);

		QuantityMeasurementApp.QuantityLength inch = new QuantityMeasurementApp.QuantityLength(36.0,
				QuantityMeasurementApp.LengthUnit.INCH);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inch));
		assertTrue(yard.equals(inch));
	}

	@Test
	void testEquality_NullUnit() {

		assertThrows(IllegalArgumentException.class, () -> new QuantityMeasurementApp.QuantityLength(1.0, null));
	}

	@Test
	void testEquality_SameReference() {

		QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		assertTrue(yard.equals(yard));
	}

	@Test
	void testEquality_NullComparison() {

		QuantityMeasurementApp.QuantityLength yard = new QuantityMeasurementApp.QuantityLength(1.0,
				QuantityMeasurementApp.LengthUnit.YARD);

		assertFalse(yard.equals(null));
	}
}
