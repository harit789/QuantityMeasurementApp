package com.QuantityMeasurementApp.controller;


import com.QuantityMeasurementApp.model.*;
import com.QuantityMeasurementApp.service.*;
import com.QuantityMeasurementApp.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // No constructor needed - Spring injects service automatically

    // POST /api/v1/quantities/compare
    // Example: {"q1": {"value": 1.0, "unit": "FEET"}, "q2": {"value": 12.0, "unit": "INCHES"}}
    @PostMapping("/compare")
    public ResponseEntity<Boolean> compare(@RequestBody CompareRequest request) {
        boolean result = service.compare(request.getQ1(), request.getQ2());
        return ResponseEntity.ok(result);
    }

    // POST /api/v1/quantities/convert
    @PostMapping("/convert")
    public ResponseEntity<QuantityDTO> convert(@RequestBody ConvertRequest request) {
        QuantityDTO result = service.convert(request.getQ(), request.getTargetUnit());
        return ResponseEntity.ok(result);
    }

    // POST /api/v1/quantities/add
    @PostMapping("/add")
    public ResponseEntity<QuantityDTO> add(@RequestBody CompareRequest request) {
        QuantityDTO result = service.add(request.getQ1(), request.getQ2());
        return ResponseEntity.ok(result);
    }

    // POST /api/v1/quantities/subtract
    @PostMapping("/subtract")
    public ResponseEntity<QuantityDTO> subtract(@RequestBody CompareRequest request) {
        QuantityDTO result = service.subtract(request.getQ1(), request.getQ2());
        return ResponseEntity.ok(result);
    }

    // POST /api/v1/quantities/divide
    @PostMapping("/divide")
    public ResponseEntity<Double> divide(@RequestBody CompareRequest request) {
        double result = service.divide(request.getQ1(), request.getQ2());
        return ResponseEntity.ok(result);
    }

    // GET /api/v1/quantities/history/operation/COMPARE
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementEntity>> getHistoryByOperation(
            @PathVariable String operation) {
        List<QuantityMeasurementEntity> history = 
            ((QuantityMeasurementServiceImpl) service).getHistoryByOperation(operation);
        return ResponseEntity.ok(history);
    }

    // GET /api/v1/quantities/history/type/LENGTH
    @GetMapping("/history/type/{type}")
    public ResponseEntity<List<QuantityMeasurementEntity>> getHistoryByType(
            @PathVariable String type) {
        List<QuantityMeasurementEntity> history = 
            ((QuantityMeasurementServiceImpl) service).getHistoryByType(type);
        return ResponseEntity.ok(history);
    }

    // ---- Inner request classes ----
    // These represent the JSON body structure for requests

    public static class CompareRequest {
        private QuantityDTO q1;
        private QuantityDTO q2;

        public QuantityDTO getQ1() { return q1; }
        public void setQ1(QuantityDTO q1) { this.q1 = q1; }
        public QuantityDTO getQ2() { return q2; }
        public void setQ2(QuantityDTO q2) { this.q2 = q2; }
    }

    public static class ConvertRequest {
        private QuantityDTO q;
        private String targetUnit;

        public QuantityDTO getQ() { return q; }
        public void setQ(QuantityDTO q) { this.q = q; }
        public String getTargetUnit() { return targetUnit; }
        public void setTargetUnit(String targetUnit) { this.targetUnit = targetUnit; }
    }
}