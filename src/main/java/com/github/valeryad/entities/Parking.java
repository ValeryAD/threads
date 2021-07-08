package com.github.valeryad.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Parking {
    private static final int WAITING_TIME_LIMIT = 1800;
    private final int SPOTS_LIMIT = 2;

    private static final String INTERRUPTED_ERROR_MESSAGE = "Because of technical issues the %s can't take place at the parking";
    private static final String CAR_IS_APPROACHING_MESSAGE = "%s is approaching the parking. Parking has %d free spots%n";
    private static final String FULL_PARKING_MESSAGE = "The parking is full and %s is waiting for releasing spot%n";
    private static final String DRIVE_AWAY_MESSAGE = "%s can't wait any more and drive away%n";
    private static final String TAKE_SPOT_MESSAGE = "s take spot at the parking%n";
    private static final String LEAVING_PARKING_MESSAGE = "%s leaving the parking%n";


    private ReentrantLock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();

    private List<Car> placedCars = new LinkedList<>();

    public boolean placeCar(Car car) {

        try {
            lock.lock();
            System.out.printf(CAR_IS_APPROACHING_MESSAGE, car, SPOTS_LIMIT - placedCars.size());
            while (placedCars.size() >= SPOTS_LIMIT) {
                System.out.printf(FULL_PARKING_MESSAGE, car);
                if (!condition.await(WAITING_TIME_LIMIT, TimeUnit.MILLISECONDS)) {
                    System.out.printf(DRIVE_AWAY_MESSAGE, car);
                    return false;
                }
            }

            placedCars.add(car);
            System.out.printf("%" + TAKE_SPOT_MESSAGE, car);

        } catch (InterruptedException e) {
            System.err.printf(INTERRUPTED_ERROR_MESSAGE, car);
        } finally {
            lock.unlock();
        }
        return true;
    }

    public void releaseCar(Car car) {
        try {
            lock.lock();
            placedCars.remove(car);
            System.out.printf(LEAVING_PARKING_MESSAGE, car);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}


