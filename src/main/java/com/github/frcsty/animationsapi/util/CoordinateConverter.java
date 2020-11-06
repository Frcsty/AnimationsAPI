package com.github.frcsty.animationsapi.util;

public final class CoordinateConverter {

    /**
     * Converts the given degrees to radians
     *
     * @param degrees    Given degrees
     * @return  Returns degrees converted to radians
     */
    public static double toRadians(final int degrees) {
        return degrees * Math.PI / 180;
    }

}
