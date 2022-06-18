package com.henripc.ray;

public class Color extends Vector {
    public Color(final double e0, final double e1, final double e2) {
        super(e0, e1, e2);
    }

    public static void writeColor(Vector pixelColor) {
        // Write the translated [0,255] value of each color component.
        final String result = ((int) (255.999 * pixelColor.x())) + " " +
                              ((int) (255.999 * pixelColor.y())) + " " +
                              ((int) (255.999 * pixelColor.z()));
        System.out.println(result);
    }
}
