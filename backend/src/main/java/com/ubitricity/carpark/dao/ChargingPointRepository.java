package com.ubitricity.carpark.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChargingPointRepository extends CrudRepository<ChargingPointDAO, Long> {

    @Query(value = "Select * from charging_points where status='available'", nativeQuery = true)
    List<ChargingPointDAO> findAvailableChargingPoint();

    @Query(value = "Select sum(ampers) from charging_points where status='occupied'", nativeQuery = true)
    Integer getAvailableAmpers();

    @Query(value = "Select * from charging_points where status = 'occupied' and ampers = 20 order by parking_time", nativeQuery = true)
    List<ChargingPointDAO> getOldestFastCharging();

    @Query(value = "select * from charging_points where car_id = ?1", nativeQuery = true)
    ChargingPointDAO findByCarId(String carId);

}
