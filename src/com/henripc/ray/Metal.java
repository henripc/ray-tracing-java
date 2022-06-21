package com.henripc.ray;

public class Metal implements Material {
    public Color albedo;

    public Metal(final Color a) {
        this.albedo = a;
    }

    @Override
    public boolean scatter(final Ray rIn, final HitRecord rec, Color attenuation, Ray scattered) {
        Vector reflected = Vector.reflect(Vector.unitVector(rIn.getDirection()), rec.normal);
        scattered.orig = rec.p;
        scattered.dir = reflected;
        attenuation.e[0] = this.albedo.x();
        attenuation.e[1] = this.albedo.y();
        attenuation.e[2] = this.albedo.z();

        return Vector.dot(scattered.getDirection(), rec.normal) > 0;
    }
}
