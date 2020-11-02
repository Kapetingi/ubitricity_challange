package com.ubitricity.carpark.controller;

import com.ubitricity.carpark.api.CarApi;
import com.ubitricity.carpark.model.Car;
import com.ubitricity.carpark.model.ParkingResponse;
import com.ubitricity.carpark.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CarController implements CarApi {

    private final ParkingService parkingService;

    public CarController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Override
    public ResponseEntity<ParkingResponse> parkCar(@Valid Car body) {
        try {
            ParkingResponse parkingResponse = parkingService.parkCar(body.getCarId());
            return ResponseEntity.ok(parkingResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ParkingResponse> unparkCar(@Valid Car body) {
        try {
            ParkingResponse parkingResponse = parkingService.unparkCar(body.getCarId());
            return ResponseEntity.ok(parkingResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
