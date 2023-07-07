package com.example.backend;

import java.util.Objects;

public class Location {
    private final double xPosition;
    private final double yPosition;

    public Location(double xPosition, double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;

    }

    public double getxPosition() {
        return xPosition;
    }


    public double getyPosition() {
        return yPosition;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return xPosition == location.xPosition && yPosition == location.yPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xPosition, yPosition);
    }


}
