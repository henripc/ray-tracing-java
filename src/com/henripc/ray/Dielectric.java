package com.henripc.ray;

public class Dielectric implements Material {
    public double ir;   // Index of Refraction

    public Dielectric(final double indexOfRefraction) {
        this.ir = indexOfRefraction;
    }

    @Override
    public boolean scatter(final Ray rIn, final HitRecord rec, final Color attenuation, final Ray scattered) {
        attenuation.e[0] = 1;
        attenuation.e[1] = 1;
        attenuation.e[2] = 1;
        final double refractionRation = rec.frontFace ? (1 / this.ir) : this.ir;

        final Vector unitDirection = Vector.unitVector(rIn.getDirection());
        final double cosTheta = Math.min(Vector.dot(unitDirection.scalarMultiplication(-1), rec.normal), 1);
        final double sinTheta = Math.sqrt(1 - cosTheta * cosTheta);

        final boolean cannotRefract = refractionRation * sinTheta > 1;
        Vector direction = new Vec3();

        if (cannotRefract || reflectance(cosTheta, refractionRation) > RtWeekend.randomDouble()) {
            direction = Vector.reflect(unitDirection, rec.normal);
        } else {
            direction = Vector.refract(unitDirection, rec.normal, refractionRation);
        }

        scattered.orig = rec.p;
        scattered.dir = direction;

        return true;
    }

    private static double reflectance(final double cosine, final double refIdx) {
        // Use Schlick's approximation for reflectance.
        double r0 = (1 - refIdx) / (1 + refIdx);
        r0 = r0 * r0;

        return r0 + (1 - r0) * Math.pow(1 - cosine, 5);
    }
}
