package com.github.valeryad.entities.carfeatures;

public enum CarColors {
    RED, WHITE, BLACK, GREEN, SILVER, PURPLE, PINK;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

}
