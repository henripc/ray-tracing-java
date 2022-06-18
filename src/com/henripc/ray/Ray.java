package com.henripc.ray;

public class Ray {
    private final Point3 orig;
    private final Vec3 dir;

    public Ray(final Point3 origin, final Vec3 direction) {
        this.orig = origin;
        this.dir = direction;
    }

    public Point3 getOrigin() {
        return this.orig;
    }

    public Vec3 getDirection() {
        return this.dir;
    }

    public Vector at(final double t) {
        return Vector.sumOfVectors(this.orig, this.dir.scalarMultiplication(t));
    }
}
