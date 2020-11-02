package com.ubitricity.carpark.controller;

import com.ubitricity.carpark.api.ReportApi;
import com.ubitricity.carpark.model.ParkingReport;
import com.ubitricity.carpark.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController implements ReportApi {

    private final ParkingService parkingService;

    public ReportController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }


    @Override
    public ResponseEntity<ParkingReport> getReport() {
        return ResponseEntity.ok(parkingService.getReport());
    }
}
