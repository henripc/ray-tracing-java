package com.henripc.ray;

public class Ray {
    private final Vector orig;
    private final Vector dir;

    public Ray(final Vector origin, final Vector direction) {
        this.orig = origin;
        this.dir = direction;
    }

    public Vector getOrigin() {
        return this.orig;
    }

    public Vector getDirection() {
        return this.dir;
    }

    public Vector at(final double t) {
        return Vector.sumOfVectors(this.orig, this.dir.scalarMultiplication(t));
    }
}
