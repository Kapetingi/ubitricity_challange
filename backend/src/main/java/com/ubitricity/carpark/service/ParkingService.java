package com.ubitricity.carpark.service;

import com.ubitricity.carpark.model.ParkingReport;
import com.ubitricity.carpark.model.ParkingResponse;


public interface ParkingService {

    ParkingResponse parkCar (String carId);

    ParkingResponse unparkCar(String carId);

    ParkingReport getReport();

}
