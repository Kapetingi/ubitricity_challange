package com.ubitricity.carpark.exceptions;

public class CarAlreadyRegistered extends RuntimeException {

    public CarAlreadyRegistered() {
        super();
    }

    public CarAlreadyRegistered(String message) {
        super(message);
    }

    public CarAlreadyRegistered(String message, Throwable cause) {
        super(message, cause);
    }
}
