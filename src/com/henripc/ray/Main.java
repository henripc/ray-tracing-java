package com.henripc.ray;

public class Main {
    public static Vector rayColor(final Ray r, final HittableList world, final int depth) {
        final HitRecord rec = new HitRecord();

        // If we've exceeded the ray bounce limit, no more light is gathered.
        if (depth <= 0) return new Color();

        if (world.hit(r, 0.001, RtWeekend.INFINITY, rec)) {
            final Ray scattered = new Ray();
            final Color attenuation = new Color();

            if (rec.mat.scatter(r, rec, attenuation, scattered)) {
                return Vector.multiplicationOfVectors(attenuation, rayColor(scattered, world, depth - 1));
            }
            
            return new Color();
        }

        final Vector unitDirection = Vector.unitVector(r.getDirection());
        final double t = 0.5 * (unitDirection.y() + 1);

        final Vector firstColor = (new Color(1, 1, 1)).scalarMultiplication(1.0 - t);
        final Vector secondColor = (new Color(0.5, 0.7, 1)).scalarMultiplication(t);

        return Vector.sumOfVectors(firstColor, secondColor);
    }

    public static HittableList randomScene() {
        final HittableList world = new HittableList();

        final Material materialGround = new Lambertian(new Color(0.5, 0.5, 0.5));
        world.add(new Sphere(new Point3(0, -1000, 0), 1000, materialGround));

        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                final double chooseMat = RtWeekend.randomDouble();
                final Point3 center = new Point3(a + 0.9 * RtWeekend.randomDouble(), 0.2, b + 0.9 * RtWeekend.randomDouble());

                if (Vector.sumOfVectors(center, (new Point3(4, 0.2, 0)).scalarMultiplication(-1)).length() > 0.9) {
                    Material sphereMaterial;

                    if (chooseMat < 0.8) {
                        // diffuse
                        final Vector albedo = Vector.multiplicationOfVectors(Color.random(), Color.random());
                        sphereMaterial = new Lambertian(albedo);
                        final Vector center2 = Vector.sumOfVectors(center, new Vec3(0, RtWeekend.randomDouble(0, 0.5), 0));
                        world.add(new MovingSphere(center, center2, 0, 1, 0.2, sphereMaterial));
                    } else if (chooseMat < 0.95) {
                        // metal
                        final Vector albedo = Color.random(0.5, 1);
                        final double fuzz = RtWeekend.randomDouble(0, 0.5);
                        sphereMaterial = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2, sphereMaterial));
                    } else {
                        // glass
                        sphereMaterial = new Dielectric(1.5);
                        world.add(new Sphere(center, 0.2, sphereMaterial));
                    }
                }
            }
        }
        
        final Material material1 = new Dielectric(1.5);
        world.add(new Sphere(new Point3(0, 1, 0), 1, material1));
        
        final Material material2 = new Lambertian(new Color(0.4, 0.2, 0.1));
        world.add(new Sphere(new Point3(-4, 1, 0), 1, material2));

        final Material material3 = new Metal(new Color(0.7, 0.6, 0.5), 0);
        world.add(new Sphere(new Point3(4, 1, 0), 1, material3));

        return world;
    }

    public static void main(String[] args) throws Exception {
        // Image
        final double aspectRatio = 16.0 / 9.0;
        final int imageWidth = 400;
        final int imageHeight = (int) (imageWidth / aspectRatio);
        final int samplesPerPixel = 100;
        final int maxDepth = 50;

        // World
        final HittableList world = randomScene();

        // Camera
        final Point3 lookFrom = new Point3(13, 2, 3);
        final Point3 lookAt = new Point3(0, 0, 0);
        final Vec3 vUp = new Vec3(0, 1, 0);
        final double distToFocus = 10;
        final double aperture = 0.1;

        final Camera camera = new Camera(lookFrom, lookAt, vUp, 20, aspectRatio, aperture, distToFocus, 0, 1);

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
