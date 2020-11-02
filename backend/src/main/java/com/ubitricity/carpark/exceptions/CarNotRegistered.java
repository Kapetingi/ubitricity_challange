package com.ubitricity.carpark.exceptions;

public class CarNotRegistered extends RuntimeException {

    public CarNotRegistered() {
        super();
    }

    public CarNotRegistered(String message) {
        super(message);
    }

    public CarNotRegistered(String message, Throwable cause) {
        super(message, cause);
    }

}
