package com.QuantityMeasurementApp.model;


public class QuantityDTO {

    private double value;
    private String unit;
    private String type;

    // No-arg constructor - required by Spring to convert JSON to object
    public QuantityDTO() {}

    // Constructor with all fields
    public QuantityDTO(double value, String unit, String type) {
        this.value = value;
        this.unit = unit;
        this.type = type;
    }

    // Getters
    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public String getType() { return type; }

    // Setters - required by Spring to map JSON fields
    public void setValue(double value) { this.value = value; }
    public void setUnit(String unit) { this.unit = unit; }
    public void setType(String type) { this.type = type; }
}