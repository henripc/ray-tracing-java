package com.henripc.ray;

public class Vec3 {
    public final double[] e;  // vector coordinates

    public Vec3() {
        this.e = new double[3];
    }

    public Vec3(final double e0, final double e1, final double e2) {
        this.e = new double[3];
        this.e[0] = e0;
        this.e[1] = e1;
        this.e[2] = e2;
    }

    public double x() {
        return this.e[0];
    }

    public double y() {
        return this.e[1];
    }

    public double z() {
        return this.e[2];
    }

    @Override
    public String toString() {
        return this.x() + " " + this.y() + " " + this.z();
    }

    public Vec3 scalarMultiplication(final double t) {
        this.e[0] *= t;
        this.e[1] *= t;
        this.e[2] *= t;

        return this;
    }

    public Vec3 scalarDivision(final double t) {
        return this.scalarMultiplication(1 / t);
    }

    public double lengthSquared() {
        return this.e[0] * this.e[0] + this.e[1] * this.e[1] + this.e[2] * this.e[2];
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public static Vec3 oppositeVector(final Vec3 vector) {
        return new Vec3(-vector.x(), -vector.y(), -vector.z());
    }

    public static Vec3 sumOfVectors(final Vec3... vectors) {
        final Vec3 resultVector = new Vec3();
        for (final Vec3 vector : vectors) {
            resultVector.e[0] += vector.x();
            resultVector.e[1] += vector.y();
            resultVector.e[2] += vector.z();
        }

        return resultVector;
    }

    public static double dot(final Vec3 u, final Vec3 v) {
        return u.e[0] * v.e[0] + u.e[1] * v.e[1] + u.e[2] * v.e[2];
    }

    public static Vec3 cross(final Vec3 u, final Vec3 v) {
        return new Vec3(u.e[1] * v.e[2] - u.e[2] * v.e[1],
                        u.e[2] * v.e[0] - u.e[0] * v.e[2],
                        u.e[0] * v.e[1] - u.e[1] * v.e[0]);
    }

    public static Vec3 unitVector(final Vec3 v) {
        return v.scalarDivision(v.length());
    }
}
// TODO: think about how to implement the Color class and the Point3 class.
