package com.henripc.ray;

public abstract class Vector {
    public double[] e; // vector coordinates

    protected Vector() {
        this.e = new double[3];
    }

    protected Vector(final double e0, final double e1, final double e2) {
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

    public Vector scalarMultiplication(final double t) {
        return new Vec3(this.e[0] * t, this.e[1] * t, this.e[2] * t);
    }

    public Vector scalarDivision(final double t) {
        return this.scalarMultiplication(1.0 / t);
    }

    public double lengthSquared() {
        return this.e[0] * this.e[0] + this.e[1] * this.e[1] + this.e[2] * this.e[2];
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public static Vector oppositeVector(final Vector vector) {
        return new Vec3(-vector.x(), -vector.y(), -vector.z());
    }

    public static Vector sumOfVectors(final Vector... vectors) {
        final Vector resultVector = new Vec3(0, 0, 0);
        for (final Vector vector : vectors) {
            resultVector.e[0] += vector.x();
            resultVector.e[1] += vector.y();
            resultVector.e[2] += vector.z();
        }

        return resultVector;
    }

    public static Vector multiplicationOfVectors(final Vector... vectors) {
        final Vector resultVector = new Vec3(1, 1, 1);
        for (final Vector vector : vectors) {
            resultVector.e[0] *= vector.x();
            resultVector.e[1] *= vector.y();
            resultVector.e[2] *= vector.z();
        }

        return resultVector;
    }

    public static double dot(final Vector u, final Vector v) {
        return u.e[0] * v.e[0] + u.e[1] * v.e[1] + u.e[2] * v.e[2];
    }

    public static Vector cross(final Vector u, final Vector v) {
        return new Vec3(u.e[1] * v.e[2] - u.e[2] * v.e[1],
                        u.e[2] * v.e[0] - u.e[0] * v.e[2],
                        u.e[0] * v.e[1] - u.e[1] * v.e[0]);
    }

    public static Vector unitVector(final Vector v) {
        return v.scalarDivision(v.length());
    }

    public static Vector random() {
        return new Vec3(RtWeekend.randomDouble(), RtWeekend.randomDouble(), RtWeekend.randomDouble());
    }

    public static Vector random(final double min, final double max) {
        return new Vec3(RtWeekend.randomDouble(min, max), RtWeekend.randomDouble(min, max), RtWeekend.randomDouble(min, max));
    }

    public static Vector randomInUnitSphere() {
        while (true) {
            final Vector p = Vector.random(-1, 1);
            if (p.lengthSquared() >= 1) continue;

            return p;
        }
    }

    public static Vector randomUniVector() {
        return unitVector(randomInUnitSphere());
    }

    public boolean nearZero() {
        // Return true if the vector is close to zero in all dimensions.
        final double s = 1E-8;
        return (Math.abs(this.e[0]) < s) && (Math.abs(this.e[1]) < s) && (Math.abs(this.e[2]) < s);
    }

    public static Vector reflect(final Vector v, final Vector n) {
        return Vector.sumOfVectors(v, n.scalarMultiplication(-2 * Vector.dot(v, n)));
    }

    public static Vector refract(final Vector uv, final Vector n, final double etaIOverEtaT) {
        final double cosTheta = Math.min(Vector.dot(uv.scalarMultiplication(-1), n), 1);
        final Vector rOutPerp = Vector.sumOfVectors(uv, n.scalarMultiplication(cosTheta)).scalarMultiplication(etaIOverEtaT);
        final Vector rOutParallel = n.scalarMultiplication(-Math.sqrt(Math.abs(1 - rOutPerp.lengthSquared())));

        return Vector.sumOfVectors(rOutPerp, rOutParallel);
    }

    public static Vector randomInUnitDisk() {
        while (true) {
            final Vector p = new Vec3(RtWeekend.randomDouble(-1, 1), RtWeekend.randomDouble(-1, 1), 0);
            if (p.lengthSquared() >= 1) continue;

            return p;
        }
    }
}
