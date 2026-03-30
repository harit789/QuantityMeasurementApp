package com.QuantityMeasurementApp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String operation;

    @Column(nullable = false)
    private String measurementType;

    private String result;

    private boolean error;

    private String message;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    // Default constructor - required by JPA
    public QuantityMeasurementEntity() {}

    // Constructor for successful operations
    public QuantityMeasurementEntity(String operation, String measurementType, String result) {
        this.operation = operation;
        this.measurementType = measurementType;
        this.result = result;
        this.error = false;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for error operations
    public QuantityMeasurementEntity(String operation, String measurementType, String message, boolean error) {
        this.operation = operation;
        this.measurementType = measurementType;
        this.message = message;
        this.error = error;
        this.createdAt = LocalDateTime.now();
    }

    // Auto set createdAt before saving to DB
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public boolean hasError() { return error; }
    public void setError(boolean error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Entity[op=" + operation + ", type=" + measurementType + ", result=" + result + "]";
    }
}