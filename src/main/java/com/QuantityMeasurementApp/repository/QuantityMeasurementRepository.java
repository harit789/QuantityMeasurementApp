package com.QuantityMeasurementApp.repository;

import com.QuantityMeasurementApp.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository 
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Find all records by operation type (e.g. "compare", "add")
    List<QuantityMeasurementEntity> findByOperation(String operation);

    // Find all records by measurement type (e.g. "LengthUnit")
    List<QuantityMeasurementEntity> findByMeasurementType(String measurementType);

    // Find all error records
    List<QuantityMeasurementEntity> findByErrorTrue();

}