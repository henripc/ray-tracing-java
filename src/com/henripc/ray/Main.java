package com.henripc.ray;

public class Main {
    public static Vector rayColor(final Ray r) {
        if (hitSphere(new Point3(0, 0, -1), 0.5, r)) return new Color(1, 0, 0);
        
        final Vector unitDirection = Vector.unitVector(r.getDirection());
        final double t = 0.5 * (unitDirection.y() + 1);

        final Vector firstColor = (new Color(1, 1, 1)).scalarMultiplication(1.0 - t);
        final Vector secondColor = (new Color(0.5, 0.7, 1)).scalarMultiplication(t);

        return Vector.sumOfVectors(firstColor, secondColor);
    }

    public static boolean hitSphere(final Point3 center, final double radius, final Ray r) {
        final Vector oc = Vector.sumOfVectors(r.getOrigin(), center.scalarMultiplication(-1));
        final double a = Vector.dot(r.getDirection(), r.getDirection());
        final double b = 2 * Vector.dot(oc, r.getDirection());
        final double c = Vector.dot(oc, oc) - radius * radius;
        final double discriminant = b * b - 4 * a * c;

        return discriminant > 0;
    }

    public static void main(String[] args) throws Exception {
        // Image
        final double aspectRatio = 16 / 9.0;
        final int imageWidth = 400;
        final int imageHeight = (int) (imageWidth / aspectRatio);

        // Camera
        final double viewportHeight = 2;
        final double viewportWidth = aspectRatio * viewportHeight;
        final double focalLength = 1;

        final Vector origin = new Point3(0, 0, 0);
        final Vector horizontal = new Vec3(viewportWidth, 0, 0);
        final Vector vertical = new Vec3(0, viewportHeight, 0);

        final Vector lowerLeftCorner = Vector.sumOfVectors(origin,
                                                           horizontal.scalarMultiplication(-1.0 / 2),
                                                           vertical.scalarMultiplication(-1.0 / 2),
                                                           (new Vec3(0, 0, focalLength)).scalarMultiplication(-1));

        // Render
        System.out.println("P3\n" + imageWidth + " " + imageHeight + "\n255");

        for (int j = imageHeight - 1; j >= 0; j--) {
            System.err.println("\rScanlines remaining: " + j);
            for (int i = 0; i < imageWidth; i++) {
                final double u = (double) i / (imageWidth - 1);
                final double v = (double) j / (imageHeight - 1);

                final Ray r = new Ray((Point3) origin, (Vec3) Vector.sumOfVectors(lowerLeftCorner,
                                                                                  horizontal.scalarMultiplication(u),
                                                                                  vertical.scalarMultiplication(v),
                                                                                  origin.scalarMultiplication(-1)));

                final Vector pixelColor = rayColor(r);
                Color.writeColor(pixelColor);
            }
        }

        System.err.println("\nDone.");
    }
}
