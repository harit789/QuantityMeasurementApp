package com.QuantityMeasurementApp.service;

import com.QuantityMeasurementApp.unit.*;
import com.QuantityMeasurementApp.Quantity;
import com.QuantityMeasurementApp.model.*;
import com.QuantityMeasurementApp.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuantityMeasurementServiceImpl 
        implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repo;

    // Get correct unit based on measurement type
    private IMeasurable getUnit(String measurementType, String unit) {
        switch (measurementType.toUpperCase()) {
            case "LENGTH":
                return LengthUnit.valueOf(unit);
            case "TEMPERATURE":
                return TemperatureUnit.valueOf(unit);
            case "VOLUME":
                return VolumeUnit.valueOf(unit);
            case "WEIGHT":
                return WeightUnit.valueOf(unit);
            default:
                throw new IllegalArgumentException(
                    "Unknown measurement type: " + measurementType);
        }
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = getUnit(q1.getType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getType(), q2.getUnit());

        Quantity<IMeasurable> a = new Quantity<>(q1.getValue(), unit1);
        Quantity<IMeasurable> b = new Quantity<>(q2.getValue(), unit2);
        boolean result = a.equals(b);

        repo.save(new QuantityMeasurementEntity(
            "COMPARE", q1.getType(), String.valueOf(result)));
        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO q, String target) {
        IMeasurable fromUnit = getUnit(q.getType(), q.getUnit());
        IMeasurable toUnit = getUnit(q.getType(), target);

        Quantity<IMeasurable> a = new Quantity<>(q.getValue(), fromUnit);
        Quantity<IMeasurable> r = a.convertTo(toUnit);

        repo.save(new QuantityMeasurementEntity(
            "CONVERT", q.getType(), r.toString()));
        return new QuantityDTO(r.getValue(), target, q.getType());
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = getUnit(q1.getType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getType(), q2.getUnit());

        Quantity<IMeasurable> a = new Quantity<>(q1.getValue(), unit1);
        Quantity<IMeasurable> b = new Quantity<>(q2.getValue(), unit2);
        Quantity<IMeasurable> r = a.add(b);

        repo.save(new QuantityMeasurementEntity(
            "ADD", q1.getType(), r.toString()));
        return new QuantityDTO(r.getValue(), 
                               r.getUnit().getUnitName(), q1.getType());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = getUnit(q1.getType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getType(), q2.getUnit());

        Quantity<IMeasurable> a = new Quantity<>(q1.getValue(), unit1);
        Quantity<IMeasurable> b = new Quantity<>(q2.getValue(), unit2);
        Quantity<IMeasurable> r = a.subtract(b);

        repo.save(new QuantityMeasurementEntity(
            "SUBTRACT", q1.getType(), r.toString()));
        return new QuantityDTO(r.getValue(), 
                               r.getUnit().getUnitName(), q1.getType());
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {
        IMeasurable unit1 = getUnit(q1.getType(), q1.getUnit());
        IMeasurable unit2 = getUnit(q2.getType(), q2.getUnit());

        Quantity<IMeasurable> a = new Quantity<>(q1.getValue(), unit1);
        Quantity<IMeasurable> b = new Quantity<>(q2.getValue(), unit2);
        double r = a.divide(b);

        repo.save(new QuantityMeasurementEntity(
            "DIVIDE", q1.getType(), String.valueOf(r)));
        return r;
    }

    // Get history by operation
    public List<QuantityMeasurementEntity> getHistoryByOperation(
            String operation) {
        return repo.findByOperation(operation);
    }

    // Get history by measurement type
    public List<QuantityMeasurementEntity> getHistoryByType(
            String measurementType) {
        return repo.findByMeasurementType(measurementType);
    }
}