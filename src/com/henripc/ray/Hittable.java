package com.henripc.ray;

public interface Hittable {
    public boolean hit(final Ray r, double tMin, double tMax, final HitRecord rec);
}
