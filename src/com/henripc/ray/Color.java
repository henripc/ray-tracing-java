package com.henripc.ray;

public class Color extends Vector {
    public Color(final double e0, final double e1, final double e2) {
        super(e0, e1, e2);
    }

    public static void writeColor(final Vector pixelColor, final int samplesPerPixel) {
        double r = pixelColor.x();
        double g = pixelColor.y();
        double b = pixelColor.z();

        // Divide the color by the number of samples.
        final double scale = 1.0 / samplesPerPixel;
        r *= scale;
        g *= scale;
        b *= scale;

        // Write the translated [0,255] value of each color component.
        final String result = ((int) (256 * RtWeekend.clamp(r, 0, 0.999))) + " " +
                              ((int) (256 * RtWeekend.clamp(g, 0, 0.999))) + " " +
                              ((int) (256 * RtWeekend.clamp(b, 0, 0.999)));
        System.out.println(result);
    }
}
