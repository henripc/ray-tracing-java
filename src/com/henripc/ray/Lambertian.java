package com.henripc.ray;

public class Lambertian implements Material {
    public Vector albedo;   // Color

    public Lambertian(final Vector a) {
        this.albedo = a;
    }

    @Override
    public boolean scatter(final Ray rIn, final HitRecord rec, Color attenuation, Ray scattered) {
        Vector scatterDirection = Vector.sumOfVectors(rec.normal, Vector.randomUniVector());

        // Catch degenerate scatter direction
        if (scatterDirection.nearZero()) scatterDirection = rec.normal;

        scattered.orig = rec.p;
        scattered.dir = scatterDirection;
        scattered.tm = rIn.getTime();
        attenuation.e[0] = this.albedo.x();
        attenuation.e[1] = this.albedo.y();
        attenuation.e[2] = this.albedo.z();

        return true;
    }    
}
