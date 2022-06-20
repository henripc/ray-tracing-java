package com.henripc.ray;

public class Main {
    public static Vector rayColor(final Ray r, final HittableList world) {
        final HitRecord rec = new HitRecord();

        if (world.hit(r, 0, RtWeekend.INFINITY, rec)) {
            return Vector.sumOfVectors(rec.normal, new Color(1, 1, 1)).scalarMultiplication(0.5);
        }

        final Vector unitDirection = Vector.unitVector(r.getDirection());
        final double t = 0.5 * (unitDirection.y() + 1);

        final Vector firstColor = (new Color(1, 1, 1)).scalarMultiplication(1.0 - t);
        final Vector secondColor = (new Color(0.5, 0.7, 1)).scalarMultiplication(t);

        return Vector.sumOfVectors(firstColor, secondColor);
    }

    public static void main(String[] args) throws Exception {
        // Image
        final double aspectRatio = 16 / 9.0;
        final int imageWidth = 400;
        final int imageHeight = (int) (imageWidth / aspectRatio);

        // World
        final HittableList world = new HittableList();
        world.add(new Sphere(new Point3(0, 0, -1), 0.5));
        world.add(new Sphere(new Point3(0, -100.5, -1), 100));

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

                final Vector pixelColor = rayColor(r, world);
                Color.writeColor(pixelColor);
            }
        }

        System.err.println("\nDone.");
    }
}
