package com.henripc.ray;

public interface Hittable {
    public boolean hit(final Ray r, double tMin, double tMax, final HitRecord rec);
    public boolean boundingBox(final double time0, final double time1, final AABB outputBox);
}
