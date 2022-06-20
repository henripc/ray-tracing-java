package com.henripc.ray;

public class Main {
    public static Vector rayColor(final Ray r, final HittableList world, final int depth) {
        final HitRecord rec = new HitRecord();

        // If we've exceeded the ray bounce limit, no more light is gathered.
        if (depth <= 0) return new Color(0, 0, 0);

        if (world.hit(r, 0.001, RtWeekend.INFINITY, rec)) {
            final Vector target = Vector.sumOfVectors(rec.p, rec.normal, Vector.randomUniVector());
            return rayColor(new Ray(rec.p, Vector.sumOfVectors(target, rec.p.scalarMultiplication(-1))), world, depth - 1).scalarMultiplication(0.5);
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
        final int samplesPerPixel = 100;
        final int maxDepth = 50;

        // World
        final HittableList world = new HittableList();
        world.add(new Sphere(new Point3(0, 0, -1), 0.5));
        world.add(new Sphere(new Point3(0, -100.5, -1), 100));

        // Camera
        final Camera camera = new Camera();

        // Render
        System.out.println("P3\n" + imageWidth + " " + imageHeight + "\n255");

        for (int j = imageHeight - 1; j >= 0; j--) {
            System.err.println("\rScanlines remaining: " + j);
            for (int i = 0; i < imageWidth; i++) {
                Vector pixelColor = new Color(0, 0, 0);
                for (int s = 0; s < samplesPerPixel; s++) {
                    final double u = (i + RtWeekend.randomDouble()) / (imageWidth - 1);
                    final double v = (j + RtWeekend.randomDouble()) / (imageHeight - 1);
                    final Ray r = camera.getRay(u, v);
                    pixelColor = Vector.sumOfVectors(pixelColor, rayColor(r, world, maxDepth));                    
                }
                Color.writeColor(pixelColor, samplesPerPixel);
            }
        }

        System.err.println("\nDone.");
    }
}
