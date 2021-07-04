package com.github.valeryad.entities.carfeatures;

public enum CarModels {
    FORD, BMW, OPEL, TESLA, AUDI, TOYOTA, NISSAN, MAZDA, KIA;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
