package com.henripc.ray;

public interface Material {
    public boolean scatter(final Ray rIn, final HitRecord rec, final Color attenuation, final Ray scattered);
}
