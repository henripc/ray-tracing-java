package com.henripc.ray;

public class Color extends Vec3 {
    public Color(final double e0, final double e1, final double e2) {
        super(e0, e1, e2);
    }

    public static void writeColor(Color pixelColor) {
        // Write the translated [0,255] value of each color component.
        final String result = (int) (255.999 * pixelColor.x()) + " " +
                              (int) (255.999 * pixelColor.x()) + " " +
                              (int) (255.999 * pixelColor.x());
        System.out.println(result);
    }
}
