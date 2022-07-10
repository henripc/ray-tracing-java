package com.henripc.ray;

public class Sphere implements Hittable {
    public Point3 center;
    public double radius;
    public Material material;

    public Sphere() {}
    public Sphere(final Point3 center, final double radius, final Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public boolean hit(final Ray r, final double tMin, final double tMax, final HitRecord rec) {
        final Vector oc = Vector.sumOfVectors(r.getOrigin(), this.center.scalarMultiplication(-1));
        final double a = r.getDirection().lengthSquared();
        final double halfB = Vector.dot(oc, r.getDirection());
        final double c = oc.lengthSquared() - this.radius * this.radius;

        final double discriminant = halfB * halfB - a * c;
        if (discriminant < 0) return false;
        final double sqrtD = Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        double root = (-halfB - sqrtD) / a;
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtD) / a;
            if (root < tMin || tMax < root) return false;
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        final Vector outwardNormal = Vector.sumOfVectors(rec.p, this.center.scalarMultiplication(-1)).scalarDivision(this.radius);
        rec.setFaceNormal(r, outwardNormal);
        rec.mat = this.material;

        return true;
    }

    @Override
    public boolean boundingBox(final double time0, final double time1, final AABB outputBox) {
        outputBox.minimum = Vector.sumOfVectors(this.center,
                                                new Vec3(this.radius, this.radius, this.radius).scalarMultiplication(-1));
        outputBox.maximum = Vector.sumOfVectors(this.center,
                                                new Vec3(this.radius, this.radius, this.radius));
        
        return true;
    }
}
