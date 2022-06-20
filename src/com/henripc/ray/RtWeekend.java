package com.henripc.ray;

public abstract class RtWeekend {
    // Constants
    public final static double INFINITY = Double.POSITIVE_INFINITY;
    public final static double PI =  Math.PI;

    // Utility Functions
    public static double degreesToRadians(final double degrees) {
        return degrees * PI / 180;
    }

    public static double randomDouble() {
        // Returns a random real in [0,1).
        return Math.random();
    }

    public static double randomDouble(final double min, final double max) {
        // Returns a random real in [min,max).
        return min + (max - min) * randomDouble();
    }

    public static double clamp(final double x, final double min, final double max) {
        if (x < min) return min;
        if (x > max) return max;
        
        return x;
    }
}
