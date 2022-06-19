package com.henripc.ray;

public class HitRecord {
    public Vector p;
    public Vector normal;
    public double t;
    public boolean frontFace;

    public HitRecord() {

    }

    public void setFaceNormal(final Ray r, final Vector outwardNormal) {
        this.frontFace = Vector.dot(r.getDirection(), outwardNormal) < 0;
        this.normal = frontFace ? outwardNormal : outwardNormal.scalarMultiplication(-1);
    }
}
