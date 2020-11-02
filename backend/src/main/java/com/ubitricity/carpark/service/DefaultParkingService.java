package com.ubitricity.carpark.service;

import com.ubitricity.carpark.dao.ChargingPointDAO;
import com.ubitricity.carpark.dao.ChargingPointRepository;
import com.ubitricity.carpark.exceptions.CarAlreadyRegistered;
import com.ubitricity.carpark.exceptions.CarNotRegistered;
import com.ubitricity.carpark.model.ChargingPoint;
import com.ubitricity.carpark.model.ParkingReport;
import com.ubitricity.carpark.model.ParkingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultParkingService implements ParkingService {

    @Value("${available.ampers}")
    Integer maxAmpers;

    private final Integer FAST_CHARGE_AMPERS = 20;
    private final Integer SLOW_CHARGE_AMPERS = 10;
    private final String  OCCUPIED = "occupied";
    private final String  AVAILABLE = "available";

    private static final Logger log = LoggerFactory.getLogger(DefaultParkingService.class);
    private final ChargingPointRepository chargingPointRepository;


    public DefaultParkingService(ChargingPointRepository chargingPointRepository) {
        this.chargingPointRepository = chargingPointRepository;
    }

    @Override
    public ParkingResponse parkCar(String carId) {
        List<ChargingPointDAO> availableChargingPoint = this.chargingPointRepository.findAvailableChargingPoint();

        if (availableChargingPoint.size() == 0) {
            ParkingResponse parkingResponse = new ParkingResponse();
            parkingResponse.setStatus(ParkingResponse.StatusEnum.FAILURE);
            return parkingResponse;
        }

        Integer usedAmpers = this.chargingPointRepository.getAvailableAmpers();
        if (usedAmpers == null) {
            usedAmpers = 0;
        }
        Integer availableAmpers = maxAmpers - usedAmpers;
        Integer targetAmpers = null;
        if (availableAmpers >= FAST_CHARGE_AMPERS) {
            targetAmpers = FAST_CHARGE_AMPERS;
        } else if (availableAmpers >= SLOW_CHARGE_AMPERS) {
            targetAmpers = SLOW_CHARGE_AMPERS;
        } else {
            List<ChargingPointDAO> oldestFastCharging = this.chargingPointRepository.getOldestFastCharging();
            if (oldestFastCharging.size() == 0) {
                ParkingResponse parkingResponse = new ParkingResponse();
                parkingResponse.setStatus(ParkingResponse.StatusEnum.FAILURE);
                return parkingResponse;
            }
            ChargingPointDAO oldest = oldestFastCharging.get(0);
            oldest.setAmpers(SLOW_CHARGE_AMPERS);
            this.chargingPointRepository.save(oldest);
            targetAmpers = SLOW_CHARGE_AMPERS;
        }

        ChargingPointDAO place = availableChargingPoint.get(0);
        ParkingResponse response = new ParkingResponse();
        try {
            place.setAmpers(targetAmpers);
            place.setCarId(carId);
            place.setStatus(OCCUPIED);
            place.setParkingTime(LocalDateTime.now());
            this.chargingPointRepository.save(place);

            response.setStatus(ParkingResponse.StatusEnum.SUCCESS);
            ParkingResponse.CharingTypeEnum chargingType = targetAmpers == FAST_CHARGE_AMPERS ?
                    ParkingResponse.CharingTypeEnum.FAST : ParkingResponse.CharingTypeEnum.SLOW;
            response.setCharingType(chargingType);
            response.setPlace("CP" + place.getId());
        } catch (DataIntegrityViolationException e) {
            log.error("Car already registered on parking");
            throw new CarAlreadyRegistered("Car already registered");
        }
        return response;
    }

    @Override
    public ParkingResponse unparkCar(String carId) {
        ChargingPointDAO chargingPoint = this.chargingPointRepository.findByCarId(carId);

        if (chargingPoint == null) {
            log.error("Car is not registered on parking");
            throw new CarNotRegistered("Car is not registered on parking yet");
        }

        chargingPoint.setStatus(AVAILABLE);
        chargingPoint.setAmpers(null);
        chargingPoint.setCarId(null);
        this.chargingPointRepository.save(chargingPoint);

        ParkingResponse response = new ParkingResponse();
        response.setStatus(ParkingResponse.StatusEnum.SUCCESS);
        return response;
    }

    @Override
    public ParkingReport getReport() {
        ParkingReport report = new ParkingReport();
        Iterable<ChargingPointDAO> chargingPointDAOS = chargingPointRepository.findAll();
        for (ChargingPointDAO chargingPointDAO : chargingPointDAOS) {
            ChargingPoint chargingPoint = new ChargingPoint();
            chargingPoint.setId("CP" + chargingPointDAO.getId());
            chargingPoint.setStatus(ChargingPoint.StatusEnum.fromValue(chargingPointDAO.getStatus()));
            Integer ampers = chargingPointDAO.getAmpers();
            if (ampers != null) {
                chargingPoint.setAmpers(BigDecimal.valueOf(chargingPointDAO.getAmpers()));
            }
            report.add(chargingPoint);
        }
        return report;
    }

}
