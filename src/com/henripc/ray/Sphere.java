package com.henripc.ray;

public class Sphere implements Hittable {
    public Point3 center;
    public double radius;

    public Sphere() {}
    public Sphere(final Point3 cen, final double r) {
        this.center = cen;
        this.radius = r;
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

        return true;
    }    
}
