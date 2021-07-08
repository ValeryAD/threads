package com.github.valeryad.entities;

import com.github.valeryad.entities.carfeatures.CarColors;
import com.github.valeryad.entities.carfeatures.CarModels;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Car extends Thread {
    private static final String INTERRUPTED_ERROR_MESSAGE = "Something got wrong and %s can't wait any more";

    private static final int BASE_PARKING_TIME = 1200;
    private static final int MAX_INCREMENT_OF_PARKING_TIME = 4000;

    private CarModels model;
    private CarColors color;
    private Parking parking;
    private final int parkingTime;

    public Car(CarModels model, CarColors color, Parking parking) {
        this.model = model;
        this.color = color;
        this.parking = parking;
        parkingTime = BASE_PARKING_TIME + new Random().nextInt(MAX_INCREMENT_OF_PARKING_TIME);
    }

    @Override
    public void run() {
        boolean wasPlacedInParking = parking.placeCar(this);
        try {
            TimeUnit.MILLISECONDS.sleep(parkingTime);
        } catch (InterruptedException e) {
            System.err.printf(INTERRUPTED_ERROR_MESSAGE, this);
        }
        if (wasPlacedInParking) {
            parking.releaseCar(this);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s", color, model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Car car = (Car) o;
        return (car.model != null ? model.equals(car.model) : model == null) &&
                (car.color != null ? color.equals(car.color) : color == null);
    }

    @Override
    public int hashCode() {
        int result = 31 + (model != null ? model.hashCode() : 0);
        result = result * 31 + (color != null ? color.hashCode() : 0);
        return result;
    }
}
