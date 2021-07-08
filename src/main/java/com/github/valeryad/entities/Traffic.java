package com.github.valeryad.entities;

import com.github.valeryad.entities.carfeatures.CarColors;
import com.github.valeryad.entities.carfeatures.CarModels;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Traffic implements Runnable {
    private static final int TIME_BETWEEN_CARS = 1000;

    private final Parking parking;
    private final int carAmount;
    private final Random random;

    public Traffic(int carAmount, Parking parking) {
        this.carAmount = carAmount;
        this.parking = parking;
        random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < carAmount; i++) {
            new Car(getRandomModel(), getRandomColor(), parking).start();
            try {
                TimeUnit.MILLISECONDS.sleep(TIME_BETWEEN_CARS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private CarModels getRandomModel() {
        CarModels[] models = CarModels.values();
        return models[random.nextInt(models.length)];
    }

    private CarColors getRandomColor() {
        CarColors[] colors = CarColors.values();
        return colors[random.nextInt(colors.length)];
    }
}
