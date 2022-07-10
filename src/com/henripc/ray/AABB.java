package com.henripc.ray;

public class AABB {
    public Vector minimum;
    public Vector maximum;

    public AABB() {
        this.minimum = new Point3();
        this.maximum = new Point3();
    }
    public AABB(final Vector a, final Vector b) {
        this.minimum = a;
        this.maximum = b;
    }

    public Vector getMin() {
        return this.minimum;
    }

    public Vector getMax() {
        return this.maximum;
    }

    public boolean hit(final Ray r, double tMin, double tMax) {
        for (int a = 0; a < 3; a++) {
            final double invD = 1.0 / r.getDirection().e[a];
            double t0 = (this.getMin().e[a] - r.getOrigin().e[a]) * invD;                                    
            double t1 = (this.getMax().e[a] - r.getOrigin().e[a]) * invD;

            if (invD < 0) {
                double temp = t0;
                t0 = t1;
                t1 = temp;
            }
            
            tMin = t0 > tMin ? t0: tMin;
            tMax = t1 < tMax ? t1: tMax;

            if (tMax <= tMin) return false;
        }

        return true;
    }

    public static AABB surroundingBox(final AABB box0, final AABB box1) {
        final Point3 small = new Point3(
            Math.min(box0.getMin().x(), box1.getMin().x()),
            Math.min(box0.getMin().y(), box1.getMin().y()),
            Math.min(box0.getMin().z(), box1.getMin().z())
        );

        final Point3 big = new Point3(
            Math.max(box0.getMax().x(), box1.getMax().x()),
            Math.max(box0.getMax().y(), box1.getMax().y()),
            Math.max(box0.getMax().z(), box1.getMax().z())
        );

        return new AABB(small, big);
    }
}
