package com.henripc.ray;

/** Abstract class that contains utility functions and constants. */
public abstract class RtWeekend {
    // Constants
    /** Represents positive {@code}infinity{@code}. */
    public final static double INFINITY = Double.POSITIVE_INFINITY;
    public final static double PI =  Math.PI;

    // Utility Functions
    /**
     * Converts the given angle from degree to radians.
     * @param degrees angle in degrees
     * @return the converted value.
     */
    public static double degreesToRadians(final double degrees) {
        return degrees * PI / 180;
    }

    /**
     * Returns a random {@code}double{@code} between {@code}0.0{@code} (inclusive) and {@code}1.0{@code} (exclusive).
     * @return a pseudorandom {@code}double{@code} in [0, 1).
     */
    public static double randomDouble() {
        // Returns a random real in [0,1).
        return Math.random();
    }

    /**
     * Returns a random {@code}double{@code} between the given values.
     * @param min lower bound
     * @param max upper bound
     * @return a pseudorandom {@code}double{@code} in [min, max).
     */
    public static double randomDouble(final double min, final double max) {
        // Returns a random real in [min,max).
        return min + (max - min) * randomDouble();
    }

    /**
     * Clamps the given value to the interval [min, max].
     * @param x the value to be clamped
     * @param min lower bound
     * @param max upper bound
     * @return {@code}x{@code} if the value in [min, max]. 
     * If {@code}x < min{@code} returns {@code}min{@code}, 
     * if {@code}x > max{@code} returns {@code}max{@code}.
     */
    public static double clamp(final double x, final double min, final double max) {
        if (x < min) return min;
        if (x > max) return max;
        
        return x;
    }

    /**
     * Returns a random {@code}int{@code} between the given values.
     * @param min lower bound
     * @param max upper bound
     * @return a pseudorandom {@code}int{@code} in [min, max].
     */
    public static int randomInt(final int min, final int max) {
        // Returns a random integer in [min,max].
        return (int) randomDouble(min, max + 1);
    }
}
