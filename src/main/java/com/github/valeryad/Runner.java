package com.github.valeryad;


import com.github.valeryad.entities.Parking;
import com.github.valeryad.entities.Traffic;

public class Runner {
    public static void main(String[] args) {
        Parking parking = new Parking();
        new Thread(new Traffic(6, parking), "traffic").start();
    }
}
