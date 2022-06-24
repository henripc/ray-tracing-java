package com.henripc.ray;

public class Metal implements Material {
    public Vector albedo;   // Color
    public double fuzz;

    public Metal(final Vector a, final double fuzz) {
        this.albedo = a;
        this.fuzz = fuzz < 1 ? fuzz : 1;
    }

    @Override
    public boolean scatter(final Ray rIn, final HitRecord rec, Color attenuation, Ray scattered) {
        Vector reflected = Vector.reflect(Vector.unitVector(rIn.getDirection()), rec.normal);
        scattered.orig = rec.p;
        scattered.dir = Vector.sumOfVectors(reflected, Vector.randomInUnitSphere().scalarMultiplication(this.fuzz));
        scattered.tm = rIn.getTime();
        attenuation.e[0] = this.albedo.x();
        attenuation.e[1] = this.albedo.y();
        attenuation.e[2] = this.albedo.z();

        return Vector.dot(scattered.getDirection(), rec.normal) > 0;
    }
}
