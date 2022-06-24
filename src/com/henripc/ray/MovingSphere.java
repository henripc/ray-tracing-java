package com.henripc.ray;

public class MovingSphere implements Hittable {
    public Vector center0;
    public Vector center1;
    public double time0;
    public double time1;
    public double radius;
    public Material material;

    public MovingSphere() {}

    public MovingSphere(final Vector center0, final Vector center1, final double _time0, final double _time1,
                        final double radius, final Material material) {
        this.center0 = center0;
        this.center1 = center1;
        this.time0 = _time0;
        this.time1 = _time1;
        this.radius = radius;
        this.material = material;
    }

    public Vector center(final double time) {
        return Vector.sumOfVectors(this.center0,
                                   Vector.sumOfVectors(this.center1,
                                                       this.center0.scalarMultiplication(-1))
                                                       .scalarMultiplication(((time - this.time0) / (this.time1 - this.time0)))) ;
    }

    @Override
    public boolean hit(final Ray r, final double tMin, final double tMax, final HitRecord rec) {
        final Vector oc = Vector.sumOfVectors(r.getOrigin(), this.center(r.getTime()).scalarMultiplication(-1));
        final double a = r.getDirection().lengthSquared();  
        final double halfB = Vector.dot(oc, r.getDirection());
        final double c = oc.lengthSquared() - this.radius * this.radius;

        final double discriminant = halfB * halfB - a * c;
        if (discriminant < 0)
            return false;
        final double sqrtD = Math.sqrt(discriminant);

        // Find the nearest root that lies in the acceptable range.
        double root = (-halfB - sqrtD) / a;
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtD) / a;
            if (root < tMin || tMax < root)
                return false;
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        final Vector outwardNormal = Vector.sumOfVectors(rec.p, this.center(r.getTime()).scalarMultiplication(-1)).scalarDivision(this.radius);
        rec.setFaceNormal(r, outwardNormal);
        rec.mat = this.material;

        return true;
    }
}
