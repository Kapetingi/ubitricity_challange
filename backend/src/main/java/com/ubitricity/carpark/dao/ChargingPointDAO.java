package com.ubitricity.carpark.dao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "charging_points")
public class ChargingPointDAO {

    @Id
    private long id;

    private Integer ampers;
    private String status;
    private String carId;
    private LocalDateTime parkingTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getAmpers() {
        return ampers;
    }

    public void setAmpers(Integer ampers) {
        this.ampers = ampers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    public void setParkingTime(LocalDateTime parkingTime) {
        this.parkingTime = parkingTime;
    }
}
